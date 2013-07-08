
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class TankClient {

	protected Shell shell;
	protected int x=50;
	protected int y=50;
	
	//�ڲ��࣬��������������ڲ�
	private class PaintThread implements Runnable{
		
		@Override
		public void run() {
			//�̵߳�ִ�д����������
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
		shell = new Shell(SWT.CLOSE|SWT.NO_BACKGROUND|SWT.DOUBLE_BUFFERED);
		shell.setSize(800, 600);
		Rectangle screen = Display.getDefault().getPrimaryMonitor().getBounds();
		shell.setLocation((screen.width-800)/2,(screen.height-600)/2);
		
		shell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		
		//�������࣬ͨ���������Ҫʵ��ĳ���ӿڣ������붨��һ�������࣬
		shell.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				
				
				//���ڴ��д���ͼ�񻺳���
				Image bufferScreen = new Image(null, shell.getClientArea());
				//����һ֧����ͼ�񻺳����ϻ��Ƶ�"����"
				GC gcImage = new GC(bufferScreen);
				
				//���ڴ滺�����л���ͼ��
				gcImage.setBackground(shell.getBackground());
				gcImage.fillRectangle(shell.getClientArea());
				
				Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
				gcImage.setBackground(red);
				gcImage.fillOval(x, y, 30, 30);
				
				//���ڴ��л��õ�ͼ��һ����������Ļ��
				e.gc.drawImage(bufferScreen, 0, 0);
				
				//�ͷ���Դ
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
		
		shell.setText("\u5766\u514B\u5927\u6218\u6E38\u620FV1.0");

	}

}
