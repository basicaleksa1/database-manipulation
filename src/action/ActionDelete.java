package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.MainFrame;
import table.view.TableView;
import tree.model.Attribute;
import tree.model.Entity;

public class ActionDelete extends AbstractAction {

	public ActionDelete() {
		putValue(NAME, "Delete");
		
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
		MainFrame.getMainFrame().getAppCore().deleteRow(e, value);
		System.out.println("broj kolone je " + value.toString());
	}
}
