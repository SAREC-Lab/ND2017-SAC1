package Node;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainNode extends Node {

	private transient Set<Node> children = new HashSet<Node>();
	
	//Types of nodes that a MainNode can be
	private static Set<NodeType> allowableNodeTypes = new HashSet<NodeType>(Arrays.asList(NodeType.GOAL, NodeType.STRATEGY, NodeType.SOLUTION));

	public MainNode(String n, String d, NodeType nt, Point c, int id) {
		super(n, d, nt, c, id);

		//if NodeType is not allowed, throw exception
		if (!allowableNodeTypes.contains(nt)) {
			throw new IllegalArgumentException("NodeType " + nt.toString() + " not an allowable type for MainNode.");
		}
	}

	public MainNode() {
	}

	//add child so that this points to the node
	public void addChild(Node n) {
		children.add(n);
	}

	//remove child
	public void removeChild(Node n) {
		children.remove(n);
	}

	public Set<Node> getChildren() {
		return children;
	}
}