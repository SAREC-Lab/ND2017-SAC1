package Drawing;

import Node.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleStrategy implements NodeDrawingStrategy {

	@Override
	public Shape drawNode(Node node) {
		Circle circle = new Circle(node.getCoordinates().getX(), node.getCoordinates().getY(), 30);
		
		// Hard coded for now
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.WHITE);

		return circle;	
	}

}
