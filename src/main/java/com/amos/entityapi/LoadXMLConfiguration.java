package com.amos.entityapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

 public final class LoadXMLConfiguration {
	 
	private String className;
	 private String password;
	 private String user;
	 private String url;
	 private int poolSize;
	 private String showSQL;
	 private String create_update;
	 private final String path;
	 private String dialect;
	 
	 private static final String DRIVER_CLASS = "driver_class";
	 private static final String CONNECTION_URL = "connection.url";
	 private static final String USERNAME = "connection.username";
	 
	 private static final String PASSWORD = "connection.password";
	 private static final String POOL_SIZE = "connection.pool_size";
	 private static final String SHOW_SQL = "show_sql";
	 private static final String HBM2DDL = "hbm2ddl.auto";
	 private static final String DIALECT = "dialect";
	 private static final String MAPPING = "package";

	 private File classes;
	 
	 LoadXMLConfiguration( String path) {
		this.path=path;
		parse();
	}
	 
	 private void parse(){
		 try(BufferedReader br= new BufferedReader(
				 new InputStreamReader(new FileInputStream(new File(path))))){
			 
			 String result;
			 while((result=br.readLine())!=null){
                  value(result);
			 }
			 
		 } catch (Exception e) {
             System.err.println("не корректно составлен файл конфигурации");
			e.printStackTrace();
		}
	 }
	 
	 
	 private void value(String row){
		 if(row.contains("<property"))
		 if(row.contains(DRIVER_CLASS)){
			 className=row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));
		 }else if(row.contains(CONNECTION_URL)){
			 url = row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));
		 }else if(row.contains(USERNAME)){
			  user = row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));
		 }else if(row.contains(PASSWORD)){
			 password = row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));
		 }else if(row.contains(POOL_SIZE)){
			  poolSize = Integer.parseInt(row.substring(row.indexOf(">")+1,row.lastIndexOf("<")));
		 }else if(row.contains(SHOW_SQL)){
			  showSQL=row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));
			  System.setProperty("show_sql", showSQL);
		 }else if(row.contains(HBM2DDL)){
			  create_update=row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));

		 }else if(row.contains(DIALECT)){
			  dialect=row.substring(row.indexOf(">")+1,row.lastIndexOf("<"));
		 }
		 if(row.contains(MAPPING)){
			String  path=row.substring(row.indexOf("\"")+1,row.lastIndexOf("\""));
			  classes=new File("src"+"/"+path.replace(".","/"));
		 }
	 }
	 
	 public String getClassName() {
		return className;
	}

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}

	public String getUrl() {
		return url;
	}

	public int getPoolSize() {
		return poolSize;
	}


    public File getDir(){
    	return classes;
    }

	public String getShowSQL() {
		return showSQL;
	}

	public String getCreate_update() {
		return create_update;
	}


	 public String getDialect(){
		 return dialect;
	 }
}
