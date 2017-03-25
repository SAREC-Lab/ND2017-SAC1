import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;
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
		
		root.setLeft(leftBar);
		root.setTop(topBar);
		
		
		scene = new Scene(root, 1000, 563);	// Hard coded temporarily
		windowStage.setScene(scene);
		windowStage.setTitle("Safety Assurance Case Editor");
		windowStage.show();
	}
}