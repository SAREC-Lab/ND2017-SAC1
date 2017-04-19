package Tests;

import java.awt.Point;
import org.junit.Test;
import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;
import SAC.SAC;

public class SACTest {

	@Test
	public void testSAC() {
		try {
			Node n = new MainNode("s1", "Strategy Node", NodeType.STRATEGY, new Point(0,0));
			new SAC(n);
			assert(false);
		}
		catch (IllegalArgumentException e) {
			
		}
		
		//make sac1
		MainNode n1 = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0));
		Node s1 = new SupportingNode("s1", "Support Node", NodeType.ASSUMPTION, new Point(0,0));
		n1.addChild(s1);
		
		SAC sac1 = new SAC(n1);
		
		//make sac2. should be equal to sac1
		MainNode n2 = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0));
		Node s2 = new SupportingNode("s1", "Support Node", NodeType.ASSUMPTION, new Point(0,0));
		n2.addChild(s2);
		
		SAC sac2 = new SAC(n2);
		
		assert(sac1.equals(sac2));
	}

	@Test
	public void testExceptions() {
		
		SAC s1 = new SAC();
		SAC s2 = new SAC();
		
		try {
			s1.getRootNode();
			assert(false);
		}
		catch (NullPointerException e){
			
		}
		
		try {
			s1.equals(s2);
			assert(false);
		}
		catch (NullPointerException e){
			
		}
		
		assert(true);
	}
}
