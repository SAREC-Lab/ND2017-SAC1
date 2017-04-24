package Drawing;

import Node.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import Node.NodeType;

public class NodeDrawer {
	NodeDrawingStrategy strategy;
	
	// Generates javafx shape to return to view
	public NodePane drawNode(Node node, Color outline, Color fill) {
		node.setFill(fill);
		node.setOutline(outline);
		setStrategy(node.getNodeType());
		return strategy.drawNode(node, outline, fill);
	}
	
	public void setStrategy(NodeType type) {
		switch (type) {
		case GOAL:
		case CONTEXT:
			strategy = new RectangleStrategy();
			break;
		case STRATEGY:
			strategy = new ParallelogramStrategy();
			break;
		case SOLUTION:
			strategy = new CircleStrategy();
			break;
		case JUSTIFICATION:
		case ASSUMPTION:
			strategy = new EllipseStrategy();
			break;
		}
	}
	
	public void resize(Node node, NodePane shape, Boolean delete){
		Text t = ((Text)((VBox)shape.getChildren().get(1)).getChildren().get(1));
		Text description = new Text(node.getDescription());
		int length = new Integer(description.getText().length());
		int r = new Integer(1);
		int m = new Integer(1);
		switch (node.getNodeType()) {
		case GOAL:
		case STRATEGY:
			//System.out.println(description.getText().length());
			r = (length-45)%34;
			m = (length-45)/34;
			if(r==0 && delete == false){
				shape.getChildren().get(0).setScaleX(1.2 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.2 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() + 15);
			}else if(r==0){
				shape.getChildren().get(0).setScaleX(1.0 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.0 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() - 15);
			}
			break;
		case CONTEXT:	//rounded rectangle
			//System.out.println(description.getText().length());
			r = (length-40)%32;
			m = (length-40)/32;
			if(r==0 && delete == false){
				shape.getChildren().get(0).setScaleX(1.2 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.2 + m*0.3);
				t.setWrappingWidth(t.getWrappingWidth() + 10);
			}else if(r==0){
				shape.getChildren().get(0).setScaleX(1.0 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.0 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() - 10);
			}
			break;
		case SOLUTION:	//circle
			//System.out.println(description.getText().length());
			r = (length-45)%34;
			m = (length-45)/34;
			if(r==0 && delete == false){
				shape.getChildren().get(0).setScaleX(1.2 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.2 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() + 10);
			}else if(r==0){
				shape.getChildren().get(0).setScaleX(1.0 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.0 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() - 10);
			}
			break;
		case JUSTIFICATION:
		case ASSUMPTION:
			//System.out.println(description.getText().length());
			r = (length-45)%34;
			m = (length-45)/34;
			if(r==0 && delete == false){
				shape.getChildren().get(0).setScaleX(1.2 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.2 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() + 10);
			}else if(r==0){
				shape.getChildren().get(0).setScaleX(1.0 + m*0.2);
				shape.getChildren().get(0).setScaleY(1.0 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() - 10);
			}
			break;
		}
	}
	
}
