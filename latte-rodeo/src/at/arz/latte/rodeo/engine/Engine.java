package at.arz.latte.rodeo.engine;

import java.io.Serializable;



public class Engine
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private DependencyTree tree;
	private TreeAction treeAction;

	/*
	 * 
	 * Engine ermittelt aus dem Dependency die ideale Reihenfolge um
	 * eine Action möglichst parallel, jedoch unter Berücksichtigung der
	 * Abhängigkeiten auszuführen.
	 */

	public Engine(DependencyTree tree, TreeAction action) {
		this.tree = tree;
		this.treeAction = action;
	}

	protected void execute(DependencyNode node) {
		treeAction.execute(node);
	}

	public void processingFinished(DependencyNode node) {
		// achtung, processingFinished kann auch während der Action Ausführung aufgerufen werden.
	}

	public void processingFailed(DependencyNode node) {
		// achtung, processingFailed kann auch während der Action Ausführung aufgerufen werden.
	}

	public void cancelAll() {

	}


}
