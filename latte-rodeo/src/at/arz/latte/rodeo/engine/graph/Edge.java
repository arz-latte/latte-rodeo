package at.arz.latte.rodeo.engine.graph;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class Edge<T> {

	@XmlElement
	private T from;
	@XmlElement
	private T to;

	protected Edge() {
		// JAXB Constructor
	}
	
	public Edge(T from, T to) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		if (from.equals(to)) {
			throw new RuntimeException("From and to must be different.");
		}
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
