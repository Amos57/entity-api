package com.amos.entityapi.dialect.MySQL;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amos.entityapi.Session;
import com.amos.entityapi.annotations.AutoValue;
import com.amos.entityapi.annotations.Ignor;
import com.amos.entityapi.entity.TableMapper;

public class MySQLSession implements Session {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;

	Map<Class<?>, TableMapper> tms;
	private boolean showSQL = Boolean.parseBoolean(System.getProperty(
			"show_sql", "false"));

	public MySQLSession(Connection connection, Map<Class<?>, TableMapper> tm) {
		this.connection = connection;
		tms = tm;

	}

	@Override
	public boolean update(Object o, Object... id) {
		Class<?> cls = o.getClass();
		TableMapper tm = tms.get(cls);
		String upd = "UPDATE " + tm.getName() + " WHERE ";
		return false;
	}

	@Override
	public boolean save(Object o) {
		Class<?> cls = o.getClass();
		TableMapper tm = tms.get(cls);
		StringBuilder sb = new StringBuilder();
		StringBuilder variables = new StringBuilder();
		PreparedStatement ps = null;
		final String insert = "INSERT INTO " + tm.getName() + "(";
		sb.append(insert);
		for (String column : tm.getColumns()) {
			if (tm.getAutoValues().contains(column))
				continue;

			sb.append(column + ",");
			variables.append("?,");
		}
		sb.delete(sb.length() - 1, sb.length());
		variables.delete(variables.length() - 1, variables.length());
		sb.append(") VALUES(" + variables + ")");
		if (showSQL)
			System.out.println("[INFO] - insert: " + sb.toString());

		try {
			ps = connection.prepareStatement(sb.toString());
			int i = 1;
			for (Field f : cls.getDeclaredFields()) {
				if (f.getAnnotation(AutoValue.class) != null
						|| f.getAnnotation(Ignor.class) != null)
					continue;

				f.setAccessible(true);
				ps.setObject(i, f.get(o));
				f.setAccessible(false);
				i++;
			}
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
			}
		}
		return false;
	}

	@Override
	public boolean delete(Object o, int param) {
		Class<?> cls = o.getClass();
		TableMapper tm = tms.get(cls);
		PreparedStatement ps = null;
		final String quer = "DELETE FROM " + tm.getName() + " WHERE ";
		if (tm.getColumns().size() <= param || param < 0)
			throw new IllegalArgumentException(
					"недопустимый параметр в delete: " + param);
		Field f = cls.getDeclaredFields()[param];
		f.setAccessible(true);
		Object value;
		String column = tm.getColumns().get(param);
		try {
			value = f.get(o);
			String query = quer + column + "=?";
			if (showSQL)
				System.out.println("[INFO] - delete:" + query);
			ps = connection.prepareStatement(query);
			ps.setObject(1, value);
			ps.execute();

			return true;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				f.setAccessible(false);
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public Object get(Class clazz, Object id) {
		TableMapper tm = tms.get(clazz);
		PreparedStatement ps = null;
		ResultSet rs =null;
		String query = "SELECT * FROM " + tm.getName() + " WHERE "
				+ tm.getPrimaryKey() + "=?";
		if (showSQL)
			System.out.println("[INFO] - get:" + query);

		Object obj = null;
		try {
			obj = clazz.newInstance();
			ps = connection.prepareStatement(query);
			ps.setObject(1, id);
		    rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				for (Field f : clazz.getDeclaredFields()) {
					f.setAccessible(true);
					f.set(obj, rs.getObject(tm.getColumns().get(i)));
					f.setAccessible(false);
					i++;
				}
			}
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
			} catch (SQLException e) {}
		}
		return null;
	}

	@Override
	public List<?> forList(Class clazz) {
		TableMapper tm = tms.get(clazz);
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<Object> objects=new ArrayList<>();
		String query = "SELECT * FROM " + tm.getName();
		if (showSQL)
			System.out.println("[INFO] - get:" + query);

		Object obj = null;
		try {
			
			ps = connection.prepareStatement(query);

		    rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				obj = clazz.newInstance();
				for (Field f : clazz.getDeclaredFields()) {
					f.setAccessible(true);
					f.set(obj, rs.getObject(tm.getColumns().get(i)));
					f.setAccessible(false);
					i++;
				}
				i=0;
				objects.add(obj);
			}
			return objects;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
			} catch (SQLException e) {}
		}
		return null;
	}

	@Override
	public void beginTransaction() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void commit() {

		try {
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<?> forFilterList(Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean close() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelQuery() {
		// TODO Auto-generated method stub
	}

}
