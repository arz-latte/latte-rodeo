package at.arz.latte.rodeo.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DependencyTreeTest {

	private DependencyTree<String> tree;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void createTree() {
		tree = DependencyTreeMother.createTree();
	}
	
	@Test
	public void removal_of_non_leaf_throws_exception() {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("DependencyNode [item=G] is not a leaf.");
		tree.removeLeaf(new StringDependencyNode("G"));
	}

	@Test
	public void removal_of_leaf_succeeds() {
		StringDependencyNode leaf = new StringDependencyNode("I");
		tree.removeLeaf(leaf);
		assertTrue(!tree.getVertexes().contains(leaf));
		assertTrue(!tree.getLeafs().contains(leaf));
	}
	
	@Test
	public void equals_tree_mother() {
		assertTrue(tree.equals(tree));
	}
	
	@Test
	public void equals_simple_tree() {
		DependencyTree<String> tree1 = new DependencyTreeBuilder<String>().addEdge(new StringDependencyNode("A"), new StringDependencyNode("B")).build();
		DependencyTree<String> tree2 = new DependencyTreeBuilder<String>().addEdge(new StringDependencyNode("A"), new StringDependencyNode("B")).build();
		assertEquals(tree1, tree2);
	}
	
	@Test
	public void merge() {
		DependencyTree<String> mergeTree = DependencyTreeMother.createTreeForMerge();
		DependencyTree<String> mergeResult = tree.merge(mergeTree);
		assertEquals(DependencyTreeMother.createMergeResultTree(), mergeResult);
		mergeResult = mergeTree.merge(tree);
		assertEquals(DependencyTreeMother.createMergeResultTree(), mergeResult);
	}

	@Test
	public void subtree_creation() {
		DependencyTree<String> expectedSubtree = new DependencyTreeBuilder<String>()
				.addEdge(new StringDependencyNode("A"), new StringDependencyNode("B"))
				.addEdge(new StringDependencyNode("A"), new StringDependencyNode("C"))
				.addEdge(new StringDependencyNode("B"), new StringDependencyNode("E"))
				.addEdge(new StringDependencyNode("C"), new StringDependencyNode("E"))
				.build();
		DependencyTree<String> subtree = tree.getDownstreamSubtree(new StringDependencyNode("E"));
		assertEquals(expectedSubtree, subtree);
	}
}
