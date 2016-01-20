package at.arz.latte.rodeo.ivy;

public class IvyRevision {

	private final Revision revision;
	private final Revision baseline;
	private final String original;

	public IvyRevision(String baseline) {
		this.revision = new Revision(baseline);
		this.baseline = new Revision(baseline);
		this.original = baseline;
	}

	public void performMajorUpdate() {
		if (baseline.major == revision.major) {
			revision.incMajor();
		}
	}

	public void performMinorUpdate() {
		if (baseline.major == revision.major && baseline.minor == revision.minor) {
			revision.incMinor();
		}
	}

	public String getRevisionString() {
		return revision.major + "." + revision.minor + ".0";
	}

	public String getRevisionWildcardString() {
		return revision.major + "." + revision.minor + ".+";
	}

	public String getOriginal() {
		return original;
	}

	@Override
	public String toString() {
		return getRevisionString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IvyRevision)) {
			return false;
		}
		IvyRevision other = (IvyRevision) obj;
		return revision.major == other.revision.major && revision.minor == other.revision.minor;
	}

	public static class Revision {

		private int major;
		private int minor;

		public Revision(String revision) {
			String[] split = revision.split("\\.");
			major = Integer.parseInt(split[0]);
			minor = Integer.parseInt(split[1]);
		}

		public void incMajor() {
			major++;
			minor = 0;
		}

		public void incMinor() {
			minor++;
		}
	}
}
