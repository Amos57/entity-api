package com.amos.entityapi.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.amos.entityapi.annotations.Table;

public abstract class ParseClass  implements TableMapper{

	protected String name;
	protected String primaryKey;
	protected List<ForeignKey> foreignKey = new ArrayList<>();

	protected List<String> column = new ArrayList<String>();
	protected List<TableRow> properties = new ArrayList<>();
	protected Class<?> clas;

	public ParseClass(Class<?> cls) {
		clas = cls;
		name = initName(cls);
		getFileds();
	}

	protected abstract void getFileds();

	private String initName(Class<?> cls) {
		Table table = (Table) cls.getAnnotation(Table.class);
		return table != null ? table.name() : cls.getSimpleName().toLowerCase();
	}

	public Class<?> getEntityClass() {
		return clas;
	}

	public String getName() {
		return name;
	}

	public List<String> getColumns() {
		return column;
	}

	public List<TableRow> getProperties() {
		return properties;
	}
	@Override
	public String getPrimaryKey() {
		return primaryKey;
	}

	@Override
	public List<ForeignKey> getForeignKey() {
		return foreignKey;
	}
	
	protected abstract String getTypeSQL(Field field) ;
}
