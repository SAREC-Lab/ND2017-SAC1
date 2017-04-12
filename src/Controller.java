import java.awt.Point;

import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;

public class Controller{

	View view;
	NodeManager manager = new NodeManager();

	public Controller(View v) {
		this.view = v;
	}

	public void createNode(Point clickLocation) {
		NodeType type = view.getSelectedNodeType();
		if (type == null)
			return;

		Node newNode;

		Point nodeLocation = new Point((int) clickLocation.getX(), (int) clickLocation.getY());

		if (type == NodeType.GOAL || type == NodeType.STRATEGY || type == NodeType.SOLUTION) {
			newNode = new MainNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation);
		} else {
			newNode = new SupportingNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation);
		}
		view.drawNode(newNode);
		view.deselectToggledNode();

		manager.addNode(newNode);
	}

	public void removeNode(Node n) {
		manager.removeNode(n);
	}

	public boolean connectNodes(Node start, Node end) {
		if (!validateConnection(start, end)) {
			return false;
		} else {
			MainNode main_node = (MainNode) start;
			main_node.addChild(end);
			return true;
		}
	}

	private boolean validateConnection(Node start, Node end) {
		//TODO: more complicated validation
		if (start.getClass() != MainNode.class) {
			return false;
		}

		if (start.equals(end)) {
			return false;
		}

		return true;
	}
}