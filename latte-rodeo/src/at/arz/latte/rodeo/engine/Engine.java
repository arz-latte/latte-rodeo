package at.arz.latte.rodeo.engine;

import java.io.Serializable;
import java.util.Set;



public class Engine<T>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private DependencyTreeTraverser<T> traverser;
	private TreeAction<T> action;

	/*
	 * 
	 * Engine ermittelt aus dem Dependency die ideale Reihenfolge um
	 * eine Action möglichst parallel, jedoch unter Berücksichtigung der
	 * Abhängigkeiten auszuführen.
	 */
	public Engine(DependencyTree<T> tree, TreeAction<T> action) {
		this(new DependencyTreeTraverser<T>(tree), action);
	}

	Engine(DependencyTreeTraverser<T> traverser, TreeAction<T> action) {
		this.traverser = traverser;
		this.action = action;
	}

	public void execute() {
		Set<DependencyNode<T>> initialNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(initialNodesToProcess);
	}

	protected void schedule(Set<DependencyNode<T>> nodes) {
		for (DependencyNode<T> node : nodes) {
			action.execute(this, node);
		}
	}

	public void processingFinished(DependencyNode<T> node) {
		traverser.notifyProcessingSucceeded(node);
		Set<DependencyNode<T>> nextNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(nextNodesToProcess);
	}

	public void processingFailed(DependencyNode<T> node) {
		traverser.notifyProcessingFailed(node);
		Set<DependencyNode<T>> nextNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(nextNodesToProcess);
	}

	public void cancelAll() {
		// No action required. Do not schedule further nodes. Already scheduled nodes will be completed
	}


}
