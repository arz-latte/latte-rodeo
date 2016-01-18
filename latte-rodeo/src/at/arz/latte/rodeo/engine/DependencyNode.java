package at.arz.latte.rodeo.engine;

import java.util.List;

// Dependency Node ist die API für Clients
public class DependencyNode<T> {

	private T item;
	private List<DependencyNode<T>> children;

	public DependencyNode(T item) {
		this.item = item;
	}

	public T getItem() {
		return item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		DependencyNode other = (DependencyNode) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

}
