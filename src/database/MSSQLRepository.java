package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import app.MainFrame;
import errorHandler.ErrorHandler;
import errorHandler.Errors;
import table.model.Row;
import table.model.TableModel;
import table.view.TableView;
import tree.model.Attribute;
import tree.model.DBnode;
import tree.model.Limitation;
import tree.model.Resurs;
import tree.model.Entity;
import tree.model.types.AtributeType;
import tree.model.types.LimitationType;

public class MSSQLRepository implements Repository{

	private Connection connection;
		
	private void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		String ip = MainFrame.getMainFrame().getAppCore().getInfo().getIp();
		String baza = MainFrame.getMainFrame().getAppCore().getInfo().getBaza();
		String username = MainFrame.getMainFrame().getAppCore().getInfo().getUsername();
		String password = MainFrame.getMainFrame().getAppCore().getInfo().getPass();
		connection = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + "/" + baza
				, username, password);
	}
	
	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			connection = null;
		}
	}
	
	@Override
	public DBnode getSchema() {
		Resurs rm = null;
		try {
			this.initConnection();
			System.out.println("preso");
			String baza = MainFrame.getMainFrame().getAppCore().getInfo().getBaza();
			DatabaseMetaData metaData = connection.getMetaData();
			rm = new Resurs(baza);
			
			String tableType[] = {"TABLE"};
			ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);
			while(tables.next()) {
				String tableName = tables.getString("TABLE_NAME");
				if(tableName.startsWith("trace") || tableName.startsWith("sys")) continue;
				Entity ttm = new Entity(tableName);
				rm.add(ttm);
				
				
				ResultSet atributes = metaData.getColumns(connection.getCatalog(), null, tableName, null);
				while(atributes.next()) {
					String atributeName = atributes.getString("COLUMN_NAME");
					String atributeType = atributes.getString("TYPE_NAME");
					int atributeSize = Integer.parseInt(atributes.getString("COLUMN_SIZE"));
					Attribute am = new Attribute(atributeName, AtributeType.valueOf((atributeType.toUpperCase())), atributeSize);
					
					ResultSet pk = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
					while(pk.next()) {
						String limit = pk.getString("COLUMN_NAME");
						if(am.getName().equalsIgnoreCase(limit)) {
							Limitation l = new Limitation(LimitationType.PRIMARY_KEY);
							ttm.setPk(am);
							am.add(l);
						}
					}
					ResultSet fk = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
					while(fk.next()) {
						String limit = fk.getString("FKCOLUMN_NAME");
						if(am.getName().equalsIgnoreCase(limit)) {
							Limitation l = new Limitation(LimitationType.FOREIGN_KEY);
							//System.out.println(fk.getString("PKTABLE_NAME"));
							//Entity e = new Entity(fk.getString("PKTABLE_NAME"));
							//e.setPk(am);
							//ttm.relations.add(e);
							am.add(l);
						}
					}
					String nullable = atributes.getString("IS_NULLABLE");
					if(nullable.equalsIgnoreCase("no")) {
						Limitation l = new Limitation(LimitationType.NOT_NULL);
						am.add(l);
					}
					ttm.add(am);
				}
			}
			for(Entity ent: rm.getTables()) {
				ResultSet fk = metaData.getImportedKeys(connection.getCatalog(), null, ent.getName());
				while(fk.next()) {
					String tname = fk.getString("PKTABLE_NAME");
					System.out.println(tname);
					for(Entity er: rm.getTables()) {
						if(er.getName().equalsIgnoreCase(tname)) {
							ent.getRelations().add(er);
						}
					}
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return rm;
	}
	
	@Override
	public List<Row> relationShow(Entity entity, String value) {
		List<Row> rows = new ArrayList<Row>();
		try {
			initConnection();
			String query = "SELECT * FROM " + entity.toString() + " WHERE " + entity.getPk().toString() + " = ";
			AtributeType a = entity.getPk().getAtributeType();
			if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
					a == AtributeType.NVARCHAR) {
				query = query.concat("'" + value + "'");
			}
			if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
						a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
						a == AtributeType.INT || a == AtributeType.SMALLINT) {
				query = query.concat(value);
			}
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Row row = new Row();
				row.setName(entity.getName());
				rows.add(row);
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), rs.getString(i));
					System.out.println(rsmd.getColumnName(i) + " " + rs.getString(i));
				}
				rows.add(row);
				System.out.println(rows.size());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public List<Row> get(String from) {
		List<Row> rows = new ArrayList<Row>();
	
		try {
			this.initConnection();
			String query = "SELECT * FROM " + from;
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Row row = new Row();
				row.setName(from);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), rs.getString(i));
				}
				rows.add(row);
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return rows;
	}

	@Override
	public void addRow(ArrayList<String> columnNames, ArrayList<String> values, Entity table) {
		String query = "INSERT INTO " + table.toString() + " (";
		for(int i = 0; i < columnNames.size(); i++) {
			if(i ==  columnNames.size() - 1) {
				query = query.concat(columnNames.get(i) + " )");
				break;
			}
			query = query.concat(columnNames.get(i) + ", ");
		}
		query = query.concat("VALUES (");
		for(int i = 0; i < columnNames.size(); i++) {
			AtributeType a = ((Attribute)table.getChildAt(i)).getAtributeType();
			if(i == columnNames.size() - 1) {
				if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
						a == AtributeType.NVARCHAR) {
					query = query.concat("'" + values.get(i) + "')");
				}
				if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
						a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
						a == AtributeType.INT || a == AtributeType.SMALLINT) {
					query = query.concat(values.get(i) + ")");
				}
				if(a == AtributeType.DATE || a == AtributeType.DATETIME) {
					query = query.concat("CONVERT(DATE,'" + values.get(i) + "', 106))");
				}
				break;
			}
			if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
					a == AtributeType.NVARCHAR) {
				query = query.concat("'" + values.get(i) + "', ");
			}
			if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
					a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
					a == AtributeType.INT || a == AtributeType.SMALLINT) {
				query = query.concat(values.get(i) + ", ");
			}
			if(a == AtributeType.DATE || a == AtributeType.DATETIME) {
				query = query.concat("CONVERT(DATE,'" + values.get(i) + "', 106), ");
			}
		}
		//ovako radi znaci samo da promenim proveru za pravljenje stringa i tjt
		//query = "INSERT INTO " + table.toString() + "(country_name, region_id, country_id) VALUES"
		//		+ "('Srbija', 1, 'SR')";
		System.out.println(query);
		try {
			initConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ErrorHandler.handle(Errors.SQL);
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}

	@Override
	public void updateRow(ArrayList<String> values, Entity table, ArrayList<String> columnNames, String value) {
		try {
			initConnection();
			String query = "UPDATE " + table.getName() + " SET ";
			
			for(int i = 0; i < columnNames.size(); i++) {
				AtributeType a = ((Attribute)table.getChildAt(i)).getAtributeType();
				if(i == columnNames.size() - 1) {
					if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
							a == AtributeType.NVARCHAR) {
						query = query.concat(columnNames.get(i) + " = " + "'" +  values.get(i) + "'");
					}
					if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
							a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
							a == AtributeType.INT || a == AtributeType.SMALLINT) {
						query = query.concat(columnNames.get(i) + " = " + values.get(i));
					}
					if(a == AtributeType.DATE || a == AtributeType.DATETIME) {
						query = query.concat(columnNames.get(i) + " = " + "CONVERT(DATE,'" + values.get(i) + "', 106))");
					}
					break;
				}
				if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
						a == AtributeType.NVARCHAR) {
					query = query.concat(columnNames.get(i) + " = " + "'" + values.get(i) + "', ");
				}
				if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
						a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
						a == AtributeType.INT || a == AtributeType.SMALLINT) {
					query = query.concat(columnNames.get(i) + " = " + values.get(i) + ", ");
				}
				if(a == AtributeType.DATE || a == AtributeType.DATETIME) {
					query = query.concat(columnNames.get(i) + " = " + "CONVERT(DATE,'" + values.get(i) + "', 106)), ");
				}
			}
			AtributeType a = table.getPk().getAtributeType();
			if(a == AtributeType.CHAR || a == AtributeType.VARCHAR || a == AtributeType.TEXT || 
						a == AtributeType.NVARCHAR) query = query.concat(" WHERE " + table.getPk().getName() + " = '" + value + "'");
			else if(a == AtributeType.REAL || a == AtributeType.FLOAT || a == AtributeType.BIT || 
						a == AtributeType.BIGINT || a == AtributeType.DECIMAL || a == AtributeType.NUMERIC || 
						a == AtributeType.INT || a == AtributeType.SMALLINT)query = query.concat(" WHERE " + table.getPk().getName() + " = " + value);
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ErrorHandler.handle(Errors.SQL);
			//e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}

	@Override
	public List<Row> searchShow(Entity entity, String condition) {
		List<Row> rows = new ArrayList<Row>();
		try {
			initConnection();
			String query = "SELECT * FROM " + entity.getName() + " WHERE " + condition;
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Row row = new Row();
				row.setName(entity.getName());
				
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), rs.getString(i));
				}
				rows.add(row);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public List<Row> sortColumn(Entity entity, ArrayList<String> order) {
		
		List<Row> rows = new ArrayList<Row>();
		try {
			this.initConnection();
			
			String query = "SELECT * FROM " + entity.getName() + " ORDER BY ";
			for(int i = 0; i < order.size(); i++) {
				if(i == order.size() - 1) {
					query = query.concat(order.get(i));
					break;
				}
				query = query.concat(order.get(i) + ", ");
			}
			System.out.println(query);
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Row row = new Row();
				row.setName(entity.getName());
				
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), rs.getString(i));
				}
				rows.add(row);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			ErrorHandler.handle(Errors.SORT);
		}
		return rows;
	}

	@Override
	public void deleteRow(Entity table, Object value) {
		String query = null;
		Object pk = table.getPk();
		try {
			initConnection();
			if(value instanceof Integer) {
				query = "DELETE FROM " + table.toString() + " WHERE " + pk + "=" + value.toString();
			}
			else if(value instanceof String) {
				query = "DELETE FROM " + table.toString() + " WHERE " + pk + " = " + "'" + value.toString() + "'";
			}
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.execute();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
	}

	@Override
	public List<Row> filterData(ArrayList<String> columns, String tableName) {
		List<Row> rows = new ArrayList<Row>();
		
		try {
			this.initConnection();
			String query = "SELECT ";
			for(int k = 0; k < columns.size(); k++) {
				if(k == columns.size() - 1) {
					query = query.concat(columns.get(k).toUpperCase() + " ");
					break;
				}
				query = query.concat(columns.get(k).toUpperCase() + ", ");
			}
			query = query + "FROM " + tableName;
			//mora da se napravi neki gui koji ce da proprati ovo
			//query = "SELECT REGION_ID, COUNTRY_ID FROM " + tableName;
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Row row = new Row();
				row.setName(tableName);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), rs.getString(i));
				}
				rows.add(row);
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeConnection();
		}
		return rows;
	}

	@Override
	public List<Row> countColumn(Entity entity, String value, ArrayList<String> groups) {
		List<Row> rows = new ArrayList<Row>();
		try {
			initConnection();
			//ov region_id je da bi se prilazala tabela
			String query = "SELECT COUNT(" + value + "), ";
			for(int i = 0; i < groups.size(); i++) {
				if(i == groups.size() - 1) {
					query = query.concat(groups.get(i));
					break;
				}
				query = query.concat(groups.get(i) + ", ");
			}
			query = query.concat(" FROM " + entity.toString() + " GROUP BY ");
			if(groups.isEmpty()) query = "SELECT COUNT(" + value + ") FROM " + entity.toString() + " ";
			for(int i = 0; i < groups.size(); i++) {
				if(i == groups.size() - 1) {
					query = query.concat(groups.get(i));
					break;
				}
				query = query.concat(groups.get(i) + ", ");
			}
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet r = ps.executeQuery();
			while(r.next()) {
				Row row = new Row();
				row.setName(entity.getName());
				
				ResultSetMetaData rsmd = r.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), r.getString(i));
				}
				rows.add(row);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return rows;
	}

	@Override
	public List<Row> average(Entity entity, String column, ArrayList<String> groups) {
		List<Row> rows = new ArrayList<Row>();
		try {
			initConnection();
			String query = "SELECT AVG (" + column + "), ";
			for(int i = 0; i < groups.size(); i++) {
				if(i == groups.size() - 1) {
					query = query.concat(groups.get(i));
					break;
				}
				query = query.concat(groups.get(i) + ", ");
			}
			query = query.concat(" FROM " + entity.toString() + " GROUP BY ");
			if(groups.isEmpty())query = "SELECT AVG (" + column + ") FROM " + entity.toString() + " ";
			for(int i = 0; i < groups.size(); i++) {
				if(i == groups.size() - 1) {
					query = query.concat(groups.get(i));
					break;
				}
				query = query.concat(groups.get(i) + ", ");
			}
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet r = ps.executeQuery();
			while(r.next()) {
				Row row = new Row();
				row.setName(entity.getName());
				
				ResultSetMetaData rsmd = r.getMetaData();
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addField(rsmd.getColumnName(i), r.getString(i));
				}
				rows.add(row);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return rows;
	}
}
