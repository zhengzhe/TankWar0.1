package nbcc;

import java.util.ArrayList;
import java.util.List;

import nbcc.Tank.Direction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author  zhengzhe
 * @2013-7-10
 */
public class Context {
	
	//单例模式：在整个程序生命周期中，只有唯一的一份实例
	
	//之所以我们能创建Java的多个实例，是因为我们可以调用公开的构造方法
	
	private static Context _instance;
	
	public static final int GAME_HEIGHT = 600;
	public static final int GAME_WIDTH = 800;
	public static final Color WAR_BACKGROUND = Utils.getSystemColor(SWT.COLOR_GREEN);
	
	protected Tank myTank ;
	protected List<Tank> enemyTanks=new ArrayList<Tank>();
	protected List<Missile> remainMissiles = new ArrayList<Missile>();
	protected int numOfEnemies = 10;
	
	protected Wall w1 ,w2;

	protected Composite warArea;
	
	private Context() {
		myTank = new Tank(50, 50, Tank.WIDTH, Tank.HEIGHT,Direction.STOP,true);
		createEnemyTanks();
		creatWall();
	}

	private void createEnemyTanks() {
		enemyTanks.clear();
		for (int i = 0; i < numOfEnemies; i++) {
			int x = Utils.nextIntRandom(GAME_WIDTH);
			int y = Utils.nextIntRandom(GAME_HEIGHT);
			enemyTanks.add(new Tank(x, y, Tank.WIDTH, Tank.HEIGHT, Direction.D,false));
		}
	}

	private void creatWall() {
		w1 = new Wall(100, 200, 20, 150);
		w2 = new Wall(300, 100, 300, 20);
	}
	
	public static Context getInstance() {
		if (_instance==null) {
			_instance = new Context();
		}
		return _instance;
	}

	public void setWarArea(Composite warArea) {
		this.warArea = warArea;
	}
	public Composite getWarArea() {
		return warArea;
	}
}

