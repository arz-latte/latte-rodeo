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

	public void breadthFirstTraversal(Visitor<DependencyNode<T>> visitor) {
		super.breadthFirstTraversal(root, visitor);
	}

	public void depthFirstTraversal(Visitor<DependencyNode<T>> visitor) {
		super.depthFirstTraversal(root, visitor);
	}

	public DependencyTree<T> merge(DependencyTree<T> tree) {
		DependencyTreeBuilder<T> builder = new DependencyTreeBuilder<T>();
		builder.addAllEdges(this);
		builder.addAllEdges(tree);
		return builder.build();
	}

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
