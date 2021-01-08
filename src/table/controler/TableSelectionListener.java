package table.controler;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.MainFrame;
import table.view.TableView;
import tree.model.Entity;

public class TableSelectionListener implements ListSelectionListener{

	private JTable table;
	
	public TableSelectionListener(JTable table) {
		this.table = table;
		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {

		//ovo se poziva dva puta jednom na kik drugi put na otpust klika
        //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
        //ovde ce da ide poziv za otvaranje donje tabele
		try {
			Entity en = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable();
			for(Entity t: en.getRelations()) {
				int row = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getSelectedRow();
				System.out.println("broj reda "+ row + "ovo je pk " + t.getPk().toString());
				int column = 0; 
				for(int i = 0; i < ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getColumnCount(); i++) {
					if(((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getColumnName(i).equalsIgnoreCase(t.getPk().toString())){
						column = i;
						break;
					}
				}
				Object value = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getJtable().getValueAt(row, column);
				MainFrame.getMainFrame().getAppCore().relationShow(t, value.toString(), t.getTableModel());
				MainFrame.getMainFrame().getTable_bot().removeAll();
				MainFrame.getMainFrame().getTable_bot().addTabs(t.getTableModel(), t.getName(), t);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
	}

}
