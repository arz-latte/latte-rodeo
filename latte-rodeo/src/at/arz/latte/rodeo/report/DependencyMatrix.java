package at.arz.latte.rodeo.report;

import java.util.Arrays;
import java.util.BitSet;

public class DependencyMatrix {

	private BitSet edges[];
	private final int size;

	public DependencyMatrix(DependencyMatrix source) {
		this(source.size);
		for (int i = 0; i < size; i++) {
			this.edges[i].or(source.edges[i]);
		}
	}

	public DependencyMatrix(int size) {
		this.size = size;
		this.edges = new BitSet[size];
		for (int i = 0; i < size; i++) {
			this.edges[i] = new BitSet(size);
		}
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			this.edges[i].clear();
		}
	}

	public DependencyMatrix copy() {
		return new DependencyMatrix(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DependencyMatrix other = (DependencyMatrix) obj;
		return Arrays.deepEquals(edges, other.edges) && size == other.size;
	}

	public boolean hasEdge(int source, int target) {
		return edges[source].get(target);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(edges);
		result = prime * result + size;
		return result;
	}

	/**
	 * Builds the transitive closure for each node.
	 * 
	 * If there is a link from target to dest, source must also link to dest.
	 * 
	 * @return
	 */
	public void produceTransitiveClosure() {
		for (int target = 0; target < size; target++) {
			for (int source = 0; source < size; source++) {
				if (target == source) {
					continue;
				}
				if (edges[source].get(target)) {
					edges[source].or(edges[target]);
				}
			}
		}
	}

	public void removeEdge(int source, int target) {
		edges[source].clear(target);
	}

	/**
	 * removes edges which links same vertex.
	 */
	public void removeReflexiveEdges() {
		for (int i = 0; i < size; i++) {
			edges[i].clear(i);
		}
	}

	public void setEdge(int source, int target) {
		edges[source].set(target);
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (BitSet bs : edges) {
			builder.append("[");
			for (int i = 0; i < size; i++) {
				builder.append(bs.get(i) ? "1" : "0");
				builder.append(",");
			}
			builder.setLength(builder.length() - 1);
			builder.append("],");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * If there is a link from target to destination, the corresponding link
	 * from source to destination is removed.
	 * 
	 * @return
	 */
	public void transitiveReduction() {
		for (int source = 0; source < edges.length; source++) {
			for (int target = 0; target < edges.length; target++) {
				if (edges[target].get(source)) {
					edges[target].andNot(edges[source]);
				}
			}
		}
	}

	public boolean isLeaf(int vertex) {
		return edges[vertex].isEmpty();
	}

	/**
	 * returns true if source is ancestor of target.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public boolean isAncestor(int source, int target) {
		return edges[source].get(target);
	}

	public BitSet getAncestors(int vertex) {
		BitSet ancestors = new BitSet(edges.length);
		for (int source = 0; source < edges.length; source++) {
			if (edges[source].get(vertex)) {
				ancestors.set(source);
			}
		}
		return ancestors;
	}

	public BitSet getLeafs() {
		BitSet leafs = new BitSet(edges.length);
		for (int source = 0; source < edges.length; source++) {
			if (edges[source].isEmpty()) {
				leafs.set(source);
			}
		}
		return leafs;
	}

	public BitSet getAncestors(BitSet query) {
		BitSet ancestors = new BitSet(edges.length);
		for (int i = query.nextSetBit(0); i >= 0; i = query.nextSetBit(i + 1)) {
			for (int source = 0; source < edges.length; source++) {
				if (edges[source].get(i)) {
					ancestors.set(source);
				}
			}
		}
		return ancestors;
	}

	/**
	 * returns a vertices which would become leafes, if the given set of leaves would be removed.
	 * 
	 * @param leafs
	 * @return
	 */
	public BitSet findAncestorLeafs(BitSet leafs) {
		BitSet ancestorLeafs = new BitSet(edges.length);
		for (int i = 0; i < edges.length; i++) {
			if (leafs.get(i)) {
				continue;
			}
			if (isResolved(i, leafs)) {
				ancestorLeafs.set(i);
			}
		}
		return ancestorLeafs;
	}

	/**
	 * returns true if the set knownVertices contains all required vertices of the given vertex v.
	 * 
	 * @param v
	 * @param knownVertices
	 * @return
	 */
	boolean isResolved(int v, BitSet knownVertices) {
		BitSet tmp = new BitSet(size);
		tmp.or(edges[v]);
		tmp.andNot(knownVertices);
		return tmp.isEmpty();
	}
}