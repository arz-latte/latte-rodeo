package at.arz.latte.rodeo.engine;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DependencyTreeTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void removal_of_non_leaf_throws_exception() {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("DependencyNode [item=G] is not a leaf.");
		DependencyTree<String> tree = DependencyTreeMother.createTree();
		tree.removeLeaf(new StringDependencyNode("G"));
	}

	@Test
	public void removal_of_leaf_succeeds() {
		DependencyTree<String> tree = DependencyTreeMother.createTree();
		StringDependencyNode leaf = new StringDependencyNode("I");
		tree.removeLeaf(leaf);
		assertTrue(!tree.getVertexes().contains(leaf));
		assertTrue(!tree.getLeafs().contains(leaf));
	}
}
