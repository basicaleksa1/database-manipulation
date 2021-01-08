package tree.model;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class Resurs extends DBnode {
	
	private ArrayList<Entity> tables;
	private String name;

	public Resurs(String name) {
		super();
		this.name = name;
		tables = new ArrayList<Entity>();
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		Entity table = (Entity)newChild;
		tables.add(table);
	}

	@Override
	public Enumeration<TreeNode> children() {
		return (Enumeration<TreeNode>)tables;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int index) {
		return this.tables.get(index);
	}

	@Override
	public int getChildCount() {
		return this.tables.size();
	}

	@Override
	public int getIndex(TreeNode aChild) {
		return this.tables.indexOf(aChild);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public void insert(MutableTreeNode arg0, int arg1) {
		this.tables.add(arg1, (Entity)arg0);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void remove(int childIndex) {
		this.tables.remove(childIndex);
	}

	@Override
	public void remove(MutableTreeNode aChild) {
		this.tables.remove(aChild);
	}

	@Override
	public boolean isRoot() {
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public ArrayList<Entity> getTables() {
		return tables;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTables(ArrayList<Entity> tables) {
		this.tables = tables;
	}
}
