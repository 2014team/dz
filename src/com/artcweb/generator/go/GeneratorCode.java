package com.artcweb.generator.go;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.artcweb.generator.MyTable;
import com.artcweb.generator.db.DataBaseUtil;
import com.artcweb.generator.db.DbConnection;
import com.artcweb.generator.factory.DbConnectionFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 代码生成器
 *
 */
public abstract class GeneratorCode {
	
	
	/**
	 * 获取模版路径
	 * @return
	 */
	public abstract String getTemplatePath();
	/**
	 * 获取要生成的表对象
	 * @return
	 */
	public MyTable getTable(String tableName){
		DbConnection connection = DbConnectionFactory.getDbConnection();
		if(null != connection){
			return connection.getTable(tableName);
		}
		return null;
	}
	
	
	/**
	 * 开始生成代码
	 * @param paramMap
	 * @throws IOException
	 */
	public void createCode(Map<String , Object> paramMap) throws IOException{
		String commonPackage = (String)paramMap.get("commonPackage");
		String tableName = (String)paramMap.get("tableName");
		String basePackageName = (String)paramMap.get("basePackageName");
		
		String className = DataBaseUtil.getClassName(tableName);
		
		//先创建目标文件夹
		String projectPath = System.getProperty("user.dir");
		String src_path = projectPath + File.separator + "src";
		File file = new File(src_path);
		if(basePackageName.indexOf(".") != -1){
			String[] packageName_part = basePackageName.split("\\.");	
			for(String part : packageName_part){
				file = new File(file,part);
				if(!file.exists())
					file.mkdirs();
			}
		}else{
			file = new File(file , basePackageName);
			if(!file.exists())
				file.mkdirs();
		}
		
		//获取模版路径
		String template_path = projectPath +File.separator + "resource"+ File.separator + "config" + File.separator + "template"+ File.separator + getTemplatePath() ;
		File templateDirtory = new File(template_path);
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(templateDirtory);
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		
		File[] template_files = templateDirtory.listFiles();
		for(File template_file : template_files){
			//得到模版文件名xxx.ftl
			String file_name = template_file.getName();
			int index = file_name.indexOf(".");
			String template_name = file_name.substring(0, index);
			if(null == template_name || "".equals(template_name)){
				//读到不相干文件了
				continue;
			}
			File target_file_dir = null , target_file = null;
			String target_file_name = null;
			//如果是po.ftl
			if(template_name.equalsIgnoreCase("entity")){
				target_file_dir = new File(file , "bean");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + ".java";
			}else if(template_name.equalsIgnoreCase("dao")){
				//如果是dao.ftl,则生成mapper接口
				target_file_dir = new File(file , "dao");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "Dao.java";				
			} else if(template_name.equalsIgnoreCase("mapper")){
				//如果是mybatis.ftl，则生成mybatis核心配置文件sqlMapConfig.xml
				File config_source_folder = new File(projectPath);
				config_source_folder = new File(config_source_folder , "resource");
				if(!config_source_folder.exists())
					config_source_folder.mkdirs();
				target_file_dir = new File(config_source_folder , "mapper");
				if(!target_file_dir.exists())
				target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "Mapper.xml";
				
				
			} else if(template_name.equalsIgnoreCase("list")){
				//如果是mybatis.ftl，则生成mybatis核心配置文件sqlMapConfig.xml
				File config_source_folder = new File(projectPath);
				config_source_folder = new File(config_source_folder , "/WebRoot/WEB-INF/pages");
				if(!config_source_folder.exists())
					config_source_folder.mkdirs();
				target_file_dir = new File(config_source_folder , DataBaseUtil.lowerFirstCapse(className));
				if(!target_file_dir.exists())
				target_file_dir.mkdirs();
				target_file_name = template_name + ".jsp";
				
				
			}else if(template_name.equalsIgnoreCase("edit")){
				//如果是mybatis.ftl，则生成mybatis核心配置文件sqlMapConfig.xml
				File config_source_folder = new File(projectPath);
				config_source_folder = new File(config_source_folder , "/WebRoot/WEB-INF/pages");
				if(!config_source_folder.exists())
					config_source_folder.mkdirs();
				target_file_dir = new File(config_source_folder , DataBaseUtil.lowerFirstCapse(className));
				if(!target_file_dir.exists())
				target_file_dir.mkdirs();
				target_file_name = template_name + ".jsp";
				
				
			}else if(template_name.equalsIgnoreCase("service")){
				//如果是service.ftl，则生成service接口
				target_file_dir = new File(file , "service");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "Service.java";
			}else if(template_name.equalsIgnoreCase("serviceImpl")){
				//如果是serviceImpl.ftl，则生成service接口实现类
				target_file_dir = new File(file , "service/impl");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "ServiceImpl.java";
			}else if(template_name.equalsIgnoreCase("controller")){
				//如果是controller.ftl，则生成controller
				target_file_dir = new File(file , "controller");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "Controller.java";
			}else if(template_name.equalsIgnoreCase("vo")){
				//如果是controller.ftl，则生成controller
				target_file_dir = new File(file , "vo");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "Vo.java";
			}else if(template_name.equalsIgnoreCase("dto")){
				//如果是controller.ftl，则生成controller
				target_file_dir = new File(file , "dto");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = DataBaseUtil.capitalFirstChar(className) + "Dto.java";
			} else if(template_name.equalsIgnoreCase("mybatis")){
				//如果是mybatis.ftl，则生成mybatis核心配置文件sqlMapConfig.xml
				File config_source_folder = new File(projectPath);
				config_source_folder = new File(config_source_folder , "config");
				if(!config_source_folder.exists())
					config_source_folder.mkdirs();
				target_file_dir = new File(config_source_folder , "mybatis");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name =  "sqlMapConfig.xml";
			} else if(template_name.equalsIgnoreCase("spring")){
				//如果是spring.ftl，则生成spring核心配置文件spring.xml
				File config_source_folder = new File(projectPath);
				config_source_folder = new File(config_source_folder , "config");
				if(!config_source_folder.exists())
					config_source_folder.mkdirs();
				target_file_dir = new File(config_source_folder , "spring");
				if(!target_file_dir.exists())
					target_file_dir.mkdirs();
				target_file_name = "spring.xml";
			}
			target_file = new File(target_file_dir , target_file_name);
			if(target_file.exists())
				target_file.delete();
			target_file.createNewFile();
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target_file)));
			
			Template template = configuration.getTemplate(file_name);
			
			//获取表对象
			MyTable table = getTable(tableName);
			table.setPackage_name_base(basePackageName);
			
			//设置包名
			paramMap.put("basePackageName", basePackageName);//baseController,baseService等包名路径
			paramMap.put("controllerPackageName", basePackageName+".controller");
			paramMap.put("daoPackageName", basePackageName+".dao");
			paramMap.put("basePackageName", basePackageName+".bean");
			paramMap.put("mapperPackageName", basePackageName+".mapper");
			paramMap.put("servicePackageName", basePackageName+".service");
			paramMap.put("serviceImplPackageName", basePackageName+".service.impl");
			paramMap.put("entityPackageName", basePackageName+".bean");
			paramMap.put("voPackageName", basePackageName+".vo");
			paramMap.put("dtoPackageName", basePackageName+".dto");

			
			paramMap.put("daoCommonPackageName", commonPackage+".dao");
			paramMap.put("serviceCommonPackageName", commonPackage+".service");
			paramMap.put("serviceImplCommonPackageName", commonPackage+".service.impl");
			paramMap.put("entityCommonPackage", commonPackage+".entity");
			paramMap.put("commonPackage", commonPackage);
			
			
			
			
			paramMap.put("dateTime", getCurrentDateFormat("yyyy年MM月dd日 HH:mm:ss"));
			
			paramMap.put("table", table);
			try {
				//freemaker渲染并生成文件
				template.process(paramMap, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			} finally{
				if(writer != null)
					writer.close();
			}
		}	
	}
	
	public static String getCurrentDateFormat(String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(new Date());
	}
}
