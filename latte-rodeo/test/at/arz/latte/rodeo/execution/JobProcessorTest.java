package at.arz.latte.rodeo.execution;

import java.io.File;

import javax.enterprise.inject.spi.BeanManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import at.arz.latte.rodeo.infrastructure.EventDispatcher;

@RunWith(MockitoJUnitRunner.class)
public class JobProcessorTest {

	@Mock
	private BeanManager beanManager;

	private EventDispatcher eventDispatcher;

	private JobProcessor processor;

	@Before
	public void setup() {
		eventDispatcher = new EventDispatcher(beanManager);
		processor = new JobProcessor(new JobIdentifier("test"));
		processor.setCommandLine("cmd /c bla bla");
		processor.setWorkDirectory(new File("."));
	}

	@Test
	public void test() throws InterruptedException {
		Thread thread = processor.execute();
		thread.join();
	}

}
