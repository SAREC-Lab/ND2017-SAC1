import java.awt.Point;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;

public class Controller{

	View view;
	NodeManager manager = new NodeManager();
	private int node_id;

	public Controller(View v) {
		node_id = 0;
		this.view = v;
	}

	public void createNode(Point clickLocation) {
		node_id++;
		NodeType type = view.getSelectedNodeType();
		if (type == null)
			return;

		Node newNode;

		Point nodeLocation = new Point((int) clickLocation.getX(), (int) clickLocation.getY());

		if (type == NodeType.GOAL || type == NodeType.STRATEGY || type == NodeType.SOLUTION) {
			newNode = new MainNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation,node_id);
		} else {
			newNode = new SupportingNode(type.toString(), "description", view.getSelectedNodeType(), nodeLocation,node_id);
		}
		view.drawNode(newNode);
		view.deselectToggledNode();

		manager.addNode(newNode);
	}
	
	public void addRoot(Node n) {
		manager.setSACRootNode(n);
	}

	public void removeNode(Node n) {
		manager.removeNode(n);
	}

	public void traverse(boolean b, File file) throws JsonGenerationException, JsonMappingException, IOException {
		manager.traverse(b,file);
	}
}