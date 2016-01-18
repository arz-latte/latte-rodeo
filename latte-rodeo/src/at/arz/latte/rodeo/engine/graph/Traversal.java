package at.arz.latte.rodeo.engine.graph;


public abstract class Traversal<V> {

	public abstract void traverse(V vertex, Visitor<V> visitor);
}
