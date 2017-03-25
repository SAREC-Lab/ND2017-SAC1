import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.control.*;


public class Controller extends Application {

	Scene scene;
	BorderPane root;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage windowStage) throws Exception {
		root = new BorderPane();
		
		Button goalBtn = new Button("Goal");
		Button contextBtn = new Button("Context");
		Button strategyBtn = new Button("Strategy");
		Button solutionBtn = new Button("Solution");
		Button assumBtn = new Button("Assumption");
		Button justBtn = new Button("Justification");
		Button cRelationBtn = new Button("Contextual Relationship");
		Button sRelationBtn = new Button("Support Relationship");
		// color picker 
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setValue(Color.WHITE);
		
		ToolBar leftBar = new ToolBar();
		leftBar.setOrientation(Orientation.VERTICAL);
		leftBar.getItems().addAll(new Separator(), 
				goalBtn,
				contextBtn,
				strategyBtn,
				solutionBtn,
				assumBtn,
				justBtn,
				cRelationBtn,
				sRelationBtn,
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