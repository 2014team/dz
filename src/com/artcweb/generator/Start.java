package com.artcweb.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.artcweb.generator.db.DataBaseUtil;
import com.artcweb.generator.factory.GeneratorFactory;
import com.artcweb.generator.go.GeneratorCode;

public class Start {
	
	public static void main(String[] args) {
		try {
			System.out.println("开始生成代码...");
			long start = System.currentTimeMillis();
			Map<String , Object> paramMap = new HashMap<String , Object>();
			paramMap.put("author", "zhuzq");//作者
			
			
			//mysql
			DataBaseUtil.jdbc_driver = "com.mysql.jdbc.Driver";
			DataBaseUtil.jdbc_url = "jdbc:mysql://127.0.0.1:3306/artcweb_admin_dev?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
			DataBaseUtil.jdbc_user = "**";
			DataBaseUtil.jdbc_password = "**";
			
			
			//sqlserver
//			DataBaseUtil.jdbc_driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//			DataBaseUtil.jdbc_url = "jdbc:sqlserver://188.188.5.27:1433;DatabaseName=doctorjob";
//			DataBaseUtil.jdbc_user = "sa";
//			DataBaseUtil.jdbc_password = "job36";
//			paramMap.put("basePackageName","com.job36.mobile");//baseController,baseService等包名路径
//			
			DataBaseUtil.init();//初始化数据库连接
			
			//开始生成代码
			GeneratorCode generator = GeneratorFactory.getGenerator();
			
			//实体基础包名
			//如com.job528.personal.domain.MyUsers 的基础包名为com.job528.personal
			paramMap.put("basePackageName","com.artcweb");
			//dao、service、实体类继承父类包
			paramMap.put("commonPackage","com.artcweb");
			paramMap.put("description","图纸库存记录");//表名

			paramMap.put("tableName","t_nail_drawing_stock_history");//表名
			generator.createCode(paramMap);//第一个表
			
			//第二个表
//			paramMap.put("entityPackageName","com.job528.teaching.teacher.teachAuth");
//			paramMap.put("tableName","t_teach_auth");//表名
//			generator.createCode(paramMap);//第二个表
//			
//			paramMap.put("entityPackageName","com.job528.teaching.teacher.teacher");
//			paramMap.put("tableName","t_teacher");//表名
//			generator.createCode(paramMap);//第二个表
//			
//			paramMap.put("entityPackageName","com.job528.teaching.teacher.teachExp");
//			paramMap.put("tableName","t_teach_exp");//表名
//			generator.createCode(paramMap);//第二个表
//			
//			paramMap.put("entityPackageName","com.job528.teaching.teacher.teachPack");
//			paramMap.put("tableName","t_teach_pack");//表名
//			generator.createCode(paramMap);//第二个表
//			
//			paramMap.put("entityPackageName","com.job528.teaching.teacher.teachOrder");
//			paramMap.put("tableName","t_teach_order");//表名
//			generator.createCode(paramMap);//第二个表
			
			long end = System.currentTimeMillis();
			System.out.println("代码生成成功!，耗时"+(end-start)/1000+"秒");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
