package Drawing;

import Node.Node;
import javafx.scene.shape.Line;

public class ConnectionDrawer {
	
	public Arrow drawConnection(Node start, Node end, boolean filled) {
		Arrow arrow = new Arrow(start.getCoordinates(), end.getCoordinates(), filled);

		optimizeConnectionPlacement(arrow, start, end);
		
		return arrow;
	}
	
	public void optimizeConnectionPlacement(Arrow arrow, Node start, Node end) {
		Line line = arrow.getArrowLine();
		
		// Difference in node coordinates
		double xDiff = start.getCoordinates().getX() - end.getCoordinates().getX();
		double yDiff = start.getCoordinates().getY() - end.getCoordinates().getY();
		
		// Unbind previous connection points
		line.startXProperty().unbind();
		line.startYProperty().unbind();
		line.endXProperty().unbind();
		line.endYProperty().unbind();
		
		// Choose point of connection based on coordinates diffs
		if (Math.abs(xDiff) > Math.abs(yDiff)) {
			// Start is on left of end
			if (xDiff < 0) {
				line.startXProperty().bind(start.getPane().getRightConnectorPropertyX());
				line.startYProperty().bind(start.getPane().getRightConnectorPropertyY());
				line.endXProperty().bind(end.getPane().getLeftConnectorPropertyX());
				line.endYProperty().bind(end.getPane().getLeftConnectorPropertyY());
				
			// Start is on right of end
			} else {
				line.startXProperty().bind(start.getPane().getLeftConnectorPropertyX());
				line.startYProperty().bind(start.getPane().getLeftConnectorPropertyY());
				line.endXProperty().bind(end.getPane().getRightConnectorPropertyX());
				line.endYProperty().bind(end.getPane().getRightConnectorPropertyY());
			}
		} else {
			// Start is above end
			if (yDiff < 0) {
				line.startXProperty().bind(start.getPane().getBottomConnectorPropertyX());
				line.startYProperty().bind(start.getPane().getBottomConnectorPropertyY());
				line.endXProperty().bind(end.getPane().getTopConnectorPropertyX());
				line.endYProperty().bind(end.getPane().getTopConnectorPropertyY());
			// Start is below end
			} else {
				line.startXProperty().bind(start.getPane().getTopConnectorPropertyX());
				line.startYProperty().bind(start.getPane().getTopConnectorPropertyY());
				line.endXProperty().bind(end.getPane().getBottomConnectorPropertyX());
				line.endYProperty().bind(end.getPane().getBottomConnectorPropertyY());
			}
		}
	}
}
