package at.arz.latte.rodeo.test;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;

public class TestUtil {
	
	public static final void setField(Object target, String fieldName, Object value){
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
