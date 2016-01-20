package at.arz.latte.rodeo.pipeline;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.pipeline.PipelineEvent.Status;

public class PipelineInstance {

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "INSTANCE_REFERENCE")
	private ListenerReference reference;

	@ManyToOne
	private Pipeline pipeline;

	@ElementCollection
	@MapKeyColumn(name = "PROPERTY_NAME")
	@Column(name = "PROPERTY_VALUE")
	@CollectionTable(name = "PIPELINE_INSTANCE_PROPERTIES", joinColumns = @JoinColumn(name = "OID") )
	private Map<String, String> properties;

	private Status currentStatus;

	@Persistent
	@Externalizer("toString")
	@Column(name = "CURRENT_STEP")
	private StepName currentStep;

	public PipelineInstance(ListenerReference reference, Pipeline pipeline, Properties properties) {
		this.reference = reference;
		this.pipeline = pipeline;
		this.properties = new HashMap<String, String>();
		for (Map.Entry<Object, Object> property : properties.entrySet()) {
			if (property.getValue() == null) {
				properties.put(property.getKey(), null);
			} else {
				properties.put(property.getKey(), property.getValue().toString());
			}
		}
		this.currentStatus = Status.CREATED;
		updateStatus(Status.PAUSED);
	}

	public void executeNext() {
		PipelineStep nextStep = pipeline.getNextStep(currentStep);
		if(nextStep==null){
			
		}else{
			
		}
	}


	private void updateStatus(Status newStatus) {
		this.currentStatus = newStatus;
		EventDispatcher.notify(new PipelineEvent(reference, currentStatus));
	}

}
