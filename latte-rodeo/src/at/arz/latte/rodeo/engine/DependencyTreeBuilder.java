package at.arz.latte.rodeo.engine;

import java.util.HashSet;
import java.util.Set;

import at.arz.latte.rodeo.engine.graph.GraphBuilder;

/**
 * A builder for dependency trees.
 * 
 * @param <T>
 */
public class DependencyTreeBuilder<T>
		extends GraphBuilder<DependencyNode<T>, DependencyTree<T>> {

	/**
	 * Creates a new dependency tree builder.
	 */
	public DependencyTreeBuilder() {
		super(new DependencyTree<T>());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DependencyTree<T> build() {
		DependencyTree<T> dependencyTree = super.build();
		check(dependencyTree);
		DependencyNode<T> root = getRoot(dependencyTree);
		dependencyTree.setRoot(root);
		return dependencyTree;
	}
	
	private void check(DependencyTree<T> dependencyTree) {
		checkIsNotEmpty(dependencyTree);
		checkIsAcyclic(dependencyTree);
	}

	private void checkIsNotEmpty(DependencyTree<T> dependencyTree) {
		if (dependencyTree.getVertexes().isEmpty()) {
			throw new RuntimeException("Tree must not be empty.");
		}
	}

	private void checkIsAcyclic(DependencyTree<T> dependencyTree) {
		// TODO Check if tree has cycles.
	}

	private DependencyNode<T> getRoot(DependencyTree<T> tree) {
		Set<DependencyNode<T>> rootNodes = new HashSet<DependencyNode<T>>();
		for (DependencyNode<T> node : tree.getVertexes()) {
			if (!tree.hasParents(node)) {
				rootNodes.add(node);
			}
		}
		if (rootNodes.isEmpty()) {
			throw new RuntimeException("Tree must be acyclic.");
		}
		if (rootNodes.size() > 1) {
			throw new RuntimeException("Tree hast more than one root: " + rootNodes);
		}
		return rootNodes.iterator().next();
	}
}
