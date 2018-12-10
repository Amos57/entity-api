package com.amos.entityapi.entity;

import java.lang.annotation.Annotation;

import com.amos.entityapi.annotations.Cascade;
import com.amos.entityapi.annotations.FetchType;
import com.amos.entityapi.annotations.ManyToMany;
import com.amos.entityapi.annotations.OneToMany;
import com.amos.entityapi.annotations.OneToOne;

public class ForeignKey {
	
	private String columnFK;
	private String columnId;
	private String table;
	private Cascade caskade;
	private FetchType ft;
	
	public ForeignKey(String columnFK,Annotation annotation) {
		this.columnFK = columnFK;

		if(annotation instanceof OneToMany){
			caskade=((OneToMany) annotation).caskade();
			table=((OneToMany) annotation).table();
			ft = ((OneToMany) annotation).fethe();
			columnId=((OneToMany) annotation).column();
		}else if(annotation instanceof ManyToMany){
			caskade=((ManyToMany) annotation).caskade();
			table=((ManyToMany) annotation).table();
			ft = ((ManyToMany) annotation).fethe();
			columnId=((ManyToMany) annotation).column();
		}else if(annotation instanceof OneToOne){
			caskade=((OneToOne) annotation).caskade();
			table=((OneToOne) annotation).table();
			ft = ((OneToOne) annotation).fethe();
			columnId=((OneToOne) annotation).column();
		}
	}
	public String getColumnFK(){
		return columnFK;
	}
	
	public String getColumnId(){
		return columnId;
	}
	public FetchType getFetchType(){
		return ft;
	}
	public Cascade getCaskade(){
		return caskade;
	}
	public String getTable(){
		return table;
	}
}
