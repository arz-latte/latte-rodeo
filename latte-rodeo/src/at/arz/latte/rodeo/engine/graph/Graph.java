package at.arz.latte.rodeo.engine.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class Graph<V>
		implements Cloneable {

	private Set<V> vertexes;
	@XmlElement
	private Set<Edge<V>> edges;
	private Map<V, Set<V>> children;
	private Map<V, Set<V>> parents;
	
	public Graph() {
		vertexes = new HashSet<V>();
		edges = new HashSet<Edge<V>>();
		children = new HashMap<V, Set<V>>();
		parents = new HashMap<V, Set<V>>();
	}
	
	public void addEdge(V from, V to) {
		addEdge(new Edge<V>(from, to));
	}
	
	public Set<V> getVertexes() {
		return new HashSet<V>(vertexes);
	}

	public Set<Edge<V>> getEdges() {
		return new HashSet<Edge<V>>(edges);
	}
	
	public Set<V> getChildren(V parent) {
		Set<V> childrenSet = children.get(parent);
		if (childrenSet == null) {
			return Collections.emptySet();
		}
		return childrenSet;
	}
	
	public boolean hasChildren(V parent) {
		return !getChildren(parent).isEmpty();
	}
	
	public Set<V> getParents(V child) {
		Set<V> parentSet = parents.get(child);
		if (parentSet == null) {
			return Collections.emptySet();
		}
		return parentSet;
	}
	
	public boolean hasParents(V child) {
		return !getParents(child).isEmpty();
	}
	
	public Set<V> getNeighbours(V vertex) {
		Set<V> neighbours = new HashSet<V>();
		neighbours.addAll(getChildren(vertex));
		neighbours.addAll(getParents(vertex));
		return neighbours;
	}
	
	public boolean isLeaf(V vertex) {
		return !hasChildren(vertex);
	}

	public Set<V> getLeafs() {
		Set<V> leafs = new HashSet<V>();
		for (V vertex : vertexes) {
			if (isLeaf(vertex)) {
				leafs.add(vertex);
			}
		}
		return leafs;
	}

	public void removeLeaf(V vertex) {
		if (!isLeaf(vertex)) {
			throw new RuntimeException(vertex + " is not a leaf.");
		}
		vertexes.remove(vertex);
		edges.removeAll(getEdges(vertex));
		parents.remove(vertex);
		Iterator<V> iterator = children.keySet().iterator();
		while (iterator.hasNext()) {
			V parent = iterator.next();
			children.get(parent).remove(vertex);
			if (children.get(parent).isEmpty()) {
				iterator.remove();
			}
		}
	}

	public void breadthFirstTraversal(V vertex, Visitor<V> visitor) {
		new BreadthFirstTraversal<V>(this).traverse(vertex, visitor);
	}
	
	public void depthFirstTraversal(V vertex, Visitor<V> visitor) {
		new DepthFirstTraversal<V>(this).traverse(vertex, visitor);
	}

	public boolean isEmpty() {
		return vertexes.isEmpty();
	}

	public String getDot() {
		return getDot(new VertexNameProvider<V>() {

			@Override
			public String getVertexName(V vertex) {
				return vertex.toString();
			}
		});
	}
	
	public String getDot(VertexNameProvider<V> nameProvider) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		for (V vertex : vertexes) {
			sb.append("  \"").append(nameProvider.getVertexName(vertex)).append("\";\n");
		}
		sb.append("\n");
		for (Edge<V> edge : edges) {
			sb.append("  \"")
				.append(nameProvider.getVertexName(edge.getFrom()))
				.append("\" -> \"")
				.append(nameProvider.getVertexName(edge.getTo()))
				.append("\";\n");
		}
		sb.append("}\n");
		return sb.toString();
	}
	
	@Override
	public Graph<V> clone() {
		Graph<V> clone = new Graph<V>();
		for (Edge<V> edge : edges) {
			clone.addEdge(edge);
		}
		return clone;
	}
	
	@Override
	public String toString() {
		return vertexes + " " + edges;
	}
	
	@Override
	public int hashCode() {
		return edges.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Graph && equals((Graph<?>) obj);
	}

	public boolean equals(Graph<?> graph) {
		return graph != null && edges.equals(graph.edges);
	}

	private void addEdge(Edge<V> edge) {
		V from = edge.getFrom();
		V to = edge.getTo();
		edges.add(edge);
		vertexes.add(from);
		vertexes.add(to);
		addToChildren(from, to);
		addToParents(from, to);
	}
	
	private void addToChildren(V from, V to) {
		Set<V> childrenSet = children.get(from);
		if (childrenSet == null) {
			childrenSet = new HashSet<V>();
			children.put(from, childrenSet);
		}
		childrenSet.add(to);
	}
	
	private void addToParents(V from, V to) {
		Set<V> parentSet = parents.get(to);
		if (parentSet == null) {
			parentSet = new HashSet<V>();
			parents.put(to, parentSet);
		}
		parentSet.add(from);
	}

	private Set<Edge<V>> getEdges(V vertex) {
		Set<Edge<V>> containingEdges = new HashSet<Edge<V>>();
		for (Edge<V> edge : edges) {
			if (edge.getFrom().equals(vertex) || edge.getTo().equals(vertex)) {
				containingEdges.add(edge);
			}
		}
		return containingEdges;
	}
}
