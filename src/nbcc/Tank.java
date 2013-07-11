package nbcc;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;


public class Tank extends Shape implements KeyListener{
	
	private static final int STEP_LEN = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	
	public boolean bL=false,bU=false,bR=false,bD=false;
	
	public enum Direction{
		L("左"),LU("左上"),U("上"),RU("右上"),R("右"),RD("右下"),D("下"),DL("左下"),STOP("停止");
		private String name;
		private Direction(String name){
			this.name = name;
		}
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	
	public Direction ptDir = Direction.R;
	
	List<Missile> missiles = new ArrayList<Missile>();
	private int step;
	private int oldY;
	private int oldX;
	private Context context;
	
	public Tank(int x,int y,int width,int height,boolean isGood) {
		this(x,y,width,height,Direction.D,isGood);
	}
	

	public Tank(int x, int y, int w, int h, Direction d, boolean bGood) {
		super(x,y,w,h,d,bGood);
		setLive(true);
		setStepLen(STEP_LEN);
	}

	/**
	 * 根据用户按下的键盘方向，来确定坦克的运动
	 */
	public void move() {
		
		oldX=x;
		oldY=y;
		super.move();
		
		if (dir!=Direction.STOP) {
			ptDir = dir;
		}
		
		hitWall(Context.getInstance().w1);
		hitWall(Context.getInstance().w2);
		
		//判断坦克是否碰到边界
		if (x<0) {
			x=0;
		}
		if (y<0) {
			y=0;
		}
		if (x>Context.getInstance().getWarArea().getClientArea().width-width) {
			x=Context.getInstance().getWarArea().getClientArea().width-width;
		}
		if (y>Context.getInstance().getWarArea().getClientArea().height-height) {
			y=Context.getInstance().getWarArea().getClientArea().height-height;
		}
		
		
		//敌方坦克的随机移动
		if (!isGood()) {
			if (step==0) {
				step = Utils.nextIntRandom(12)+3;
				Direction[] newDir =Direction.values();
				dir = newDir[Utils.nextIntRandom(newDir.length)];
			}
			step--;
			
			if (Utils.nextIntRandom(40)>38) {
				fire();
			}
			
		}
//		System.out.println("x:="+x+",y:="+y+"dir:"+dir);
		
		hitTanks(Context.getInstance().enemyTanks);
		
		
	}
	public void draw(GC gc) {
		Color red = Utils.getSystemColor(SWT.COLOR_RED);
		if (isGood()) {
			//绘制我方坦克
			if (live) {
				drawTank(gc, red);
			}
		}else {
			//绘制敌方坦克
			for (int i = 0; i < Context.getInstance().enemyTanks.size(); i++) {
				drawTank(gc, Utils.getSystemColor(SWT.COLOR_BLUE));
			}
		}	
		
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			if(missile.isLive())
				missile.draw(gc);
			else {
				missiles.remove(missile);
			}
		}
		move();
	}


	private void drawTank(GC gc, Color color) {
		gc.setBackground(color);
		gc.fillOval(x, y, width, height);
		drawPtDir(gc);
	}

	private void drawPtDir(GC gc) {
		switch (ptDir) {
		case D:
			gc.drawLine(x+width/2, y+height/2, x+width/2, y+height);
			break;
		case DL:
			gc.drawLine(x+width/2, y+height/2, x, y+height);
			break;
		case L:
			gc.drawLine(x+width/2, y+height/2, x, y+height/2);
			break;
		case LU:
			gc.drawLine(x+width/2, y+height/2, x, y);
			break;
		case R:
			gc.drawLine(x+width/2, y+height/2, x+width,y+height/2);
			break;
		case RD:
			gc.drawLine(x+width/2, y+height/2, x+width, y+height);
			break;
		case RU:
			gc.drawLine(x+width/2, y+height/2, x+width, y);
			break;
		case U:
			gc.drawLine(x+width/2, y+height/2, x+width/2,y);
		default:
			break;
		}
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(e.character+" Pressed");
		switch (e.keyCode) {
		case SWT.ARROW_UP:
			bU = true;
			break;
		case SWT.ARROW_LEFT:
			bL = true;
			break;
		case SWT.ARROW_RIGHT:
			bR = true;
			break;
		case SWT.ARROW_DOWN:
			bD = true;
			break;
		default:
			break;
		}
		locateDirection();
	}


	/**
	 * 根据用户按下的键盘状态决定运动的方向
	 */
	public void locateDirection() {
		if (bL&&!bU&&!bR&&!bD) dir = Direction.L;
		else if( bL&& bU&&!bR&&!bD) dir = Direction.LU;
		else if(!bL&& bU&&!bR&&!bD) dir = Direction.U;
		else if(!bL&& bU&& bR&&!bD) dir = Direction.RU;
		else if(!bL&& !bU&& bR&&!bD) dir = Direction.R;
		else if(!bL&& !bU&& bR&& bD) dir = Direction.RD;
		else if(!bL&& !bU&&!bR&& bD) dir = Direction.D;
		else if(bL&& !bU&&!bR&&bD) dir = Direction.DL;
		else if(!bL&& !bU&&!bR&&!bD) dir = Direction.STOP;
//		System.out.println(dir);
//		printStatus();
	}

	public void keyReleased(KeyEvent e) {
		System.out.println("key released: "+e.character);
		switch (e.keyCode) {
		case SWT.ARROW_UP:
			bU = false;
			break;
		case SWT.ARROW_LEFT:
			bL = false;
			break;
		case SWT.ARROW_RIGHT:
			bR = false;
			break;
		case SWT.ARROW_DOWN:
			bD = false;
			break;
		case SWT.CTRL:
			fire();
			break;
		default:
			break;
		}
		locateDirection();
	}
	
	public Missile fire() {
		if (!live) {
			return null;
		}
		int x  = this.x + width/2-Missile.WIDTH/2;
		int y = this.y +height/2-Missile.HEIGHT/2;
		
		Missile m = new Missile(x,y,ptDir,isGood());
		missiles.add(m);
		return m;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	public boolean hitWall(Wall w) {
		if (isLive()&&getRect().intersects(w.getRect())) {
			x = oldX;
			y = oldY;
			return true;
		}
		return false;
	}

	public void hitTanks(List<Tank> enemyTanks) {
		
		for (Tank other : enemyTanks) {
			if (this.getRect().intersects(other.getRect())&&this!=other) {
				this.stay();
				other.stay();
			}
		}
	}

	private void stay() {
		x=oldX;
		y=oldY;
	}

//	private void printStatus() {
//		System.out.println("bL"+bL+",bU"+bU+",bR"+bR+",bD"+bD);
//	}
	
}
