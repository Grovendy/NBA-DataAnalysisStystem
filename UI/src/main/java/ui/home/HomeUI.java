package ui.home;

/**
 * 主界面panel
 */
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import service.impl.ServiceFactoryImpl;
import ui.common.MotherPanel;
import ui.config.FrameConfig;
import ui.config.SystemConfig;
import ui.live.LivePanel;
import ui.util.FrameUtil;

public class HomeUI extends JFrame {
	public HomePanel home;
	public MotherPanel motherPanel;
	public LivePanel livePanel;
	
	public HomeUI() {
		// 获得窗口配置
		FrameConfig fcfg = SystemConfig.getHOME_CONFIG();
		// 设置大小
		this.setSize(fcfg.getWidth(), fcfg.getHeight());

		this.setLayout(null);

		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		//去掉框架
		this.setUndecorated(true);
		// 设置不可更改大小
		this.setResizable(false);
		// 默认关闭退出
		this.setDefaultCloseOperation(3);
		// 居中
		FrameUtil.setFrameCenter(this, fcfg.getWindowUp());
		//设置程序小图标
		ImageIcon icon = new ImageIcon("img/common/icon.png");
		this.setIconImage(icon.getImage());
		
		home = new HomePanel(this);
		this.getContentPane().add(home);
		//this.getContentPane().add(new MotherPanel(this));
		//this.getContentPane().add(new SeasonDetail(this,"13-14","HOU"));
		this.setVisible(true);


//		LiveServiceImpl.getInstance().startLiveService();
	}

	public static void main(String[] args) {
		new HomeUI();
//		try {
//			ServiceFactoryImpl.getInstance().getInferStatsService().getMultiRegression("13-14");
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	

}
