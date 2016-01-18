package at.arz.latte.rodeo.engine.graph;

public class GraphBuilder<V, G extends Graph<V>> {

	private G graph;
	
	public GraphBuilder(G graph) {
		this.graph = graph;
	}

	public GraphBuilder<V, G> addEdge(V from, V to) {
		graph.addEdge(from, to);
		return this;
	}
	
	public GraphBuilder<V, G> fromGraph(G template) {
		for (Edge<V> edge : template.getEdges()) {
			graph.addEdge(edge.getFrom(), edge.getTo());
		}
		return this;
	}

	public G build() {
		if (graph == null) {
			throw new RuntimeException("Builder can be used only once.");
		}
		try {
			return graph;
		} finally {
			graph = null;
		}
	}
}
