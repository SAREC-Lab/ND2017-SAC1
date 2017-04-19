import SAC.SAC;
import javafx.scene.shape.Line;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import Node.Node;
import Node.NodeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.util.Iterator;
import java.util.Set;
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
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void traverse(boolean b, File file) throws IOException {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(file, connections);
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Node.class, new NodeDeserializer<Node>());
	    gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		String str = gson.toJson(connections);
		FileWriter writer = new FileWriter(file);
		writer.write(str);
		writer.close();
		//gson.toJson(connections,new FileWriter(file));
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
		connections.clear();
		nodes.clear();

		Set<Node> nodeSet = new HashSet<Node>();
		MainNode start;
		Node end;
//		ObjectMapper mapper = new ObjectMapper();
//		NodeDeserializer nd = new NodeDeserializer();
//		nd.registerNode("children", MainNode.class);
//		nd.registerNode("supportingNode", SupportingNode.class);
//		SimpleModule module = new SimpleModule();
//		module.addDeserializer(Node.class, nd);
//		mapper.registerModule(module);
//		connections = mapper.readValue(file, new TypeReference<ArrayList<Connection>>(){});
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Node.class, new NodeDeserializer<Node>());
	    gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		JsonReader reader = new JsonReader(new FileReader(file));
		connections = gson.fromJson(reader, new TypeToken<ArrayList<Connection>>(){}.getType());
		
		for (Connection c: connections) {
			start = (MainNode) c.getStart();
			end = c.getEnd();
			start.addChild(end);
			end.addParent(start);
			nodeSet.add(start);
			nodeSet.add(end);
			c.setLine(new Line(c.getStartx(),c.getStarty(),c.getEndx(),c.getEndy()));
		}
		nodes.addAll(nodeSet);
	}
}