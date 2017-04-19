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
		description.setWrappingWidth(70);
		
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
	public NodePane redraw(Node node, Color outline, Color fill) {
		// TODO Auto-generated method stub
		return null;
	}

}
