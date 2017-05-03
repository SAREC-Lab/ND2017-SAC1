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
	//Test NodeManager.addNode(node);
	public void testAddNode() {
		NodeManager nm = new NodeManager();
		
		//check that list of nodes is empty
		assert(nm.getNodes().size() == 0);
		
		//add two nodes
		nm.addNode(new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1));
		nm.addNode(new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 2));
		
		//check that the list of nodes has 2 nodes
		assert(nm.getNodes().size() == 2);
	}

	@Test
	//Test NodeManager.addConnection(connection)
	public void testAddConnection() {
		NodeManager nm = new NodeManager();

		//create two nodes and a connection between them
		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1);
		Node end = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 2);
		Connection connection = new Connection(start, end, false);

		//add nodes and connection to model
		nm.addNode(start);
		nm.addNode(end);
		nm.addConnection(connection);

		//check that start node has one child
		assert(start.getChildren().contains(end));
		assert(start.getChildren().size() == 1);
		
		//check that end node has one parent
		assert(end.getParents().contains(start));
		assert(end.getParents().size() == 1);
		
		//check that model has one connection
		assert(nm.getConnections().size() == 1);
	}

	@Test
	//Test NodeManager.removeConnection(connection)
	public void testRemoveConnection() {
		NodeManager nm = new NodeManager();

		//create three nodes and two connections
		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1);
		Node end1 = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 2);
		Node end2 = new SupportingNode("S2", "Support Node2", NodeType.ASSUMPTION, new Point(0, 0), 3);
		Connection connection1 = new Connection(start, end1, false);
		Connection connection2 = new Connection(start, end2, false);

		//add nodes and connections to model
		nm.addNode(start);
		nm.addNode(end1);
		nm.addNode(end2);
		nm.addConnection(connection1);
		nm.addConnection(connection2);
		
		//remove one of the connections from the model
		nm.removeConnection(connection1);

		//check that nodes have correct numbers of parents/children
		assert(start.getChildren().size() == 1);
		assert(end1.getParents().size() == 0);
		assert(end2.getParents().size() == 1);
		
		//check that the model has 1 connection
		assert(nm.getConnections().size() == 1);
	}

	@Test
	//Test NodeManager.removeNode(node)
	public void testRemoveNode() {
		NodeManager nm = new NodeManager();

		//create 4 nodes and 3 connections
		MainNode start = new MainNode("G1", "Goal Node", NodeType.GOAL, new Point(0,0), 1);
		MainNode middle = new MainNode("S3", "Strategy Node", NodeType.STRATEGY, new Point(0,0), 2);
		Node end1 = new SupportingNode("S1", "Support Node", NodeType.ASSUMPTION, new Point(0, 0), 3);
		Node end2 = new SupportingNode("S2", "Support Node2", NodeType.ASSUMPTION, new Point(0, 0), 4);
		Connection connection1 = new Connection(start, end1, false);
		Connection connection2 = new Connection(start, middle, true);
		Connection connection3 = new Connection(middle, end2, false);

		//add nodes and connections to model
		nm.addNode(start);
		nm.addNode(middle);
		nm.addNode(end1);
		nm.addNode(end2);
		
		nm.addConnection(connection1);
		nm.addConnection(connection2);
		nm.addConnection(connection3);
		
		//remove one of the nodes
		nm.removeNode(start);

		//ensure that the model has 3 nodes
		assert(nm.getNodes().size() == 3);
		assert(nm.getNodes().contains(end1));
		assert(nm.getNodes().contains(end2));
		assert(nm.getNodes().contains(middle));

		//ensure the model has 1 connection
		assert(nm.getConnections().size() == 1);
		assert(nm.getConnections().contains(connection3));

		//assert middle has 1 child (end2)
		assert(middle.getChildren().size() == 1);
		assert(middle.getChildren().contains(end2));
		
		//assert end2 has one parent (middle)
		assert(end2.getParents().size() == 1);
		assert(end2.getParents().contains(middle));
		
		//assert that end1 has no parents
		assert(end1.getParents().size() == 0);
	}

}
