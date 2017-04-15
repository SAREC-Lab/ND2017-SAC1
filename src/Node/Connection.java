package Node;

import javafx.scene.shape.Line;

public class Connection {
	Line line;
	Node start;
	Node end;
	
	public Connection(Node s, Node e, Line l) {
		start = s;
		end = e;
		line = l;
	}
	
	public Line getLine() {
		return line;
	}
	
	public void setLine(Line l) {
		line = l;
	}
	
	public Node getStart() {
		return start;
	}
	
	public Node getEnd() {
		return end;
	}
}
