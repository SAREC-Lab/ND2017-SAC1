package Drawing;

import java.awt.Point;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class Arrow {
	
	private Group arrow;
	private Line line;
	private Polygon arrowhead;
	
	public Arrow(Point start, Point end, boolean filled) {
		arrow = new Group();
		
		// Create initial line
		line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
		
		// Create initial triangle
		arrowhead = new Polygon(end.getX(), end.getY(), 
				end.getX() - 6, end.getY() + 12, 
				end.getX() + 6, end.getY() + 12);
		
		arrowhead.setStroke(Color.BLACK);
		if (filled) {
			arrowhead.setFill(Color.BLACK);
		} else {
			arrowhead.setFill(Color.WHITE);
		}
		
		arrow.getChildren().addAll(line, arrowhead);
		updateArrowheadLocation();
	}
	
	public Line getArrowLine() {
		return line;
	}
	
	public Group getArrow() {
		return arrow;
	}
	
	public void updateArrowheadLocation() {		
		double originalX = arrowhead.getPoints().get(0);
		double originalY = arrowhead.getPoints().get(1);

		// Translate according to the offset 
		arrowhead.setTranslateX(line.getEndX() - originalX);
		arrowhead.setTranslateY(line.getEndY() - originalY);
		
		// Set correct angle
		arrowhead.getTransforms().clear();
		double xDiff = line.getEndX() - line.getStartX();
		double yDiff = line.getEndY() - line.getStartY();
        double angle = Math.toDegrees(Math.atan2(yDiff, xDiff)) + 90;
		arrowhead.getTransforms().add(new Rotate(angle, arrowhead.getPoints().get(0), arrowhead.getPoints().get(1)));
	}
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		
		if (other == null) {
			return false;
		}
		
		if (other.getClass() != getClass()) {
			return false;
		}
		
		Arrow arrow = (Arrow)other;
		
		return (this.getArrow()==arrow.getArrow() && this.getArrowLine() == arrow.getArrowLine());
	}
}
