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
	private int node_id;

	public Controller(View v) {
		node_id = 0;
		this.view = v;
	}

	public void createNode(Point clickLocation) {
		node_id++;
		NodeType type = view.getSelectedNodeType();
		if (type == null)
			return;

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
	
	public void addRoot(Node n) {
		manager.setSACRootNode(n);
	}

	public void removeNode(Node n) {
		manager.removeNode(n);
	}

	public void traverse(boolean b, File file) throws IOException {
		manager.traverse(b,file);
	}
	
	private boolean validateConnection(Node start, Node end) {
		//TODO: more complicated validation
		if (start.getClass() != MainNode.class) {
			return false;
		}

		if (start == end) {
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

	public void createConnection(Node start, Node end) {
		if (validateConnection(start, end)) {
			MainNode main_node = (MainNode) start;
			main_node.addChild(end);
			end.addParent(main_node);
			Connection connection = new Connection(start, end);
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