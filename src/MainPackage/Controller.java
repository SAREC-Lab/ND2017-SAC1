package MainPackage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Node.Connection;
import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;
import javafx.scene.Group;

public class Controller{

	View view;
	NodeManager manager = new NodeManager();
	private int node_id = 0;

	public Controller(View v) {
		this.view = v;
	}

	public void createNode(Point clickLocation) {
		NodeType type = view.getSelectedNodeType();
		if (type == null) {
			return;
		}
		
		node_id++;

		Node newNode;

		Point nodeLocation = new Point((int) clickLocation.getX(), (int) clickLocation.getY());

		if (type == NodeType.GOAL || type == NodeType.STRATEGY || type == NodeType.SOLUTION) {
			newNode = new MainNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation,node_id);
		} else {
			newNode = new SupportingNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation,node_id);
		}
		view.drawNode(newNode);
		view.deselectToggledNode();

		manager.addNode(newNode);
	}
	
	public void removeNode(Node n) {
		manager.removeNode(n);
	}

	public void traverse(boolean b, File file) throws IOException {
		manager.traverse(b,file);
	}
	
	private boolean validateConnection(Node start, Node end, boolean filled) {
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
		if (end.getClass() == MainNode.class && !filled) {
			view.alert("This relationship is contextual and must used a filled-in arrow.");
			return false;
		}

		//unfilled arrow must point to SupportingNode
		if (end.getClass() == SupportingNode.class && filled) {
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
		if (validateConnection(start, end, filled)) {
			Connection connection = new Connection(start, end, filled);
			manager.addConnection(connection);
			view.drawConnection(connection);
		}
	}

	public void removeConnection(Connection connection) {
		manager.removeConnection(connection);	
	}

	public void load(File file) throws IOException {
		manager.load(file);
		view.clearView();
		ArrayList<Connection> connections = manager.getConnections();
		ArrayList<Node> nodes = manager.getNodes();
		for (Node n: nodes) {
			view.drawNode(n);
		}
		for (Connection c: connections) {
			view.drawConnection(c);
		}
		
	}
}