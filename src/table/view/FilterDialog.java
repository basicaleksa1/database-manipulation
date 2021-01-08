package table.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import app.MainFrame;
import tree.model.Entity;

public class FilterDialog extends JDialog{

	private ArrayList<String> columnNames;
	private ArrayList<JCheckBox> cb;
	
	public FilterDialog() {
		super(MainFrame.getMainFrame(), "Filter");
		columnNames = new ArrayList<String>();
		cb = new ArrayList<JCheckBox>();
		
		BoxLayout box = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(box);
		Entity table = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable();
		int columnNumb = table.getChildCount();
		for(int i = 0; i < columnNumb; i++) {
			JCheckBox c = new JCheckBox(table.getChildAt(i).toString());
			cb.add(c);
			add(c);
		}
		JButton btn = new JButton("filter");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < cb.size(); i++) {
					if(cb.get(i).isSelected()) {
						columnNames.add(table.getChildAt(i).toString());
					}
				}
				MainFrame.getMainFrame().getAppCore().filterData(columnNames, table.getName(), table.getTableModel());
				FilterDialog.this.setVisible(false);
			}
		});
		add(btn);
	}
}
