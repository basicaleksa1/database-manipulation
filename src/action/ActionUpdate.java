package action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import app.MainFrame;
import table.model.Row;
import table.model.TableModel;
import table.view.TableView;
import table.view.UpdateDialog;
import tree.model.Entity;

public class ActionUpdate extends AbstractAction{

	public ActionUpdate() {
		putValue(NAME, "Update");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Entity e = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable();
		int row = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getSelectedRow();
		System.out.println("broj reda "+ row + "ovo je pk " + e.getPk().toString());
		int column = 0; 
		for(int i = 0; i < ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getColumnCount(); i++) {
			if(((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getColumnName(i).equalsIgnoreCase(e.getPk().toString())){
				column = i;
				break;
			}
		}
		Object value = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getValueAt(row, column);
		System.out.println(value.toString());
		UpdateDialog u = new UpdateDialog(value);
		u.setVisible(true);
		// value cu da stavim na kraj values
	}

}
