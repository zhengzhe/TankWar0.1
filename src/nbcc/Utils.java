package nbcc;

import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author  zhengzhe
 * @2013-7-10
 */
public class Utils {
	private static Random r = new Random(); 
	public static Color getSystemColor(int color)
	{
		return Display.getDefault().getSystemColor(color);
	}
	
	public static int nextIntRandom(int n) {
		return r.nextInt(n);
	}

}

