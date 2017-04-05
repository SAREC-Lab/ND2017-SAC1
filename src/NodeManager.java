import SAC.SAC;

import java.util.ArrayList;

import Node.Node;

public class NodeManager {
	private SAC sac = new SAC();
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public NodeManager() {
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void deleteNode(Node n) {
		nodes.remove(n);
	}
	
	public void setSACRootNode(Node n) {
		sac.setRootNode(n);
	}
}