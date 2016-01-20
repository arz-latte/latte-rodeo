package at.arz.latte.rodeo.engine;


public interface TreeAction<T> {

	void execute(Engine<T> engine, DependencyNode<T> node);
}
