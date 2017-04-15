import java.awt.Point;
import java.util.ArrayList;

import Drawing.ConnectionDrawer;
import Drawing.NodeDrawer;
import Drawing.NodePane;
import Node.Connection;
import Node.Node;
import Node.NodeType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class View {

	private Scene scene;
	private BorderPane root;
	private TabPane tabPane;
	private NodeDrawer nodeDrawer;
	private ConnectionDrawer connectionDrawer;
	private Point clickLocation;
	private ToggleGroup toolGroup;
	private ColorPicker outlinePicker, fillPicker;
	private Button deleteBtn;
	private TextArea description;
	private TextField title;
	private Controller controller;
	private boolean makingConnection;
	private Node selectedNode;

	public View(Stage windowStage) {
		root = new BorderPane();

		ToolBar leftBar = new ToolBar();
		leftBar = createToolBox();

		Button newBtn = new Button("New");
		Button importBtn = new Button("Import");
		Button exportBtn = new Button("Export");
		Button saveBtn = new Button("Save");

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
		makingConnection = false;
		scene = new Scene(root, 1100, 619);	// Hard coded temporarily
		windowStage.setScene(scene);
		windowStage.setTitle("Safety Assurance Case Editor");
		windowStage.show();
	}

	public void setController(Controller c) {
		controller = c;
	}

	private ToolBar createToolBox() {
		Text title1 = new Text("ToolBox");
		Text title2 = new Text("Style");

		// Group for tools so only one can be toggled at a time
		toolGroup = new ToggleGroup();

		//Goal Button
		ToggleButton goalBtn = new ToggleButton("Goal");
		goalBtn.setMaxWidth(200);
		Rectangle r1 = new Rectangle(15,10);
		r1.setStroke(Color.BLACK);
		r1.setFill(null);
		goalBtn.setGraphic(r1);
		goalBtn.setToggleGroup(toolGroup);
		goalBtn.setUserData(NodeType.GOAL);

		//Context Button
		ToggleButton contextBtn = new ToggleButton("Context");
		contextBtn.setMaxWidth(200);
		Rectangle r2 = new Rectangle(20,10);
		r2.setStroke(Color.BLACK);
		r2.setArcHeight(10);
		r2.setArcWidth(10);
		r2.setFill(null);
		contextBtn.setGraphic(r2);
		contextBtn.setToggleGroup(toolGroup);
		contextBtn.setUserData(NodeType.CONTEXT);

		//Strategy Button
		ToggleButton strategyBtn = new ToggleButton("Strategy");
		strategyBtn.setMaxWidth(200);
		Polygon parallelogram = new Polygon();
		parallelogram.setFill(null);
		parallelogram.getPoints().addAll(5.0, 0.0,
				20.0, 0.0, 
				15.0, 9.0, 
				0.0, 9.0);
		parallelogram.setStrokeType(StrokeType.OUTSIDE);
		parallelogram.setStroke(Color.BLACK);
		strategyBtn.setGraphic(parallelogram);
		strategyBtn.setToggleGroup(toolGroup);
		strategyBtn.setUserData(NodeType.STRATEGY);

		//Solution Button
		ToggleButton solutionBtn = new ToggleButton("Solution");
		solutionBtn.setMaxWidth(200);
		Circle c = new Circle();
		c.setRadius(6);
		c.setFill(null);
		c.setStrokeType(StrokeType.OUTSIDE);
		c.setStroke(Color.BLACK);
		c.setCenterX(50.0);
		c.setCenterY(10.0);
		solutionBtn.setGraphic(c);
		solutionBtn.setToggleGroup(toolGroup);
		solutionBtn.setUserData(NodeType.SOLUTION);

		//Assumption Button
		ToggleButton assumBtn = new ToggleButton("Assumption");
		assumBtn.setMaxWidth(200);
		assumBtn.setToggleGroup(toolGroup);
		Rectangle r3 = new Rectangle(20,10);
		r3.setStroke(Color.BLACK);
		r3.setArcHeight(20);
		r3.setArcWidth(20);
		r3.setFill(null);
		assumBtn.setGraphic(r3);
		assumBtn.setUserData(NodeType.ASSUMPTION);

		//Justification Button
		ToggleButton justBtn = new ToggleButton("Justification");
		justBtn.setMaxWidth(200);
		justBtn.setToggleGroup(toolGroup);
		Rectangle r4 = new Rectangle(20,10);
		r4.setStroke(Color.BLACK);
		r4.setArcHeight(20);
		r4.setArcWidth(20);
		r4.setFill(null);
		justBtn.setGraphic(r4);
		justBtn.setUserData(NodeType.JUSTIFICATION);


		//Contextual Relationship Button
		HBox hBox = new HBox();
		Line line = new Line(0, 0,
				7,   0);
		line.setStrokeWidth(1.5);
		hBox.setAlignment(Pos.CENTER);
		Polygon arrowHead = new Polygon();
		arrowHead.getPoints().addAll(0.0, 0.0,
				0.0, -10.0,
				10.0, -5.0);
		hBox.getChildren().addAll(line, arrowHead);
		ToggleButton cRelationBtn = new ToggleButton("Contextual Relationship");
		cRelationBtn.setMaxWidth(200);
		cRelationBtn.setToggleGroup(toolGroup);
		cRelationBtn.setGraphic(hBox);
		cRelationBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event){
				makingConnection = cRelationBtn.isSelected();
				selectedNode = null;
			}
		});


		//Support Relationship Button
		HBox hBox2 = new HBox();
		Line line2 = new Line(0, 0,
				7,   0);
		line2.setStrokeWidth(1.5);
		hBox2.setAlignment(Pos.CENTER);
		Polygon arrowHead2 = new Polygon();
		arrowHead2.setFill(null);
		arrowHead2.setStroke(Color.BLACK);
		arrowHead2.setStrokeWidth(1.5);
		arrowHead2.getPoints().addAll(0.0, 0.0,
				0.0, -8.6,
				8.6, -4.3);
		hBox2.getChildren().addAll(line2, arrowHead2);
		ToggleButton sRelationBtn = new ToggleButton("Support Relationship");
		sRelationBtn.setMaxWidth(200);
		sRelationBtn.setToggleGroup(toolGroup);
		sRelationBtn.setGraphic(hBox2);
		sRelationBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event){
				makingConnection = sRelationBtn.isSelected();
				selectedNode = null;
			}
		});


		//Fill Button
		Text fillTitle = new Text("Fill");
		fillPicker = new ColorPicker();
		fillPicker.setValue(Color.WHITE);

		//Outline Button
		Text outlineTitle = new Text("Outline");
		outlinePicker = new ColorPicker();
		outlinePicker.setValue(Color.BLACK);		


		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(3);
		grid.add(fillTitle,0,0);
		grid.add(fillPicker, 1, 0);
		grid.add(outlineTitle, 0, 1);
		grid.add(outlinePicker,1,1);

		//editable text boxers
		title = new TextField();
		title.setPromptText("Title");
		title.setPrefColumnCount(10);


		description = new TextArea();
		description.setPromptText("Description");
		description.setPrefColumnCount(10);
		description.setPrefRowCount(4);
		description.setWrapText(true);

		deleteBtn = new Button("Delete");
		deleteBtn.setMaxWidth(200);

		deleteBtn.setVisible(false);
		title.setVisible(false);
		description.setVisible(false);

		//add items to toolbar
		ToolBar leftBar = new ToolBar();
		leftBar.setOrientation(Orientation.VERTICAL);
		leftBar.getItems().addAll( title1,
				new Separator(), 
				goalBtn,
				contextBtn,
				strategyBtn,
				solutionBtn,
				assumBtn,
				justBtn,
				cRelationBtn,
				sRelationBtn,
				new Separator(),
				title2,
				new Separator(),
				grid,
				new Separator(),
				title,
				description,
				deleteBtn);
		return leftBar;

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
		workPane.setMinSize(1500, 1500);	// TODO: Make pane expand as dragging of nodes occurs
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
		if (toolGroup.getSelectedToggle() == null) {
			return null;
		} else {
			return (NodeType)toolGroup.getSelectedToggle().getUserData();
		}
	}

	// Deselect toggled node
	public void deselectToggledNode() {
		toolGroup.getSelectedToggle().setSelected(false);
	}

	// Set nodedrawer strategy and draw node adding it to pane
	public void drawNode(Node node) {
		node.setPane(nodeDrawer.drawNode(node, outlinePicker.getValue(), fillPicker.getValue()));
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
				if (makingConnection && selectedNode != null) {
					controller.createConnection(selectedNode, node);
				}

				selectedNode = node;
				deleteBtn.setVisible(true);
				title.setVisible(true);
				description.setVisible(true);
				title.setText(node.getName());
				description.setText(node.getDescription());

				title.setOnKeyReleased(new EventHandler<KeyEvent>()
				{
					@Override
					public void handle(KeyEvent event) {
						node.setName(title.getText());
						((Text)((VBox) (shape.getChildren().get(1))).getChildren().get(0)).setText(title.getText());
						nodeDrawer.redraw(node);
					}
				});
				description.setOnKeyReleased(new EventHandler<KeyEvent>()
				{
					@Override
					public void handle(KeyEvent event) {
						node.setDescription(description.getText());
						((Text)((VBox) (shape.getChildren().get(1))).getChildren().get(1)).setText(description.getText());
						nodeDrawer.redraw(node);
					}
				});
				deleteBtn.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event){
						ArrayList<Line> lines = controller.getConnectionLines(node);
						for (Line line: lines) {
							((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().remove(line);
						}
						controller.removeNode(node);
						((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().remove(shape);
						deleteBtn.setVisible(false);
						title.setVisible(false);
						description.setVisible(false);
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
		Line line = connectionDrawer.drawConnection(connection.getStart(), connection.getEnd());
		connection.setLine(line);
		((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().add(line);
		addEventHandlersToConnection(connection);
		connection.getStart().getPane().updateNodeProperties();
		connection.getEnd().getPane().updateNodeProperties();
		selectedNode = null;
		makingConnection = false;
		deselectToggledNode();
	}

	private void addEventHandlersToConnection(Connection connection) {
		
	}
}