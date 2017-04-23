package MainPackage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage windowStage) throws Exception {
		View view = new View(windowStage);
		Controller controller = new Controller(view);
		view.setController(controller);
	}

}
