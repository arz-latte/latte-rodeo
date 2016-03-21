package at.arz.latte.rodeo.report;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DependencyMatrixTest {
	private static final int SIZE = 5;
	private DependencyMatrix matrix;

	private DependencyMatrix emptyMatrix;

	@Before
	public void setup() {
		matrix = new DependencyMatrix(SIZE);
		emptyMatrix = new DependencyMatrix(SIZE);
	}

	@Test
	public void clear_resets_matrix() {
		matrix.setEdge(0, 1);
		matrix.clear();
		assertThat(matrix, is(emptyMatrix));
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
	public void transitiveReduction_removes_unnecessary_edges() {
		matrix.setEdge(0, 1);
		matrix.setEdge(1, 2);
		matrix.setEdge(0, 2);
		matrix.transitiveReduction();
		assertThat(matrix.hasEdge(0, 2), is(false));
	}
	

	@Test
	public void complex_test() {
		matrix.setEdge(0, 0);
		matrix.setEdge(0, 1);
		matrix.setEdge(0, 2);
		matrix.setEdge(2, 3);
		matrix.setEdge(3, 4);
		matrix.setEdge(4, 1);
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
	public void diamond() {
		matrix = new DependencyMatrix(6);
		matrix.setEdge(0, 1);
		matrix.setEdge(0, 2);
		matrix.setEdge(1, 3);
		matrix.setEdge(3, 2);
		matrix.setEdge(2, 4);
		matrix.setEdge(4, 5);
		matrix.setEdge(0, 5);
		System.out.println(matrix.toString());
		matrix.removeReflexiveEdges();
		System.out.println(matrix.toString());
		matrix.produceTransitiveClosure();
		System.out.println(matrix.toString());
		matrix.transitiveReduction();
		System.out.println(matrix.toString());
	}

}
