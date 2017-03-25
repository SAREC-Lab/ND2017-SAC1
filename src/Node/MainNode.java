package Node;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class MainNode extends Node {
	
	private Set<Node> children = new HashSet<Node>();

	public MainNode(String n, String d, NodeType nt, Point c) {
		super(n, d, nt, c);
	}
	
	public void addChild(Node n) {
		children.add(n);
	}
	
	public void removeChild(Node n) {
		children.remove(n);
	}
	
	public Set<Node> returnChildren() {
		return children;
	}
}