package at.arz.latte.rodeo.report;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DependencyMatrixTest {

	private static final int SIZE = 5;
	private DependencyMatrix matrix;

	private DependencyMatrix emptyMatrix;

	@Test
	public void clear_resets_matrix() {
		matrix.setEdge(0, 1);
		matrix.clear();
		assertThat(matrix, is(emptyMatrix));
	}

	private void createSimpleGraph() {
		matrix.setEdge(0, 1);
		matrix.setEdge(0, 2);
		matrix.setEdge(2, 3);
		matrix.setEdge(3, 4);
		matrix.setEdge(4, 1);
	}

	@Test
	public void howto_iterate_all_leafs_test() {
		createSimpleGraph();

		List<String> levels = new ArrayList<String>();
		BitSet knownLeafs = matrix.getLeafs();
		levels.add(knownLeafs.toString());

		BitSet newLeafs = matrix.findAncestorLeafs(knownLeafs);
		while (!newLeafs.isEmpty()) {
			levels.add(newLeafs.toString());
			knownLeafs.or(newLeafs);
			newLeafs = matrix.findAncestorLeafs(knownLeafs);
		}

		assertThat(levels.get(0), is("{1}"));
		assertThat(levels.get(1), is("{4}"));
		assertThat(levels.get(2), is("{3}"));
		assertThat(levels.get(3), is("{2}"));
		assertThat(levels.get(4), is("{0}"));
	}

	@Test
	public void howto_use_transitive_reduction_test() {
		createSimpleGraph();
		matrix.setEdge(0, 0); // connect a vertex with itself
		assertThat(matrix.toString(), is("[1,1,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[0,0,0,0,1],[0,1,0,0,0]"));
		matrix.removeReflexiveEdges();
		assertThat(matrix.toString(), is("[0,1,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[0,0,0,0,1],[0,1,0,0,0]"));
		matrix.produceTransitiveClosure();
		assertThat(matrix.toString(), is("[0,1,1,1,1],[0,0,0,0,0],[0,1,0,1,1],[0,1,0,0,1],[0,1,0,0,0]"));
		matrix.transitiveReduction();
		assertThat(matrix.toString(), is("[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[0,0,0,0,1],[0,1,0,0,0]"));
		matrix.clear();
		assertThat(matrix.toString(), is("[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]"));
	}

	@Test
	public void isAncestor() {
		createSimpleGraph();
		assertTrue(matrix.isAncestor(0, 1));
		assertTrue(matrix.isAncestor(4, 1));
		assertTrue(matrix.isAncestor(0, 2));
		assertTrue(matrix.isAncestor(2, 3));
		assertTrue(matrix.isAncestor(3, 4));
	}

	@Test
	public void node_is_leaf() {
		createSimpleGraph();
		assertFalse(matrix.isLeaf(0));
		assertTrue(matrix.isLeaf(1));
	}

	@Test
	public void pathMatrix_creates_transitive_closure() {
		matrix.setEdge(0, 1);
		matrix.setEdge(1, 2);
		matrix.setEdge(2, 3);
		matrix.produceTransitiveClosure();
		assertThat(matrix.hasEdge(0, 2), is(true));
		assertThat(matrix.hasEdge(0, 3), is(true));
		assertThat(matrix.hasEdge(1, 3), is(true));
	}

	@Test
	public void reflexiveReduce_removes_links_to_self() {
		for (int i = 0; i < matrix.size(); i++) {
			matrix.setEdge(i, i);
		}
		matrix.removeReflexiveEdges();
		assertThat(matrix, is(emptyMatrix));
	}

	@Test
	public void return_all_direct_known_ancestors_of_a_vertex() {
		createSimpleGraph();
		assertThat(matrix.getAncestors(1).toString(), is("{0, 4}"));
		assertThat(matrix.getAncestors(2).toString(), is("{0}"));
		assertThat(matrix.getAncestors(3).toString(), is("{2}"));
		assertThat(matrix.getAncestors(4).toString(), is("{3}"));
	}

	@Test
	public void return_all_direct_known_ancestors_of_a_vertex_set() {
		createSimpleGraph();
		BitSet query = new BitSet();
		query.set(1);
		query.set(2);
		assertThat(matrix.getAncestors(query).toString(), is("{0, 4}"));
	}

	@Test
	public void return_leafs_of_graph() {
		createSimpleGraph();
		assertThat(matrix.getLeafs().toString(), is("{1}"));
	}

	@Before
	public void setup() {
		matrix = new DependencyMatrix(SIZE);
		emptyMatrix = new DependencyMatrix(SIZE);
	}

	@Test
	public void transitiveReduction_removes_unnecessary_edges() {
		matrix.setEdge(0, 1);
		matrix.setEdge(1, 2);
		matrix.setEdge(0, 2);
		matrix.transitiveReduction();
		assertThat(matrix.hasEdge(0, 2), is(false));
	}

}
