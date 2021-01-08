package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.InfoDialog;

public class ActionInfo extends AbstractAction{

	
	public ActionInfo() {
		putValue(NAME, "set database");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		InfoDialog id = new InfoDialog();
		id.show();
	}

}
