<#-- 生成实体类 -->
package ${voPackageName};
 <#--
<#if table.date_flag>
import java.util.Date;
</#if>
-->
<#if table.math_flag>
import java.math.BigDecimal;
</#if>
import ${entityPackageName}.${table.className?cap_first};
 
/**
 * @ClassName: ${table.className?cap_first}Vo
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */ 
public class ${table.className?cap_first}Vo extends ${table.className?cap_first}{
	
	private static final long serialVersionUID = 1L;
	
}