package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import table.view.SortDialog;

public class ActionSort extends AbstractAction{

	public ActionSort() {
		putValue(NAME, "sort");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SortDialog s = new SortDialog();
		s.setVisible(true);
	}

}
