package Drawing;

import Node.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public interface NodeDrawingStrategy {
	public Pane drawNode(Node node, Color outline, Color fill);
	public Pane redraw(Node node, Color outline, Color fill);
}
