package nbcc;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;


public class Tank {
	
	private static final int STEP_LEN = 5;
	int x;
	int y;
	int width=30;
	int height=30;
	
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
	private Missile missile;
	public Direction ptDir = Direction.R;
	
	
	public Tank(int x,int y,int width,int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}




	/**
	 * 根据用户按下的键盘方向，来确定坦克的运动
	 */
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
		
		
	}
	public void draw(GC gc) {
		Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		gc.setBackground(red);
		gc.fillOval(x, y, width, height);
		if (missile!=null) {
			missile.draw(gc);
		}
		move();
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
			missile = fire();
			break;
		default:
			break;
		}
		locateDirection();
	}
	
	public Missile fire() {
		
		int x  = this.x + width/2-Missile.width/2;
		int y = this.y +height/2-Missile.height/2;
		
		Missile m = new Missile(x,y,dir);
		return m;
	}
	
	
}
