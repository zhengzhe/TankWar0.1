package nbcc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public class Explode extends Thread{
	
	int x,y ;
	public boolean live = true;
	private int step;
	int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	
	public Explode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {
		
		for (step = 0; step < diameter.length; step++) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					Color c = Utils.getSystemColor(SWT.COLOR_RED);
					GC gc = new GC(Context.getInstance().warArea);
					gc.setBackground(c);
					gc.fillOval(x, y, diameter[step], diameter[step]);
				}
			});
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
