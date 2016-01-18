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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
