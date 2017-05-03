package Node;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SupportingNode extends Node {
	
	//Types of nodes that a SupportingNode can be
	private static Set<NodeType> allowableNodeTypes = new HashSet<NodeType>(Arrays.asList(NodeType.CONTEXT, NodeType.ASSUMPTION, NodeType.JUSTIFICATION));

	public SupportingNode() {

	}

	public SupportingNode(String n, String d, NodeType nt, Point c, int id) {
		super(n, d, nt, c, id);

		//If NodeType is not allowed, throw exception
		if (!allowableNodeTypes.contains(nt)) {
			throw new IllegalArgumentException("NodeType " + nt.toString() + " not an allowable type for SupportingNode.");
		}
	}
}
