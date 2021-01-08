package tree.model;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import tree.model.types.LimitationType;

public class Limitation extends DBnode{

	private LimitationType name;
	
	public Limitation(LimitationType name) {
		this.name = name;
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		return;
	}

	@Override
	public Enumeration<TreeNode> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TreeNode getChildAt(int index) {
		return null;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return super.getChildCount();
	}

	@Override
	public int getIndex(TreeNode aChild) {
		// TODO Auto-generated method stub
		return super.getIndex(aChild);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public void remove(MutableTreeNode aChild) {
		return;
	}

	@Override
	public void removeAllChildren() {
		return;
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
}
