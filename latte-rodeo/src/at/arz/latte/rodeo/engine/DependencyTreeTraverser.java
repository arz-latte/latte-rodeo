package at.arz.latte.rodeo.engine;

import java.util.Collections;
import java.util.Set;

/**
 * Der <code>DependencyTreeTraverser</code> traversiert den {@link DependencyTree} unter Berücksichtigung der bereits
 * verarbeiteten {@link DependencyNode}s. Er ist auf
 * maximale parallele Verarbeitung optimiert.
 */
public class DependencyTreeTraverser<T> {

	private DependencyTree<T> tree;

	/**
	 * Erzeugt einen neuen <code>DependencyTreeTraverser</code> für den angegebenen <code>DependencyTree</code>.
	 * 
	 * @param tree - der zu verarbeitende <code>DependencyTree</code>
	 */
	public DependencyTreeTraverser(DependencyTree<T> tree) {
		this.tree = tree;
	}

	/**
	 * Informiert über die erfolgreiche Verarbeitung eines <code>DependencyNode</code>s.
	 * 
	 * @param node - der erfolgreich verarbeitete <code>DependencyNode</code>
	 */
	public void notifyProcessingSucceeded(DependencyNode<T> node) {
	}

	/**
	 * Informiert über die fehlgschlagene Verarbeitung eines <code>DependencyNode</code>s.
	 * 
	 * @param node - der nicht verarbeitete <code>DependencyNode</code>
	 */
	public void notifyProcessingFailed(DependencyNode<T> node) {

	}

	/**
	 * Gibt die nächsten zu verarbeitenden <code>DependencyNode</code>s zurück. Gibt ein leeres <code>Set</code> zurück,
	 * wenn derzeit keine <code>DependencyNodes</code> verarbeitet werden können.
	 * 
	 * @return die nächsten zu verarbeitenden <code>DependencyNode</code>s.
	 */
	public Set<DependencyNode<T>> retrieveProcessableNodes() {
		return Collections.emptySet();
	}

}
