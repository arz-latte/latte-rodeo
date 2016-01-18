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

	@XmlElement(name = "item")
	private List<DirItem> items;

	protected DirListResult() {
		// tool constructor
	}

	public DirListResult(String path, List<DirItem> items) {
		Objects.requireNonNull(path, "path required");
		Objects.requireNonNull(items, "items required");
		this.path = path;
		this.items = items;
		// tool constructor
	}

	public List<DirItem> getItems() {
		return items;
	}

	public String getPath() {
		return path;
	}
}
