package Drawing;

import Node.Node;
import javafx.scene.shape.Line;

public class ConnectionDrawer {
	
	public Line drawConnection(Node start, Node end) {
		double xDiff = start.getCoordinates().getX() - end.getCoordinates().getX();
		double yDiff = start.getCoordinates().getY() - end.getCoordinates().getY();
		double startX, endX, startY, endY;
		
		if (Math.abs(xDiff) > Math.abs(yDiff)) {
			// Start is on left of end
			if (xDiff < 0) {
				startX = start.getCoordinates().getX() + start.getPane().getWidth();
				startY = start.getCoordinates().getY() + start.getPane().getHeight()/2;
				endX = end.getCoordinates().getX();
				endY = end.getCoordinates().getY() + end.getPane().getHeight()/2;
			// Start is on right of end
			} else {
				startX = start.getCoordinates().getX();
				startY = start.getCoordinates().getY() + start.getPane().getHeight()/2;
				endX = end.getCoordinates().getX() + end.getPane().getWidth();
				endY = end.getCoordinates().getY() + end.getPane().getHeight()/2;			}
		} else {
			// Start is above end
			if (yDiff < 0) {
				startX = start.getCoordinates().getX() + start.getPane().getWidth()/2;
				startY = start.getCoordinates().getY() + start.getPane().getHeight();
				endX = end.getCoordinates().getX() + end.getPane().getWidth()/2;
				endY = end.getCoordinates().getY();
			// Start is below end
			} else {
				startX = start.getCoordinates().getX() + start.getPane().getWidth()/2;
				startY = start.getCoordinates().getY();
				endX = end.getCoordinates().getX() + end.getPane().getWidth()/2;
				endY = end.getCoordinates().getY() + end.getPane().getHeight();
			}
		}
		
		return new Line(startX, startY, endX, endY);
	}
}
