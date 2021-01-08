package table.view;

import java.awt.Dimension;
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
import tree.model.types.AtributeType;

public class CountAvgDialog extends JDialog{

	private JComboBox<String> chooser;
	private JComboBox<String> column;
	private ArrayList<JCheckBox> groups;
	
	public CountAvgDialog() {
		super(MainFrame.getMainFrame(), "Count/Average");
		Entity table = ((TableView)MainFrame.getMainFrame().getTable_top().getSelectedComponent()).getTable();
		chooser = new JComboBox<String>();
		groups = new ArrayList<JCheckBox>();
		chooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				column.removeAllItems();
				if(((String)chooser.getSelectedItem()).equalsIgnoreCase("Count")) {
					for(int i = 0; i < table.getChildCount(); i++) {
						column.addItem(table.getChildAt(i).toString());
					}
				}
				else {
					for(int i = 0; i < table.getChildCount(); i++) {
						AtributeType a = ((Attribute)table.getChildAt(i)).getAtributeType();
						if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
								a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
								a == AtributeType.INT || a == AtributeType.SMALLINT)
							column.addItem(table.getChildAt(i).toString());
					}
					
				}
			}
		});
		column = new JComboBox<String>();
		JPanel p = new JPanel();
		BoxLayout b = new BoxLayout(p ,BoxLayout.Y_AXIS);
		p.setLayout(b);
		chooser.setPreferredSize(new Dimension(100, 100));
		chooser.addItem("Count");
		chooser.addItem("Average");
		BoxLayout box = new BoxLayout(this.getContentPane(),BoxLayout.X_AXIS);
		
		for(int i = 0; i < table.getChildCount(); i++) {
			JCheckBox c = new JCheckBox(table.getChildAt(i).toString());
			groups.add(c);
			p.add(c);
		}
		
		this.setLayout(box);
		this.add(chooser);
		this.add(column);
		this.add(p);
		//this.add(new JLabel("where"));
		JButton btn = new JButton("Go");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> grp = new ArrayList<String>();
				int counter = 0;
				for(int i = 0; i < groups.size(); i++) {
					if(groups.get(i).isSelected()) {
						grp.add(counter++, groups.get(i).getText());
					}
				}
				if(chooser.getSelectedItem().toString().equalsIgnoreCase("count"))
					MainFrame.getMainFrame().getAppCore().countColumns(table, column.getSelectedItem().toString(), grp);
				else MainFrame.getMainFrame().getAppCore().averageValue(table, column.getSelectedItem().toString(), grp);
				CountAvgDialog.this.setVisible(false);
			}
		});
		this.add(btn);
	}
}
