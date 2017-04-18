import SAC.SAC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Node.Node;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class NodeManager {
	private SAC sac = new SAC();
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	
	public NodeManager() {
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void removeNode(Node n) {
		nodes.remove(n);
	}
	
	public void setSACRootNode(Node n) {
		sac.setRootNode(n);
	}

	public void traverse(boolean b, File file) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Node.class, Mixin.class);
		mapper.addMixInAnnotations(Node.class, Mixin.class);
		mapper.writeValue(file, nodes);
	}
}