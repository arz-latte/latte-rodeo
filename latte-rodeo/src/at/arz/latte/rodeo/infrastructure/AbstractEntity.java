package at.arz.latte.rodeo.infrastructure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	@Column(name = "EVERSION")
	private long entityVersion;
}
