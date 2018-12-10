package com.amos.entityapi.entity;

public class Property implements TableRow {

	private String name;
	private SQLDataType sqlDataType;
	private String length;
	private String ai;
	private String unique;
	private String notnull;
	private String defValue;

	private String query;

	public Property(String query) {
		this.query = query;
	}

	public Property(String name, SQLDataType sqlDataType, String length,
			String ai, String unique, String notnull, String defValue) {
		this.name = name;
		this.sqlDataType = sqlDataType;
		this.length = length;
		this.ai = ai;
		this.unique = unique;
		this.notnull = notnull;
		this.defValue = defValue;
	}

	public String getName() {
		return name;
	}

	public SQLDataType getSqlDataType() {
		return sqlDataType;
	}

	public String getUnique() {
		return unique;
	}

	public String getLenght() {
		return length;
	}

	public String getNotnull() {
		return notnull;
	}

	public String getDefValue() {
		return defValue;
	}

	public String getAutoValue() {
		return ai;
	}

	@Override
	public String toString() {
		return query != null ? query : name + " " + sqlDataType.name()
				+ length + " " + unique + " " + notnull + " " + (!defValue.equals("") ? "DEFAULT "+defValue : "");
	}

	@Override
	public String getQuery() {
		return query;
	}
}
