package at.arz.latte.rodeo.execution.restapi;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class JobsResultXmlHeaderBodyWriter
		implements MessageBodyWriter<JobsResult> {

	@Override
	public long getSize(JobsResult jobsResult, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public void writeTo(JobsResult jobsResult,
						Class<?> clazz,
						Type type,
						Annotation[] annotations,
						MediaType mediaType,
						MultivaluedMap<String, Object> map,
						OutputStream outputStream) throws IOException {
		try {
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><?xml-stylesheet type=\"text/xsl\" href=\"/rodeo/css/rodeo.xsl\" ?>";
			outputStream.write(header.getBytes());
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			marshaller.marshal(jobsResult, outputStream);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
