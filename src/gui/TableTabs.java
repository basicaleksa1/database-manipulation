package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.MainFrame;
import table.model.TableModel;
import table.view.TableView;
import tree.model.Entity;

public class TableTabs extends JTabbedPane {

	public TableTabs() {
		super(JTabbedPane.TOP);
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(TableTabs.this == MainFrame.getMainFrame().getTable_top()) {
					Entity en = ((TableView)TableTabs.this.getSelectedComponent()).getTable();
					MainFrame.getMainFrame().getTable_bot().removeAll();
					for(Entity ent: en.getRelations()) {
						MainFrame.getMainFrame().getAppCore().readDataFromTable(ent.toString(), ent.getTableModel());
						MainFrame.getMainFrame().getTable_bot().addTabs(ent.getTableModel(), ent.getName(), ent);
					}//za sada radi ovako!!!!
					
				}
				
			}
		});
	}
	
	public void addTabs(TableModel tm, String tableName, Entity entity) {
		TableView table = new TableView(tm, tableName, entity);
		this.addTab(tableName, table);
	}

}
