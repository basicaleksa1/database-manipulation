package tree.model;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import table.model.TableModel;

public class Entity extends DBnode {
		
	private String name;
	private ArrayList<Attribute> atributes;
	private TableModel tableModel;
	private ArrayList<Entity> relations;
	private Attribute pk;

	public Entity(String name) {
		super();
		this.name = name;
		atributes = new ArrayList<Attribute>();
		setRelations(new ArrayList<Entity>());
		this.tableModel = new TableModel();
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		Attribute atribute = (Attribute)newChild;
		atributes.add(atribute);
	}

	@Override
	public Enumeration<TreeNode> children() {
		return (Enumeration<TreeNode>) atributes;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int index) {
		return this.atributes.get(index);
	}

	@Override
	public int getChildCount() {
		return this.atributes.size();
	}

	@Override
	public int getIndex(TreeNode aChild) {
		return this.atributes.indexOf(aChild);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public void removeFromParent() {
		parent.remove(this);
	}

	@Override
	public void insert(MutableTreeNode arg0, int arg1) {
		this.atributes.add(arg1, (Attribute)arg0);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void remove(int childIndex) {
		this.atributes.remove(childIndex);
	}

	@Override
	public void remove(MutableTreeNode aChild) {
		this.atributes.remove(aChild);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Attribute> getAtrbiutes() {
		 return atributes;
	}
	
	public TableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public Attribute getPk() {
		return pk;
	}

	public void setPk(Attribute pk) {
		this.pk = pk;
	}

	public ArrayList<Entity> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<Entity> relations) {
		this.relations = relations;
	}


}
