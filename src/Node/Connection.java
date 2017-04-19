package Node;

import javafx.scene.Group;

public class Connection {
	Group arrow;
	Node start;
	Node end;
	
	public Connection(Node s, Node e) {
		start = s;
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
}
