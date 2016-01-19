package at.arz.latte.rodeo.workspace;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;

public class Settings {

	private String[] EMPTY = {};

	private Properties properties;

	private VariableResolver resolver;

	private String domain;

	public Settings(Properties properties, VariableResolver resolver) {
		this("", properties, resolver);
		this.properties = properties;
		this.resolver = resolver;
	}

	public Settings(String domain, Properties properties, VariableResolver resolver) {
		this.domain = domain;
		this.properties = properties;
		this.resolver = resolver;
	}

	public String getDomain() {
		return domain;
	}

	public String resolvedProperty(String key) {
		return resolve(property(key));
	}

	public String resolvedProperty(String key, String defaultValue) {
		String value = resolve(property(key, defaultValue));
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public String resolve(String value) {
		return resolver.resolve(value);
	}

	public String property(String key) {
		return properties.getProperty(key);
	}

	public String property(String key, String defaultValue) {
		String property = property(key);
		if (property == null) {
			return defaultValue;
		}
		return property;
	}

	public String[] propertyAsArray(String key) {
		return propertyAsArray(key, ",| ");
	}

	public String[] propertyAsArray(String key, String seperator) {
		String property = property(key);
		if (property == null) {
			return EMPTY;
		}
		return property.split(seperator);
	}

	public Settings settingsFor(String subDomain) {
		Properties result = new Properties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			String newKey = removePrefix(subDomain, key);
			if (newKey == null) {
				continue;
			}
			result.put(newKey, entry.getValue());
		}
		return new Settings(subDomain, result, resolver);
	}

	public static String removePrefix(String prefix, String key) {
		int length = prefix.length();
		if (key.startsWith(prefix) && key.length() > length) {
			if (key.charAt(length) == '.') {
				return key.substring(length + 1);
			}
		}
		return null;
	}

	public void store(OutputStream outputStream) throws IOException {
		properties.store(outputStream, "updated by rodeo");
	}

	public static final Properties loadDefaultProperties() {
		URL resource = Settings.class.getResource("workspace.properties");
		try (InputStream inputStream = resource.openStream()) {
			return loadProperties(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("unable to load default properties from " + resource);
		}
	}

	public static Properties loadProperties(InputStream inputStream) throws IOException {
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

	public File getWorkspaceDir() {
		File workspaceDir = new File(resolvedProperty("rodeo.workspace.dir", "workspace"));
		if (workspaceDir.isAbsolute()) {
			return workspaceDir;
		}
		return new File(new File(resolve("${rodeo.home}")), workspaceDir.getPath());
	}

}
