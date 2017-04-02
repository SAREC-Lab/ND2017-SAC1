import java.awt.Point;
import java.util.Observable;

import Drawing.CircleStrategy;
import Drawing.EllipseStrategy;
import Drawing.NodeDrawer;
import Drawing.ParallelogramStrategy;
import Drawing.RectangleStrategy;
import Node.Node;
import Node.NodeType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class View extends Observable {
	
	Scene scene;
	BorderPane root;
	TabPane tabPane;
	NodeDrawer nodeDrawer;
	Point clickLocation;
	final ToggleGroup toolGroup;
	ColorPicker outlinePicker, fillPicker;
		
	public View(Stage windowStage) {
		root = new BorderPane();
		
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
				new Separator());
		
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
		scene = new Scene(root, 1000, 563);	// Hard coded temporarily
		windowStage.setScene(scene);
		windowStage.setTitle("Safety Assurance Case Editor");
		windowStage.show();
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
                setChanged();
                notifyObservers();
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
		switch (node.getNodeType()) {
		case GOAL:
		case CONTEXT:
			nodeDrawer.setStrategy(new RectangleStrategy());
			break;
		case STRATEGY:
			nodeDrawer.setStrategy(new ParallelogramStrategy());
			break;
		case SOLUTION:
			nodeDrawer.setStrategy(new CircleStrategy());
			break;
		case JUSTIFICATION:
		case ASSUMPTION:
			nodeDrawer.setStrategy(new EllipseStrategy());
			break;
		}
		
		Shape drawnNode = nodeDrawer.drawNode(node, outlinePicker.getValue(), fillPicker.getValue());
		addEventHandlersToNode(drawnNode, node);
		((Pane)((ScrollPane)tabPane.getSelectionModel().getSelectedItem().getContent()).getContent()).getChildren().add(drawnNode);
	}
	
	// Add clicking and dragging event handlers to nodes
	private void addEventHandlersToNode(Shape shape, Node node) {
		final Point originalTranslation = new Point();
		
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
                
                // TODO: Update model with new coordinates
            }
        });
	}
}