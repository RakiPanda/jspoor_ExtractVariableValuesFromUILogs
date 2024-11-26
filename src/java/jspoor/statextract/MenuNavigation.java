package jspoor.statextract;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is used to compute some attributes realted to users behavior on
 * menus.
 * 
 * @author arin ghazarian
 * 
 */
public class MenuNavigation {

	/**
	 * calculates how many times the user has vibrated around a given menu item
	 * before selecting it and what is the average radius of the vibration
	 * 
	 * @param navigatedItems
	 * @param targetItm
	 * @return
	 */
	public static float[] calcMenuItemVibrationFeatures(
			ArrayList navigatedItems, String targetItm) {

		Iterator itr = navigatedItems.iterator();
		int m = 0;
		int vibrationCount = 0;
		int vibrationRadsiusSum = 0;
		float[] res = new float[2];
		while (itr.hasNext()) {
			String item = (String) itr.next();
			if (item.equalsIgnoreCase(targetItm))
				break;
		}
		while (itr.hasNext()) {
			String item = (String) itr.next();
			if (item.equalsIgnoreCase(targetItm)) {
				vibrationCount++;
				vibrationRadsiusSum += ((m + 1) / 2);
				m = 0;

			} else
				m++;

		}
		res[0] = vibrationCount;
		if (vibrationCount != 0)
			res[1] = (float) vibrationRadsiusSum / vibrationCount;
		else
			res[1] = 0;
		return res;

	}

	public static void printMenuItemVibrationFeatures(PrintWriter out,
			float[] features) {
		out.print("#menu navigation vibration features");
		out.print("@attribute vibrationsCount numeric");
		out.println(features[0] + ",");
		out.print("@attribute avgVibrationsRadius numeric");
		out.println(features[1] + ",");
	}
}
