package at.arz.latte.rodeo.scm.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.ScmType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ScmResult {

	private ScmName name;
	private ScmType type;
	private ScmLocation location;

	protected ScmResult() {
		// tool constructor
	}

	public ScmResult(ScmType type, ScmName name, ScmLocation location) {
		this.type = type;
		this.name = name;
		this.location = location;
	}

	public ScmName getName() {
		return name;
	}

	public ScmType getType() {
		return type;
	}

	public ScmLocation getLocation() {
		return location;
	}

}
