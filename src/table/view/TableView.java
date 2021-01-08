package table.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.Toolbar;
import table.controler.TableSelectionListener;
import table.model.TableModel;
import tree.model.Entity;

public class TableView extends JPanel {
	
	private TableModel tm;
	private String tableName;
	private JTable jtable;
	private Entity table;
	
	public TableView(TableModel tm, String tableName, Entity table) {
		super(new BorderLayout());
		this.tm = tm;
		this.tableName = tableName;
		this.table = table;
		add(new Toolbar(), BorderLayout.SOUTH);
		jtable = new JTable(tm);
		jtable.getSelectionModel().addListSelectionListener(new TableSelectionListener(jtable));
		JScrollPane scroll = new JScrollPane(jtable);
		add(scroll, BorderLayout.CENTER);
	}

	public TableModel getTm() {
		return tm;
	}

	public void setTm(TableModel tm) {
		this.tm = tm;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public JTable getJtable() {
		return jtable;
	}

	public void setJtable(JTable jtable) {
		this.jtable = jtable;
	}

	public Entity getTable() {
		return table;
	}

	public void setTable(Entity table) {
		this.table = table;
	}

}
