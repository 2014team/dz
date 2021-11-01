<#-- 生成实体类 -->
package ${entityPackageName};
 <#--
<#if table.date_flag>
import java.util.Date;
</#if>
-->
<#if table.math_flag>
import java.math.BigDecimal;
</#if>
import ${entityPackageName}.BaseBean;
 
/**
 * @ClassName: ${table.className?cap_first}
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */ 
public class ${table.className?cap_first} extends BaseBean{

	private static final long serialVersionUID = 1L;
	<#-- 生成字段属性 -->
	<#list table.key_fields + table.common_fields as field>
	<#if (field.java_field_Name != 'id' && field.java_field_Name != 'createDate' && field.java_field_Name != 'updateDate')>
	/**
	 * ${field.field_comment}
	 */
	private ${field.java_type} ${field.java_field_Name};
	</#if>
	</#list>	
 
	<#list table.key_fields + table.common_fields as field>
	<#if (field.java_field_Name != 'id' && field.java_field_Name != 'createDate' && field.java_field_Name != 'updateDate')>
	<#-- 生成字段get方法 -->
	public ${field.java_type} get${field.java_field_Name?cap_first}(){
		return this.${field.java_field_Name};
	}
	
	<#-- 生成字段set方法 -->
	public void set${field.java_field_Name?cap_first}(${field.java_type} ${field.java_field_Name}){
		this.${field.java_field_Name} = ${field.java_field_Name};
	}
	</#if>
	</#list>
}