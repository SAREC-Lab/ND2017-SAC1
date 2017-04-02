package Drawing;

import Node.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class ParallelogramStrategy implements NodeDrawingStrategy {

	@Override
	public Shape drawNode(Node node, Color outline, Color fill) {
		
		double x = node.getCoordinates().getX();
		double y = node.getCoordinates().getY();
		
		Polygon p = new Polygon(x, y, x+100, y, x+80, y+50, x-20, y+50); 
		p.setStrokeType(StrokeType.OUTSIDE);
		p.setStroke(outline);
		p.setFill(fill);
		
		return p;
	}

}
