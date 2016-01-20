package at.arz.latte.rodeo.engine;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

// Dependency Node ist die API für Clients
public class DependencyNode<T> {

	@XmlElement
	private T item;

	protected DependencyNode() {
		// JAXB Constructor
	}

	public DependencyNode(T item) {
		this.item = Objects.requireNonNull(item);
	}

	public T getItem() {
		return item;
	}

	@Override
	public String toString() {
		return "DependencyNode [item=" + item + "]";
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof DependencyNode && equals((DependencyNode<?>) obj);
	}

	public boolean equals(DependencyNode<?> dependencyNode) {
		return dependencyNode != null && Objects.equals(item, dependencyNode.item);
	}
}
