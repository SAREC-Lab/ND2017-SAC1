package SAC;
import java.util.ArrayDeque;

import Node.MainNode;
import Node.Node;
import Node.NodeType;

public class SAC {
	Node rootNode;
	
	public SAC(Node rn) {
		setRootNode(rn);
	}
	
	public void setRootNode(Node rn) {
		if (rn.getNodeType() == NodeType.GOAL) {
			rootNode = rn;
		} else {
			throw new IllegalArgumentException("Root node of SAC must have a NodeType of GOAL.");
		}
	}
	
	public Node getRootNode() {
		return rootNode;
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
		
		SAC sac = (SAC)other;
		
		ArrayDeque<Node> thisList = new ArrayDeque<Node>();
		ArrayDeque<Node> otherList = new ArrayDeque<Node>();
		
		thisList.push(rootNode);
		otherList.push(sac.getRootNode());
		
		while (thisList.size() > 0 && otherList.size() > 0) {
			Node thisNode = thisList.pop();
			Node otherNode = otherList.pop();
			
			if (thisNode.equals(otherNode)) {
				//if nodes are MainNode, need to check if the sets of children are the same
				if (thisNode.getClass() == MainNode.class && thisNode.getClass() == MainNode.class) {
					MainNode thisMainNode = (MainNode) thisNode;
					MainNode otherMainNode = (MainNode) otherNode;
					
					if (!thisMainNode.getChildren().equals(otherMainNode.getChildren())) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		
		//check if size of both lists is > 0
		return (thisList.size() == 0 && otherList.size() == 0);
	}
}