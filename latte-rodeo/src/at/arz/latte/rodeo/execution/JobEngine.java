package at.arz.latte.rodeo.execution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.pipeline.CommandLineStep;
import at.arz.latte.rodeo.pipeline.StepName;
import at.arz.latte.rodeo.pipeline.admin.FindStep;
import at.arz.latte.rodeo.workspace.Settings;
import at.arz.latte.rodeo.workspace.VariableResolver;
import at.arz.latte.rodeo.workspace.Workspace;

@Stateless
@LocalBean
public class JobEngine {

	private static final Logger log = Logger.getLogger(JobEngine.class.getSimpleName());

	@Inject
	private Workspace workspace;

	@Inject
	private JobQueue queue;

	@Inject
	private RodeoModel model;

	File createJobDir(JobIdentifier identifier, Date jobTime) {
		String DATE_PATTERN = "yyyy/MM/dd";
		String datePart = new SimpleDateFormat(DATE_PATTERN).format(jobTime);
		File dir = new File(workspace.getJobDir(), datePart + "/" + identifier.toString());
		if (dir.exists()) {
			throw new RuntimeException("job dir for new job exists:" + dir.getAbsolutePath());
		}
		dir.mkdirs();
		return dir;
	}

	File createScript(File jobDir, CommandLineStep step, Properties properties) {
		String script = new VariableResolver(properties).resolve(step.getMainScript());
		Settings settings = workspace.getSettings("jobEngine");
		File scriptFile = new File(jobDir, settings.resolvedProperty("runStep.filename", "run_step.bat"));

		try (Writer writer = new FileWriter(scriptFile)) {
			writer.append(script);
		} catch (IOException e) {
			throw new RuntimeException("can't create step schript file:" + scriptFile.getAbsolutePath());
		}
		return scriptFile;
	}

	public Long submit(JobIdentifier identifier, StepName stepName, File workDirectory, Properties properties) {
		log.info("submitting job " + identifier);
		CommandLineStep step = model.query(FindStep.forName(stepName, CommandLineStep.class));
		Date jobDate = new Date(System.currentTimeMillis());
		File jobDir = createJobDir(identifier, jobDate);
		File scriptFile = createScript(jobDir, step, properties);
		Job job = new Job(identifier);
		JobProcessor processor = new JobProcessor(identifier);
		processor.setLogFile(new File(jobDir, "log.txt"));
		processor.setWorkDirectory(workDirectory);
		processor.setCommandLine("cmd /k " + scriptFile.getAbsolutePath());
		processor.execute();
		return job.getId();
	}

	public void jobStatusChanged(@Observes() final JobStatusChanged event) {
		model.applyAll(new FindJobs(event.getIdentifier()), new RodeoFunction<Job, Void>() {

			@Override
			public Void apply(Job job) {
				log.info("job " + job.getIdentifier() + " changed status to " + job.getStatus());
				job.setStatus(event.getStatus());
				return null;
			}
		});
	}

}
