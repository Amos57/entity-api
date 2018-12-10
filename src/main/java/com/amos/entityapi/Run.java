package com.amos.entityapi;

import java.beans.PropertyVetoException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.amos.entityapi.entity.TableMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Run {

	private LoadXMLConfiguration loadXMLConfiguration;
    private LoaderClasses lc;
    private Dialect dialect;
    private  Map<Class<?>, TableMapper> tables;

	
	public Run() {
		getLoadXMLConfiguration();
		lc = new LoaderClasses(loadXMLConfiguration.getDir());
		dialect=getDialectSQL();


	}
	
	private LoadXMLConfiguration getLoadXMLConfiguration(){
		if(loadXMLConfiguration==null)
		return loadXMLConfiguration = new LoadXMLConfiguration("src/hibernate.cfg.xml");
	return loadXMLConfiguration;
	}
	
	
	private ComboPooledDataSource getConnectionPool(){
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(loadXMLConfiguration.getClassName());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} //loads the jdbc driver
		cpds.setJdbcUrl( loadXMLConfiguration.getUrl() );
		cpds.setUser(loadXMLConfiguration.getUser());
		cpds.setPassword(loadXMLConfiguration.getPassword());
		cpds.setMinPoolSize(loadXMLConfiguration.getPoolSize());
		return cpds;
	}
	

	public Map<Class<?>, TableMapper> getTables(){
		if(tables==null)
			tables=  dialect.getTables();
		return tables;
	}
	
	private Dialect getDialectSQL(){
		if(dialect==null){
			try {
				@SuppressWarnings("unchecked")
				Class<? extends Dialect> cls=(Class<? extends Dialect>) Class.forName(loadXMLConfiguration.getDialect());
				Constructor<? extends Dialect>constructor=(Constructor<? extends Dialect>) cls.getConstructor(Connection.class,String.class,List.class);
				return constructor.newInstance(getConnectionPool().getConnection(),
						loadXMLConfiguration.getCreate_update(),
						lc.getClasses());
			} catch (ClassNotFoundException e) {
				System.err.println("[ERROR] - класс диалекта не найден "+loadXMLConfiguration.getDialect());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return  dialect;
	}
	public Session getSession(){
		return dialect.getSession();
	}
}
