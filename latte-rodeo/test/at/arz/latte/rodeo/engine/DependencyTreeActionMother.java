package at.arz.latte.rodeo.engine;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import at.arz.latte.rodeo.engine.DependencyNode;
import at.arz.latte.rodeo.engine.Engine;
import at.arz.latte.rodeo.engine.TreeAction;

public class DependencyTreeActionMother {

	private static abstract class ActionAnswer
			implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			Engine<String> engine = (Engine<String>) invocation.getArguments()[0];
			DependencyNode<String> node = (DependencyNode<String>) invocation.getArguments()[1];
			notifyEngine(engine, node);
			return null;
		}

		protected abstract void notifyEngine(Engine<String> engine, DependencyNode<String> node);

	}
	

	public static TreeAction<String> cancelAllAction() {
		TreeAction<String> action = mock(TreeAction.class);
		ActionAnswer cancelAll = new ActionAnswer() {

			@Override
			protected void notifyEngine(Engine<String> engine, DependencyNode<String> node) {
				engine.cancelAll();
			}
		};
		doAnswer(cancelAll).when(action).execute((Engine) any(), (DependencyNode) any());
		return action;
	}

	public static TreeAction<String> processingFailedAction(final Set<DependencyNode<String>> failed) {
		TreeAction<String> action = mock(TreeAction.class);
		ActionAnswer partialSuccess = new ActionAnswer() {

			@Override
			protected void notifyEngine(Engine<String> engine, DependencyNode<String> node) {
				if (failed.contains(node)) {
					engine.processingFailed(node);
				} else {
					engine.processingFinished(node);
				}
			}
		};

		doAnswer(partialSuccess).when(action).execute((Engine) any(), (DependencyNode) any());
		return action;

	}

	public static TreeAction<String> processingSuccededAction() {
		TreeAction<String> action = mock(TreeAction.class);
		ActionAnswer success = new ActionAnswer() {

			@Override
			protected void notifyEngine(Engine<String> engine, DependencyNode<String> node) {
				engine.processingFinished(node);
			}
		};

		doAnswer(success).when(action).execute((Engine) any(), (DependencyNode) any());
		return action;
	}

}
