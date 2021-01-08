package table.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.MainFrame;
import tree.model.Attribute;
import tree.model.Entity;
import tree.model.types.AtributeType;

public class SearchDialog extends JDialog{

	private ArrayList<JComboBox<Attribute>> columnNames;
	private ArrayList<JComboBox<String>> comparison;
	private ArrayList<JTextField> values;
	private ArrayList<String> andor;
	int counter;
	
	public SearchDialog() {
		super(MainFrame.getMainFrame(), "Search");
		counter = 0;
		columnNames = new ArrayList<JComboBox<Attribute>>();
		comparison = new ArrayList<JComboBox<String>>();
		values = new ArrayList<JTextField>();
		andor = new ArrayList<String>();
		BoxLayout box = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		JPanel panel = new JPanel(new FlowLayout());
		this.setLayout(box);
		Entity table = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable();
		int columnNumb = table.getChildCount();
		JComboBox<Attribute> j = fillJCB(columnNumb, table);
		panel.add(j);
		columnNames.add(j);
		JComboBox<String> k = new JComboBox<String>();
		comparison.add(k);
		panel.add(k);
		JTextField f = new JTextField("", 20);
		values.add(f);
		JPanel a = new JPanel(new FlowLayout());
		JButton and = new JButton("and");
		and.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel asd = new JPanel(new FlowLayout());
				JComboBox<Attribute> j = fillJCB(columnNumb, table);
				asd.add(j);
				columnNames.add(j);
				JComboBox<String> k = new JComboBox<String>();
				comparison.add(k);
				asd.add(k);
				JTextField f = new JTextField("", 20);
				values.add(f);
				asd.add(f);
				andor.add("AND");
				SearchDialog.this.add(asd, counter++);
				SearchDialog.this.revalidate();
				SearchDialog.this.repaint();
				
			}
		});
		JButton or = new JButton("or");
		or.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel asd = new JPanel(new FlowLayout());
				JComboBox<Attribute> j = fillJCB(columnNumb, table);
				asd.add(j);
				columnNames.add(j);
				JComboBox<String> k = new JComboBox<String>();
				comparison.add(k);
				asd.add(k);
				JTextField f = new JTextField("", 20);
				values.add(f);
				asd.add(f);
				andor.add("OR");
				SearchDialog.this.add(asd, counter++);
				SearchDialog.this.revalidate();
				SearchDialog.this.repaint();
				
			}
		});
		a.add(and);
		a.add(or);
		panel.add(f);
		this.add(panel);
		this.add(a);
		JButton search = new JButton("search");
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = "";
				for(int i = 0; i < columnNames.size(); i++) {
					AtributeType a = ((Attribute)columnNames.get(i).getSelectedItem()).getAtributeType();
					if(i == columnNames.size() - 1) {
						if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
								a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
								a == AtributeType.INT || a == AtributeType.SMALLINT) {
							query = query.concat(columnNames.get(i).getSelectedItem().toString() + comparison.get(i).getSelectedItem().toString() + values.get(i).getText());
							break;
						}
						else if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
								a == AtributeType.NVARCHAR) {
							query = query.concat(columnNames.get(i).getSelectedItem().toString() + " LIKE '" + comparison.get(i).getSelectedItem().toString() + values.get(i).getText() + "'");
							break;
						}
					}
					if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
							a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
							a == AtributeType.INT || a == AtributeType.SMALLINT) {
						query = query.concat(columnNames.get(i).getSelectedItem().toString() + comparison.get(i).getSelectedItem().toString() + values.get(i).getText() + " " + andor.get(i) + " ");
					}
					else if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
							a == AtributeType.NVARCHAR) {
						query = query.concat(columnNames.get(i).getSelectedItem().toString() + " LIKE '" + comparison.get(i).getSelectedItem().toString() + values.get(i).getText() + "' " + andor.get(i) + " ");
					}
				}
				System.out.println(query);
				MainFrame.getMainFrame().getAppCore().searchShow(table, query, table.getTableModel());
				SearchDialog.this.setVisible(false);
			}
		});
		this.add(search);
		
	}
	
	private JComboBox<Attribute> fillJCB(int n, Entity table) {
		JComboBox<Attribute> j = new JComboBox<Attribute>();
		for(int i = 0; i < n; i++) {
			j.addItem((Attribute) table.getChildAt(i));
			j.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> k = comparison.get(columnNames.indexOf(j));
					k.removeAllItems();
					AtributeType a = ((Attribute)j.getSelectedItem()).getAtributeType();
					if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
							a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
							a == AtributeType.INT || a == AtributeType.SMALLINT) {
						k.addItem("<");
						k.addItem(">");
						k.addItem("=");
					}
					else {
						k.addItem("%");
						k.addItem("_");
					}
				}
			});
		}
		return j;
	}
}
