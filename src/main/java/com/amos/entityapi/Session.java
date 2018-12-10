package com.amos.entityapi;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface Session extends Serializable{

	boolean update(Object o,Object id);
	boolean save(Object o);
	boolean delete(Object o);
	Object get(Class<?> clazz,Object id);
	List<?> forList();
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
