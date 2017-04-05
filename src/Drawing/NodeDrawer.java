package Drawing;

import Node.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class NodeDrawer {
	NodeDrawingStrategy strategy;
	
	// Generates javafx shape to return to view
	public Pane drawNode(Node node, Color outline, Color fill) {
		return strategy.drawNode(node, outline, fill);
	}
	
	public void setStrategy(NodeDrawingStrategy strategy) {
		this.strategy = strategy;
	}
}
