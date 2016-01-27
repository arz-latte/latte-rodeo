package at.arz.latte.rodeo.engine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class EngineTest {

	private Set<DependencyNode<String>> initialNodes;
	private Set<DependencyNode<String>> nextNodes;

	private DependencyTreeTraverser<String> traverser;

	private Engine<String> engine;
	private TreeAction<String> action;

	@Before
	public void initTestDoubles() {
		initialNodes = DependencyNodeMother.createNodes("INITIAL", 3);
		nextNodes = DependencyNodeMother.createNodes("NEXT", 2);
		
		traverser = mock(DependencyTreeTraverser.class);
		when(traverser.retrieveProcessableNodes()).thenReturn(initialNodes)
													.thenReturn(nextNodes)
													.thenReturn(Collections.<DependencyNode<String>> emptySet());
	}

	@Test
	public void skip_processing_of_further_nodes_after_cancel_all_event() {
		action = DependencyTreeActionMother.cancelAllAction();
		engine = new Engine<String>(traverser, action);
		engine.execute();
		
		// Verify retrieval of initial nodes.
		verify(traverser).retrieveProcessableNodes();
		// Verify invocation of first node.
		verify(action).execute(engine, initialNodes.iterator().next());
		// Keine weiteren Interaktionen, da die action sofort cancelAll() ausgelöst hat!
		verifyNoMoreInteractions(traverser, action);

	}

	@Test
	public void try_to_process_as_most_nodes_as_possible_if_processing_of_single_node_has_failed() {
		// Create an action that can process the initial nodes successfully, but fails to process
		// the next sequence of nodes.
		action = DependencyTreeActionMother.processingFailedAction(nextNodes);

		// Create engine and execute action
		engine = new Engine<String>(traverser, action);
		engine.execute();

		verify(traverser, times(1 + initialNodes.size() + nextNodes.size())).retrieveProcessableNodes();
		// Verify successful processing of initial nodes
		for(DependencyNode<String> initial : initialNodes){
			verify(action).execute(engine, initial);
			verify(traverser).notifyProcessingSucceeded(initial);
		}

		// Verify faild processing of next nodes.
		for (DependencyNode<String> next : nextNodes) {
			verify(action).execute(engine, next);
			verify(traverser).notifyProcessingFailed(next);
		}
		verifyNoMoreInteractions(traverser, action);

	}

	@Test
	public void process_all_nodes_as_if_processing_of_single_node_has_failed() {
		// Create an action that can process every node successfully
		action = DependencyTreeActionMother.processingSuccededAction();

		// Create engine and execute action
		engine = new Engine<String>(traverser, action);
		engine.execute();

		// Verify retrieval of initial nodes.
		verify(traverser, times(1 + initialNodes.size() + nextNodes.size())).retrieveProcessableNodes();
		// Verify successful processing of initial nodes
		for(DependencyNode<String> initial : initialNodes){
			verify(action).execute(engine, initial);
			verify(traverser).notifyProcessingSucceeded(initial);
		}

		// Verify successful processing of next nodes
		for (DependencyNode<String> next : nextNodes) {
			verify(action).execute(engine, next);
			verify(traverser).notifyProcessingSucceeded(next);
		}
		
		verifyNoMoreInteractions(traverser);


	}

}
