package at.arz.latte.rodeo.engine;



public final class DependencyTreeMother {

	private DependencyTreeMother() {
		// Hide utility class constructor
	}

	/**
	 * Creates the following graph:
	 * 
	 * <pre>
	 *     A
	 *    / \
	 *   B   C
	 *  / \ / \
	 * D   E   F
	 *  \  |   |
	 *   \ G   H
	 *    \|
	 *     I
	 * </pre>
	 * 
	 * @return A String Graph for testing purposes.
	 */
	public static DependencyTree<String> createTree() {
		DependencyTreeBuilder<String> builder = new DependencyTreeBuilder<String>();
		builder.addEdge(node("A"), node("B"));
		builder.addEdge(node("A"), node("C"));
		builder.addEdge(node("B"), node("D"));
		builder.addEdge(node("B"), node("E"));
		builder.addEdge(node("C"), node("E"));
		builder.addEdge(node("C"), node("F"));
		builder.addEdge(node("D"), node("I"));
		builder.addEdge(node("E"), node("G"));
		builder.addEdge(node("F"), node("H"));
		builder.addEdge(node("G"), node("I"));
		return builder.build();
	}

	private static StringDependencyNode node(String item) {
		return new StringDependencyNode(item);
	}
}
