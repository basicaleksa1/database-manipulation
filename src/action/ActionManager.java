package action;

import javax.swing.AbstractAction;

public class ActionManager {

	private static ActionManager instance;
	
	private ActionAdd actionAdd;
	private ActionDelete actionDelete;
	private ActionUpdate actionUpdate;
	private ActionInfo actionInfo;
	private ActionFilter actionFilter;
	private ActionCountAvg actionCountAvg;
	private ActionSort actionSort;
	private ActionSearch actionSearch;
	
	private ActionManager() {
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		actionAdd = new ActionAdd();
		actionDelete = new ActionDelete();
		actionUpdate = new ActionUpdate();
		setActionInfo(new ActionInfo());
		actionFilter = new ActionFilter();
		actionCountAvg = new ActionCountAvg();
		actionSort = new ActionSort();
		actionSearch = new ActionSearch();
	}

	public static ActionManager getActionManager() {
		if(instance == null) 
			instance = new ActionManager();
		return instance;
	}

	public AbstractAction getActionAdd() {
		return actionAdd;
	}

	public AbstractAction getActionDelete() {
		return actionDelete;
	}

	public AbstractAction getActionUpdate() {
		return actionUpdate;
	}

	public ActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(ActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}

	public ActionFilter getActionFilter() {
		return actionFilter;
	}

	public void setActionFilter(ActionFilter actionFilter) {
		this.actionFilter = actionFilter;
	}

	public ActionCountAvg getActionCountAvg() {
		return actionCountAvg;
	}

	public void setActionCountAvg(ActionCountAvg actionCountAvg) {
		this.actionCountAvg = actionCountAvg;
	}

	public ActionSort getActionSort() {
		return actionSort;
	}

	public void setActionSort(ActionSort actionSort) {
		this.actionSort = actionSort;
	}

	public ActionSearch getActionSearch() {
		return actionSearch;
	}

	public void setActionSearch(ActionSearch actionSearch) {
		this.actionSearch = actionSearch;
	}
	
}
