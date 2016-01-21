package at.arz.latte.rodeo.engine.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstTraversal<V>
		extends Traversal<V> {

	private final Graph<V> graph;

	public BreadthFirstTraversal(Graph<V> graph) {
		this.graph = graph;
	}

	@Override
	public void traverse(V vertex, Visitor<V> visitor) {
		Queue<V> queue = new LinkedList<V>();
		Set<V> visited = new HashSet<V>();

		queue.add(vertex);

		while (!queue.isEmpty()) {
			V next = queue.remove();
			visited.add(vertex);
			visitor.visit(next);
			queueUnvisitedChildren(queue, visited, next);
		}
	}

	private void queueUnvisitedChildren(Queue<V> queue, Set<V> visited, V next) {
		Set<V> children = graph.getChildren(next);
		for (V child : children) {
			Set<V> parents = graph.getParents(child);
			if (visited.containsAll(parents) && visited.add(child)) {
				queue.add(child);
			}
		}
	}
}
