package database;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import table.model.Row;
import tree.model.Entity;
import tree.model.TreeModel;

public interface Database {

	DefaultMutableTreeNode loadResource();
	
	List<Row> readDataFromTable(String tableName);
	
	void addRow(ArrayList<String> columnNames, ArrayList<String> values, Entity table);
	
	void deleteRow(Entity table, Object value);
	
	void updateRow(ArrayList<String> values, Entity table, ArrayList<String> columnNames, String value);
	
	List<Row> filterData(ArrayList<String> columns, String tableName);

	List<Row> countColumn(Entity entity, String value, ArrayList<String> groups);
	
	List<Row> average(Entity entity, String column, ArrayList<String> groups);

	List<Row> sortColumn(Entity entity, ArrayList<String> order);

	List<Row> relationShow(Entity entity, String value);

	List<Row> searchShow(Entity entity, String condition);
}
