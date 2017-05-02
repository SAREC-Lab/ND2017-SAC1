package MainPackage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Drawing.Arrow;
import Drawing.ConnectionDrawer;
import Drawing.NodeDrawer;
import Drawing.Sidebar;
import Drawing.NodePane;
import Node.Connection;
import Node.Node;
import Node.NodeType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class View {

	private Stage windowStage;
	private Scene scene;
	private BorderPane root;
	private TabPane tabPane;
	private NodeDrawer nodeDrawer;
	private ConnectionDrawer connectionDrawer;
	private Point clickLocation;
	private Controller controller;
	private Sidebar sidebar;

	public View(Stage windowStage) {
		
		this.windowStage = windowStage;
		root = new BorderPane();
		sidebar = new Sidebar();

		ToolBar leftBar = sidebar.createToolBox();

		Button newBtn = new Button("New");
		newBtn.setFocusTraversable(false);
		Button importBtn = new Button("Import");
		importBtn.setFocusTraversable(false);
		Button exportBtn = new Button("Export");
		exportBtn.setFocusTraversable(false);
		Button saveBtn = new Button("Save");
		saveBtn.setFocusTraversable(false);

		addFileManipulationHandlers(importBtn, exportBtn, saveBtn);

		ToolBar topBar = new ToolBar();
		topBar.prefWidthProperty().bind(windowStage.widthProperty());
		topBar.getItems().addAll( 
				newBtn,
				importBtn,
				exportBtn,
				saveBtn);

		initializeTabPane();

		root.setLeft(leftBar);
		root.setTop(topBar);

		clickLocation = new Point();
		nodeDrawer = new NodeDrawer();
		connectionDrawer = new ConnectionDrawer();
		scene = new Scene(root, 1100, 619);	// Hard coded temporarily
		windowStage.setScene(scene);
		windowStage.setTitle("Safety Assurance Case Editor");
		windowStage.show();
	}

	public void setController(Controller c) {
		controller = c;
	}
	
	// Add handlers to import and export buttons
	private void addFileManipulationHandlers(Button importBtn, Button exportBtn, Button saveBtn) {
		FileChooser fileChooser = new FileChooser();
		
		exportBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event){
				fileChooser.setTitle("Export File");
				File file = fileChooser.showSaveDialog(windowStage);
				try {
					if (file != null) {
						controller.traverse(false,file);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event){
				fileChooser.setTitle("Save File");
				File file = fileChooser.showSaveDialog(windowStage);
				try {
					if (file != null) {
						controller.traverse(true,file);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		
		importBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event){
				fileChooser.setTitle("Import File");
				File file = fileChooser.showOpenDialog(windowStage);
				try {
					if (file != null) {
						controller.load(file);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
	}

	private void initializeTabPane() {
		tabPane = new TabPane();
		final Tab addTab = new Tab("+");
		addTab.setClosable(false);
		tabPane.getTabs().add(addTab);
		createNewTab(tabPane);

		// Add listener so that when "+" tab clicked, new tab is created
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable,
					Tab oldTab, Tab newTab) {
				if (newTab == addTab) {
					createNewTab(tabPane);
				}
			}
		});

		root.setCenter(tabPane);
	}

	private void createNewTab(TabPane tabPane) {
		Tab newTab = new Tab("unnamed.sac");
		ScrollPane scrollPane = new ScrollPane();
		Pane workPane = new Pane();
		workPane.setMinSize(1500, 1500);
		scrollPane.setContent(workPane);

		// Handle clicks directly to pane by delegating to observing controller
		workPane.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent) {
				clickLocation.x = (int)mouseEvent.getX();
				clickLocation.y = (int)mouseEvent.getY();
				controller.createNode(getClickLocation());
			}
		});

		newTab.setContent(scrollPane);	// Scroll pane within each tab
		tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
		tabPane.getSelectionModel().select(newTab);
	}

	public Point getClickLocation() {
		return clickLocation;
	}

	// Return which node type is selected
	public NodeType getSelectedNodeType() {
		if (sidebar.toolGroup.getSelectedToggle() == null) {
			return null;
		} else if (sidebar.toolGroup.getSelectedToggle().getUserData() instanceof Boolean) {
			return null;
		} else {
			return (NodeType)sidebar.toolGroup.getSelectedToggle().getUserData();
		}
	}

	// Deselect toggled node
	public void deselectToggledNode() {
		if (sidebar.toolGroup.getSelectedToggle() != null) {
			sidebar.toolGroup.getSelectedToggle().setSelected(false);
		}
	}

	// Set nodedrawer strategy and draw node adding it to pane
	public void drawNode(Node node) {
		node.setPane(nodeDrawer.drawNode(node, sidebar.outlinePicker.getValue(), sidebar.fillPicker.getValue()));
		addEventHandlersToNode(node);
		((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().add(node.getPane());
		node.getPane().updateNodeProperties();

		// If the node ever moves, update connection properties to match
		node.getPane().localToParentTransformProperty().addListener(new ChangeListener<Transform>() {
			@Override 
			public void changed(ObservableValue<? extends Transform> ov, Transform ob, Transform nb) {
				node.getPane().updateNodeProperties();
			}
		});
	}

	// Add clicking and dragging event handlers to nodes
	private void addEventHandlersToNode(Node node) {
		final Point originalTranslation = new Point();
		NodePane shape = node.getPane();

		shape.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent) {

				shape.toFront();
				clickLocation.x = (int) mouseEvent.getSceneX();
				clickLocation.y = (int) mouseEvent.getSceneY();
				originalTranslation.x = (int) shape.getTranslateX();
				originalTranslation.y = (int) shape.getTranslateY();
			}
		});

		shape.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent) {

				// Handle making connection if in progress
				if (sidebar.makingConnection && sidebar.selectedNode != null) {
					boolean filled = (boolean) sidebar.toolGroup.getSelectedToggle().getUserData();
					controller.createConnection(sidebar.selectedNode, node, filled);
					sidebar.selectedNode = null;
					sidebar.makingConnection = false;
					deselectToggledNode();
				}
				
				// Show node details in sidebar when selected
				sidebar.selectedNode = node;
				sidebar.deleteBtn.setVisible(true);
				sidebar.title.setVisible(true);
				sidebar.description.setVisible(true);
				sidebar.title.setText(node.getName());
				sidebar.description.setText(node.getDescription());

				sidebar.title.setOnKeyReleased(new EventHandler<KeyEvent>()
				{
					@Override
					public void handle(KeyEvent event) {
						node.setName(sidebar.title.getText());
						((Text)((VBox) (shape.getChildren().get(1))).getChildren().get(0)).setText(sidebar.title.getText());
					}
				});
				sidebar.description.setOnKeyReleased(new EventHandler<KeyEvent>()
				{
					@Override
					public void handle(KeyEvent event) {
						Boolean delete = new Boolean(false);
						node.setDescription(sidebar.description.getText());
						((Text)((VBox) (shape.getChildren().get(1))).getChildren().get(1)).setText(sidebar.description.getText());
						if (event.getCode().equals(KeyCode.BACK_SPACE)) {
							delete = true;
						}
						nodeDrawer.resize(node, shape, delete);
					}
				});
				sidebar.deleteBtn.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event){
						ArrayList<Group> arrows = controller.getConnectionArrows(node);
						for (Group arrow: arrows) {
							((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().remove(arrow);
						}
						controller.removeNode(node);
						((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().remove(shape);
						sidebar.deleteBtn.setVisible(false);
						sidebar.title.setVisible(false);
						sidebar.description.setVisible(false);
					}

				});
			}
		});

		shape.setOnMouseDragged(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent) {

				double offsetX = mouseEvent.getSceneX() - clickLocation.getX();
				double offsetY = mouseEvent.getSceneY() - clickLocation.getY();
				double newTranslateX = originalTranslation.getX() + offsetX;
				double newTranslateY = originalTranslation.getY() + offsetY;

				shape.setTranslateX(newTranslateX);
				shape.setTranslateY(newTranslateY);

				// Set new coordinates
				Bounds bounds = shape.getBoundsInLocal();
				Bounds screenBounds = shape.localToParent(bounds);
				node.getCoordinates().setLocation((int) screenBounds.getMinX(), (int) screenBounds.getMinY());
			}
		});
	}

	// Draw connection between two nodes (called in event handlers)
	public void drawConnection(Connection connection) {
		Arrow arrow = connectionDrawer.drawConnection(connection.getStart(), connection.getEnd(), connection.isFilled());
		connection.setArrow(arrow.getArrow());

		((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().add(arrow.getArrow());
		addEventHandlersToConnection(connection, arrow);

		connection.getStart().getPane().updateNodeProperties();
		connection.getEnd().getPane().updateNodeProperties();
		arrow.updateArrowheadLocation();
	}

	// Add handlers to connection that allow it to update according to its nodes and also be deleted
	private void addEventHandlersToConnection(Connection connection, Arrow arrowObject) {
		Group arrow = connection.getArrow();

		arrow.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent) {
				sidebar.deleteBtn.setVisible(true);
				sidebar.title.setVisible(false);
				sidebar.description.setVisible(false);

				sidebar.deleteBtn.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event){
						//remove from view
						((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().remove(connection.getArrow());
						controller.removeConnection(connection);
						sidebar.deleteBtn.setVisible(false);
					}

				});
			}
		});
		
		// Handles height changes in nodes, essential to connections importing correctly
		connection.getStart().getPane().heightProperty().addListener(new ChangeListener<Number>() {
			@Override 
			public void changed(ObservableValue<? extends Number> ov, Number ob, Number nb) {
				connection.getStart().getPane().updateNodeProperties();
				connectionDrawer.optimizeConnectionPlacement(arrowObject, connection.getStart(), connection.getEnd());
				arrowObject.updateArrowheadLocation();
			}
		});
		connection.getEnd().getPane().heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number ob, Number nb) {
				connection.getEnd().getPane().updateNodeProperties();
				connectionDrawer.optimizeConnectionPlacement(arrowObject, connection.getStart(), connection.getEnd());
				arrowObject.updateArrowheadLocation();
			}
		});

		// If either node ever moves, update arrowhead and connection to match
		connection.getStart().getPane().localToParentTransformProperty().addListener(new ChangeListener<Transform>() {
			@Override 
			public void changed(ObservableValue<? extends Transform> ov, Transform ob, Transform nb) {
				connectionDrawer.optimizeConnectionPlacement(arrowObject, connection.getStart(), connection.getEnd());
				arrowObject.updateArrowheadLocation();
			}
		});
		connection.getEnd().getPane().localToParentTransformProperty().addListener(new ChangeListener<Transform>() {
			@Override 
			public void changed(ObservableValue<? extends Transform> ov, Transform ob, Transform nb) {
				connectionDrawer.optimizeConnectionPlacement(arrowObject, connection.getStart(), connection.getEnd());
				arrowObject.updateArrowheadLocation();
			}
		});
	}

	public void clearView() {
		((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().clear();
	}
	
	public void alert(String message) {
		Alert a = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		a.showAndWait();
	}
}