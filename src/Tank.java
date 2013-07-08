import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;


public class Tank {
	
	int x;
	int y;
	int width=30;
	int height=30;
	
	
	public Tank(int x,int y,int width,int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}


	public void draw(GC gc) {
		Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		gc.setBackground(red);
		gc.fillOval(x, y, 30, 30);
	}


	public void keyPressed(KeyEvent e) {
		switch (e.keyCode) {
		case SWT.ARROW_LEFT:
			x-=5;
			break;
		case SWT.ARROW_DOWN:
			y+=5;
			break;
		case SWT.ARROW_RIGHT:
			x+=5;
			break;
		case SWT.ARROW_UP:
			y-=5;
			break;
		default:
			break;
		}
	}
	
	
}
