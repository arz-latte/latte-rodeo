package at.arz.latte.rodeo.engine.graph;

import java.util.HashSet;
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

		stack.push(vertex);
		visited.add(vertex);
		visitor.visit(vertex);

		while (!stack.isEmpty()) {
			V next = stack.peek();
			Set<V> unvisitedNeighbours = getUnvisitedNeighbours(graph.getNeighbours(next), visited);
			if (unvisitedNeighbours.isEmpty()) {
				stack.pop();
			}
			for (V neighbour : unvisitedNeighbours) {
				visited.add(neighbour);
				stack.push(neighbour);
				visitor.visit(neighbour);
			}
		}
	}

	private Set<V> getUnvisitedNeighbours(Set<V> neighbours, Set<V> visited) {
		Set<V> unvisited = new HashSet<V>();
		for (V neighbour : neighbours) {
			if (!visited.contains(neighbour)) {
				unvisited.add(neighbour);
			}
		}
		return unvisited;
	}
}
