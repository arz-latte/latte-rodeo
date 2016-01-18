package at.arz.latte.rodeo.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DependencyTreeTraverserTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void process_successful_executions() {
		DependencyTree<String> tree = DependencyTreeMother.createTree();
		DependencyTreeTraverser<String> traverser = new DependencyTreeTraverser<String>(tree);
		assertFalse(traverser.done());

		Set<DependencyNode<String>> processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 2);
		assertTrue(processableNodes.contains(new StringDependencyNode("H")));
		assertTrue(processableNodes.contains(new StringDependencyNode("I")));

		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.isEmpty());

		traverser.notifyProcessingSucceeded(new StringDependencyNode("I"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 2);
		assertTrue(processableNodes.contains(new StringDependencyNode("G")));
		assertTrue(processableNodes.contains(new StringDependencyNode("D")));

		traverser.notifyProcessingSucceeded(new StringDependencyNode("D"));
		traverser.notifyProcessingSucceeded(new StringDependencyNode("H"));
		traverser.notifyProcessingSucceeded(new StringDependencyNode("G"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 2);
		assertTrue(processableNodes.contains(new StringDependencyNode("E")));
		assertTrue(processableNodes.contains(new StringDependencyNode("F")));

		traverser.notifyProcessingSucceeded(new StringDependencyNode("E"));
		traverser.notifyProcessingSucceeded(new StringDependencyNode("F"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 2);
		assertTrue(processableNodes.contains(new StringDependencyNode("B")));
		assertTrue(processableNodes.contains(new StringDependencyNode("C")));

		traverser.notifyProcessingSucceeded(new StringDependencyNode("B"));
		traverser.notifyProcessingSucceeded(new StringDependencyNode("C"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 1);
		assertTrue(processableNodes.contains(new StringDependencyNode("A")));

		traverser.notifyProcessingSucceeded(new StringDependencyNode("A"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.isEmpty());
		assertTrue(traverser.done());
	}

	@Test
	public void process_execution_with_error() {
		DependencyTree<String> tree = DependencyTreeMother.createTree();
		DependencyTreeTraverser<String> traverser = new DependencyTreeTraverser<String>(tree);
		assertFalse(traverser.done());

		Set<DependencyNode<String>> processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 2);
		assertTrue(processableNodes.contains(new StringDependencyNode("H")));
		assertTrue(processableNodes.contains(new StringDependencyNode("I")));

		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.isEmpty());

		traverser.notifyProcessingSucceeded(new StringDependencyNode("I"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 2);
		assertTrue(processableNodes.contains(new StringDependencyNode("G")));
		assertTrue(processableNodes.contains(new StringDependencyNode("D")));

		traverser.notifyProcessingFailed(new StringDependencyNode("H")); // F, C, A cannot be built

		traverser.notifyProcessingSucceeded(new StringDependencyNode("D"));
		traverser.notifyProcessingSucceeded(new StringDependencyNode("G"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 1);
		assertTrue(processableNodes.contains(new StringDependencyNode("E")));

		traverser.notifyProcessingSucceeded(new StringDependencyNode("E"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.size() == 1);
		assertTrue(processableNodes.contains(new StringDependencyNode("B")));

		traverser.notifyProcessingSucceeded(new StringDependencyNode("B"));
		processableNodes = traverser.retrieveProcessableNodes();
		assertTrue(processableNodes.isEmpty());
		assertTrue(traverser.done());
	}

	@Test
	public void succeeded_of_node_not_marked_as_in_progress_throws_exception() {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Node DependencyNode [item=A] hasn't been marked as in progress.");
		DependencyTree<String> tree = DependencyTreeMother.createTree();
		DependencyTreeTraverser<String> traverser = new DependencyTreeTraverser<String>(tree);
		traverser.notifyProcessingSucceeded(new StringDependencyNode("A"));
	}

	@Test
	public void failed_of_node_not_marked_as_in_progress_throws_exception() {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Node DependencyNode [item=A] hasn't been marked as in progress.");
		DependencyTree<String> tree = DependencyTreeMother.createTree();
		DependencyTreeTraverser<String> traverser = new DependencyTreeTraverser<String>(tree);
		traverser.notifyProcessingFailed(new StringDependencyNode("A"));
	}
}
