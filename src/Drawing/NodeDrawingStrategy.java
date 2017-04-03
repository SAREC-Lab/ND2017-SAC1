package Drawing;

import Node.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface NodeDrawingStrategy {
	public Pane drawNode(Node node, Color outline, Color fill);
}
