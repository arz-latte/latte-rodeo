package at.arz.latte.rodeo.workspace.restapi;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DirItem {

	private String name;
	private boolean directory;
	private long size;
	private Date lastModified;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
