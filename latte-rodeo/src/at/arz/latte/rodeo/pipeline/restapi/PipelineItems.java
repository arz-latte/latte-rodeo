package at.arz.latte.rodeo.pipeline.restapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pipelines")
@XmlAccessorType(XmlAccessType.FIELD)
public class PipelineItems {
	
	@XmlElement(name="pipeline")
	private List<PipelineItem> items;
	
	protected  PipelineItems() {
		// tool constructor
	}

	public PipelineItems(List<PipelineItem> items) {
		this.items = items;
	}
	
}
