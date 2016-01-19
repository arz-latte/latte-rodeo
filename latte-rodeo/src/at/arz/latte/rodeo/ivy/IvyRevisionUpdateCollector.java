package at.arz.latte.rodeo.ivy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import at.arz.latte.rodeo.engine.DependencyNode;
import at.arz.latte.rodeo.engine.DependencyTree;
import at.arz.latte.rodeo.engine.graph.Visitor;

public class IvyRevisionUpdateCollector<T> {

	private final DependencyTree<T> tree;
	private final Set<DependencyNode<T>> minorRevNodes;
	private final Set<DependencyNode<T>> majorRevNodes;
	private final BaselineRepository<T> baselineRepository;
	private Map<DependencyNode<T>, IvyRevision> changes;

	public IvyRevisionUpdateCollector(	DependencyTree<T> tree,
										Set<DependencyNode<T>> minorRevNodes,
										Set<DependencyNode<T>> majorRevNodes,
										BaselineRepository<T> baselineRepository) {
		this.tree = tree;
		this.minorRevNodes = minorRevNodes;
		this.majorRevNodes = majorRevNodes;
		this.baselineRepository = baselineRepository;
		simulate();
	}

	public void simulate() {
		changes = new HashMap<DependencyNode<T>, IvyRevision>();
		for (DependencyNode<T> minorRevNode : minorRevNodes) {
			simulateMinorRevUpdate(minorRevNode);
		}
		for (DependencyNode<T> majorRevNode : majorRevNodes) {
			simulateMajorRevUpdate(majorRevNode);
		}
	}

	public Map<DependencyNode<T>, IvyRevision> getChanges() {
		return changes;
	}

	public Map<DependencyNode<T>, IvyRevision> getDependencies(DependencyNode<T> node) {
		Map<DependencyNode<T>, IvyRevision> dependencies = new HashMap<DependencyNode<T>, IvyRevision>();
		for (DependencyNode<T> child : tree.getChildren(node)) {
			if (changes.containsKey(child)) {
				dependencies.put(child, changes.get(child));
			}
		}
		return dependencies;
	}

	private void simulateMinorRevUpdate(DependencyNode<T> minorRevNode) {
		if (tree.getRoot().equals(minorRevNode)) {
			updateMinor(minorRevNode);
			return;
		}
		DependencyTree<T> subtree = tree.getDownstreamSubtree(minorRevNode);
		if (subtree == null) {
			return;
		}
		subtree.breadthFirstTraversal(new Visitor<DependencyNode<T>>() {

			@Override
			public void visit(DependencyNode<T> node) {
				updateMinor(node);
			}
		});
	}

	private void simulateMajorRevUpdate(DependencyNode<T> majorRevNode) {
		if (tree.getRoot().equals(majorRevNode)) {
			updateMajor(majorRevNode);
			return;
		}
		DependencyTree<T> subtree = tree.getDownstreamSubtree(majorRevNode);
		if (subtree == null) {
			return;
		}
		subtree.breadthFirstTraversal(new Visitor<DependencyNode<T>>() {

			@Override
			public void visit(DependencyNode<T> node) {
				updateMajor(node);
			}
		});
	}

	private void updateMinor(DependencyNode<T> node) {
		IvyRevision ivyRevision = registerAndGetRevision(node);
		ivyRevision.performMinorUpdate();
	}

	private void updateMajor(DependencyNode<T> node) {
		IvyRevision ivyRevision = registerAndGetRevision(node);
		ivyRevision.performMajorUpdate();
	}

	private IvyRevision registerAndGetRevision(DependencyNode<T> node) {
		IvyRevision ivyRevision = changes.get(node);
		if (ivyRevision == null) {
			ivyRevision = baselineRepository.getBaseline(node);
			changes.put(node, ivyRevision);
		}
		return ivyRevision;
	}
}
