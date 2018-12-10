package com.amos.entityapi.entity;

import java.util.List;

public interface TableMapper {

	String getName();
	String getPrimaryKey();
	List<ForeignKey> getForeignKey();
	List<String> getColumns();
	List<TableRow> getProperties();
	Class<?> getEntityClass();
	
}
