package com.artcweb.generator.factory;

import com.artcweb.generator.db.DataBaseUtil;
import com.artcweb.generator.go.GeneratorCode;
import com.artcweb.generator.go.impl.MysqlGeneratorImpl;
import com.artcweb.generator.go.impl.SqlserverGeneratorImpl;

public class GeneratorFactory {
	
	public static GeneratorCode getGenerator(){
		
		String dbType = DataBaseUtil.getDbType();
		if(null != dbType){
			if(dbType.equals("mysql")){
				return new MysqlGeneratorImpl();
				
			}else if(dbType.equals("sqlserver")){
				return new SqlserverGeneratorImpl();
				
			}
		}
		return null;
	}

}
