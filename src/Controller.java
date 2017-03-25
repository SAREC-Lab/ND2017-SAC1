import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller extends Application {

	Scene scene;
	Pane root;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage windowStage) throws Exception {
		root = new AnchorPane();
		
		scene = new Scene(root, 1000, 563);	// Hard coded temporarily
		windowStage.setScene(scene);
		windowStage.setTitle("Safety Assurance Case Editor");
		windowStage.show();
	}
}