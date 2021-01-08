package database;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import table.model.Row;
import tree.model.Entity;
import tree.model.TreeModel;


@Data
public class DatabaseImplementation implements Database {

	private Repository repository;
	
	public DatabaseImplementation(Repository repository) {
		this.repository = repository;
	}
	
	@Override
	public DefaultMutableTreeNode loadResource() {
		return repository.getSchema();
	}

	@Override
	public List<Row> readDataFromTable(String tableName) {
		return repository.get(tableName);
	}

	@Override
	public void addRow(ArrayList<String> columnNames, ArrayList<String> values, Entity table) {
		repository.addRow(columnNames, values, table);
	}

	@Override
	public void deleteRow(Entity table, Object value) {
		repository.deleteRow(table, value);
	}

	@Override
	public void updateRow(ArrayList<String> values, Entity table, ArrayList<String> columnNames, String value) {
		repository.updateRow(values, table, columnNames, value);
	}

	@Override
	public List<Row> filterData(ArrayList<String> columns, String tableName) {
		return repository.filterData(columns, tableName);
	}

	@Override
	public List<Row> countColumn(Entity entity, String value, ArrayList<String> groups) {
		return repository.countColumn(entity, value, groups);
	}

	@Override
	public List<Row> sortColumn(Entity entity, ArrayList<String> order) {
		return repository.sortColumn(entity, order);
	}

	@Override
	public List<Row> average(Entity entity, String column, ArrayList<String> groups) {
		return repository.average(entity, column, groups);
	}

	@Override
	public List<Row> relationShow(Entity entity, String value) {
		return repository.relationShow(entity, value);
	}

	@Override
	public List<Row> searchShow(Entity entity, String condition) {
		return repository.searchShow(entity, condition);
	}

}
