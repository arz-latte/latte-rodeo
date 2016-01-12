package at.arz.latte.rodeo.api;

import javax.persistence.EntityManager;

public interface RodeoQuery<T> {
	T execute(EntityManager entityManager);
}
