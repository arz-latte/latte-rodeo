package at.arz.latte.rodeo.api;

public interface RodeoFunction<T, R> {
	R apply(T input);
}
