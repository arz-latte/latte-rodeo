package at.arz.latte.rodeo.project;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import at.arz.latte.rodeo.scm.BranchName;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectId {

	private ProjectName name;
	private BranchName branch;

	protected ProjectId() {
		// tool constructor
	}

	public ProjectId(ProjectName name, BranchName branch) {
		super();
		this.name = name;
		this.branch = branch;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectId other = (ProjectId) obj;
		return Objects.equals(name, other.name) && Objects.equals(branch, other.branch);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, branch);
	}

	@Override
	public String toString() {
		return "ProjectId [name=" + name + ", branch=" + branch + "]";
	}

}
