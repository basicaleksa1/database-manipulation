package table.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import obs.IListener;
import obs.IObserver;
import tree.model.Entity;

public class TableModel extends DefaultTableModel {
	
	private List<Row> rows;
	
	public TableModel(ArrayList<Row> rows, Entity table) {
		this.rows = rows;
		setRows(rows);
	}
	
	public TableModel() {
		this.addColumn("no data");
	}

	private void updateModel() {
		int columnCount = rows.get(1).getFields().keySet().size();

        Vector columnVector = DefaultTableModel.convertToVector(rows.get(1).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i = 0; i < rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        setDataVector(dataVector, columnVector);
        fireTableDataChanged();
	}
	
	public void setRows(List<Row> rows) {
		this.rows = rows;
		updateModel();
	}

}
