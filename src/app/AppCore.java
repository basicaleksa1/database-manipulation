package app;

import java.util.ArrayList;

import database.Database;
import database.DatabaseImplementation;
import database.MSSQLRepository;
import obs.Dbe;
import obs.IListener;
import obs.IObserver;
import table.model.TableModel;
import tree.model.Entity;

public class AppCore implements IObserver{

	private Database database;
	private Information info;
	private ArrayList<IListener> listeners;
	
	public AppCore() {
		listeners = new ArrayList<IListener>();
		info = new Information("147.91.175.155","tim_42_bp2020","tim_42_bp2020","jewnCeYd");	
		database = new DatabaseImplementation(new MSSQLRepository());
		//setTree();
	}
	
	public void searchShow(Entity entity, String condition, TableModel tableModel) {
		tableModel.setRows(database.searchShow(entity, condition));
	}
	
	public void relationShow(Entity entity, String value, TableModel tableModel) {
		tableModel.setRows(database.relationShow(entity, value));
	}
	
	public void sortColumn(Entity entity, ArrayList<String> order, TableModel tabelModel) {
		tabelModel.setRows(database.sortColumn(entity, order));
	}
	
	public void averageValue(Entity entity, String column, ArrayList<String> groups) {
		entity.getTableModel().setRows(database.average(entity, column, groups));
	}
	
	public void countColumns(Entity entity, String column, ArrayList<String> groups) {
		entity.getTableModel().setRows(database.countColumn(entity, column, groups));
	}
	
	//saljemo colone ime trenutni model za trenutnu tabelu i ime tabele
	public void filterData(ArrayList<String> columns, String tableName, TableModel tableModel) {
		tableModel.setRows(database.filterData(columns, tableName));
	}
	
	public void deleteRow(Entity table, Object value) {
		database.deleteRow(table, value);
		table.getTableModel().setRows(database.readDataFromTable(table.getName()));
	}
	
	public void updateRow(ArrayList<String> values, Entity table, ArrayList<String> columnNames, String value) {
		database.updateRow(values, table, columnNames, value);
		table.getTableModel().setRows(database.readDataFromTable(table.getName()));
	}
	
	public void addRow(ArrayList<String> columnNames, ArrayList<String> values, Entity table) {
		database.addRow(columnNames, values, table);
		table.getTableModel().setRows(database.readDataFromTable(table.getName()));
	}
	
	public void readDataFromTable(String tableName, TableModel tableModel) {
		
		tableModel.setRows(database.readDataFromTable(tableName));
	}
	
	public void setTree() {
		this.notifyListeners(database.loadResource());
	}
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Information getInfo() {
		return info;
	}

	public void setInfo(Information info) {
		this.info = info;
	}

	@Override
	public void addListener(IListener listener) {
		if(listener == null) return;
		if(listeners == null) listeners = new ArrayList<IListener>();
		if(listeners.contains(listener)) return;
		listeners.add(listener);
		
	}

	@Override
	public void removeListener(IListener listener) {
		if(listener == null || listeners == null || !listeners.contains(listener)) return;
		listeners.remove(listener);	
	}

	@Override
	public void notifyListeners(Object event) {
		if(this.listeners == null || this.listeners.isEmpty() || event == null)
			return;
		for(IListener l: listeners) {
			l.update(event);
		}
	}
}
