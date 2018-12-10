package com.amos.entityapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.amos.entityapi.entity.SQLDataType;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	
	String name() default "";
	SQLDataType type() default SQLDataType.NULL;
	int length() default 0;
	boolean unique() default false;
	boolean notnull() default false;
	String defValue() default "";
	
	
	
	String query() default "";
		
}
