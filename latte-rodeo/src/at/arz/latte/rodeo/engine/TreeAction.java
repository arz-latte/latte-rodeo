package at.arz.latte.rodeo.engine;


/**
 * An action to be performed for a dependency node, such as building a project for example.
 * 
 * @param <T>
 */
public interface TreeAction<T> {

	/**
	 * Executes this action for the given node and notifies the engine about the state of the processing result.
	 * <p>
	 * Invokes {@link Engine#processingFinished(DependencyNode)} if the node has been processed successfully. Invokes
	 * {@link Engine#processingFailed(DependencyNode)} if the procssing of the node has failed. Invokes
	 * {@link Engine#cancelAll()} to cancel the processing of further nodes, if further nodes to be processed are
	 * available.
	 * </p>
	 * 
	 * @param engine - the engine to be notified about the processing result
	 * @param node the node to be processed.
	 */
	void execute(Engine<T> engine, DependencyNode<T> node);
}
