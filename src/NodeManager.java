import SAC.SAC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Node.Node;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import Node.Connection;
import Node.MainNode;

public class NodeManager {
	private SAC sac = new SAC();
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	
	public NodeManager() {
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void removeNode(Node n) {
		Iterator<Connection> connection_it = connections.iterator();
		while(connection_it.hasNext()) {
			Connection connection = connection_it.next();
			if (connection.getStart() == n || connection.getEnd() == n) {
				connection_it.remove();
			}
		}
		
		Iterator<MainNode> parent_it = n.getParents().iterator();
		while (parent_it.hasNext()) {
			Node parent = parent_it.next();
			if (parent.getClass() == MainNode.class) {
				((MainNode) parent).getChildren().remove(n);
			}
		}
		
		if (n.getClass() == MainNode.class) {
			MainNode main_node = (MainNode) n;
			Iterator<Node> child_it = main_node.getChildren().iterator();
			while (child_it.hasNext()) {
				Node child = child_it.next();
				child.getParents().remove(main_node);
			}
		}
		
		nodes.remove(n);
	}
	
	public void setSACRootNode(Node n) {
		sac.setRootNode(n);
	}

	public void traverse(boolean b, File file) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Node.class, Mixin.class);
		mapper.addMixInAnnotations(Node.class, Mixin.class);
		mapper.writeValue(file, nodes);
	}
	
	public void addConnection(Connection r) {
		connections.add(r);
	}
	
	public ArrayList<Connection> getNodeConnections(Node n) {
		ArrayList<Connection> conns = new ArrayList<Connection>();
		for (Connection c : connections) {
			if (c.getStart() == n || c.getEnd() == n) {
				conns.add(c);
			}
		}
		return conns;
	}
	
	public void printNodes() {
		System.out.println("TOTAL NODES: " + nodes.size());
		System.out.println("TOTAL CONNECTIONS:" + connections.size());
		for (Node n : nodes) {
			System.out.println(n.getName() + ": " + n.getDescription());
			System.out.println(n.getParents().size() + " parents");
			if (n.getClass() == MainNode.class) {
				System.out.println(((MainNode)n).getChildren().size() + " children");
			}
		}
	}

	public void removeConnection(Connection connection) {
		connections.remove(connection);
		if (connection.getStart().getClass() == MainNode.class) {
			MainNode main_node = (MainNode) connection.getStart();
			connection.getEnd().removeParent(main_node);
			main_node.removeChild(connection.getEnd());
		}
	}
}