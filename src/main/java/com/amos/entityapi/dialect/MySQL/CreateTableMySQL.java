package com.amos.entityapi.dialect.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amos.entityapi.entity.CreateTable;
import com.amos.entityapi.entity.ForeignKey;
import com.amos.entityapi.entity.TableMapper;
import com.amos.entityapi.entity.TableRow;

public class CreateTableMySQL implements CreateTable {

	private static final String CASKADE_DEL = "ON DELETE CASCADE";
	private static final String CASKADE_UPDATE = "ON UPDATE CASCADE";
	private static final String SHOW_COLUMNS = "SHOW COLUMNS FROM ";


	private Connection connection;

	public CreateTableMySQL( Connection connection) {
		this.connection = connection;

	}

	@Override
	public boolean create(TableMapper tableMapper) {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps;
		sb.append(CR_TABLE + " " + IF_N_EX + " " + tableMapper.getName() + "(");
		for (TableRow s : tableMapper.getProperties()) {
			sb.append(s + ",");
		}
		if (tableMapper.getPrimaryKey() != null) {
			sb.append(PR_KEY + "(" + tableMapper.getPrimaryKey() + ")" + ",");
		}
		if (tableMapper.getForeignKey().size() != 0) {
			for (ForeignKey fk : tableMapper.getForeignKey()) {
				sb.append(FOR_KEY + "(" + fk.getColumnFK() + ") " + REFERENCE+" "
						+ fk.getTable() + "(" + fk.getColumnId() + ")");
				switch (fk.getCaskade()) {
				case ALL:
					sb.append(" "+CASKADE_DEL);
					sb.append(" "+CASKADE_UPDATE + ",");
					break;
				case DELETE:
					sb.append(CASKADE_DEL + ",");
					break;
				case UPDATE:
					sb.append(CASKADE_UPDATE + ",");
					break;
				default:
					sb.append(",");
					break;
				}
			}
		}
        sb.delete(sb.length()-1, sb.length());
        sb.append(")");
        if(Boolean.parseBoolean(System.getProperty("show_sql", "false")))
        System.out.println("[INFO] - create: "+sb.toString());
        try {
			ps=connection.prepareStatement(sb.toString());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        
		return true;
	}

	@Override
	public boolean update(TableMapper tableMapper) {
		ResultSet rs = null;
		Statement ps=null;
		List<TableInfoForUpdate> tifus=new ArrayList<TableInfoForUpdate>();
		try {
			 create(tableMapper);//if not exists
			 
			 ps=connection.createStatement();
		
			 rs=ps.executeQuery(SHOW_COLUMNS+tableMapper.getName());

			while(rs.next()){
				TableInfoForUpdate tifu=new TableInfoForUpdate();
				tifu.name=rs.getString("Field");
				tifu.type=rs.getString("Type");
				tifu.key=rs.getString("KEY");
			     tifus.add(tifu);
			}
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {}
		}
		
	}

	@Override
	public boolean delete(TableMapper tableMapper) {
		PreparedStatement ps=null;
		
		try {
			 ps= connection.prepareStatement(SHOW_TABL);
			return true;
		} catch (SQLException e) {
          e.printStackTrace();
			return false;
		}finally{
			
		}
		
	}

}
