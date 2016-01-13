package at.arz.latte.rodeo.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class InputStreamForwarder
		implements Runnable {

	private final InputStream inputStream;
	private Thread currentThread;

	public InputStreamForwarder(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public synchronized void forwardTo(final OutputStream outputStream) {
		if (currentThread == null) {
			currentThread = new Thread(createRunnable(outputStream));
			currentThread.setDaemon(true);
			currentThread.start();
		} else {
			throw new IllegalStateException("a thread is currently active");
		}
	}

	public void waitForCompletion() {
		if (currentThread == null) {
			return;
		}
		while (currentThread.isAlive()) {
			try {
				currentThread.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}
		currentThread = null;
	}

	private Runnable createRunnable(final OutputStream outputStream) {
		return new Runnable() {

			@Override
			public void run() {
				String encoding = System.getProperty("file.encoding");
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
					PrintStream out = new PrintStream(outputStream);
					try {
						String line = reader.readLine();
						while (line != null) {
							out.println(line);
							line = reader.readLine();
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					out.flush();
				} catch (UnsupportedEncodingException e) {
					throw new IllegalStateException("this jvm does not support the current file encoding:" + encoding);
				}
			}
		};
	}

	@Override
	public void run() {
	}
}
