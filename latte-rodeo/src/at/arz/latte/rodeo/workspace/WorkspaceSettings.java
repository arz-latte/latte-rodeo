package at.arz.latte.rodeo.workspace;

import java.util.HashMap;
import java.util.Map;


public final class WorkspaceSettings {

	public static final String CVS_EXECUTABLE = "CVS_EXECUTABLE";
	public static final String ARZBUILD = "ARZBUILD";
	
	private static final Map<String, String> settings;

	
	static{
		settings = new HashMap<String, String>();
		settings.put(CVS_EXECUTABLE, "cvs");
		settings.put(	ARZBUILD,
						"C:/irad/apache-ant-1.9.6/bin/ant.bat " + "-Darzbuild.dir=C:/irad/build/at.arz.tools.arzbuild "
								+ "-Darzbuild.conf=irad "
								+ "-Darzbuild.status=integration "
								+ "-propertyfile=C:/IRAD/build/at.arz.tools.arzbuild/config/global-environment-with-latte-irad.properties");
	}
	
	public static String get(String name){
		return settings.get(name);
	}

	public static String get(String name, String defaultValue) {
		String value = settings.get(name);
		if (value == null) {
			return defaultValue;
		}
		return settings.get(name);
	}

}
