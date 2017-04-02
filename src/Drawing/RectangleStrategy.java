package Drawing;

import Node.Node;
import Node.NodeType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleStrategy implements NodeDrawingStrategy {

	@Override
	public Shape drawNode(Node node, Color outline, Color fill) {
		Rectangle rect = new Rectangle(node.getCoordinates().getX(), node.getCoordinates().getY(), 100, 50);
		
		// Hard coded for now
		rect.setStroke(outline);
		rect.setFill(fill);
		
		if (node.getNodeType() == NodeType.CONTEXT) {
			rect.setArcHeight(50);
			rect.setArcWidth(50);
		}
		return rect;
	}

}
