package nbcc;

import java.util.ArrayList;
import java.util.List;

import nbcc.Tank.Direction;
import java.util.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class TankClient {

	public static final int GAME_HEIGHT = 600;
	public static final int GAME_WIDTH = 800;

	private static final GC gcImage = null;
	
	protected Shell shell;
	protected int x=50;
	protected int y=50;
	protected Tank myTank = new Tank(50, 50, 30, 30,true,this);
	
	protected List<Tank> enemyTanks = new ArrayList<Tank>();
	protected Wall w1 = new Wall(100, 200, 20, 150),w2=new Wall(300, 100, 300, 20);
	
	
	
	//内部类，定义在其他类的内部
	private class PaintThread implements Runnable{
		
		@Override
		public void run() {
			//线程的执行代码放在这里
			while (true) {
				
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						shell.redraw();
					}
				});
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TankClient window = new TankClient();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		
		new Thread(new PaintThread()).start();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE|SWT.NO_BACKGROUND|SWT.DOUBLE_BUFFERED);
		shell.setSize(GAME_WIDTH, GAME_HEIGHT);//硬编码
		Rectangle screen = Display.getDefault().getPrimaryMonitor().getBounds();
		shell.setLocation((screen.width-GAME_WIDTH)/2,(screen.height-GAME_HEIGHT)/2);
		
		shell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		
		
		for (int i = 0; i < 10; i++) {
			
			enemyTanks.add(new Tank(200+i*50, 200, 30, 30, false,Direction.D,this));
			
		}
		
		
		//匿名内类，通常，如果需要实现某个接口，而不想定义一个完整类，
		shell.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				
				//在内存中创建图像缓冲区
				Image bufferScreen = new Image(null, shell.getClientArea());
				//创建一支能在图像缓冲区上绘制的"画笔"
				GC gcImage = new GC(bufferScreen);
				
				//在内存缓冲区中绘制图形
				gcImage.setBackground(shell.getBackground());
				gcImage.fillRectangle(shell.getClientArea());
				
				gcImage.drawString("敌方坦克数量:"+enemyTanks.size(),20,30);
				w1.draw(gcImage);
				w2.draw(gcImage);
				if(enemyTanks.size()<=0){
					for(int i=0;i<10;i++){
						enemyTanks.add(new Tank(200+i*50, 200, 30, 30, false,Direction.D,TankClient.this));
					}
					
				}
				
				if (myTank!=null) {
					myTank.draw(gcImage);
				}
				
				//绘制所有的敌方坦克
				for (int i = 0; i < enemyTanks.size(); i++) {
					Tank enemyTank = enemyTanks.get(i);
					enemyTank.draw(gcImage);
				}
				
				
				//将内存中画好的图像一次性贴到屏幕上
				e.gc.drawImage(bufferScreen, 0, 0);
				
				//释放资源
				if (bufferScreen!=null) {
					bufferScreen.dispose();
					bufferScreen=null;
				}
				if (gcImage!=null) {
					gcImage.dispose();
					gcImage=null;
				}
				
			}
		});
		
		
		shell.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				myTank.keyReleased(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				myTank.keyPressed(e);
			}
		});
		
		shell.setText("\u5766\u514B\u5927\u6218\u6E38\u620FV1.0");

	}

}
