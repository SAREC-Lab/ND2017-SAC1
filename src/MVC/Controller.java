package MVC;
import java.awt.Point;
import java.util.ArrayList;

import Node.Connection;
import Node.ConnectionType;
import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;
import javafx.scene.Group;

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

	private boolean validateConnection(Node start, Node end, ConnectionType ct) {
		if (start == end) {
			view.alert("A node cannot be connected to itself.");
			return false;
		}

		//start node must be of MainNode class
		if (start.getClass() != MainNode.class) {
			view.alert("A parent node must be GOAL, STRATEGY, or SOLUTION");
			return false;
		}

		//filled arrow must point to a MainNode
		if (end.getClass() == MainNode.class && ct != ConnectionType.CONTEXTUAL) {
			view.alert("This relationship is contextual and must used a filled-in arrow.");
			return false;
		}

		//unfilled arrow must point to SupportingNode
		if (end.getClass() == SupportingNode.class && ct != ConnectionType.SUPPORTING) {
			view.alert("This relationship is supporting and must used an empty arrow.");
			return false;
		}

		//check if they are already connected
		if (end.isParentOf(start)) {
			view.alert("The selected child node cannot already be a parent of the other node.");
			return false;
		}
		
		if (start.isParentOf(end)) {
			view.alert("The selected parent node cannot already be a parent of the child node.");
			return false;
		}

		return true;
	}

	public ArrayList<Group> getConnectionArrows(Node node) {
		ArrayList<Group> arrows = new ArrayList<Group>();
		
		for (Connection connection : manager.getNodeConnections(node)) {
			arrows.add(connection.getArrow());
		}
		
		return arrows;
	}

	public void createConnection(Node start, Node end, boolean filled) {
		//determine ConnectionType using filled
		ConnectionType ct;
		
		if (filled) {
			ct = ConnectionType.CONTEXTUAL;
		} else {
			ct = ConnectionType.SUPPORTING;
		}
		
		if (validateConnection(start, end, ct)) {
			Connection connection = new Connection(start, end, ct);
			manager.addConnection(connection);
			view.drawConnection(connection);
		}
	}

	public void removeConnection(Connection connection) {
		manager.removeConnection(connection);	
	}
}