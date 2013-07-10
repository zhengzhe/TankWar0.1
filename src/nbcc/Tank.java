package nbcc;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nbcc.Tank.Direction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;


public class Tank {
	
	private static final int STEP_LEN = 5;
	int x;
	int y;
	int width=30;
	int height=30;
	
	boolean bGood;
	
	private static Random r = new Random();
	
	
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
	
	Direction dir=Direction.STOP;
	public Direction ptDir = Direction.R;
	
	List<Missile> missiles = new ArrayList<Missile>();
	private TankClient tc;
	private boolean live=true;
	private int step;
	
	
	public Tank(int x,int y,int width,int height,boolean isGood) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bGood = isGood;
		
	}
	
	public Tank(int x,int y,int width,int height,boolean isGood,TankClient tc){
		this(x,y,width,height,isGood);
		this.tc = tc;
	}




	public Tank(int x, int y, int width, int height, boolean isGood, Direction d,		
			TankClient tc) {
		this(x,y,width,height,isGood,tc);
		dir = d;
	}

	/**
	 * 根据用户按下的键盘方向，来确定坦克的运动
	 */
	public void move() {
		dealXY();
		dealPtDir();		
		dealBound();
		if (!bGood) {
			if (step==0) {
				step = r.nextInt(12)+3;
				Direction [] newDir = Direction.values();
				dir = newDir[r.nextInt(newDir.length)];
			}
			
			if (r.nextInt(40)>38) {
				fire();
			}
			step--;
		}
	}

	private void dealPtDir() {
		if (dir!=Direction.STOP) {
			ptDir = dir;
		}
	}

	private void dealXY() {
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
	}

	/**
	 * 边界处理
	 */
	private void dealBound() {
		if (x<0) {
			x=0;
		}
		if (y<0) {
			y=0;
		}
		if (x>tc.shell.getClientArea().width-width) {
			x=tc.shell.getClientArea().width-width;
		}
		if (y>tc.shell.getClientArea().height-height) {
			y=tc.shell.getClientArea().height-height;
		}
	}
	public void draw(GC gc) {
		
		
		Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
//		gc.setBackground(red);
		if (live) {
			if (bGood) {
				//绘制我方坦克
				gc.drawString("炮弹数量"+missiles.size(),20,10);
				drawTank(gc, red);
			}else {
				//绘制敌方坦克
				drawTank(gc,Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
			}
		}
		
		
		//绘制炮弹
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			
			for (int j = 0; j < tc.enemyTanks.size(); j++) {
				Tank enemyTank = tc.enemyTanks.get(j);
				if(missile.hitTank(enemyTank))
				{
					tc.enemyTanks.remove(enemyTank);
					new Explode(enemyTank.x, enemyTank.y, tc).start();
				}
			}
			if(missile.isLive())
				missile.draw(gc);
			else {
				missiles.remove(missile);
			}
		}
			
		move();
	}

	private void drawTank(GC gc, Color red) {
		gc.setBackground(red);
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


//	private void printStatus() {
//		System.out.println("bL"+bL+",bU"+bU+",bR"+bR+",bD"+bD);
//	}


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
		
		int x  = this.x + width/2-Missile.width/2;
		int y = this.y +height/2-Missile.height/2;
		
		Missile m = new Missile(x,y,bGood,ptDir);
		missiles.add(m);
		return m;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	public void setLive(boolean isLive) {
		this.live = isLive;
	}
	
	public boolean isLive() {
		return live;
	}
	
	
}
