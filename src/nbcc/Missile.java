package nbcc;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import nbcc.Tank.Direction;



public class Missile {
	
	public static final int height = 10;
	public static final int width = 10;
	int x;
	int y;
	Tank.Direction dir;
	private int STEP_LEN=10;
	
	private boolean live=true;
	private Explode e;
	private boolean good;
	
	public boolean isLive() {
		return live;
	}
	
	
	public Missile(int x, int y, boolean isGood,Direction dir) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = isGood;
	}
	public void draw(GC gcImage) {
		if (isLive()) {
			Color color = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
			gcImage.setBackground(color);
			gcImage.fillOval(x, y, width, height);
			move();
		}
		
	}
	public void move() {
		switch (dir) {
		case L:
			x-=STEP_LEN;
			break;
		case LU:
			x-=STEP_LEN;		
			y-=STEP_LEN;
			break;
		case U:
			y-=STEP_LEN;
			break;
		case RU:
			x+=STEP_LEN;
			y-=STEP_LEN;
			break;
		case R:
			x+=STEP_LEN;
			break;
		case RD:
			x+=STEP_LEN;
			y+=STEP_LEN;
			break;
		case D:
			y+=STEP_LEN;
			break;
		case DL:
			x-=STEP_LEN;
			y+=STEP_LEN;
			break;
		default:
			break;
		}
		
		if (x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT) {
			live = false;
		}
	}


	public boolean hitTank(Tank anyTank) {
		
		//炮弹存在，坦克也活着，炮弹打到坦克上 而且，炮弹不能打自己
		if(isLive()&& anyTank.isLive()&& getRect().intersects(anyTank.getRect())&& good!=anyTank.bGood )
		{
			if (!anyTank.bGood) {
				anyTank.setLive(false);
			}else 
			{
				if(anyTank.life<0)
					this.live = false;
			}
			return true;
		}
			
		else {
			return false;
		}
	}


	private Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}


	public void setLive(boolean isLive) {
		this.live = isLive;
	}


	public boolean hitWall(Wall w) {
		if (isLive()&&getRect().intersects(w.getRect())) {
			
			setLive(false);
			return true;
		}
		return false;
	}

}
