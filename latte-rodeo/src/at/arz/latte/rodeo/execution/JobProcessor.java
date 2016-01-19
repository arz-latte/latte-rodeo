package at.arz.latte.rodeo.execution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.arz.latte.rodeo.execution.Job.Status;
import at.arz.latte.rodeo.infrastructure.EventDispatcher;

public class JobProcessor
		implements Runnable {

	private static final Logger logger = Logger.getLogger(JobProcessor.class.getName());

	private JobLogForwarder streamStdErr;
	private JobLogForwarder streamStdOut;
	private String[] environmentVariables;
	private File workDirectory;
	private JobIdentifier identifier;
	private File logFile;
	private String commandLine;

	private PrintStream outputStream;

	private JobQueue queue;

	public JobProcessor(JobQueue queue, JobIdentifier identifier) {
		this.queue = queue;
		this.identifier = identifier;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public void setEnvironmentVariables(String[] environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public void setWorkDirectory(File workDirectory) {
		this.workDirectory = workDirectory;
	}

	public void run() {
		EventDispatcher.notify(new JobStatusChanged(identifier, Status.WAITING));
		if (commandLine == null || commandLine.length() == 0) {
			throw new RuntimeException("invalid emtpy command line");
		}
		int executeProcess = -1;
		try{
			executeProcess = executeProcess(commandLine);
			if (executeProcess == 0) {
				JobStatusChanged event = new JobStatusChanged(identifier, Status.SUCCESS);
				queue.jobStatusChanged(event);
			}
		} finally {
			if (executeProcess != 0) {
				JobStatusChanged event = new JobStatusChanged(identifier, Status.FAILED);
				queue.jobStatusChanged(event);
			}
		}
	}

	protected Process createProcess(String command) {
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Trying to create Process '" + command + "' with workingDir='" + workDirectory + "'...");
		}
		try {
			return Runtime.getRuntime().exec(command, environmentVariables, workDirectory);
		} catch (Throwable t) {
			throw new RuntimeException("can't create process '" + command + "' in '" + workDirectory + "'", t);
		}
	}

	protected int executeProcess(String commandLine) {
		logger.info("execute batch process: " + commandLine + ", " + workDirectory);
		Process p = createProcess(commandLine);
		queue.jobStatusChanged(new JobStatusChanged(identifier, Job.Status.RUNNING));
		try {
			startOutputStreams(p);
			try {
				p.waitFor();
			} catch (InterruptedException ie) {
				logger.severe("execution interrupted:" + ie.getMessage());
			}
			return p.exitValue();
		} catch (Throwable t) {
			throw new RuntimeException("execution failed:" + t.getMessage(), t);
		} finally {
			stopOutputStreams();
		}
	}

	protected void startOutputStreams(Process process) {
		createOutputStream();
		streamStdOut = new JobLogForwarder(process.getInputStream());
		streamStdErr = new JobLogForwarder(process.getErrorStream());
		streamStdOut.forwardTo(new JobLog(outputStream, "[OUT]"));
		streamStdErr.forwardTo(new JobLog(outputStream, "[ERR]"));
	}

	private void createOutputStream() {
		try {
			outputStream = new PrintStream(new FileOutputStream(logFile));
		} catch (IOException e) {
			throw new RuntimeException("can't create output stream for logging", e);
		}
	}

	protected void stopOutputStreams() {
		try {
			streamStdOut.waitForCompletion();
			streamStdErr.waitForCompletion();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

}
