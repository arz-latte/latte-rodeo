package at.arz.latte.rodeo.engine.graph;

import java.util.ArrayList;
import java.util.List;

public class CollectVisitor<T>
		implements Visitor<T> {

	private final List<T> nodes;

	public CollectVisitor() {
		nodes = new ArrayList<T>();
	}

	public List<T> getNodes() {
		return nodes;
	}

	@Override
	public void visit(T node) {
		nodes.add(node);
	}

}
