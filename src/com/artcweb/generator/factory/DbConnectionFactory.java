package com.artcweb.generator.factory;

import com.artcweb.generator.db.DataBaseUtil;
import com.artcweb.generator.db.DbConnection;
import com.artcweb.generator.db.impl.MySqlConnectionImpl;
import com.artcweb.generator.db.impl.SqlserverConnectionImpl;

public class DbConnectionFactory {
	
	public static DbConnection getDbConnection(){
		String dbType = DataBaseUtil.getDbType();
		if(null != dbType){
			if(dbType.equals("mysql")){
				return new MySqlConnectionImpl();
				
			}else if(dbType.equals("sqlserver")){
				return new SqlserverConnectionImpl();
				
			}
		}
		return null;
	}
}
