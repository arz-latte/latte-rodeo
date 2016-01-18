package at.arz.latte.rodeo.engine.graph;

import java.util.Objects;

class Edge<T> {

	private final T from;
	private final T to;
	
	public Edge(T from, T to) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		this.from = from;
		this.to = to;
	}
	
	public T getFrom() {
		return from;
	}
	
	public T getTo() {
		return to;
	}
	
	@Override
	public String toString() {
		return "(" + from + ", " + to + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Edge && equals((Edge<?>) obj);
	}

	public boolean equals(Edge<?> edge) {
		return edge != null && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
	}
}
