package nbcc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author  zhengzhe
 * @2013-7-10
 */
public class TankShell extends Composite implements PaintListener {
	private Context context = null;

	public TankShell(Composite parent, int style) {
		super(parent, style);
		context = Context.getInstance();
		addPaintListener(this);
		setSize(Context.GAME_WIDTH,Context.GAME_HEIGHT);
		addKeyListener(context.myTank);
	}


	@Override
	public void paintControl(PaintEvent e) {
		//���ڴ��д���ͼ�񻺳���
		Image bufferScreen = new Image(null, getClientArea());
		//����һ֧����ͼ�񻺳����ϻ��Ƶ�"����"
		GC gcImage = new GC(bufferScreen);
		
		//���ڴ滺�����л���ͼ��
		gcImage.setBackground(getBackground());
		gcImage.fillRectangle(getClientArea());
		
		gcImage.drawString("�з�̹������:"+context.enemyTanks.size(), 20, 30);
		gcImage.drawString("�ڵ�����"+context.myTank.missiles.size(),20,10);
		
		
		
		if (context.myTank.isLive()) {
			context.myTank.draw(gcImage);
		} else {
			Color oldColor = gcImage.getBackground();
			gcImage.setBackground(Utils.getSystemColor(SWT.COLOR_GREEN));
			gcImage.setFont(new Font(null, new FontData("����", 50, SWT.NORMAL)));
			gcImage.drawString("GameOver", 289, 189);
			gcImage.setBackground(oldColor);
			
		}
		
		if (context.enemyTanks.size()<=0) {
			Color oldColor = gcImage.getBackground();
			gcImage.setBackground(Utils.getSystemColor(SWT.COLOR_GREEN));
			gcImage.setFont(new Font(null, new FontData("����", 50, SWT.NORMAL)));
			gcImage.drawString("You Win!", 289, 189);
			gcImage.setBackground(oldColor);
		}else{
			for (int i = 0; i < context.enemyTanks.size(); i++) {
				Tank enemyTank = context.enemyTanks.get(i);
				enemyTank.draw(gcImage);
			}
		}
		for (int i = 0; i < context.remainMissiles.size(); i++) {
			Missile m =context.remainMissiles.get(i);
			m.draw(gcImage);
		}
		context.w1.draw(gcImage);
		context.w2.draw(gcImage);
		
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
}

