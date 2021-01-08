package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import action.ActionManager;
import gui.TableTabs;
import obs.Dbe;
import obs.IListener;
import table.model.Row;
import table.model.TableModel;
import tree.model.TreeModel;
import tree.view.TreeView;

public class MainFrame extends JFrame implements IListener{

	private static MainFrame mainf;
	
	private JSplitPane splitY;
	private JSplitPane splitX;
	private TableTabs table_top;
	private TableTabs table_bot;

	private AppCore appCore;
	
	private TreeModel treeModel;
	private TreeView treeView;
	
	private MainFrame() {
		setTitle("baze projekat");
		this.setSize(new Dimension(800,500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initElements();
		addElements();
	}

	public void setAppCore(AppCore appCore) {
		this.appCore = appCore;
		appCore.addListener(this);
	}
	
	private void initElements() {
		//appCore = new AppCore();
		//appCore.addListener(this);
		
		treeModel = new TreeModel();
		treeView = new TreeView();
		treeView.setModel(treeModel);
				
		table_top = new TableTabs();
		table_bot = new TableTabs();
		
	}
	
	private void addElements() {
		splitY = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitX = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitY.setRightComponent(splitX);
		
		JScrollPane treePane = new JScrollPane(treeView,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		treePane.setMinimumSize(new Dimension(250,500));
		splitY.setLeftComponent(treePane);
		
		splitX.setTopComponent(table_top);
		splitX.setBottomComponent(table_bot);
		splitX.setDividerLocation(230);
		
		add(splitY, BorderLayout.CENTER);
		JToolBar jt = new JToolBar();
		jt.add(ActionManager.getActionManager().getActionInfo());
		add(jt, BorderLayout.SOUTH);
	}
	
	public static MainFrame getMainFrame() {
		if(mainf == null)
			mainf = new MainFrame();
		return mainf;	
	}

	public TreeView getTreeView() {
		// TODO Auto-generated method stub
		return treeView;
	}

	public JSplitPane getSplitY() {
		return splitY;
	}

	public JSplitPane getSplitX() {
		return splitX;
	}

	public TreeModel getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(TreeModel treeModel) {
		this.treeModel = treeModel;
	}

	public TableTabs getTable_top() {
		return table_top;
	}

	public void setTable_top(TableTabs table_top) {
		this.table_top = table_top;
	}

	public TableTabs getTable_bot() {
		return table_bot;
	}

	public void setTable_bot(TableTabs table_bot) {
		this.table_bot = table_bot;
	}


	public void setSplitY(JSplitPane splitY) {
		this.splitY = splitY;
	}

	public void setSplitX(JSplitPane splitX) {
		this.splitX = splitX;
	}

	public void setTreeView(TreeView treeView) {
		this.treeView = treeView;
	}

	public AppCore getAppCore() {
		return appCore;
	}

	@Override
	public void update(Object event) {
		if(event instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode tm = (DefaultMutableTreeNode)event;
			treeModel.setRoot(tm);
			SwingUtilities.updateComponentTreeUI(treeView);
		}
		if(event == Dbe.table) {
			//namestiti update da prima dva argumenta tako da imam tablemoddel za koji cu da dodajem tab
		}
	}

}
