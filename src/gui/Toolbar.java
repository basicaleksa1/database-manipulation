package gui;

import javax.swing.Action;
import javax.swing.JToolBar;

import action.ActionManager;

public class Toolbar extends JToolBar{
	
	public Toolbar() {
		super();
		add(ActionManager.getActionManager().getActionAdd());
		this.addSeparator();
		add(ActionManager.getActionManager().getActionDelete());
		this.addSeparator();
		add((Action)ActionManager.getActionManager().getActionUpdate());
		this.addSeparator();
		add((Action)ActionManager.getActionManager().getActionFilter());
		this.addSeparator();
		add((Action)ActionManager.getActionManager().getActionSort());
		this.addSeparator();
		add((Action)ActionManager.getActionManager().getActionCountAvg());
		this.addSeparator();
		add((Action)ActionManager.getActionManager().getActionSearch());
	}
}
