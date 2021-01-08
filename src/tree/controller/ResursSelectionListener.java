package tree.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import app.MainFrame;
import tree.model.Entity;

public class ResursSelectionListener implements TreeSelectionListener {

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		TreePath path = e.getPath();
		MainFrame.getMainFrame().getTreeView().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					if(node instanceof Entity) {
						MainFrame.getMainFrame().getAppCore().readDataFromTable(((Entity) node).toString().toUpperCase(), ((Entity) node).getTableModel());
						MainFrame.getMainFrame().getTable_top().addTabs(((Entity) node).getTableModel(), ((Entity) node).toString().toUpperCase(), (Entity) node);
						//MainFrame.getMainFrame().getTable_top().getSelectedComponent();
						MainFrame.getMainFrame().getTable_bot().removeAll();
						for(Entity en: ((Entity)node).getRelations()) {
							MainFrame.getMainFrame().getAppCore().readDataFromTable(en.toString().toUpperCase(), en.getTableModel());
							MainFrame.getMainFrame().getTable_bot().addTabs(en.getTableModel(), en.toString(), (Entity) node);
						}
					}
				}
			}
		});
	}

}
