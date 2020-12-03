<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackageName}.${table.className?cap_first}Dao">
	
	<!-- resultMap -->
	<resultMap type="${entityPackageName}.${table.className?cap_first}" id="resultMap">
	<#list (table.key_fields + table.common_fields) as field>
		<result property="${field.java_field_Name}" column="${field.field_name}" />
	</#list>
	</resultMap>

	<!-- 保存 -->
	<insert id="save" parameterType="${entityPackageName}.${table.className?cap_first}" useGeneratedKeys="true" keyProperty="${table.key_fields[0].java_field_Name}">
		insert into ${table.tableName} (
		<#list (table.common_fields) as field>
		<#if field_index == 0>
			${field.field_name}
		<#else>
			,${field.field_name}
		</#if>
		</#list>
		)
		 values
		 (
			<#list (table.common_fields) as field>
			<#if field_index ==0>
			${"#"}{${field.java_field_Name}}
			<#else>
			<#if (field.java_field_Name != 'Date' || field.java_field_Name != 'Date')>
				,${"#"}{${field.java_field_Name}}
			</#if>
			</#if>
			</#list>
		)
	</insert>
	
	<!-- 删除-->
	<delete id="delete" parameterType="Integer">
		delete from ${table.tableName} where ${table.key_fields[0].field_name} = ${"#"}{${table.key_fields[0].java_field_Name}};
	</delete>
	
	<!-- 批量删除 -->
	<delete id="deleteByBatch"  parameterType="java.util.List">
		delete from
		${table.tableName}
		where ${table.key_fields[0].field_name} in
		<foreach collection="list" item="item" open="(" close=")" separator=",">
			${"#"}{item}
		</foreach>
	</delete>
	
	<!-- 修改-->
	<#if table.key_fields?size != 0>
	<#-- 主键只有一个时 -->	
	<#if table.key_fields?size == 1>
	<update id="update" parameterType="${entityPackageName}.${table.className?cap_first}" >
		update ${table.tableName} 
			set update_Date = now()
		<#list table.common_fields as field>
			<#if (field.field_name != 'createDate' && field.field_name != 'updateDate')>
				,${field.field_name} = ${"#"}{${field.java_field_Name}}
			</#if>
		</#list>
		where ${table.key_fields[0].field_name} = ${"#"}{${table.key_fields[0].java_field_Name}}
	</update>
	</#if>
	</#if>
	
	<!-- 根据id获取对象-->
 	<select id="get" parameterType="Integer" resultMap="resultMap">
 		select 
		<#list (table.key_fields + table.common_fields) as field>
 			<#if (field_index  == 0 )>
				${field.field_name}
				<#else>
				,${field.field_name}
			</#if>
		</#list>
		 from ${table.tableName} where ${table.key_fields[0].field_name} = ${"#"}{${table.key_fields[0].java_field_Name}} 
		 order by sort_id ,create_date desc limit 1;
 	</select>
 	
 	<!-- 通过Map单个查找-->
	<select id="getOneByMap" parameterType="Map" resultMap="resultMap">
		select 
			<#list (table.key_fields + table.common_fields) as field>
 			<#if (field_index  == 0 )>
				${field.field_name}
				<#else>
				,${field.field_name}
			</#if>
		</#list>
		 from
		${table.tableName}
		<where>
			<#list table.common_fields as field>
 			<#if (field_index  == 0 )>
 			<if test="null != ${field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${field.java_field_Name}</#if>">
			${field.field_name} = ${"#"}{${field.java_field_Name}}
			</if>
			<#else>
			<if test="null != ${field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${field.java_field_Name}</#if>">
			and ${field.field_name} = ${"#"}{${field.java_field_Name}}
			</if> 
			</#if>
 		</#list>
		</where>
		order by sort_id ,create_date desc limit 1;
	</select>
	
	<!-- 列表查询 -->
	<select id="select" parameterType="Map" resultMap="resultMap">
 		select 
 		<#list (table.key_fields + table.common_fields) as field>
 			<#if (field_index  == 0 )>
				${field.field_name}
				<#else>
				,${field.field_name}
			</#if>
		</#list>
 		 from ${table.tableName}
 		<where>
 		<#list table.common_fields as field>
 			<#if (field_index  == 0 )>
 			<if test="null != ${field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${field.java_field_Name}</#if>">
			${field.field_name} = ${"#"}{${field.java_field_Name}}
			</if>
			<#else>
			<if test="null != ${field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${field.java_field_Name}</#if>">
			and ${field.field_name} = ${"#"}{${field.java_field_Name}}
			</if> 
			</#if>
 		</#list>
 		</where>
 		order by sort_id ,create_date desc;
 	</select>
 	
 	<!-- 分页查找 -->
	<select id="findByPage" parameterType="Map" resultMap="resultMap">
		select 
 		<#list (table.key_fields + table.common_fields) as field>
 			<#if (field_index  == 0 )>
				${field.field_name}
				<#else>
				,${field.field_name}
			</#if>
		</#list>
 		 from ${table.tableName}
 		<where>
 		<#list table.key_fields + table.common_fields as field>
 			<#if (field_index  == 0 )>
 			<if test="null != ${table.className?uncap_first +"Vo."+ field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${table.className?uncap_first +"Vo."+ field.java_field_Name}</#if>">
			${field.field_name} = ${"#"}{${table.className?uncap_first +"Vo."+ field.java_field_Name}} 
			</if>	
			<#else>
			<if test="null != ${table.className?uncap_first +"Vo."+ field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${table.className?uncap_first +"Vo."+ field.java_field_Name}</#if>">
			and ${field.field_name} = ${"#"}{${table.className?uncap_first +"Vo."+ field.java_field_Name}}
			</if> 
			</#if>
 		</#list>
 		</where>
 		order by sort_id ,create_date desc   
 		<if test="null != page and '' != page and null != page.begin and null !=page.limit ">
 		 limit ${"$"}{page.begin} , ${"$"}{page.limit};
 		 </if>
	</select>
	
	<!-- 分页查找数量 -->
	<select id="findByPageCount" parameterType="Map" resultType="Integer">
		select count(1)  from ${table.tableName}
 		<where>
 		<#list table.key_fields + table.common_fields as field>
 			<#if (field_index  == 0 )>
 			<if test="null != ${table.className?uncap_first +"Vo."+ field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${table.className?uncap_first +"Vo."+ field.java_field_Name}</#if>">
			${field.field_name} = ${"#"}{${table.className?uncap_first +"Vo."+ field.java_field_Name}} 
			</if>	
			<#else>
			<if test="null != ${table.className?uncap_first +"Vo."+ field.java_field_Name} <#if field.java_type != 'Integer'>and ''!= ${table.className?uncap_first +"Vo."+ field.java_field_Name}</#if>">
			and ${field.field_name} = ${"#"}{${table.className?uncap_first +"Vo."+ field.java_field_Name}}
			</if> 
			</#if>
 		</#list>
 		</where>
 		order by sort_id ,create_date desc ;
	</select>

</mapper>