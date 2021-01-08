package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import table.view.SearchDialog;

public class ActionSearch extends AbstractAction{

	public ActionSearch() {
		putValue(NAME, "search");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SearchDialog s = new SearchDialog();
		s.setVisible(true);
	}
	
	
}
