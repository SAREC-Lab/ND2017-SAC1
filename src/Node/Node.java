package Node;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import Drawing.NodePane;
import javafx.scene.paint.Color;

public abstract class Node {
	private String name;
	private String description;
	private NodeType nodeType;
	Point coordinates;
	private Color outline;
	private Color fill;
	private int id;
	private transient NodePane pane;
	private transient ArrayList<MainNode> parents = new ArrayList<MainNode>();

	public Node() {
	}

	public Node(String n, String d, NodeType nt, Point c, int id) {
		name = n;
		description = d;
		this.id = id;
		nodeType = nt;
		coordinates = c;
		fill = Color.WHITE;
		outline = Color.BLACK;
	}

	public void setParents(ArrayList<MainNode> p) {
		parents = p;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setName(String n) {
		name = n;
	}

	public void setDescription(String d) {
		description = d;
	}

	public void setNodeType(NodeType nt) {
		nodeType = nt;
	}

	public void setCoordinates(Point c) {
		coordinates.setLocation(c.getX(), c.getY());
	}

	public void setFill(Color f) {
		fill = f;
	}

	public void setOutline(Color o) {
		outline = o;
	}

	public Color getOutline() {
		return outline;
	}

	public Color getFill() {
		return fill;
	}

	public void setPane(NodePane p) {
		pane = p;
	}

	public NodePane getPane() {
		return pane;
	}

	public void addParent(MainNode n) {
		parents.add(n);
	}

	public void removeParent(MainNode n) {
		parents.remove(n);
	}

	public ArrayList<MainNode> getParents() {
		return parents;
	}
	
	public boolean isParentOf(Node node) {

		//tree traversal
		LinkedList<Node> frontier = new LinkedList<Node>();

		frontier.push(this);

		while (frontier.size() > 0) {
			Node popped_node = frontier.pop();

			//if popped_node is equal to node, then this is the parent of the node
			if (popped_node == node) {
				return true;
			}

			//add children of popped_node to frontier
			if (popped_node.getClass() == MainNode.class) {
				MainNode main_node = (MainNode) popped_node;

				for (Node child : main_node.getChildren()) {
					frontier.push(child);
				}
			}
		}

		//if node has not been found, then it is not a child of this
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 
				* (7 + getId());
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other == null) {
			return false;
		}

		if (other.getClass() != getClass()) {
			return false;
		}

		Node node = (Node)other;

		return (getId() == node.getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
