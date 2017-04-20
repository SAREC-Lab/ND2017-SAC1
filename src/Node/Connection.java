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

		Connection conn = (Connection)other;

		return (conn.isFilled()==this.isFilled() && conn.getStart()==this.getStart() && conn.getEnd()==this.getEnd() && conn.getArrow()==this.getArrow());
	}
}
