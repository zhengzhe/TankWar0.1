package nbcc;
import nbcc.Tank.Direction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;



public class Missile extends Shape {
	
	public static final int HEIGHT = 10;
	public static final int WIDTH = 10;
	public static final int STEP_LEN=10;
	
	private Context context;
	
	
	public Missile(int x, int y,Direction dir, boolean good) {
		super(x,y,dir,good);
		context = Context.getInstance();
		setLive(true);
		setStepLen(STEP_LEN);
	}
	public void draw(GC gcImage) {
			drawMissile(gcImage);
			move();
	}
	private void drawMissile(GC gcImage) {
		Color oldColor = gcImage.getBackground();
		Color color =Utils.getSystemColor(SWT.COLOR_BLACK);
		gcImage.setBackground(color);
		gcImage.fillOval(x, y, WIDTH, HEIGHT);
		gcImage.setBackground(oldColor);
	}

	public boolean isHit(Tank otherTank) {
		if(getRect().intersects(otherTank.getRect())&& otherTank.isLive() && this.isGood()!=otherTank.isGood())
			return true;
		else 
			return false;
	}


	public Rectangle getRect() {
		Rectangle rec= new Rectangle(x, y, WIDTH, HEIGHT);
		return rec;
	}


	public boolean hitWall(Wall w) {
		if (getRect().intersects(w.getRect())) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isLive() {
		//如果打在墙上，子弹死
		if (hitWall(context.w1)||hitWall(context.w2)) {
			setLive(false);
		}
		//如果超出边界，子弹死
		if (isOutOfBound()) {
			setLive(false);
		}
		//如果打中敌方坦克，子弹死
		for (int i = 0 ;i<context.enemyTanks.size();i++)
		{
			Tank tank = context.enemyTanks.get(i);
			if (isHit(tank)) {
				context.remainMissiles.addAll(tank.missiles);
				tank.setLive(false);
				context.enemyTanks.remove(tank);
				live = false;
				new Explode(x, y).start();
			}
		}
		//如果打中我方坦克，子弹死
		if (isHit(context.myTank)) {
			context.myTank.setLive(false);
		}
		return live;
	}
	public boolean isOutOfBound() {
		return x<0||y<0||x>context.warArea.getClientArea().width||y>context.warArea.getClientArea().height;
	}

}
