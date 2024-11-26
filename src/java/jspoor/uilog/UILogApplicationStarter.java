package jspoor.uilog;

import java.awt.Toolkit;
import java.lang.reflect.Method;

/**
 * this main class is run by aspectj's aj5. The application which users
 * interactions with it should be logged is given to this class to run it
 * 
 * @author arin ghazarian
 * 
 */
public class UILogApplicationStarter {
	public static void main(String args[]) {
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(
				new UIProfilerEventQueue());

		try {
			Class cls = Class.forName(args[0]);
			String s[] = new String[args.length - 1];
			for (int i = 1; i < args.length; i++) {
				s[i - 1] = args[i];
			}
			Object arguments[] = new Object[] { s };
			Method mainMethod = cls.getMethod("main", new Class[] { s
					.getClass() });
			Object result = mainMethod.invoke(null, arguments);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}

	}
}
