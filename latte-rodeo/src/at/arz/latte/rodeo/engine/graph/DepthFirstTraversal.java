package at.arz.latte.rodeo.engine.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DepthFirstTraversal<V>
		extends Traversal<V> {

	private final Graph<V> graph;

	public DepthFirstTraversal(Graph<V> graph) {
		this.graph = graph;
	}

	@Override
	public void traverse(V vertex, Visitor<V> visitor) {
		Stack<V> stack = new Stack<V>();
		Set<V> visited = new HashSet<V>();

		stack.add(vertex);

		while (!stack.isEmpty()) {
			V next = stack.pop();
			if (visited.add(next)) {
				visitor.visit(next);
			}
			stackUnvisitedChildren(visited, next, stack);
		}
	}


	private void stackUnvisitedChildren(Set<V> visited, V node, Stack<V> stack) {
		List<V> unvisited = new LinkedList<>();
		for (V child : graph.getChildren(node)) {
			if (!visited.contains(child)) {
				// Store children in reversed order to keep traversal top-down and left-to-right.
				unvisited.add(0, child);
			}
		}
		// Push the unvisited nodes on top of stack.
		stack.addAll(unvisited);
	}


}
