package com.amos.entityapi.dialect.MySQL;

import java.lang.reflect.Field;

import com.amos.entityapi.annotations.AutoValue;
import com.amos.entityapi.annotations.Column;
import com.amos.entityapi.annotations.Id;
import com.amos.entityapi.annotations.Ignor;
import com.amos.entityapi.annotations.ManyToMany;
import com.amos.entityapi.annotations.OneToMany;
import com.amos.entityapi.annotations.OneToOne;
import com.amos.entityapi.entity.ForeignKey;
import com.amos.entityapi.entity.ParseClass;
import com.amos.entityapi.entity.Property;
import com.amos.entityapi.entity.SQLDataType;

public class ParseClassMySQL extends ParseClass {

	public ParseClassMySQL(Class<?> cls) {
		super(cls);
	}

	protected void getFileds() {

		Field[] fields = clas.getDeclaredFields();
		
		for (Field f : fields) {
			String name=f.getName();
			String ai = "";
			Ignor ignor = f.getAnnotation(Ignor.class);
			if (ignor != null)
				continue;

			AutoValue au = f.getAnnotation(AutoValue.class);
			if (au != null){
				ai = "AUTO_INCREMENT";
				autoValue.add(name);
			}
			Column column = f.getAnnotation(Column.class);
			if (column == null) {
							
				this.column.add(f.getName());
				this.properties.add(new Property(f.getName() + " "
						+ getTypeSQL(f) + " " + ai));

			}else{
				if (!column.query().equals("")) {
				properties.add(new Property(column.query()));
				this.column.add(column.query().substring(0,
						column.query().indexOf(" ")));
				continue;
			}	
			 name = column.name().equals("") ? f.getName() : column
					.name();
			String type = column.type() != SQLDataType.NULL ? column.type()
					.name() : getTypeSQL(f);
			String len = column.length() != 0 ? "(" + column.length() + ")"
					: "";
			String unique = column.unique() ? "UNIQUE" : "";
			String notnull = column.notnull() ? "NOT NULL" : "";
	/*		String defValue = !column.defValue().equals("") ? column.defValue()
					: "";
			if (f.getType().getSimpleName().equals("String")
					&& !defValue.equals(""))
				defValue = "\'" + defValue + "\'";*/
			this.column.add(name);
			properties.add(new Property(name, SQLDataType.valueOf(type), len,
					ai, unique, notnull));//defValue
			}
			
			
			if (primaryKey == null)
				primaryKey = f.getAnnotation(Id.class) == null ? null : name;
			ManyToMany mtm = f.getAnnotation(ManyToMany.class);
			OneToOne oto = f.getAnnotation(OneToOne.class);
			OneToMany otm = f.getAnnotation(OneToMany.class);
			if (mtm != null)
				foreignKey.add(new ForeignKey(name, mtm));
			else if (oto != null)
				foreignKey.add(new ForeignKey(name, oto));
			else if (otm != null)
				foreignKey.add(new ForeignKey(name, otm));
		}

	}

	protected String getTypeSQL(Field field) {
		String fn = field.getType().getSimpleName();
		Column column = field.getAnnotation(Column.class);
		String len = (column != null && column.length() != 0) ? "("
				+ column.length() + ")" : "(28)";
		switch (fn) {
		case "String":
			return SQLDataType.VARCHAR.name() + len;
		case "Date":
			return SQLDataType.DATE.name();
		case "Time":
			return SQLDataType.TIME.name();
		case "Timestamp":
			return SQLDataType.TIMESTAMP.name();
		case "Integer":
			return SQLDataType.INTEGER.name();
		case "int":
			return SQLDataType.INTEGER.name();
		case "BibDecimal":
			return SQLDataType.DECIMAL.name();
		case "Long":
			return SQLDataType.BIGINT.name();
		case "long":
			return SQLDataType.BIGINT.name();
		case "Boolean":
			return SQLDataType.BIT.name();
		case "boolean":
			return SQLDataType.BIT.name();
		case "float":
			return SQLDataType.REAL.name();
		case "Float":
			return SQLDataType.REAL.name();
		case "double":
			return SQLDataType.DOUBLE.name();
		case "Double":
			return SQLDataType.DOUBLE.name();
		}
		return fn;
	}

}
