package Node;

import javafx.scene.Group;

public class Connection {

	private transient Group arrow;
	private Node start;
	private Node end;
	private boolean filled;
	
	public Connection() {
	}

	
	public Connection(Node s, Node e) {
		start = s;
		end = e;
	}
	
	public void setStart(Node s) {
		start = s;
	}
	
	public void setEnd(Node e) {
		end = e;
	}
	
	public Group getArrow() {
		return arrow;
	}
	
	public void setArrow(Group a) {
		arrow = a;
	}
	
	public Node getStart() {
		return start;
	}
	
	public Node getEnd() {
		return end;
	}

	public boolean isFilled() {
		return filled;
	}


	public void setFilled(boolean filled) {
		this.filled = filled;
	}
}
