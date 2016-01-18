package at.arz.latte.rodeo.engine;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class Engine<T>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private DependencyTreeTraverser<T> traverser;
	private TreeAction<T> action;

	/**
	 * Diese Queue stellt sicher, dass die Verarbeitungsreihenfolge exakt der vom {@link DependencyTreeTraverser}
	 * vorgegebenen Reihenfolge entspricht.
	 */
	private BlockingQueue<DependencyNode<T>> queue;

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
		this.queue = new LinkedBlockingQueue<DependencyNode<T>>();
	}

	public void execute() {
		Set<DependencyNode<T>> initialNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(initialNodesToProcess);
	}

	protected void schedule(Set<DependencyNode<T>> nodes) {
		queue.addAll(nodes);
		DependencyNode<T> node = queue.poll();
		while (node != null) {
			action.execute(this, node);
			node = queue.poll();
		}
	}

	public void processingFinished(DependencyNode<T> node) {
		synchronized (traverser) {
			traverser.notifyProcessingSucceeded(node);
			Set<DependencyNode<T>> nextNodesToProcess = traverser.retrieveProcessableNodes();
			schedule(nextNodesToProcess);
		}
	}

	public void processingFailed(DependencyNode<T> node) {
		synchronized (traverser) {
			traverser.notifyProcessingFailed(node);
			Set<DependencyNode<T>> nextNodesToProcess = traverser.retrieveProcessableNodes();
			schedule(nextNodesToProcess);
		}
	}

	public void cancelAll() {
		// No action required. Do not schedule further nodes. Already scheduled nodes will be completed
		queue.clear();
	}


}
