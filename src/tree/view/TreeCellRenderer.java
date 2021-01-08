package tree.view;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import tree.model.Attribute;
import tree.model.Limitation;
import tree.model.Resurs;
import tree.model.Entity;


public class TreeCellRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree,
													Object value,
													boolean selected,
													boolean expanded,
													boolean leaf,
													int row,
													boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		
		if(value instanceof Resurs) {
			URL image = getClass().getResource("/images/resurs.png");
			Icon icon = null;
			if(image != null) {
				icon = new ImageIcon(image);
				this.setIcon(icon);
			}
		}
		else if(value instanceof Entity) {
			URL image = getClass().getResource("/images/table.png");
			Icon icon = null;
			if(image != null) {
				icon = new ImageIcon(image);
				this.setIcon(icon);
			}
		}
		else if(value instanceof Attribute) {
			URL image = this.getClass().getResource("/images/atribute.png");
			Icon icon = null;
			if(image != null) {
				icon = new ImageIcon(image);
				this.setIcon(icon);
			}
		}
		else if(value instanceof Limitation) {
			URL image = this.getClass().getResource("/images/limit.png");
			Icon icon = null;
			if(image != null) {
				icon = new ImageIcon(image);
				this.setIcon(icon);
			}
		}
		return this;
	}

}
