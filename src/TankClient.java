
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class TankClient {

	protected Shell shell;
	protected int x=50;
	protected int y=50;
	
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
						x+=5;
						y+=5;
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
		shell = new Shell(SWT.CLOSE);
		shell.setSize(800, 600);
		Rectangle screen = Display.getDefault().getPrimaryMonitor().getBounds();
		shell.setLocation((screen.width-800)/2,(screen.height-600)/2);
		
		shell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		
		//匿名内类，通常，如果需要实现某个接口，而不想定义一个完整类，
		shell.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				GC gc =e.gc; //得到画笔
				Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
				gc.setBackground(red);
				gc.fillOval(x, y, 30, 30);
				
			}
		});
		
		shell.setText("\u5766\u514B\u5927\u6218\u6E38\u620FV1.0");

	}

}
