package nbcc;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;


public class Wall {
	
	int x,y,width,height;

	public Wall(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(GC gc) {
		Color oldColor = gc.getBackground();
		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		gc.fillRectangle(x,y,width,height);
		gc.setBackground(oldColor);
		
		
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}


}
