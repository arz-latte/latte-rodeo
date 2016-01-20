package at.arz.latte.rodeo.workspace;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;


public class VariableResolverTest {

	private VariableResolver resolver;

	@Before
	public void setup() {
		Properties properties = new Properties();
		properties.put("var1", "value1");
		properties.put("var2", "value2");
		resolver = new VariableResolver(properties);
	}

	@Test
	public void must_return_original_value() {
		assertThat(resolver.resolve("input"), is(("input")));
	}

	@Test
	public void must_return_variable_only() {
		assertThat(resolver.resolve("${var1}"), is("value1"));
	}

	@Test
	public void must_return_variable_at_end() {
		assertThat(resolver.resolve("${var1} end"), is("value1 end"));
	}

	@Test
	public void must_return_variable_at_center() {
		assertThat(resolver.resolve("begin ${var1} end"), is("begin value1 end"));
	}

	@Test
	public void must_return_variable_at_begin() {
		assertThat(resolver.resolve("${var1} end"), is("value1 end"));
	}

	@Test
	public void is_not_recursive() {
		assertThat(resolver.resolve("${${var1}}"), is("${value1}"));
	}

	@Test
	public void replaces_multiple_vars() {
		assertThat(resolver.resolve("${var1} ${var2}"), is("value1 value2"));
	}

	@Test
	public void accepts_escape_char() {
		assertThat(resolver.resolve("\\${var1}"), is("${var1}"));
	}
	
	@Test
	public void must_accept_null_value() {
		assertThat(resolver.resolve(null), is(nullValue()));
	}

	@Test
	public void must_accept_unknown_variable() {
		assertThat(resolver.resolve("${unknown}"), is("${unknown}"));
	}

}
