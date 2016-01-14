package at.arz.latte.rodeo.event;

public class Event {

	public String getEventName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return getEventName();
	}
}
