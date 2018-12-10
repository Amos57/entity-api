package com.amos.entityapi.entity;

public interface TableRow {

	String getName();
	SQLDataType getSqlDataType();
	String getLenght();
	String getAutoValue();
	String getUnique();
	String getNotnull();
	//String getDefValue();
	
	String getQuery();
}
