package Drawing;

import Node.Node;
import Node.NodeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class Sidebar{
	
	public ToggleGroup toolGroup;
	public boolean makingConnection;
	public Node selectedNode;
	public ColorPicker outlinePicker, fillPicker;
	public TextArea description;
	public TextField title;
	public Button deleteBtn;
	public Button addRootBtn;
	
	public Sidebar(){
		createToolBox();
	}
	
	public ToolBar createToolBox() {

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
		cRelationBtn.setUserData(true);
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
		sRelationBtn.setUserData(false);
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

		addRootBtn = new Button("Add Root");
		addRootBtn.setMaxWidth(200);

		deleteBtn.setVisible(false);
		title.setVisible(false);
		description.setVisible(false);
		addRootBtn.setVisible(false);


		//add items to toolbar
		ToolBar toolbar = new ToolBar();
		toolbar.setOrientation(Orientation.VERTICAL);
		toolbar.getItems().addAll( title1,
				new Separator(), 
				goalBtn,
				contextBtn,
				solutionBtn,
				strategyBtn,
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
				deleteBtn,
				addRootBtn);

		return toolbar;

	}
}