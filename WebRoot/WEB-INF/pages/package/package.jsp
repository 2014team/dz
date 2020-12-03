<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">绕线画管理</a>
        <a>
          <cite>模板列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-primary layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:38px">&#xe669;</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row demoTable">
           	图纸名称：
          <div class="layui-inline">
		    <input class="layui-input" name="packageName" id="packageName" autocomplete="off">
		  </div>
          <button class="layui-btn" type="button" id="search_id">搜索</button>
           <button class="layui-btn" type="button" id="clean_search_input">置空搜索框</button>
      </div>
      
   	
   	 <!-- 列表 -->	
     <table class="layui-hide" id="table_list" lay-filter="table_list" ></table>
     
     <!-- 头部工具条 -->
	<script type="text/html" id="toolbar">
  		<div class="layui-btn-container">
   			 <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="package_delAll('rendReloadId','/admin/center/package/delete/batch.do')">批量删除</button>
   			 <button class="layui-btn layui-btn-sm"  onclick="x_admin_show('编辑','/admin/center/package/add.do')"><i class="layui-icon"></i>增加</button>
  		</div>
	</script>
     
     <!--列表行Bar  -->
     <script type="text/html" id="rowBar">
		 <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
 		 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
  </body>
  

<!--图片模板  -->
<script type="text/html" id="minImageUrl">
  <img alt="{{d.minImageUrl}}" src="{{d.minImageUrl}}" >
</script>

<script type="text/javascript">
     var editRowObj;

	layui.use('table', function() {
		var table = layui.table;

		  table.render({
			elem : '#table_list',
			url : '/admin/center/package/list.do',
			toolbar: '#toolbar',
		    defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
		      title: '提示'
		      ,layEvent: 'LAYTABLE_TIPS'
		      ,icon: 'layui-icon-tips'
		    }],
		    method:"post",
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
				 {checkbox: true},
				{
					field : 'indexId', 
					title : '序号',
					type: 'numbers',
					width:75,
					sort : true,					
				}
				, {
					field : 'packageName',
					title : '图纸名称'
				}
				, {
					sort : true,
					field : 'useCount',
					title : '用户数'
				}
				, {
					sort : true,
					field : 'categoryName',
					title : '类别'
				}
				, {
					field : 'step',
					title : '执行步骤'
				}
				, {
					field : 'minImageUrl' ,
					title : '图片' ,
					templet: '#minImageUrl',
					width:80
				}
				, {
					align:'left', toolbar: '#rowBar',
					title : '操作'
				}

			] ]
			  ,id: 'rendReloadId'
		});
		
		
		/* 搜索 */
		$('#packageName').on('click', function(){
           var searchPackageName = $('#packageName').val();
		      //执行重载
		      table.reload( 'rendReloadId',{
		      	method:"post",
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		            "packageName": searchPackageName
		        }
		      }, 'data');
      
        }); 
        
        
        $("#packageName").bind("keydown",function(e){
		　　// 兼容FF和IE和Opera
		　　var theEvent = e || window.event;
		　　var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
		　　 if (code == 13) {
			　　var packageName = $('#packageName').val();
			      //执行重载
			      table.reload( 'rendReloadId',{
			      	method:"post",
			        page: {
			          curr: 1 //重新从第 1 页开始
			        }
			        ,where: {
			            "packageName": packageName
			        }
			      }, 'data');
			　　}
		});
		
		//监听行工具条
		table.on('tool(table_list)', function(obj) {
		editRowObj = obj;
			 var data = obj.data;
			 switch(obj.event){
			  case 'del': //删除
				crup_delete(obj,'/admin/center/package/delete.do',data.packageId);
		      break;
		      case 'edit':// 编辑
				x_admin_show('编辑','/admin/center/package/edit/'+obj.data.packageId+'.do');
		      break;
			 }
		});
	});
	
	
	//置空搜索框
	$("#clean_search_input").on('click',function(){
			$("#packageName").val('');
	});
	
	
	//批量删除
	function package_delAll(layfilterId,url) {
	var selectData = layui.table.checkStatus(layfilterId).data;
	if(selectData.length < 1){	
		layer.msg('请选择要删除的数据！', {icon: 2});
		return false;
	}
	layer.confirm('确认要删除吗？', function(index) {
		var array = new Array();
		$.each(selectData,function(i,e){
			array.push(e.packageId);
		 })
		$.ajax({
			url : url,
			type : "POST",
			data : {"array":JSON.stringify(array)},
			dataType : "json",
			success : function(data) {
				if (data.code == 200) { //这个是从后台取回来的状态值
					layer.close(index);
					layer.msg(data.msg, {
						icon : 1,
						time : 1000
					},function(){
						
						//刷新列表
						window.location.reload();
					});
					
					
				} else {
					layer.msg(data.msg, {
						icon : 2,
						time : 1000
					});
				}
			},
			error : function(e) {
				//console.log(e);
				layer.msg("系统异常，稍后再试!", {
					icon : 2,
					time : 1000
				});
			}
		});
		
	});
		
   }	
   
 
   //刷新
	function reloadTable(packageId,packageName){
		if(packageId){
			editRelaod(packageName);
		}else{
		   addRelaod();
		}
	}
	
		function addRelaod(){
				//获取当前页
				// var pageNO = $(".layui-laypage-skip .layui-input").val();
				//执行重载
			     layui.table.reload('rendReloadId', {
			       page: {
			         curr:1 //重新从第 1 页开始
			       }
			     }, 'data'); 
		}
		
		function editRelaod(packageName){
				 $.ajax({
					url : '/admin/center/package/list.do',
					type : "POST",
					data :{
				            "packageName": packageName,
				            "page": "1",
							"limit": 10,
				        }, //这个是传给后台的值
					dataType : "json",
					success : function(resp) {
					var data = resp.data[0];
					//console.info(data);
					editRowObj.update({
						 packageName: data.packageName
						 ,useCount: data.useCount
						 ,categoryName: data.categoryName
						 ,step: data.step
						 ,minImageUrl: data.minImageUrl
						 });
						
					}, 
					
				});
		}
	
</script>

</html>