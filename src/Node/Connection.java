package Node;

import javafx.scene.shape.Line;

public class Connection {

	private transient Line line;
	private Node start;
	private Node end;
	private double startx, starty, endx, endy;
	
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
	
	public Line getLine() {
		return line;
	}
	
	public void setLine(Line l) {
		line = l;
		startx = line.getStartX();
		starty = line.getStartY();
		endx = line.getEndX();
		endy = line.getEndY();
	}
	
	public Node getStart() {
		return start;
	}
	
	public Node getEnd() {
		return end;
	}

	public double getStartx() {
		return startx;
	}

	public void setStartx(double startx) {
		this.startx = startx;
	}

	public double getStarty() {
		return starty;
	}

	public void setStarty(double starty) {
		this.starty = starty;
	}

	public double getEndx() {
		return endx;
	}

	public void setEndx(double endx) {
		this.endx = endx;
	}

	public double getEndy() {
		return endy;
	}

	public void setEndy(double endy) {
		this.endy = endy;
	}
}
