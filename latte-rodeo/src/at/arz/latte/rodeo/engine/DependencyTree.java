package at.arz.latte.rodeo.engine;

import at.arz.latte.rodeo.engine.graph.Graph;
import at.arz.latte.rodeo.engine.graph.Visitor;

public class DependencyTree<T>
		extends Graph<DependencyNode<T>> {

	private DependencyNode<T> root;

	public DependencyNode<T> getRoot() {
		return root;
	}

	void setRoot(DependencyNode<T> root) {
		this.root = root;
	}

	public void breadthFirstTraversal(Visitor<DependencyNode<T>> visitor) {
		super.breadthFirstTraversal(root, visitor);
	}

	public void depthFirstTraversal(Visitor<DependencyNode<T>> visitor) {
		super.depthFirstTraversal(root, visitor);
	}
}
