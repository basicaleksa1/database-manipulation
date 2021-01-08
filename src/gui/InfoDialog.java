package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.Information;
import app.MainFrame;

public class InfoDialog extends JDialog{

	private JTextField ip;
	private JTextField name;
	private JTextField username;
	private JPasswordField pass;
	
	
	public InfoDialog() {
		this.setPreferredSize(new Dimension(100, 100));
		init();
		add();
	}
	
	private void init() {
		ip = new JTextField("147.91.175.155");
		name = new JTextField("tim_42_bp2020");
		username = new JTextField("tim_42_bp2020");
		pass = new JPasswordField("jewnCeYd");
	}

	private void add() {
		JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel,BoxLayout.X_AXIS);
		panel.setPreferredSize(new Dimension(100, 100));
		panel.add(ip);
		panel.add(name);
		panel.add(username);
		panel.add(pass);
		JButton a = new JButton("ajde breee");
		a.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getMainFrame().getAppCore().setInfo(new Information(ip.getText(),
						name.getText(), username.getText(), pass.getText()));
				MainFrame.getMainFrame().getAppCore().setTree();
				setVisible(false);
				
			}
		});
		panel.add(a);
		this.add(panel);
	}
	
}
