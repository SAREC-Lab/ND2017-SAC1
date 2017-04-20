package MainPackage;
import SAC.SAC;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Node.Node;
import Node.NodeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.util.Iterator;
import Node.Connection;
import Node.MainNode;

public class NodeManager {
	private SAC sac = new SAC();
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private Gson gson;
	
	public NodeManager() {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Node.class, new NodeDeserializer<Node>());
	    gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
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
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void traverse(boolean b, File file) throws IOException {
		// convert connections list to JSON string
		String str = gson.toJson(connections);
		
		// write string to file
		FileWriter writer = new FileWriter(file);
		writer.write(str);
		writer.close();
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
	
	public void load(File file) throws IOException {
		// clear nodes and connections from model
		connections.clear();
		nodes.clear();
		
		// start and end nodes
		MainNode start;
		Node end;
		
		// JSON Reader
		JsonReader reader = new JsonReader(new FileReader(file));
		connections = gson.fromJson(reader, new TypeToken<ArrayList<Connection>>(){}.getType());
		
		// iterate through connections in JSON
		// adding nodes to hash and adding connections to list
		for (Connection c: connections) {
			start = (MainNode) c.getStart();
			end = c.getEnd();
			// add parents and children
			start.addChild(end);
			end.addParent(start);
			if (!nodes.contains(start)) {
				nodes.add(start);
			}
			if (!nodes.contains(end)) {
				nodes.add(end);
			}
			c.setStart(getNodeByID(start.getId()));
			c.setEnd(getNodeByID(end.getId()));
		}
	}

	private Node getNodeByID(int id) {
		for(Node n: nodes) {
			if(n.getId() == id) {
				return n;
			}
		}
		return null;
	}
}