package Drawing;

import Node.Node;
import javafx.scene.shape.Shape;

public class NodeDrawer {
	NodeDrawingStrategy strategy;
	
	// Generates javafx shape to return to view
	public Shape drawNode(Node node) {
		return strategy.drawNode(node);
	}
	
	public void setStrategy(NodeDrawingStrategy strategy) {
		this.strategy = strategy;
	}
}
