package at.arz.latte.rodeo.execution;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

/**
 * Entity implementation class for Entity: Job
 * 
 */
@Entity
@Table(name = "JOBS")
public class Job
		implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Status {
		CREATED {

			@Override
			public boolean hasStarted() {
				return false;
			}
		},
		WAITING {

			@Override
			public boolean hasStarted() {
				return false;
			}
		},
		SUCCESS {

			@Override
			public boolean isFinished() {
				return true;
			}
		},
		FAILED {

			@Override
			public boolean isFinished() {
				return true;
			}
		},
		RUNNING {

			public boolean isActive() {
				return true;
			}
		};

		public boolean isActive() {
			return false;
		}

		public boolean hasStarted() {
			return true;
		}

		public boolean isFinished() {
			return false;
		}

	};

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "JOB_IDENTIFIER", unique = true, nullable = false)
	private JobIdentifier identifier;

	@Enumerated(EnumType.STRING)
	@Column(name = "JOB_STATUS", nullable = false)
	private Status status;

	private Timestamp created;

	protected Job() {
		// jpa constructor
	}

	public Job(JobIdentifier identifier) {
		this.identifier = identifier;
		this.status = Status.CREATED;
		this.created = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public JobIdentifier getIdentifier() {
		return identifier;
	}

}
