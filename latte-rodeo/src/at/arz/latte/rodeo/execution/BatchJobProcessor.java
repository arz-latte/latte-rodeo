package at.arz.latte.rodeo.execution;

import java.io.File;
import java.io.OutputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.arz.latte.rodeo.workspace.Workspace;

public class BatchJobProcessor {

	private static final Logger logger = Logger.getLogger(BatchJobProcessor.class.getName());
	private final OutputStream stderrStream;
	private final OutputStream stdoutStream;
	private InputStreamForwarder streamStdErr;
	private InputStreamForwarder streamStdOut;
	private final Workspace workspace;
	private String[] environmentVariables;
	private File workDirectory;

	public BatchJobProcessor(Workspace workspace) {
		this(workspace, System.out, System.err);
	}

	public BatchJobProcessor(Workspace workspace, OutputStream stdoutStream, OutputStream stderrStream) {
		Objects.requireNonNull(workspace, "argument workspace is required");
		Objects.requireNonNull(stdoutStream, "argument stdoutStream is required");
		Objects.requireNonNull(stderrStream, "argument stderrStream is required");
		this.workspace = workspace;
		this.stdoutStream = stdoutStream;
		this.stderrStream = stderrStream;
	}

	public void setEnvironmentVariables(String[] environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public void setWorkDirectory(File workDirectory) {
		this.workDirectory = workDirectory;
	}

	public int execute(String commandLine) {
		if (commandLine == null || commandLine.length() == 0) {
			throw new RuntimeException("invalid emtpy command line");
		}
		if (workDirectory == null) {
			// workDirectory = workspace.getWorkspaceDir();
		}
		return executeProcess(commandLine);
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
		streamStdOut = new InputStreamForwarder(process.getInputStream());
		streamStdErr = new InputStreamForwarder(process.getErrorStream());
		streamStdOut.forwardTo(stdoutStream);
		streamStdErr.forwardTo(stderrStream);
	}

	protected void stopOutputStreams() {
		streamStdOut.waitForCompletion();
		streamStdErr.waitForCompletion();
	}


}
