package Node;

import javafx.scene.Group;

public class Connection {
	Group arrow;
	Node start;
	Node end;
	ConnectionType connectionType;
	
	public Connection(Node s, Node e, ConnectionType c) {
		start = s;
		end = e;
		connectionType = c;
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
	
	public ConnectionType getConnectionType() {
		return connectionType;
	}
}
