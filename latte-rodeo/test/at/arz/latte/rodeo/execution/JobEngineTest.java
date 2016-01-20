package at.arz.latte.rodeo.execution;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class JobEngineTest {

	@Test
	public void test() {
		System.out.println(System.getProperties());
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String string = format.format(new Date());
		System.out.println(string);

	}

}
