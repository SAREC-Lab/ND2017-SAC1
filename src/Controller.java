import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;


public class Controller extends Application {

	Scene scene;
	BorderPane root;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage windowStage) throws Exception {
		root = new BorderPane();
		
		Text title1 = new Text("ToolBox");
		Text title2 = new Text("Style");
		
		Rectangle r1 = new Rectangle(15,10);
		r1.setStroke(Color.BLACK);
		r1.setFill(null);
		Button goalBtn = new Button("Goal");
		goalBtn.setMaxWidth(200);
		goalBtn.setGraphic(r1);
		
		
		Button contextBtn = new Button("Context");
		contextBtn.setMaxWidth(200);
		Rectangle r2 = new Rectangle(20,10);
		r2.setStroke(Color.BLACK);
		r2.setArcHeight(15);
		r2.setArcWidth(15);
		r2.setFill(null);
		contextBtn.setGraphic(r2);
		
		Button strategyBtn = new Button("Strategy");
		strategyBtn.setMaxWidth(200);
		Polygon parallelogram = new Polygon();
		parallelogram.setFill(null);
		parallelogram.getPoints().addAll(30.0, 0.0,
                130.0, 0.0, 
                120.00, 50.0, 
                0.0, 50.0);
		strategyBtn.setGraphic(parallelogram);
		
		Button solutionBtn = new Button("Solution");
		solutionBtn.setMaxWidth(200);
		Button assumBtn = new Button("Assumption");
		assumBtn.setMaxWidth(200);
		Button justBtn = new Button("Justification");
		justBtn.setMaxWidth(200);
		Button cRelationBtn = new Button("Contextual Relationship");
		cRelationBtn.setMaxWidth(200);
		Button sRelationBtn = new Button("Support Relationship");

		sRelationBtn.setMaxWidth(200);
		Button fillBtn = new Button("Fill");
		fillBtn.setMaxWidth(200);
		Button outlineBtn = new Button("Outline");
		outlineBtn.setMaxWidth(200);
		
		// color picker 
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setValue(Color.WHITE);

		
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
				fillBtn,
				outlineBtn,
				colorPicker,
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
		
		scene = new Scene(root, 1000, 563);	// Hard coded temporarily
		windowStage.setScene(scene);
		windowStage.setTitle("Safety Assurance Case Editor");
		windowStage.show();
	}
	
	private void initializeTabPane() {
		 final TabPane tabPane = new TabPane();
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
		newTab.setContent(new ScrollPane());	// Scroll pane within each tab
	    tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
	    tabPane.getSelectionModel().select(newTab);
	}

}