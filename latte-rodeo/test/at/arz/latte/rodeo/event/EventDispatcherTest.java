package at.arz.latte.rodeo.event;

import javax.enterprise.inject.spi.BeanManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import at.arz.latte.rodeo.infrastructure.EventDispatcher;


@RunWith(MockitoJUnitRunner.class)
public class EventDispatcherTest {
	
	@Mock
	private BeanManager beanManager;
	
	@Before
	public void setup(){
		new EventDispatcher(beanManager);
	}

	@Test
	public void is_usable_in_unit_tests() {
		EventDispatcher.notify("hallo");
		Mockito.verify(beanManager).fireEvent("hallo");
	}

}
