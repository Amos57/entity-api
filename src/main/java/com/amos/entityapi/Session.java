package com.amos.entityapi;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface Session extends Serializable{

	boolean update(Object o,Object...id);
	boolean save(Object o);
	
	/**
	 * 
	 * 
	 * 
	 * @param o - object
	 * @param param number field from object, top to down, begin from zero
	 * @return
	 */
	boolean delete(Object o,int param);
	Object get(Class clazz,Object id);
	List<?> forList(Class clazz);
	void beginTransaction();
	void commit();
	List<?> forFilterList(Class<?> clazz);
	
	boolean close() throws SQLException;
	
	/**
	 * 
	 * 
	 */
	 void	cancelQuery() ;
	
	
}
