package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.MainFrame;
import table.view.AddDialog;

public class ActionAdd extends AbstractAction{

	public ActionAdd() {
		putValue(NAME, "Add");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		AddDialog a = new AddDialog();
		a.setVisible(true);
	}

}
