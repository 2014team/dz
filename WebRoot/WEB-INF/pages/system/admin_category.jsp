<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  	<%@include file="/WEB-INF/pages/common/jsp_jstl.jsp" %>
  </head>
  
   <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">管理员管理</a>
        <a>
          <cite>权限分类</cite></a>
      </span>
      <a class="layui-btn layui-btn-primary layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:38px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row demoTable">
        <!-- <form class="layui-form layui-col-md12 x-so layui-form-pane"> -->
           	分类名：
          <div class="layui-inline">
		    <input class="layui-input" name="categoryName" id=categoryName autocomplete="off">
		  </div>
          <button class="layui-btn" type="button" id="search_id">搜索</button>
      <!--   </form> -->
      </div>
      
   	
   	 <!-- 列表 -->	
     <table class="layui-hide" id="category_list" lay-filter="category_list" ></table>
     
     <!-- 头部工具条 -->
	<script type="text/html" id="toolbar">
  		<div class="layui-btn-container">
   			 <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="crup_delAll('catogoryReload','/admin/center/system/category/delete/batch.do')">批量删除</button>
   			 <button class="layui-btn layui-btn-sm"  onclick="x_admin_show('编辑','/admin/center/system/category/add.do',600,260)"><i class="layui-icon"></i>增加</button>
  		</div>
	</script>
     
     <!--列表行Bar  -->
     <script type="text/html" id="categoryBar">
		 <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
 		 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>

   
  </body>

<script type="text/javascript">
	layui.use('table', function() {
		var table = layui.table;

		  table.render({
			elem : '#category_list',
			url : '/admin/center/system/category/list.do',
			toolbar: '#toolbar',
		    defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
		      title: '提示'
		      ,layEvent: 'LAYTABLE_TIPS'
		      ,icon: 'layui-icon-tips'
		    }],
			page : { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
				layout : [ 'limit', 'count', 'prev', 'page', 'next', 'skip' ], //自定义分页布局 //,curr: 5 //设定初始在第 5 页
				limit : 10,//每页显示的条数
				groups : 5, //步长
				first : '首页', //不显示首页
				last : '尾页', //不显示尾页
				prev :'上一页',
				next:'下一页'

			},
			cols : [ [
				 {checkbox: true, fixed: true},
				{
					field : 'id',
					title : 'ID',
					sort : true
				}
				, {
					field : 'categoryId',
					title : '分类ID',
					sort : true
				}
				, {
					field : 'categoryName',
					title : '分类名'
				}
				, {
					align:'center', toolbar: '#categoryBar',
					title : '操作'
				}

			] ]
			  ,id: 'catogoryReload'
		});
		
		
		/* 搜索 */
		$('#search_id').on('click', function(){
           var categoryName = $('#categoryName').val();
		      //执行重载
		      table.reload( 'catogoryReload',{
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		            categoryName: categoryName
		        }
		      }, 'data');
      
        }); 
		
		//监听行工具条
		table.on('tool(category_list)', function(obj) {
			 var data = obj.data;
			 switch(obj.event){
			  case 'del': //删除
				crup_delete(obj,'/admin/center/system/category/delete.do');
		      break;
		      case 'edit':// 编辑
				x_admin_show('编辑','/admin/center/system/category/edit/'+obj.data.id+'.do',600,260);
		      break;
			 }
		});
	});
	
</script>

</html>