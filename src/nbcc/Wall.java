package nbcc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author  zhengzhe
 * @2013-7-9
 */
public class Wall {
	
	int x,y,w,h;
	TankClient tc;
	public Wall(int x, int y, int w, int h, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	public Wall(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void draw(GC gc) {
		Color oldColor = gc.getBackground();
		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		gc.fillRectangle(x, y, w, h);
		gc.setBackground(oldColor);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	

}

