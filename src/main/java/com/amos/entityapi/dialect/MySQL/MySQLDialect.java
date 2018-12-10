package com.amos.entityapi.dialect.MySQL;

import java.sql.Connection;
import java.util.List;

import com.amos.entityapi.Dialect;
import com.amos.entityapi.Session;

public class MySQLDialect extends Dialect{

	
	public MySQLDialect(Connection connection, String udaDel, List<Class<?>> classes,boolean showsql) {
		super(connection, udaDel, classes,showsql);
		createTable= new CreateTableMySQL(connection,showSQL);
		for(Class<?> class1 : classes){
			tables.put(class1, new ParseClassMySQL(class1));
			init(udaDel, tables.get(class1));
		}
	}


	@Override
	protected Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
