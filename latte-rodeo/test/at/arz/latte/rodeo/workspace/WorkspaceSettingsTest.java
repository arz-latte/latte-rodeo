package at.arz.latte.rodeo.workspace;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;

public class WorkspaceSettingsTest {

	@Test
	public void removePrefix_does_not_return_equal_name() {
		assertThat(Settings.removePrefix("domain", "domain"), is(nullValue()));
		// haha
	}

	@Test
	public void removePrefix_removes_prefix() {
		assertThat(Settings.removePrefix("domain", "domain.subdomain"), is("subdomain"));
	}

	@Test
	public void removePrefix_returns_null_if_not_subdomain() {
		assertThat(Settings.removePrefix("domain", "x"), is(nullValue()));
	}

	@Test
	public void produce_properties_for_domain() {
		Properties properties = new Properties();
		properties.put("some.domain", "1");
		properties.put("domain", "2");
		properties.put("domain.subdomain", "3");
		Settings settings = new Settings(properties, new VariableResolver(System.getProperties()));
		Settings domain = settings.settingsFor("domain");
		assertThat(domain.property("domain"), is(nullValue()));
		assertThat(domain.property("subdomain"), is("3"));
	}

	@Test
	public void get_property() {
		Properties properties = new Properties();
		properties.put("key_1", "1");
		Settings settings = new Settings(properties, new VariableResolver(System.getProperties()));
		assertThat(settings.property("key_1"), is("1"));
	}

	@Test
	public void get_property_as_array() {
		Properties properties = new Properties();
		properties.put("key_1", "1");
		properties.put("key_2", ",1");
		properties.put("key_3", "1,");
		properties.put("key_4", "1,2");
		Settings settings = new Settings(properties, new VariableResolver(System.getProperties()));
		assertThat(settings.propertyAsArray("unknown"), is(new String[] {}));
		assertThat(settings.propertyAsArray("key_1"), is(new String[] { "1" }));
		assertThat(settings.propertyAsArray("key_2"), is(new String[] { "", "1" }));
		assertThat(settings.propertyAsArray("key_3"), is(new String[] { "1" }));
		assertThat(settings.propertyAsArray("key_4"), is(new String[] { "1", "2" }));
	}

	@Test
	public void load_default_properties() {
		Properties properties = Settings.loadDefaultProperties();
		assertThat(properties, is(notNullValue()));
	}

}
