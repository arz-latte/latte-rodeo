package at.arz.latte.rodeo.execution;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.arz.latte.rodeo.execution.Job.Status;
import at.arz.latte.rodeo.infrastructure.EventDispatcher;

public class JobProcessor {

	private static final Logger logger = Logger.getLogger(JobProcessor.class.getName());

	private JobLogForwarder streamStdErr;
	private JobLogForwarder streamStdOut;
	private String[] environmentVariables;
	private File workDirectory;
	private JobIdentifier identifier;
	private File logFile;
	private String commandLine;

	public JobProcessor(JobIdentifier identifier) {
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

	public Thread execute() {
		EventDispatcher.notify(new JobStatusChanged(identifier, Status.WAITING));
		if (commandLine == null || commandLine.length() == 0) {
			throw new RuntimeException("invalid emtpy command line");
		}
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				executeProcess(commandLine);
			}

		});
		thread.start();
		return thread;
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
		streamStdOut = new JobLogForwarder(process.getInputStream());
		streamStdErr = new JobLogForwarder(process.getErrorStream());
		streamStdOut.forwardTo(new JobLog(System.out, "[OUT]"));
		streamStdErr.forwardTo(new JobLog(System.out, "[ERR]"));
	}

	protected void stopOutputStreams() {
		streamStdOut.waitForCompletion();
		streamStdErr.waitForCompletion();
	}

}
