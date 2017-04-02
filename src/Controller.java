import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import Node.MainNode;
import Node.Node;
import Node.NodeType;
import Node.SupportingNode;

public class Controller implements Observer{

	View view;
	Point clickLocation;
	
	public Controller(View v) {
		this.view = v;
		view.addObserver(this);
	}
	
	// Observe view to handle clicks inside tabs
	@Override
	public void update(Observable v, Object arg) {
		if (v instanceof View) {
			clickLocation = ((View)v).getClickLocation();
			createNode();
		}		
	}
	
	private void createNode() {
		NodeType type = view.getSelectedNodeType();
		if (type == null)
			return;
				
		Node newNode;
		if (type == NodeType.GOAL || type == NodeType.STRATEGY || type == NodeType.SOLUTION) {
			newNode = new MainNode("name", "description", view.getSelectedNodeType(), clickLocation);
		} else {
			newNode = new SupportingNode("name", "description", view.getSelectedNodeType(), clickLocation);
		}
		view.drawNode(newNode);
		view.deselectToggledNode();
		
		// TODO : Add newNode to model
	}
	
}