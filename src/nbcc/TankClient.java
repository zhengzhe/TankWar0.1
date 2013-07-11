package nbcc;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class TankClient {

	
	protected static Shell shell;
	private Context context = Context.getInstance();
	private Display display;
	private Composite warArea;
	private Thread t;
	
	
	
	//�ڲ��࣬��������������ڲ�
	private class PaintThread implements Runnable{
		
		@Override
		public void run() {
			//�̵߳�ִ�д����������
			while (true) {
				
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						warArea.redraw();
					}
				});
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					System.out.println("thread exit...");
					break;
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
		display = Display.getDefault();
		createContents();
		shell.open();
		
		t = new Thread(new PaintThread());
		t.start();
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
		shell = new Shell(display,SWT.CLOSE|SWT.NO_BACKGROUND|SWT.DOUBLE_BUFFERED);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		warArea = new TankShell(shell, SWT.CLOSE|SWT.NO_BACKGROUND|SWT.DOUBLE_BUFFERED);
		Rectangle screen = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screen.width-Context.GAME_WIDTH)/2,(screen.height-Context.GAME_HEIGHT)/2);
		warArea.setBackground(Utils.getSystemColor(SWT.COLOR_GREEN));
		context.setWarArea(warArea);
		
		shell.setText("\u5766\u514B\u5927\u6218\u6E38\u620FV1.0");
		
		
		//������ģʽ
		shell.addShellListener(new ShellAdapter() {
			
			@Override
			public void shellClosed(ShellEvent e) {
				boolean isConfirm = MessageDialog.openConfirm(shell, "ȷ��", "�Ƿ����Ҫ�˳���Ϸ?");
				if (isConfirm) {
					t.interrupt();
					
				}
			}
		});

	}


}
