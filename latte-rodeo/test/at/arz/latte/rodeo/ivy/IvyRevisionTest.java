package at.arz.latte.rodeo.ivy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class IvyRevisionTest {

	private IvyRevision rev;
	
	@Before
	public void createIvyRevision() {
		rev = new IvyRevision("1.2.3-integration17");
	}
	
	@Test
	public void major_updates_only_once() {
		assertEquals("1.2.0", rev.getRevisionString());
		assertEquals("1.2.+", rev.getRevisionWildcardString());
		rev.performMajorUpdate();
		assertEquals("2.0.0", rev.getRevisionString());
		rev.performMajorUpdate();
		assertEquals("2.0.0", rev.getRevisionString());
	}
	
	@Test
	public void minor_updates_only_once() {
		rev.performMinorUpdate();
		assertEquals("1.3.0", rev.getRevisionString());
		rev.performMinorUpdate();
		assertEquals("1.3.0", rev.getRevisionString());
	}
	
	@Test
	public void minor_update_is_ignored_in_favour_of_previous_major_update() {
		rev.performMajorUpdate();
		rev.performMinorUpdate();
		assertEquals("2.0.0", rev.getRevisionString());
	}
	
	@Test
	public void major_update_overwrites_minor_update() {
		rev.performMinorUpdate();
		rev.performMajorUpdate();
		assertEquals("2.0.0", rev.getRevisionString());
	}
	
	@Test
	public void equals() {
		IvyRevision rev1 = new IvyRevision("1.2.0-integration1");
		IvyRevision rev2 = new IvyRevision("1.2.x");
		assertEquals(rev1, rev2);
	}

	@Test
	public void equals_after_update() {
		IvyRevision rev1 = new IvyRevision("2.0.0-integration1");
		IvyRevision rev2 = new IvyRevision("1.2.x");
		rev2.performMajorUpdate();
		assertEquals(rev1, rev2);
	}
}
