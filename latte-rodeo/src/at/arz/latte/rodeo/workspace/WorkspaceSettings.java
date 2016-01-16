package at.arz.latte.rodeo.workspace;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class WorkspaceSettings {

	private String[] EMPTY = {};

	public static final String CVS_EXECUTABLE = "CVS_EXECUTABLE";
	public static final String ARZBUILD = "ARZBUILD";

	private static final Map<String, String> settings;
	private Properties properties;

	static {
		settings = new HashMap<String, String>();
		settings.put(CVS_EXECUTABLE, "cvs");
		settings.put(	ARZBUILD,
						"C:/irad/apache-ant-1.9.6/bin/ant.bat " + "-Darzbuild.dir=C:/irad/build/at.arz.tools.arzbuild "
								+ "-Darzbuild.conf=irad "
								+ "-Darzbuild.status=integration "
								+ "-propertyfile=C:/IRAD/build/at.arz.tools.arzbuild/config/global-environment-with-latte-irad.properties");
	}

	public static String get(String name) {
		return settings.get(name);
	}

	public static String get(String name, String defaultValue) {
		String value = settings.get(name);
		if (value == null) {
			return defaultValue;
		}
		return settings.get(name);
	}

	public WorkspaceSettings(Properties properties) {
		this.properties = properties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String[] getPropertyAsArray(String key) {
		String property = properties.getProperty(key);
		if (property == null) {
			return EMPTY;
		}
		return property.split(";");
	}

	public Properties propertiesFor(String prefix) {
		Properties result = new Properties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			String newKey = removePrefix(prefix, key);
			if (newKey == null) {
				continue;
			}
			result.put(newKey, entry.getValue());
		}
		return result;
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
		URL resource = WorkspaceSettings.class.getResource("workspace.properties");
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

}
