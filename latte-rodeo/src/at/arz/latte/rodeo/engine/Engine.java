package at.arz.latte.rodeo.engine;

import java.io.Serializable;
import java.util.Set;

/**
 * Traverses a depencency tree and peforms an action for each node of the dependency tree.
 * <p>
 * The engine is optimized to process as many nodes in parallel as possible, but invokes the action's execute method
 * sequentially for each node. If the action's execute method returns, the engine invokes the action for the next
 * processable node. The action must notfiy the engine, when the processing of an node is completed. The engine then
 * updates the list of processable nodes.<br />
 * An action must process the node <em>asynchronously</em> to take full advantage of the engines list of processables
 * nodes. That is, an action's execute method must return while the processing of the node is still in progress, so the
 * engine can schedule further processable nodes.
 * </p>
 * 
 * @param <T>
 */
public class Engine<T>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private DependencyTreeTraverser<T> traverser;
	private TreeAction<T> action;
	private volatile boolean cancelled;

	/**
	 * Creates a new engine instance.
	 * 
	 * @param tree - the tree to be processed
	 * @param action - the action to be executed
	 */
	public Engine(DependencyTree<T> tree, TreeAction<T> action) {
		this(new DependencyTreeTraverser<T>(tree), action);
	}

	Engine(DependencyTreeTraverser<T> traverser, TreeAction<T> action) {
		this.traverser = traverser;
		this.action = action;
	}

	/**
	 * Executes the tree action for each node of the depenceny tree, unless the action raises a {@link #cancelAll()}
	 * event.
	 */
	public void execute() {
		Set<DependencyNode<T>> initialNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(initialNodesToProcess);
	}

	protected void schedule(Set<DependencyNode<T>> nodes) {
		for (DependencyNode<T> node : nodes) {
			if (cancelled) {
				return;
			}
			action.execute(this, node);
		}
	}

	/**
	 * Notifies the engine, that the action has processed the given node successfully.
	 * 
	 * @param node - the sucessfully processed node
	 */
	public void processingFinished(DependencyNode<T> node) {
		traverser.notifyProcessingSucceeded(node);
		Set<DependencyNode<T>> nextNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(nextNodesToProcess);
	}

	/**
	 * Notifies the engine, that the action was not able to process the given node.
	 * 
	 * @param node - the node not being processed successfully
	 */
	public void processingFailed(DependencyNode<T> node) {
		traverser.notifyProcessingFailed(node);
		Set<DependencyNode<T>> nextNodesToProcess = traverser.retrieveProcessableNodes();
		schedule(nextNodesToProcess);
	}

	/**
	 * Notifies the engine to stop. The engine does not schedule further nodes but may process nodes already
	 * stored in the internal list of processable nodes.
	 */
	public void cancelAll() {
		// No action required. Do not schedule further nodes. Already scheduled nodes will be completed
		cancelled = true;
	}

}
