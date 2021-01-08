package app;

public class Main {
	
	public static void main(String[] args) {
		AppCore appCore = new AppCore();
		MainFrame mf = MainFrame.getMainFrame();
		mf.setAppCore(appCore);
		mf.setVisible(true);
		
		mf.getAppCore().setTree();
	}
}
