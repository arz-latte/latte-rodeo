package at.arz.latte.rodeo.workspace;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VariableResolver {

	private static final Pattern PROPERTY_PATTERN = Pattern.compile("(\\\\\\$)|\\$\\{([^\\\\$}]+)\\}");
	private Properties properties;

	public VariableResolver(Properties properties) {
		this.properties = properties;
	}

	public String resolve(String value) {
		if (value == null) {
			return null;
		}
		Matcher matcher = PROPERTY_PATTERN.matcher(value);
		final StringBuffer buffer = new StringBuffer();
		int current = 0;
		while (matcher.find()) {
			buffer.append(value.substring(current, matcher.start()));
			current = replace(matcher, buffer);
		}
		buffer.append(value.substring(current));
		return buffer.toString();
	}

	private int replace(Matcher matcher, final StringBuffer buffer) {
		String escape = matcher.group(1);
		if (escape == null) {
			appendVariable(buffer, matcher.group(2));
		} else {
			buffer.append("$");
		}
		return matcher.end();
	}

	private void appendVariable(final StringBuffer buffer, String key) {
		String newValue = properties.getProperty(key);
		if (newValue == null) {
			buffer.append("${");
			buffer.append(key);
			buffer.append("}");
		} else {
			buffer.append(newValue);
		}
	}

}
