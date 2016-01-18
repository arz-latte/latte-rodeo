package at.arz.latte.rodeo.engine;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import at.arz.latte.rodeo.engine.graph.CollectVisitor;
import at.arz.latte.rodeo.engine.graph.VertexNameProvider;

public class BreadthFirstTraversalTest {

	@Test
	public void leafTest() {
		DependencyTree<String> tree = DependencyTreeObjectMother.createTree();
		System.out.println(tree.getLeafs());
	}

	@Test
	public void preorder() {
		DependencyTree<String> tree = DependencyTreeObjectMother.createTree();
		CollectVisitor<DependencyNode<String>> visitor = new CollectVisitor<DependencyNode<String>>();
		tree.breadthFirstTraversal(visitor);
		List<DependencyNode<String>> nodes = visitor.getNodes();
		assertOrder(nodes, "A,C,B,E,F,D,G,H,I");
		System.out.println(tree.getDot(new VertexNameProvider<DependencyNode<String>>() {

			@Override
			public String getVertexName(DependencyNode<String> vertex) {
				return vertex.getItem();
			}
		}));
	}

	private void assertOrder(List<DependencyNode<String>> nodes, String expected) {
		List<String> expectedList = Arrays.asList(expected.split(","));
		assertEquals(expectedList.size(), nodes.size());
		for (int i = 0; i < expectedList.size(); i++) {
			assertEquals(expectedList.get(i), nodes.get(i).getItem());
		}
	}
}
