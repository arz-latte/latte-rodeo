package at.arz.latte.rodeo.engine;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import at.arz.latte.rodeo.engine.DependencyNode;

public class DependencyNodeMother {

	public static Set<DependencyNode<String>> createNodes(String name, int instances) {
		Set<DependencyNode<String>> nodes = new LinkedHashSet<>();
		for (int i = 0; i < instances; i++) {
			nodes.add(new DependencyNode<String>(name + "_" + i));
		}
		return Collections.unmodifiableSet(nodes);
	}

}
