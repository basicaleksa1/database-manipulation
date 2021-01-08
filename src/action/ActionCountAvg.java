package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import table.view.CountAvgDialog;

public class ActionCountAvg extends AbstractAction{

	public ActionCountAvg() {
		putValue(NAME, "Count/Avg");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CountAvgDialog c = new CountAvgDialog();
		c.setVisible(true);
		
	}

}
