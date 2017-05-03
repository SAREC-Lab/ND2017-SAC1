package Drawing;

import Node.Node;
import Node.NodeType;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RectangleStrategy implements NodeDrawingStrategy {

	@Override
	public NodePane drawNode(Node node, Color outline, Color fill) {

		Rectangle rect = new Rectangle(node.getCoordinates().getX(), node.getCoordinates().getY(), 100, 50);
		
		// Hard coded for now
		rect.setStroke(outline);
		rect.setFill(fill);
		
		if (node.getNodeType() == NodeType.CONTEXT) {
			rect.setArcHeight(50);
			rect.setArcWidth(50);
		}
		
		Text text = new Text(node.getName());
		text.setFont(new Font(10));
		//text.setBoundsType(TextBoundsType.VISUAL);
		
		Text description = new Text("description");
		description.setFont(new Font(8));
		//description.setBoundsType(TextBoundsType.VISUAL);
		description.setFill(Color.GRAY);
		description.setWrappingWidth(45);
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(text, description);
		vBox.setAlignment(Pos.CENTER);
		
		NodePane stack = new NodePane(node);
		stack.getChildren().addAll(rect, vBox);
		stack.setTranslateX(node.getCoordinates().getX());
		stack.setTranslateY(node.getCoordinates().getY());
		
		return stack;
	}

	@Override
	public void resize(Node node, boolean delete) {
		NodePane pane = node.getPane();
		Text t = ((Text)((VBox)pane.getChildren().get(1)).getChildren().get(1));
		Text description = new Text(node.getDescription());
		int length = description.getText().length();
		int r = 1, m = 1;
		
		if (node.getNodeType() == NodeType.CONTEXT) {
			if(t.getWrappingWidth() < 70){
				t.setWrappingWidth(70);
			}
			r = (length-40)%32;
			m = (length-40)/32;
			if(r==0 && delete == false){
				pane.getChildren().get(0).setScaleX(1.2 + m*0.2);
				pane.getChildren().get(0).setScaleY(1.2 + m*0.3);
				t.setWrappingWidth(t.getWrappingWidth() + 10);
			}else if(r==0){
				pane.getChildren().get(0).setScaleX(1.0 + m*0.2);
				pane.getChildren().get(0).setScaleY(1.0 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() - 10);
			}
		} else {
			if(t.getWrappingWidth() < 70){
				t.setWrappingWidth(70);
			}	
			r = (length-45)%34;
			m = (length-45)/34;
			if(r==0 && delete == false){
				pane.getChildren().get(0).setScaleX(1.2 + m*0.2);
				pane.getChildren().get(0).setScaleY(1.2 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() + 15);
			}else if(r==0){
				pane.getChildren().get(0).setScaleX(1.0 + m*0.2);
				pane.getChildren().get(0).setScaleY(1.0 + m*0.2);
				t.setWrappingWidth(t.getWrappingWidth() - 15);
			}
		}
	}

}
