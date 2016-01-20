package at.arz.latte.rodeo.execution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class JobLog {

	private PrintStream printStream;
	private List<Thread> streamingThreads;

	public JobLog(File logFile) {
		this.printStream = createOutputStream(logFile);
		streamingThreads = new ArrayList<>();
	}
	
	private static PrintStream createOutputStream(File logFile) {
		try {
			return new PrintStream(new FileOutputStream(logFile));
		} catch (IOException e) {
			throw new RuntimeException("can't create output stream for logging", e);
		}
	}

	public void log(String prefix, String line) {
		synchronized (printStream) {
			if (prefix == null) {
				prefix = "UNKNOWN";
			}
			printStream.append("[");
			printStream.append(prefix);
			printStream.append("] | ");
			printStream.append(line);
			printStream.println();
		}
	}

	public void addLogStream(InputStream inputStream, String logPrefix) {
		Thread thread = new Thread(createRunnerForStream(inputStream, logPrefix));
		thread.setDaemon(true);
		thread.start();
		synchronized (streamingThreads) {
			streamingThreads.add(thread);
		}
	}

	public void waitForCompletion() {
		for (Thread thread : streamingThreads) {
			waitForCompletion(thread);
		}
		synchronized (streamingThreads) {
			streamingThreads.clear();
		}
		printStream.close();
	}

	void waitForCompletion(Thread currentThread) {
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
	}

	public Runnable createRunnerForStream(final InputStream inputStream, final String streamPrefix) {
		return new Runnable() {

			@Override
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				try {
					String line = reader.readLine();
					while (line != null) {
						JobLog.this.log(streamPrefix, line);
						line = reader.readLine();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		};
	}

}
