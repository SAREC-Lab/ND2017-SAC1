package Drawing;

import Node.Node;
import javafx.scene.paint.Color;

public interface NodeDrawingStrategy {
	public NodePane drawNode(Node node, Color outline, Color fill);
	public void resize(Node node, boolean delete);
}
