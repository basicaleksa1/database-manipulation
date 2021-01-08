package table.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import action.ActionManager;
import app.MainFrame;

public class AddDialog extends JDialog {

	private ArrayList<JTextField> tfs;
	private ArrayList<String> columnNames;
	
	public AddDialog() {
		super(MainFrame.getMainFrame(), "Add");
		columnNames = new ArrayList<String>();
		setPreferredSize(new Dimension(200, 200));
		tfs = new ArrayList<JTextField>();
		BoxLayout box = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
		this.setLayout(box);
		int columnNumb = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTm().getColumnCount();
		for(int i = 0; i < columnNumb; i++) {
			String cName = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable().getChildAt(i).toString();
			columnNames.add(cName);
			add(new JLabel(cName));
			JTextField jf = new JTextField();
			tfs.add(jf);
			add(jf);
		}
		JButton btn = new JButton("add");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> values = new ArrayList<String>();
				for(JTextField tf: tfs) {
					values.add(tf.getText());
				}
				MainFrame.getMainFrame().getAppCore().addRow(columnNames, values, ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable());
				AddDialog.this.setVisible(false);
				//poziv funkcije sa dva niza stringa
			}
		});
		add(btn);
	}

	public ArrayList<JTextField> getTfs() {
		return tfs;
	}

	public void setTfs(ArrayList<JTextField> tfs) {
		this.tfs = tfs;
	}

	public ArrayList<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(ArrayList<String> columnNames) {
		this.columnNames = columnNames;
	}
	
}
