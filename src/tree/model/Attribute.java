package tree.model;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import tree.model.types.AtributeType;

public class Attribute extends DBnode {

	private String name;
	private ArrayList<Limitation> limitations;
	private ArrayList<Attribute> relations;
	private AtributeType atributeType;
	private int length;
	
	public Attribute(String name, AtributeType atributeType, int length) {
		this.name = name;
		this.atributeType = atributeType;
		this.setLength(length);
		setRelations(new ArrayList<Attribute>());
		limitations = new ArrayList<Limitation>();
	}
	
	public Attribute(String name) {
		this.name = name;
		limitations = new ArrayList<Limitation>();
		
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		Limitation page = (Limitation)newChild;
		limitations.add(page);
	}

	@Override
	public Enumeration<TreeNode> children() {
		return (Enumeration<TreeNode>) limitations;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int index) {
		return this.limitations.get(index);
	}

	@Override
	public int getChildCount() {
		return this.limitations.size();
	}

	@Override
	public int getIndex(TreeNode aChild) {
		return this.limitations.indexOf(aChild);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}


	@Override
	public void insert(MutableTreeNode arg0, int arg1) {
		this.limitations.add(arg1, (Limitation)arg0);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void remove(int childIndex) {
		this.limitations.remove(childIndex);
	}

	@Override
	public void remove(MutableTreeNode aChild) {
		this.limitations.remove((Limitation)aChild);
	}

	@Override
	public void removeFromParent() {
		parent.remove(this);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<Limitation> getLimitations() {
		return limitations;
	}

	public AtributeType getAtributeType() {
		return atributeType;
	}

	public void setAtributeType(AtributeType atributeType) {
		this.atributeType = atributeType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public ArrayList<Attribute> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<Attribute> relations) {
		this.relations = relations;
	}
}
