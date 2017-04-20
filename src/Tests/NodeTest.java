package Tests;

import java.awt.Point;
import org.junit.Test;
import Node.MainNode;
import Node.NodeType;
import Node.SupportingNode;

public class NodeTest {

	@Test
	public void testEqualsAndHash() {
		MainNode mainNode1 = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0),0);
		MainNode mainNode2 = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,1),0);
		MainNode mainNode3 = new MainNode("G2", "Goal Node", NodeType.GOAL, new Point(0,2),2);
		MainNode mainNode4 = new MainNode("G1", "GoalNode", NodeType.GOAL, new Point(0,3),3);
		MainNode mainNode5 = new MainNode("G1", "Goal Node", NodeType.STRATEGY, new Point(0,4),4);

		//mainNode1 and mainNode2 are equal
		assert(mainNode1.equals(mainNode2));
		assert(mainNode1.hashCode() == mainNode2.hashCode());

		//mainNode2 should be different from mainNode3, mainNode4, mainNode5
		assert(!mainNode2.equals(mainNode3));
		assert(mainNode2.hashCode() != mainNode3.hashCode());

		assert(!mainNode2.equals(mainNode4));
		assert(mainNode2.hashCode() != mainNode4.hashCode());

		assert(!mainNode2.equals(mainNode5));
		assert(mainNode2.hashCode() != mainNode5.hashCode());
	}

	@Test
	public void testNodeConstructors() {

		//Exceptions should be thrown for both
		try {
			new MainNode("G1", "Goal Node", NodeType.ASSUMPTION, new Point(0,0),0);
			assert(false);
		}
		catch (IllegalArgumentException e) {

		}

		//Exceptions should be thrown for both
		try {
			new SupportingNode("s1", "Support Node", NodeType.GOAL, new Point(0,0),0);
			assert(false);
		}
		catch (IllegalArgumentException e) {

		}
		assert(true);
	}
}
