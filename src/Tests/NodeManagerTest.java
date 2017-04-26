package Tests;

import java.awt.Point;

import org.junit.Test;

import MainPackage.NodeManager;
import Node.Connection;
import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;

public class NodeManagerTest {
	
	@Test
	public void testAddNode() {
		NodeManager nm = new NodeManager();
		
		assert(nm.getNodes().size() == 0);
		
		nm.addNode(new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1));
		nm.addNode(new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 2));
		
		assert(nm.getNodes().size() == 2);
	}

	@Test
	public void testAddConnection() {
		NodeManager nm = new NodeManager();

		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1);
		Node end = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 2);
		Connection connection = new Connection(start, end, false);

		nm.addNode(start);
		nm.addNode(end);
		nm.addConnection(connection);

		assert(start.getChildren().contains(end));
		assert(start.getChildren().size() == 1);
		assert(end.getParents().contains(start));
		assert(end.getParents().size() == 1);
		assert(nm.getConnections().size() == 1);
	}

	@Test
	public void testRemoveConnection() {
		NodeManager nm = new NodeManager();

		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1);
		Node end1 = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 2);
		Node end2 = new SupportingNode("S2", "Support Node2", NodeType.ASSUMPTION, new Point(0, 0), 3);
		Connection connection1 = new Connection(start, end1, false);
		Connection connection2 = new Connection(start, end2, false);

		nm.addNode(start);
		nm.addNode(end1);
		nm.addNode(end2);
		nm.addConnection(connection1);
		nm.addConnection(connection2);
		nm.removeConnection(connection1);

		assert(start.getChildren().size() == 1);
		assert(end1.getParents().size() == 0);
		assert(end2.getParents().size() == 1);
		assert(nm.getConnections().size() == 1);
	}

	@Test
	public void testRemoveNode() {
		NodeManager nm = new NodeManager();

		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1);
		MainNode middle = new MainNode("S3", "Strategy Node", NodeType.STRATEGY, new Point(0,0), 2);
		Node end1 = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 3);
		Node end2 = new SupportingNode("S2", "Support Node2", NodeType.ASSUMPTION, new Point(0, 0), 4);
		Connection connection1 = new Connection(start, end1, false);
		Connection connection2 = new Connection(start, middle, true);
		Connection connection3 = new Connection(middle, end2, false);

		nm.addNode(start);
		nm.addNode(middle);
		nm.addNode(end1);
		nm.addNode(end2);
		
		nm.addConnection(connection1);
		nm.addConnection(connection2);
		nm.addConnection(connection3);
		
		nm.removeNode(start);

		assert(nm.getNodes().size() == 3);
		assert(nm.getNodes().contains(end1));
		assert(nm.getNodes().contains(end2));
		assert(nm.getNodes().contains(middle));

		assert(nm.getConnections().size() == 1);
		assert(nm.getConnections().contains(connection3));

		assert(middle.getChildren().size() == 1);
		assert(middle.getChildren().contains(end2));
		
		assert(end2.getParents().size() == 1);
		assert(end2.getParents().contains(middle));
		
		assert(end1.getParents().size() == 0);
	}

}
