package at.arz.latte.rodeo.release;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * an application is - like component - a releaseable unit and is deployable.
 * 
 * @author mrodler
 */
@Entity
@NamedQueries({ @NamedQuery(name = Application.SELECT_ALL, query = "select o from Application o ") })
@DiscriminatorValue("APPLICATION")
public class Application
		extends Component {

	private static final long serialVersionUID = 1L;

	public static final String SELECT_ALL = "Application.selectAll";

	protected Application() {
		// jpa constructor
	}

	public Application(String applicationName) {
		super(applicationName);
	}

	@Override
	public String toString() {
		return "Application [id=" + getId() + ", applicationName=" + getName() + "]";
	}

}
