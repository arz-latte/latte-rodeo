package at.arz.latte.rodeo.scm.admin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.spi.BeanManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import at.arz.latte.rodeo.api.ObjectExists;
import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.scm.Scm;
import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.ScmRepository;
import at.arz.latte.rodeo.scm.ScmType;
import at.arz.latte.rodeo.scm.ScmUserId;
import at.arz.latte.rodeo.test.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class CreateScmTest {

	private static final ScmUserId USER_ID = new ScmUserId("userId");

	private static final ScmType TYPE = new ScmType("type");

	private static final ScmLocation LOCATION = new ScmLocation("location");

	private static final ScmName NAME = new ScmName("scm");

	@Mock
	private BeanManager beanManager;

	@Mock
	private ScmRepository repository;

	private CreateScm command;

	@Before
	public void setup() {
		new EventDispatcher(beanManager);
		command = new CreateScm(NAME, LOCATION, TYPE, USER_ID);
		TestUtil.setField(command, "repository", repository);
	}

	@Test
	public void creates_a_new_scm() {
		command.execute();
		ArgumentCaptor<Scm> captor = ArgumentCaptor.forClass(Scm.class);
		verify(repository).create(captor.capture());
		Scm scm = captor.getValue();
		assertThat(NAME, is(scm.getName()));
		assertThat(LOCATION, is(scm.getLocation()));
		assertThat(TYPE, is(scm.getType()));
		assertThat(USER_ID, is(scm.getUserId()));
	}

	@Test
	public void fires_create_scm_event() {
		command.execute();
		ArgumentCaptor<ScmCreated> captor = ArgumentCaptor.forClass(ScmCreated.class);
		verify(beanManager).fireEvent(captor.capture());
		ScmCreated value = captor.getValue();
		assertThat(LOCATION, is(value.getLocation()));
	}

	@Test(expected = ObjectExists.class)
	public void prevents_duplicate_locations() {
		List<Scm> list = new ArrayList<Scm>();
		list.add(new Scm(new ScmName("other"), LOCATION, TYPE, USER_ID));
		when(repository.findByLocationOrName(LOCATION, NAME)).thenReturn(list);
		command.execute();
	}

	@Test(expected = ObjectExists.class)
	public void prevents_duplicate_names() {
		List<Scm> list = new ArrayList<Scm>();
		list.add(new Scm(NAME, new ScmLocation("OTHER"), TYPE, USER_ID));
		when(repository.findByLocationOrName(LOCATION, NAME)).thenReturn(list);
		command.execute();
	}

}
