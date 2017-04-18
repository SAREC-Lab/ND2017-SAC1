import com.fasterxml.jackson.annotation.JsonIgnore;

import Drawing.NodePane;

abstract class Mixin {
	@JsonIgnore NodePane pane; // we don't need it!
}
