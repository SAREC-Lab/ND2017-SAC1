package Drawing;

import Node.Node;
import javafx.scene.shape.Shape;

public interface NodeDrawingStrategy {
	public Shape drawNode(Node node);
}
