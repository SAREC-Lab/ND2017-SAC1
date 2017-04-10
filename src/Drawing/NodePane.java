package Drawing;

import Node.Node;
import Node.NodeType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.StackPane;

public class NodePane extends StackPane {
	
	private Node node;
	private DoubleProperty topConnectorX, topConnectorY;
	private DoubleProperty bottomConnectorX, bottomConnectorY;
	private DoubleProperty leftConnectorX, leftConnectorY;
	private DoubleProperty rightConnectorX, rightConnectorY;

	public NodePane(Node n) {
		super();
		
		node = n;
		topConnectorX = new SimpleDoubleProperty();
		topConnectorY = new SimpleDoubleProperty();
		bottomConnectorX = new SimpleDoubleProperty();
		bottomConnectorY = new SimpleDoubleProperty();
		leftConnectorX = new SimpleDoubleProperty();
		leftConnectorY = new SimpleDoubleProperty();
		rightConnectorX = new SimpleDoubleProperty();
		rightConnectorY = new SimpleDoubleProperty();
	}
	
	public void updateNodeProperties() {
		topConnectorX.set(node.getCoordinates().getX() + node.getPane().getWidth()/2);
		topConnectorY.set(node.getCoordinates().getY());
		bottomConnectorX.set(node.getCoordinates().getX() + node.getPane().getWidth()/2);
		bottomConnectorY.set(node.getCoordinates().getY() + node.getPane().getHeight());
		leftConnectorX.set(node.getCoordinates().getX());
		leftConnectorY.set(node.getCoordinates().getY() + node.getPane().getHeight()/2);
		rightConnectorX.set(node.getCoordinates().getX() + node.getPane().getWidth());
		rightConnectorY.set(node.getCoordinates().getY() + node.getPane().getHeight()/2);
		
		// Handle slanted sides of parallelograms
		if (node.getNodeType() == NodeType.STRATEGY) {
			rightConnectorX.set(node.getCoordinates().getX() + node.getPane().getWidth() - 11);
			leftConnectorX.set(node.getCoordinates().getX() + 11);
		}
	}
	
	public ReadOnlyDoubleProperty getTopConnectorPropertyX() {
		return topConnectorX;
	}
	
	public ReadOnlyDoubleProperty getTopConnectorPropertyY() {
		return topConnectorY;
	}
	
	public ReadOnlyDoubleProperty getBottomConnectorPropertyX() {
		return bottomConnectorX;
	}
	
	public ReadOnlyDoubleProperty getBottomConnectorPropertyY() {
		return bottomConnectorY;
	}
	
	public ReadOnlyDoubleProperty getLeftConnectorPropertyX() {
		return leftConnectorX;
	}
	
	public ReadOnlyDoubleProperty getLeftConnectorPropertyY() {
		return leftConnectorY;
	}
	
	public ReadOnlyDoubleProperty getRightConnectorPropertyX() {
		return rightConnectorX;
	}
	
	public ReadOnlyDoubleProperty getRightConnectorPropertyY() {
		return rightConnectorY;
	}
}
