package Node;

import java.awt.Point;

public abstract class Node {
	private String name;
	private String description;
	private NodeType nodeType;
	Point coordinates;
	
	public Node(String n, String d, NodeType nt, Point c) {
		name = n;
		description = d;
		nodeType = nt;
		coordinates = c;
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
		coordinates = c;
	}
	
	@Override
	public int hashCode() {
		return 31 
				* (7 + getName().hashCode()) 
				* (11 + getDescription().hashCode()) 
				* (41 + getNodeType().hashCode());
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
		
		return (getName() == node.getName()
				&& getDescription() == node.getDescription()
				&& getNodeType() == node.getNodeType());
	}
}
