package at.arz.latte.rodeo.workspace.restapi;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DirListResult {

	private String path;

	private String parent;

	@XmlElement(name = "item")
	private List<DirItem> items;

	protected DirListResult() {
		// tool constructor
	}

	public DirListResult(String parent, String path, List<DirItem> items) {
		Objects.requireNonNull(parent, "parent required");
		Objects.requireNonNull(path, "path required");
		Objects.requireNonNull(items, "items required");
		this.parent = parent;
		this.path = path;
		this.items = items;
	}

	public List<DirItem> getItems() {
		return items;
	}

	public String getPath() {
		return path;
	}

	public String getParent() {
		return parent;
	}
}
