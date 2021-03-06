package at.arz.latte.rodeo.release;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DependencyTest {

	private ModuleStory runtime;
	private ModuleStory framework;
	private ModuleStory application;

	@Before
	public void setup_test_model() {
		runtime = ModuleStory	.module("at.arz", "runtime")
								.hasRevision("1.0.0")
								.hasRevision("1.0.1")
								.hasRevision("1.0.2");

		framework = ModuleStory	.module("at.arz", "framework")
								.hasRevision("1.1.0")
								.hasRevision("1.1.1")
								.hasRevision("1.1.2");

		application = ModuleStory	.module("at.arz", "application")
									.hasRevision("2.0.0")
									.hasRevision("2.0.1")
									.hasRevision("2.0.2");

	}

	@Test
	public void two_revisions_are_not_same() {
		Revision revision_0 = application.module.getRevision("2.0.0");
		Revision revision_1 = application.module.getRevision("2.0.1");
		assertFalse(revision_0.equals(revision_1));
	}


}
