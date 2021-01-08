package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import table.view.FilterDialog;

public class ActionFilter extends AbstractAction{

	public ActionFilter() {
		putValue(NAME, "filter");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		FilterDialog f = new FilterDialog();
		f.setVisible(true);
	}

}
