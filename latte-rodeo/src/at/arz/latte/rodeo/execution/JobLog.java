package at.arz.latte.rodeo.execution;

import java.io.PrintStream;

public class JobLog {

	private PrintStream printStream;
	private String prefix;

	public JobLog(PrintStream printStream, String prefix) {
		this.printStream = printStream;
		this.prefix = prefix;
	}

	public void log(String line) {
		synchronized (printStream) {
			if (prefix != null) {
				printStream.append(prefix);
			}
			printStream.append(line);
			printStream.println();
		}
	}

}
