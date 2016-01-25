package at.arz.latte.rodeo.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
public class XmlHeaderBodyWriter
		implements MessageBodyWriter<Object> {

	private static final ConcurrentMap<Class<?>, JAXBContext> CONTEXT_CACHE = new ConcurrentHashMap<>();

	private JAXBContext newContext(Class<?> clazz) throws JAXBException {
		JAXBContext context = CONTEXT_CACHE.get(clazz);
		if (context == null) {
			JAXBContext newContext = JAXBContext.newInstance(clazz);
			// We drop a created context, if another thread has already created the context in the meantime
			context = CONTEXT_CACHE.putIfAbsent(clazz, newContext);
			if (context == null) {
				context = newContext;
			}
		}
		return context;
	}

	@Override
	public long getSize(Object jobsResult, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {

		if (clazz.equals(File.class)) {
			return false;
		}

		String styleSheet = getStyleSheet(annotations);

		if (styleSheet == null) {
			return false;
		}

		return true;
	}

	@Override
	public void writeTo(Object jobsResult,
						Class<?> clazz,
						Type type,
						Annotation[] annotations,
						MediaType mediaType,
						MultivaluedMap<String, Object> map,
						OutputStream outputStream) throws IOException {
		try {
			String styleSheet = getStyleSheet(annotations);

			JAXBContext jaxbContext = newContext(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();

			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><?xml-stylesheet type=\"text/xsl\" href=\"/rodeo/css/" + styleSheet
							+ ".xsl\" ?>";
			outputStream.write(header.getBytes());
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

			marshaller.marshal(jobsResult, outputStream);

		} catch (JAXBException e) {
			throw new RuntimeException(e);

		}
	}

	private String getStyleSheet(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(XSLTSheet.class)) {
				return ((XSLTSheet) annotation).value();
			}
		}
		return null;
	}

}
