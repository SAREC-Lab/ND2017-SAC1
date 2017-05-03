package MainPackage;
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

//NodeManager acts as the model in MVC
public class NodeManager {
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private Gson gson;

	public NodeManager() {
		//gson is used to save and load JSON files
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Node.class, new NodeDeserializer<Node>());
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	//add node
	public void addNode(Node n) {
		nodes.add(n);
	}

	//remove node
	public void removeNode(Node n) {
		
		//for each connection, if it connects to the node, remove the connection
		Iterator<Connection> connection_it = connections.iterator();
		while(connection_it.hasNext()) {
			Connection connection = connection_it.next();
			if (connection.getStart() == n || connection.getEnd() == n) {
				connection_it.remove();
			}
		}

		//for each of the node's parents, remove the node from the parent's list of children
		Iterator<MainNode> parent_it = n.getParents().iterator();
		while (parent_it.hasNext()) {
			Node parent = parent_it.next();
			if (parent.getClass() == MainNode.class) {
				((MainNode) parent).getChildren().remove(n);
			}
		}

		//if the node is a MainNode
		if (n.getClass() == MainNode.class) {
			MainNode main_node = (MainNode) n;
			
			//for each of the node's children, remove the node from the child's list of parents
			Iterator<Node> child_it = main_node.getChildren().iterator();
			while (child_it.hasNext()) {
				Node child = child_it.next();
				child.getParents().remove(main_node);
			}
		}

		//remove node from list of nodes
		nodes.remove(n);
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	//save the model in JSON format
	public void save(boolean b, File file) throws IOException {
		// convert connections list to JSON string
		String str = gson.toJson(connections);

		// write string to file
		FileWriter writer = new FileWriter(file);
		writer.write(str);
		writer.close();
	}

	//add a connection
	public void addConnection(Connection r) {
		
		//add to connections list
		connections.add(r);
		
		MainNode main_node = (MainNode) r.getStart();
	
		//add child to parent's list of children
		main_node.addChild(r.getEnd());
		
		//add parent to child's list of parents
		r.getEnd().addParent(main_node);
	}

	//get all of the connections that connect a node to any other node
	public ArrayList<Connection> getNodeConnections(Node n) {
		
		ArrayList<Connection> conns = new ArrayList<Connection>();
		
		//for each connection, if n is the start or end node, add to conns
		for (Connection c : connections) {
			if (c.getStart() == n || c.getEnd() == n) {
				conns.add(c);
			}
		}
		return conns;
	}

	//print nodes; used for debugging purposes
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

	//remove a connection
	public void removeConnection(Connection connection) {
		connections.remove(connection);
		
		//if the parent is a MainNode
		if (connection.getStart().getClass() == MainNode.class) {
			MainNode main_node = (MainNode) connection.getStart();
			
			//remove parent from child's list of parents
			connection.getEnd().removeParent(main_node);
			
			//remove child from parent's list of children
			main_node.removeChild(connection.getEnd());
		}
	}

	//load model from JSON file
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

	//return node with the unique id number
	private Node getNodeByID(int id) {
		for(Node n: nodes) {
			if(n.getId() == id) {
				return n;
			}
		}
		return null;
	}
}