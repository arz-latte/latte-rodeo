package at.arz.latte.rodeo.project;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import javax.enterprise.inject.spi.BeanManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.project.admin.ProjectCreated;

@RunWith(MockitoJUnitRunner.class)
public class OnCreateProjectTest {

	@Mock
	private BeanManager beanManager;

	@Before
	public void setup() {
		new EventDispatcher(beanManager);
	}

	@Test
	public void event_ProjectCreated_must_be_sendt() {
		new Project("sample");
		verify(beanManager).fireEvent(argThat(isA(ProjectCreated.class)));
	}

}
