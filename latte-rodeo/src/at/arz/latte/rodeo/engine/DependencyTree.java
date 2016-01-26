package at.arz.latte.rodeo.engine;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import at.arz.latte.rodeo.engine.graph.Graph;
import at.arz.latte.rodeo.engine.graph.Visitor;
import at.arz.latte.rodeo.release.ModuleName;

@XmlRootElement
@XmlSeeAlso({ DependencyNode.class, ModuleName.class })
public class DependencyTree<T>
		extends Graph<DependencyNode<T>>
		implements Cloneable {

	private DependencyNode<T> root;

	public DependencyNode<T> getRoot() {
		return root;
	}
	
	/**
	 * Returns a sub tree containing all parent nodes of the given node.
	 * 
	 * @param node - the leaf node
	 * @return a sub tree contaiing all parent nodes of the given node.
	 */
	public DependencyTree<T> getDownstreamSubtree(DependencyNode<T> node) {
		DependencyTreeBuilder<T> builder = new DependencyTreeBuilder<T>();
		addParentEdges(builder, node);
		if (builder.isEmpty()) {
			return null;
		}
		return builder.build();
	}

	void setRoot(DependencyNode<T> root) {
		this.root = root;
	}

	/**
	 * Visits each neighbor of a node, before moving to the next level of nodes.
	 * 
	 * @param visitor - the visitor to be calles for each visited node
	 */
	public void breadthFirstTraversal(Visitor<DependencyNode<T>> visitor) {
		super.breadthFirstTraversal(root, visitor);
	}

	/**
	 * Explores each branch of this tree as far as possible, before moving to the next branch.
	 * 
	 * @param visitor - the visitor to be called for each visited node.
	 */
	public void depthFirstTraversal(Visitor<DependencyNode<T>> visitor) {
		super.depthFirstTraversal(root, visitor);
	}

	/**
	 * Merges this dependency tree with the given dependency tree. This operation does not modify this tree,
	 * but returns a new tree containing the elements of this tree and the given tree.
	 * 
	 * @param tree - the tree to be merged with this tree
	 * @return a new tree containing the elements of this tree and the given tree
	 */
	public DependencyTree<T> merge(DependencyTree<T> tree) {
		DependencyTreeBuilder<T> builder = new DependencyTreeBuilder<T>();
		builder.addAllEdges(this);
		builder.addAllEdges(tree);
		return builder.build();
	}

	/**
	 * Creates a shallow clone of this tree: the nodes itself are not cloned.
	 */
	@Override
	public DependencyTree<T> clone() {
		DependencyTreeBuilder<T> builder = new DependencyTreeBuilder<T>();
		builder.addAllEdges(this);
		return builder.build();
	}

	private void addParentEdges(DependencyTreeBuilder<T> builder, DependencyNode<T> node) {
		for (DependencyNode<T> parent : getParents(node)) {
			builder.addEdge(parent, node);
			addParentEdges(builder, parent);
		}
	}
}
