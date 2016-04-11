package at.arz.latte.rodeo.execution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.rodeo.api.ObjectNotFound;
import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.pipeline.CommandLineStep;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepName;
import at.arz.latte.rodeo.pipeline.admin.FindStep;
import at.arz.latte.rodeo.settings.Settings;
import at.arz.latte.rodeo.settings.VariableResolver;
import at.arz.latte.rodeo.workspace.AsynchronousRunner;
import at.arz.latte.rodeo.workspace.Workspace;

@Stateless(name = "JobEngine")
@LocalBean
public class JobEngine {

	private static final String JOBDIR_DATEFORMAT = "yyyy/MM/dd";

	private static final Logger log = Logger.getLogger(JobEngine.class.getSimpleName());

	@Inject
	private Workspace workspace;

	@Inject
	private AsynchronousRunner runner;

	@Inject
	private RodeoModel model;

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	private Settings settings;

	private String runtime;

	File createJobDir(JobIdentifier identifier, Date jobTime) {
		String directoryFormat = settings.resolvedProperty("dateFormatForJobDirectory", JOBDIR_DATEFORMAT);
		String datePart = new SimpleDateFormat(directoryFormat).format(jobTime);
		File dir = new File(workspace.getJobDir(), datePart + "/" + identifier.toString());
		if (dir.exists()) {
			throw new RuntimeException("job dir for new job exists:" + dir.getAbsolutePath());
		}
		dir.mkdirs();
		return dir;
	}

	File createScript(File jobDir, CommandLineStep step, Properties properties) {
		String script = new VariableResolver(properties).resolve(step.getMainScript());
		File scriptFile = new File(jobDir, settings.resolvedProperty("job.filename." + runtime, "job.sh"));
		try (Writer writer = new FileWriter(scriptFile)) {
			writer.append(script);
		} catch (IOException e) {
			throw new RuntimeException("can't create step schript file:" + scriptFile.getAbsolutePath());
		}
		return scriptFile;
	}

	public Long submit(JobIdentifier identifier, StepName stepName, File workDirectory, Properties properties) {
		log.info("submitting job " + identifier);
		List<CommandLineStep> steps = model.query(FindStep.forName(stepName, CommandLineStep.class));
		if (steps.isEmpty()) {
			throw new ObjectNotFound(Step.class, stepName);
		}
		Date jobDate = new Date(System.currentTimeMillis());
		File jobDir = createJobDir(identifier, jobDate);
		File scriptFile = createScript(jobDir, steps.get(0), properties);
		Job job = new Job(identifier);
		job.setWorkDirectory(jobDir.getAbsolutePath());
		JobRunner processor = new JobRunner(runner, identifier);
		processor.setLogFile(new File(jobDir, "log.txt"));
		processor.setWorkDirectory(workDirectory);
		String[] environmentVariables = buildEnvironmentVariables();
		processor.setEnvironmentVariables(environmentVariables);
		String shell = settings.resolvedProperty("shell." + runtime, "/bin/sh");
		processor.setCommandLine(shell + " " + scriptFile.getAbsolutePath());
		runner.runAsynchron(processor);
		entityManager.persist(job);
		return job.getId();
	}

	private String[] buildEnvironmentVariables() {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : System.getenv().entrySet()) {
			list.add(entry.getKey() + "=" + entry.getValue());
		}
		return list.toArray(new String[list.size()]);
	}

	public void jobStatusChanged(@Observes() final JobStatusChanged event) {
		log.info("job " + event.getIdentifier() + " changed status to " + event.getStatus());
		model.applyAll(new JobsByIdentifierOrStatus(event.getIdentifier()), new RodeoFunction<Job, Void>() {

			@Override
			public Void apply(Job job) {
				log.info("job " + job.getIdentifier() + " changed status to " + job.getStatus());
				job.setStatus(event.getStatus());
				return null;
			}
		});
	}

	@PostConstruct
	public void setup() {
		settings = workspace.getSettings("jobEngine");
		runtime = settings.resolvedProperty("runtime");
		if (runtime == null) {
			runtime = System.getProperty("os.name").startsWith("Windows") ? "windows" : "unix";
		}
	}
}
