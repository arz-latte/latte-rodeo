package at.arz.latte.rodeo.ivy;

import at.arz.latte.rodeo.engine.DependencyNode;


public interface BaselineRepository<T> {

	IvyRevision getBaseline(DependencyNode<T> node);
}
