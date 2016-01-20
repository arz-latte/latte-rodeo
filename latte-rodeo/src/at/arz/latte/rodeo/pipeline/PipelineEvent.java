package at.arz.latte.rodeo.pipeline;

import java.util.Objects;

public class PipelineEvent {

	public enum Status {
						CREATED,
						WAITING,
						ACTIVE,
						PAUSED,
						CANCELLED,
						FAILED,
						SUCCESS
	}

	private ListenerReference reference;
	private Status status;

	public PipelineEvent(ListenerReference reference, Status status) {
		this.reference = reference;
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isFor(ListenerReference reference) {
		return Objects.equals(this.reference, reference);
	}
}
