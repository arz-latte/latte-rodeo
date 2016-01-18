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
		visited.add(vertex);
		visitor.visit(vertex);

		while (!queue.isEmpty()) {
			V next = queue.remove();
			for (V neighbour : graph.getNeighbours(next)) {
				if (!visited.contains(neighbour)) {
					queue.add(neighbour);
					visited.add(neighbour);
					visitor.visit(neighbour);
				}
			}
		}
	}
}
