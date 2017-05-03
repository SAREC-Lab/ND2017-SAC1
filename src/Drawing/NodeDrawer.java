package Drawing;

import Node.Node;
import javafx.scene.paint.Color;
import Node.NodeType;

public class NodeDrawer {
	NodeDrawingStrategy strategy;
	
	// Generates javafx shape to return to view
	public NodePane drawNode(Node node, Color outline, Color fill) {
		node.setFill(fill);
		node.setOutline(outline);
		setStrategy(node.getNodeType());
		return strategy.drawNode(node, outline, fill);
	}
	
	public void resize(Node node, boolean delete){
		setStrategy(node.getNodeType());
		strategy.resize(node, delete);
	}
	
	public void setStrategy(NodeType type) {
		switch (type) {
		case GOAL:
		case CONTEXT:
			strategy = new RectangleStrategy();
			break;
		case STRATEGY:
			strategy = new ParallelogramStrategy();
			break;
		case SOLUTION:
			strategy = new CircleStrategy();
			break;
		case JUSTIFICATION:
		case ASSUMPTION:
			strategy = new EllipseStrategy();
			break;
		}
	}
}
