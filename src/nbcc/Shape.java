package nbcc;

import nbcc.Tank.Direction;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * @author  zhengzhe
 * @2013-7-10
 */
public abstract class Shape {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Tank.Direction dir;
	protected boolean good;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tank.Direction getDir() {
		return dir;
	}

	public void setDir(Tank.Direction dir) {
		this.dir = dir;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	protected boolean live;
	private int stepLen;
	
	public void setStepLen(int stepLen) {
		this.stepLen = stepLen;
	}
	public int getStepLen() {
		return stepLen;
	}

	public Shape() {
		super();
	}

	public Shape(int x, int y, boolean isGood) {
		this(x,y,Direction.D,isGood);
	}
	
	public Shape(int x, int y,Direction d, boolean isGood) {
		this(x,y,30,30,d,isGood);
	}

	public Shape(int x, int y, int width, int height,Direction d, boolean isGood) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.good = isGood;
		this.dir = d;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean isLive) {
		this.live = isLive;
	}

	public void move() {
		switch (dir) {
		case L:
			x-=stepLen;
			break;
		case LU:
			x-=stepLen;		
			y-=stepLen;
			break;
		case U:
			y-=stepLen;
			break;
		case RU:
			x+=stepLen;
			y-=stepLen;
			break;
		case R:
			x+=stepLen;
			break;
		case RD:
			x+=stepLen;
			y+=stepLen;
			break;
		case D:
			y+=stepLen;
			break;
		case DL:
			x-=stepLen;
			y+=stepLen;
			break;
		default:
			break;
		}
		
	}
	
	public abstract void draw(GC gc) ;
	
	public abstract Rectangle getRect() ;
}
