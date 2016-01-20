package at.arz.latte.rodeo.engine;

import java.util.HashSet;
import java.util.Set;

/**
 * Der <code>DependencyTreeTraverser</code> traversiert den {@link DependencyTree} unter Berücksichtigung der bereits
 * verarbeiteten {@link DependencyNode}s. Er ist auf
 * maximale parallele Verarbeitung optimiert.
 */
public class DependencyTreeTraverser<T> {

	private final DependencyTree<T> tree;
	private final Set<DependencyNode<T>> nodesInProgress;
	private final Set<DependencyNode<T>> failedNodes;

	/**
	 * Erzeugt einen neuen <code>DependencyTreeTraverser</code> für den angegebenen <code>DependencyTree</code>.
	 * 
	 * @param tree - der zu verarbeitende <code>DependencyTree</code>
	 */
	public DependencyTreeTraverser(DependencyTree<T> tree) {
		this.tree = tree.clone();
		nodesInProgress = new HashSet<DependencyNode<T>>();
		failedNodes = new HashSet<DependencyNode<T>>();
	}

	/**
	 * Informiert über die erfolgreiche Verarbeitung eines <code>DependencyNode</code>s.
	 * 
	 * @param node - der erfolgreich verarbeitete <code>DependencyNode</code>
	 */
	public void notifyProcessingSucceeded(DependencyNode<T> node) {
		if (!nodesInProgress.contains(node)) {
			throw new RuntimeException("Node " + node + " hasn't been marked as in progress.");
		}
		nodesInProgress.remove(node);
		tree.removeLeaf(node);
	}

	/**
	 * Informiert über die fehlgschlagene Verarbeitung eines <code>DependencyNode</code>s.
	 * 
	 * @param node - der nicht verarbeitete <code>DependencyNode</code>
	 */
	public void notifyProcessingFailed(DependencyNode<T> node) {
		if (!nodesInProgress.contains(node)) {
			throw new RuntimeException("Node " + node + " hasn't been marked as in progress.");
		}
		nodesInProgress.remove(node);
		failedNodes.add(node);
	}

	/**
	 * Gibt die nächsten zu verarbeitenden <code>DependencyNode</code>s zurück. Gibt ein leeres <code>Set</code> zurück,
	 * wenn derzeit keine <code>DependencyNodes</code> verarbeitet werden können.
	 * 
	 * @return die nächsten zu verarbeitenden <code>DependencyNode</code>s.
	 */
	public Set<DependencyNode<T>> retrieveProcessableNodes() {
		Set<DependencyNode<T>> leafs = new HashSet<DependencyNode<T>>();
		for (DependencyNode<T> leaf : tree.getLeafs()) {
			if (!nodesInProgress.contains(leaf) && !failedNodes.contains(leaf)) {
				leafs.add(leaf);
				nodesInProgress.add(leaf);
			}
		}
		return leafs;
	}

	/**
	 * Liefert <code>true</code> wenn alle Knoten des Baums verarbeitet wurden.
	 * 
	 * @return <code>true</code> wenn alle Knoten des Baums verarbeitet wurden.
	 */
	public boolean done() {
		if (tree.isEmpty()) {
			return true;
		}
		return tree.getLeafs().equals(failedNodes);
	}
}
