package at.arz.latte.rodeo.project;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import javax.enterprise.inject.spi.BeanManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.project.admin.CreateProject;
import at.arz.latte.rodeo.project.admin.ProjectCreated;
import at.arz.latte.rodeo.test.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class CreateProjectTest {

	private static final String NAME = "name";

	@Mock
	private BeanManager beanManager;
	
	@Mock
	private ProjectRepository repository;

	private CreateProject command;


	@Before
	public void setup() {
		new EventDispatcher(beanManager);
		command = new CreateProject(NAME);
		TestUtil.setField(command, "repository", repository);
	}

	@Test
	public void creates_a_new_project() {
		command.execute();
		ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
		verify(repository).create(captor.capture());
		Project project = captor.getValue();
		assertThat(NAME, is(project.getName()));
	}

	@Test
	public void fires_event() {
		command.execute();
		ArgumentCaptor<ProjectCreated> captor = ArgumentCaptor.forClass(ProjectCreated.class);
		verify(beanManager).fireEvent(captor.capture());
		ProjectCreated value = captor.getValue();
		assertThat(NAME, is(value.getName()));
	}


}
