package com.amos.entityapi;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class LoaderClasses {

	private File[] classes;
	private List<Class<?>> classe = new ArrayList<Class<?>>();
	LoaderClasses(File file){
		classes=file.listFiles(new Filter());
		load();
	}
	
	public void load(){
		for(File f : classes){
			String path=f.getPath().substring(4,f.getPath().indexOf(".java"));
			path=path.replace("\\", ".");
			try {
				Class<?> clas=Class.forName(path);
				classe.add(clas);
			} catch (ClassNotFoundException e) {
			
				e.printStackTrace();
			}
		
		
		}
	}
	
	public List<Class<?>> getClasses(){
		return classe;
	}
	class Filter implements FileFilter{

		@Override
		public boolean accept(File pathname) {
		
			return pathname.getName().endsWith(".java");
		}
		
		
		
	}
}
