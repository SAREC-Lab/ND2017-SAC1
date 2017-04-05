package Drawing;

import Node.Node;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class CircleStrategy implements NodeDrawingStrategy {

	@Override
	public Pane drawNode(Node node, Color outline, Color fill) {
		Circle circle = new Circle(0,0,40);
		// Hard coded for now
		circle.setStroke(outline);
		circle.setFill(fill);
		
		Text text = new Text(node.getName());
		text.setFont(new Font(10));
		//text.setBoundsType(TextBoundsType.VISUAL);
		
		Text description = new Text("description");
		description.setFont(new Font(8));
		//description.setBoundsType(TextBoundsType.VISUAL);
		description.setFill(Color.GRAY);
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(text, description);
		vBox.setAlignment(Pos.CENTER);
		
		StackPane stack = new StackPane();
		stack.getChildren().addAll(circle, vBox);
		stack.setTranslateX(node.getCoordinates().getX());
		stack.setTranslateY(node.getCoordinates().getY());
		
		

		return stack;	
	}

}
