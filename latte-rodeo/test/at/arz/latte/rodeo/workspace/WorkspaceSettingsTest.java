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
		assertThat(WorkspaceSettings.removePrefix("domain", "domain"), is(nullValue()));
	}

	@Test
	public void removePrefix_removes_prefix() {
		assertThat(WorkspaceSettings.removePrefix("domain", "domain.subdomain"), is("subdomain"));
	}

	@Test
	public void removePrefix_returns_null_if_not_subdomain() {
		assertThat(WorkspaceSettings.removePrefix("domain", "x"), is(nullValue()));
	}

	@Test
	public void produce_properties_for_domain() {
		Properties properties = new Properties();
		properties.put("some.domain", "1");
		properties.put("domain", "2");
		properties.put("domain.subdomain", "3");
		WorkspaceSettings settings = new WorkspaceSettings(properties);
		Properties domain = settings.propertiesFor("domain");
		assertThat(domain.getProperty("domain"), is(nullValue()));
		assertThat(domain.getProperty("subdomain"), is("3"));
	}

	@Test
	public void get_property() {
		Properties properties = new Properties();
		properties.put("key_1", "1");
		WorkspaceSettings settings = new WorkspaceSettings(properties);
		assertThat(settings.getProperty("key_1"), is("1"));
	}

	@Test
	public void get_property_as_array() {
		Properties properties = new Properties();
		properties.put("key_1", "1");
		properties.put("key_2", ";1");
		properties.put("key_3", "1;");
		properties.put("key_4", "1;2");
		WorkspaceSettings settings = new WorkspaceSettings(properties);
		assertThat(settings.getPropertyAsArray("unknown"), is(new String[] {}));
		assertThat(settings.getPropertyAsArray("key_1"), is(new String[] { "1" }));
		assertThat(settings.getPropertyAsArray("key_2"), is(new String[] { "", "1" }));
		assertThat(settings.getPropertyAsArray("key_3"), is(new String[] { "1" }));
		assertThat(settings.getPropertyAsArray("key_4"), is(new String[] { "1", "2" }));
	}

	@Test
	public void load_default_properties() {
		Properties properties = WorkspaceSettings.loadDefaultProperties();
		assertThat(properties, is(notNullValue()));
	}

}
