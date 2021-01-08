package tree.view;

import javax.swing.JTree;
import tree.controller.ResursSelectionListener;

public class TreeView extends JTree{

		public TreeView() {
			setCellRenderer(new TreeCellRenderer());
			addTreeSelectionListener(new ResursSelectionListener());
		}
}
