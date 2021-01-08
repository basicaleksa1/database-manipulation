package table.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.MainFrame;
import tree.model.Attribute;
import tree.model.Entity;

public class SortDialog extends JDialog{

	private ArrayList<JComboBox<String>> sort;
	private ArrayList<JComboBox<Attribute>> columnNames;
	private ArrayList<String> columnSort;
	int counter = 1;
	
	public SortDialog() {
		super(MainFrame.getMainFrame(), "Sort");
		sort = new ArrayList<JComboBox<String>>();
		columnNames = new ArrayList<JComboBox<Attribute>>();
		columnSort = new ArrayList<String>();
		BoxLayout box = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		JPanel panel = new JPanel(new FlowLayout());
		this.setLayout(box);
		Entity table = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable();
		int columnNumb = table.getChildCount();
		JComboBox<Attribute> j = new JComboBox<Attribute>();
		for(int i = 0; i < columnNumb; i++) {
			j.addItem((Attribute) table.getChildAt(i));
		}
		panel.add(new JLabel(String.valueOf(counter++)));
		panel.add(j);
		columnNames.add(j);
		JComboBox<String> k = makeSort();
		panel.add(k);
		sort.add(k);
		JButton btn = new JButton("Add another sort");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel asd = new JPanel(new FlowLayout());
				JComboBox<Attribute> j = new JComboBox<Attribute>();
				for(int i = 0; i < columnNumb; i++) {
					j.addItem((Attribute) table.getChildAt(i));
				}
				asd.add(new JLabel(String.valueOf(counter++)));
				asd.add(j);
				columnNames.add(j);
				JComboBox<String> k = makeSort();
				asd.add(k);
				sort.add(k);
				SortDialog.this.add(asd, 0);
				SortDialog.this.revalidate();
				SortDialog.this.repaint();
				
			}
		});
		this.add(panel);
		this.add(btn);
		JButton sortn = new JButton("Sort");
		sortn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> orders = new ArrayList<String>();
				for(int i = 0; i < columnNames.size(); i++) {
					String s = columnNames.get(i).getSelectedItem().toString() + " " + sort.get(i).getSelectedItem().toString();
					orders.add(s);
				}
				MainFrame.getMainFrame().getAppCore().sortColumn(table, orders, table.getTableModel());
				SortDialog.this.setVisible(false);
			}
		});
		this.add(sortn);
	}
	
	private JComboBox<String> makeSort(){
		JComboBox<String> j = new JComboBox<String>();
		j.addItem("Asc");
		j.addItem("Desc");
		return j;
	}
}
