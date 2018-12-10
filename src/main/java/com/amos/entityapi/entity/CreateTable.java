package com.amos.entityapi.entity;

import java.sql.SQLException;



public interface CreateTable {

      final String CR_TABLE="CREATE TABLE";
      final String IF_N_EX="IF NOT EXISTS";
	  final String PR_KEY="PRIMARY KEY";
	  final String REFERENCE="REFERENCES";
	  final String FOR_KEY="FOREIGN KEY";
	
	  final String SHOW_TABL="SHOW TABLES";
	boolean create(TableMapper tableMapper)  throws SQLException ;
	boolean update(TableMapper tableMapper)  throws SQLException ;
	boolean delete(TableMapper tableMapper)  throws SQLException ;
	
	
	class TableInfoForUpdate{
		
		public String name;
		public String type;
		public String key;
	}
}
