package at.arz.latte.rodeo.engine.graph;

public interface Visitor<T> {

	void visit(T node);
}
