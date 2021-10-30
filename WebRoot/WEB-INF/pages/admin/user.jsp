<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">管理员管理</a>
        <a>
          <cite>列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-primary layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:38px">&#xe669;</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row demoTable">
           	用户名：
          <div class="layui-inline">
		    <input class="layui-input" name="userName" id="userName" autocomplete="off">
		  </div>
          <button class="layui-btn" type="button" id="search_id">搜索</button>
          <button class="layui-btn" type="button" id="clean_search_input">置空搜索框</button>
      <!--   </form> -->
      </div>
      
   	
   	 <!-- 列表 -->	
     <table class="layui-hide" id="table_list" lay-filter="table_list" ></table>
     
     <!-- 头部工具条 -->
	 
	<script type="text/html" id="toolbar">
  		<div class="layui-btn-container">
   			 <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="crup_delAll('tableReload','/admin/center/account/delete/batch.do')">批量删除</button>
   			 <button class="layui-btn layui-btn-sm"  onclick="x_admin_show('编辑','/admin/center/account/add.do',600,400)"><i class="layui-icon"></i>增加</button>
  		</div>
	</script>
    
     <!--列表行Bar  -->
     
     <script type="text/html" id="rowBar">
		 <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
 		 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a> 
	</script>
  
  </body>




<script type="text/javascript">

var editRowObj;
	layui.use('table', function() {
		var table = layui.table;

		  table.render({
			elem : '#table_list',
			url : '/admin/center/account/list.do',
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
					field : 'indexId', 
					title : '序号',
					type: 'numbers',
					sort : true,
					width:80,
				}
				, {
					field : 'userName',
					title : '用户名',
					
				}
				, {
					field : 'password',
					title : '密码'
				}
				, {
					field : 'vaild',
					title : '状态',
					templet : function(d) {
					  if(d.vaild == 0){
					  	return "有效"
					  }else if(d.vaild == 1){
					  	return "禁用"
					  }else{return""}
					}
				}
				 , {
					align:'center', toolbar: '#rowBar',
					title : '操作'
				}  

			] ]
			  ,id: 'tableReload'
		});
		
		
		/* 搜索 */
		$('#search_id').on('click', function(){
           var userName = $('#userName').val();
		      //执行重载
		      table.reload( 'tableReload',{
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		            userName: userName
		        }
		      }, 'data');
      
        }); 
		
		//监听行工具条
		table.on('tool(table_list)', function(obj) {
			editRowObj = obj;
			 var data = obj.data;
			 switch(obj.event){
			  case 'del': //删除
				crup_delete(obj,'/admin/center/account/delete.do',data.id);
		      break;
		      case 'edit':// 编辑
				x_admin_show('编辑','/admin/center/account/edit/'+obj.data.id+'.do',600,400);
		      break;
			 }
		});
	});
	
	//置空搜索框
	$("#clean_search_input").on('click',function(){
			$("#userName").val('');
	});
	
	
	
	
	//刷新
	function reloadTable(id){
		if(id){
			editRelaod(id);
		}else{
		   addRelaod();
		}
	}
	
		function addRelaod(){
				//获取当前页
				// var pageNO = $(".layui-laypage-skip .layui-input").val();
				//执行重载
			     layui.table.reload('tableReload', {
			       page: {
			         curr:1 //重新从第 1 页开始
			       }
			     }, 'data'); 
		}
		
		function editRelaod(id){
				 $.ajax({
					url : '/admin/center/account/get.do',
					type : "POST",
					data :{
				            "id": id,
				            "page": "1",
							"limit": 10,
				        }, //这个是传给后台的值
					dataType : "json",
					success : function(resp) {
					//console.info(data);
					if(resp.code == 200){
					editRowObj.update({
						 userName: resp.data.userName,
						 password: resp.data.password,
						 vaild: resp.data.vaild
						 });
					}
					
					
						
					}, 
					
				});
		}
   
	
	
</script>

</html>