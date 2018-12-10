package com.amos.entityapi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amos.entityapi.entity.CreateTable;
import com.amos.entityapi.entity.TableMapper;

public abstract class Dialect {



	protected  Map<Class<?>, TableMapper> tables= new HashMap<Class<?>, TableMapper>();
	protected CreateTable createTable;
	protected boolean showSQL;
	protected Dialect(Connection connection, String udaDel,List<Class<?>> classes,boolean showsql) {
		this.showSQL=showsql;
	}

	protected void init(String val,TableMapper tableMapper) {
		switch (val) {
		case "create":
			create(tableMapper);
			break;
		case "update":
			upadte(tableMapper);
			break;
		default:
			delete(tableMapper);
			break;
		}
	}

	private boolean create(TableMapper tableMapper){
		try {
			return createTable.create(tableMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean upadte(TableMapper tableMapper){
		try {
			return createTable.update(tableMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean delete(TableMapper tableMapper){
		try {
			return createTable.delete(tableMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	protected abstract Session getSession();

	public Map<Class<?>, TableMapper> getTables() {
		return tables;
	}
}
