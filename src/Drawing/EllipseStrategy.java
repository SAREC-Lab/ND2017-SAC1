package Drawing;

import Node.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class EllipseStrategy implements NodeDrawingStrategy {

	@Override
	public Shape drawNode(Node node) {
		Rectangle rect = new Rectangle(node.getCoordinates().getX(), node.getCoordinates().getY(), 100, 50);
		
		// Hard coded for now
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.WHITE);
		rect.setArcHeight(100);
		rect.setArcWidth(100);
		
		return rect;
	}

}
