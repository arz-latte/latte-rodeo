package at.arz.latte.rodeo.scm.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.scm.Scm;
import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.ScmType;

public class FindScmRepositories
		implements RodeoQuery<GetScmRepositoriesResult> {
	private enum Order {ASC, DESC};

	private ScmType type;
	private ScmName name;
	private ScmLocation location;
	private String order = "order by o.name asc";

	public void orderByName(Order order) {
		this.order = "order by o.name " + order.name().toLowerCase();
	}
	
	public void orderByLocation(Order order) {
		this.order = "order by o.location " + order.name().toLowerCase();
	}

	public FindScmRepositories with(ScmName name) {
		this.name = name;
		return this;
	}

	public FindScmRepositories with(ScmLocation location) {
		this.location = location;
		return this;
	}

	public FindScmRepositories with(ScmType type) {
		this.type = type;
		return this;
	}

	@Override
	public GetScmRepositoriesResult execute(EntityManager entityManager) {
		TypedQuery<Scm> query = buildQuery(entityManager);
		setOptionalParam(query, "type", type);
		setOptionalParam(query, "name", name);
		setOptionalParam(query, "location", location);
		List<Scm> list = query.getResultList();
		ArrayList<ScmResult> locations = new ArrayList<ScmResult>();
		for (Scm scm : list) {
			locations.add(new ScmResult(scm.getType(), scm.getName(), scm.getLocation()));
		}
		return new GetScmRepositoriesResult(locations);
	}

	private void setOptionalParam(TypedQuery<Scm> query, String name, Object value) {
		if (value != null) {
			query.setParameter(name, value);
		}
	}

	private TypedQuery<Scm> buildQuery(EntityManager entityManager) {
		if (name == null && type == null && location == null) {
			return entityManager.createNamedQuery(Scm.FIND_ALL, Scm.class);
		}
		StringBuilder builder = new StringBuilder("select o from Scm o where ");
		if (type == null) {
			builder.append(nameQuery());
		} else {
			String nameQuery = nameQuery();
			if (nameQuery.isEmpty()) {
				builder.append("o.type=:type");
			} else {
				builder.append("o.type=:type and (");
				builder.append(nameQuery);
				builder.append(")");
			}
		}
		builder.append(" ");
		builder.append(order);
		return entityManager.createQuery(builder.toString(), Scm.class);

	}

	private String nameQuery() {
		if (name == null) {
			return locationQuery();
		}
		String expression = "o.name like :name";
		String locationQuery = locationQuery();
		return locationQuery.isEmpty() ? expression : expression + " or " + locationQuery;
	}

	private String locationQuery() {
		if (location == null) {
			return "";
		}
		return "o.location like :location";
	}


}
