package at.arz.latte.rodeo.execution;

import static org.mockito.Mockito.mock;

import java.io.File;

import javax.enterprise.inject.spi.BeanManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.workspace.AsynchronousRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobProcessorTest {

	@Mock
	private BeanManager beanManager;

	private EventDispatcher eventDispatcher;

	private JobRunner processor;

	@Before
	public void setup() {
		eventDispatcher = new EventDispatcher(beanManager);
		processor = new JobRunner(mock(AsynchronousRunner.class), new JobIdentifier("test"));
		processor.setCommandLine("cmd /c bla bla");
		processor.setWorkDirectory(new File("."));
	}

	@Test
	public void test() throws InterruptedException {
		Thread thread = new Thread(processor);
		thread.start();
		thread.join();
	}

}
