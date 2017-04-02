package Drawing;

import Node.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface NodeDrawingStrategy {
	public Shape drawNode(Node node, Color outline, Color fill);
}
