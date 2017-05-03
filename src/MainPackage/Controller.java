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
	
	//model
	NodeManager manager = new NodeManager();
	
	//use on creation of node as unique id
	private int node_id = 0;

	public Controller(View v) {
		this.view = v;
	}

	//Create a node
	public void createNode(Point clickLocation) {
		//get the node type
		NodeType type = view.getSelectedNodeType();
		if (type == null) {
			return;
		}
		
		node_id++;

		Node newNode;

		//create node location based on where the user clicked
		Point nodeLocation = new Point((int) clickLocation.getX(), (int) clickLocation.getY());

		//create a MainNOde or SupportingNode based on the node type
		if (type == NodeType.GOAL || type == NodeType.STRATEGY || type == NodeType.SOLUTION) {
			newNode = new MainNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation,node_id);
		} else {
			newNode = new SupportingNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation,node_id);
		}
		
		//instructo view to draw node
		view.drawNode(newNode);
		view.deselectToggledNode();

		//add node to model
		manager.addNode(newNode);
	}
	
	//remove node from the model
	public void removeNode(Node n) {
		manager.removeNode(n);
	}

	//save the model in a JSON format
	//TODO: distinguish between exporting and saving
	//boolean b can be used to distinguish between the two
	public void saveToFile(boolean b, File file) throws IOException {
		manager.saveToFile(b,file);
	}
	
	//return true if the connection from start to end if valid
	//and false if it is not
	private boolean validateConnection(Node start, Node end, boolean filled) {
		//node cannot point to itself
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

		//check if the nodes are already connected
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

	//get all of the arrows that point to or from the node
	public ArrayList<Group> getConnectionArrows(Node node) {
		ArrayList<Group> arrows = new ArrayList<Group>();
		
		for (Connection connection : manager.getNodeConnections(node)) {
			arrows.add(connection.getArrow());
		}
		
		return arrows;
	}

	//create a connection between two nodes
	//filled refers to the arrowhead -- 0 for a support connection and 1 for contextual
	public void createConnection(Node start, Node end, boolean filled) {
		
		//first validate the connection
		if (validateConnection(start, end, filled)) {
			
			//if connection is valid, create one
			Connection connection = new Connection(start, end, filled);
			
			//add connection to manager
			manager.addConnection(connection);
			
			//draw connection in the view
			view.drawConnection(connection);
		}
	}

	//remove a connection between two nodes from the model
	public void removeConnection(Connection connection) {
		manager.removeConnection(connection);	
	}

	//load a graph from a JSON file
	public void load(File file) throws IOException {
		
		//load nodes and connections into the model
		manager.load(file);
		
		//clear the view
		view.clearView();
		
		//draw nodes and connections
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
