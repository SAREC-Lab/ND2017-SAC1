package Tests;

import java.awt.Point;

import org.junit.Test;

import MVC.NodeManager;
import Node.Connection;
import Node.ConnectionType;
import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;

public class NodeManagerTest {

	@Test
	public void testAddConnection() {
		NodeManager nm = new NodeManager();

		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0));
		Node end = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0));
		Connection connection = new Connection(start, end, ConnectionType.SUPPORTING);

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

		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0));
		Node end = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0));
		Connection connection = new Connection(start, end, ConnectionType.SUPPORTING);

		nm.addNode(start);
		nm.addNode(end);
		nm.addConnection(connection);
		nm.removeConnection(connection);

		assert(start.getChildren().size() == 0);
		assert(end.getParents().size() == 0);
		assert(nm.getConnections().size() == 0);
	}

	@Test
	public void testRemoveNode() {
		NodeManager nm = new NodeManager();

		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0));
		Node end1 = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0));
		Node end2 = new SupportingNode("S2", "Support Node2", NodeType.ASSUMPTION, new Point(0, 0));
		Connection connection1 = new Connection(start, end1, ConnectionType.SUPPORTING);
		Connection connection2 = new Connection(start, end2, ConnectionType.SUPPORTING);

		nm.addNode(start);
		nm.addNode(end1);
		nm.addNode(end2);
		nm.addConnection(connection1);
		nm.addConnection(connection2);
		nm.removeNode(end1);

		assert(nm.getNodes().size() == 2);
		assert(nm.getNodes().contains(start));
		assert(nm.getNodes().contains(end2));

		assert(nm.getConnections().size() == 1);
		assert(nm.getConnections().contains(connection2));

		assert(start.getChildren().size() == 1);
		assert(start.getChildren().contains(end2));
	}

}
