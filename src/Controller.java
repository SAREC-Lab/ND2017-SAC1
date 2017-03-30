import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer{

	View view;
	Point clickLocation;
	
	public Controller(View v) {
		this.view = v;
		view.addObserver(this);
	}
	
	// Observe view to handle clicks inside tabs
	@Override
	public void update(Observable v, Object arg) {
		if (v instanceof View) {
			clickLocation = ((View)v).getClickLocation();
			handleClick();
		}		
	}
	
	private void handleClick() {
		if (view.getSelectedNodeType() == null)
			return;
		
		System.out.println(view.getSelectedNodeType().toString() + ": " + clickLocation.x + "," + clickLocation.y);
		view.deselectToggledNode();
	}
	
}