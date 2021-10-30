<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">钉子画管理</a>
        <a>
          <cite>画框库存列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-primary layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:38px">&#xe669;</i></a>
    </div>
    <div class="x-body">
      <div class="layui-form layui-row demoTable">
          
		  
		  
				 	画框尺寸：
		          <div class="layui-inline">
				    <input class="layui-input" name="colorName" id="colorName" autocomplete="off">
				  </div>
				 	<!-- 库存数量：
		          <div class="layui-inline">
				    <input class="layui-input" name="stockNumber" id="stockNumber" autocomplete="off">
				  </div>
				 -->
				
				<!--  	序号：
		          <div class="layui-inline">
				    <input class="layui-input" name="sort" id="sort" autocomplete="off">
				  </div> -->
			
		  
				
          <button class="layui-btn" lay-submit lay-filter="searchFilter" >搜索</button>
      </div>
      
   	
   	 <!-- 列表 -->	
     <table class="layui-hide" id="table_list" lay-filter="table_list" ></table>
     
     <!-- 头部工具条 -->
	<script type="text/html" id="toolbar">
  		<div class="layui-btn-container">
   			 <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="delAll('rendReloadId','/admin/center/nailPictureframeStock/delete/batch.do')">批量删除</button>
   			  <button class="layui-btn layui-btn-sm"  onclick="x_admin_show('编辑','/admin/center/nailPictureframeStock/add.do')"><i class="layui-icon"></i>增加</button>
			
  		</div>
	</script>
     
     <!--列表行Bar  -->
     <script type="text/html" id="rowBar">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
 		 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="add_stock">添加库存</a>
	</script>
  </body>
  



<script type="text/javascript">
var editRowObj;
layui.use([ 'table', 'form', 'laydate' ], function() {
	    var table  = layui.table,
		form = layui.form,
		laydate = layui.laydate;
		
		//年月范围选择
		laydate.render({
			elem : '#createDateStr'
			,type: 'datetime'
			,range : '~'
		});

		  table.render({
			elem : '#table_list',
			url : '/admin/center/nailPictureframeStock/list.do',
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
				 {checkbox: true, fixed: true}
			,{
					field : 'indexId', 
					title : '序号',
					type: 'numbers',
					sort : true,
					width:80,
				}
				,{
					field : 'colorName' ,
					title : '画框尺寸' ,
				},{
					field : 'black' ,
					title : '黑框库存' ,
				},{
					field : 'white' ,
					title : '白框库存' ,
				},{
					field : 'blue' ,
					title : '蓝框库存' ,
				},{
					field : 'powder' ,
					title : '粉框库存' ,
				},{
					field : 'gold' ,
					title : '金框库存' ,
				}
				,{
					field : 'silver' ,
					title : '银框库存' ,
				},{
					field : 'rose' ,
					title : '玫瑰金库存' ,
				}
				, {
					field : 'createDate' ,
					title : '创建日期' ,
					templet : function(d) {
					return date.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss');
				    }
				}
				, {
					field : 'updateDate' ,
					title : '更新日期' ,
					hide:true,
					templet : function(d) {
					return date.toDateString(d.updateDate, 'yyyy-MM-dd HH:mm:ss');
				    }
				}
				,{
					field : 'sort' ,
					title : '排序号' ,
				}
				,{
					align:'left', toolbar: '#rowBar',
					title : '操作',
					width: 240
				}

			] ]
			  ,id: 'rendReloadId'
		});
		
        /*搜索*/
	 form.on('submit(searchFilter)', function (data) {
			data = JSON.parse(JSON.stringify(data.field));
		      //执行重载
		      table.reload( 'rendReloadId',{
		      	method:"post",
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: data
		      }, 'data');
  
    });  
		
		//监听行工具条
		table.on('tool(table_list)', function(obj) {
		editRowObj = obj;
			 var data = obj.data;
			 switch(obj.event){
			  case 'del': //删除
				orderd_delete(obj,'/admin/center/nailPictureframeStock/delete.do');
		      break;
		      case 'edit':// 编辑
				x_admin_show('编辑','/admin/center/nailPictureframeStock/edit/'+obj.data.id+'.do');
		      break;
		      case 'add_stock'://添加库存
				x_admin_show('添加库存','/admin/center/nailPictureframeStock/addStock/'+obj.data.id+'.do');
		      break;
			 }
		});
	});
	
	
	//置空搜索框
	$("#clean_search_input").on('click',function(){
			$("#mobile").val('');
	});
	
	// 订单删除
	function orderd_delete(obj, url) {
	layer.confirm('确认要删除吗？', function(index) {
		$.ajax({
			url : url,
			type : "POST",
			data : {
				"id" : obj.data.id
			}, //这个是传给后台的值
			dataType : "json",
			success : function(data) {
				if (data.code == 200) { //这个是从后台取回来的状态值
					obj.del();
					layer.close(index);
					layer.msg(data.msg, {
						icon : 1,
						time : 1000
					});
				} else {
					layer.msg(data.msg, {
						icon : 2,
						time : 1000
					});
				}
			},
			error : function(e) {
				console.error(e);
				layer.msg("系统异常，稍后再试!", {
					icon : 2,
					time : 1000
				});
			}
		});
	});
}


// 批量删除
function delAll(layfilterId,url) {
	var selectData = layui.table.checkStatus(layfilterId).data;
	if(selectData.length < 1){	
		layer.msg('请选择要删除的数据！', {icon: 2});
		return false;
	}
	layer.confirm('确认要删除吗？', function(index) {
		var array = new Array();
		$.each(selectData,function(i,e){
			array.push(e.id);
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
				console.error(e);
				layer.msg("系统异常，稍后再试!", {
					icon : 2,
					time : 1000
				});
			}
		});
		
	});
		
   }
   
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
			     layui.table.reload('rendReloadId', {
			       page: {
			         curr:1 //重新从第 1 页开始
			       }
			     }, 'data'); 
		}
		
		function editRelaod(id){
				 $.ajax({
					url : '/admin/center/nailPictureframeStock/get.do',
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
							 colorName: resp.data.colorName,
							 stockNumber: (resp.data.black +resp.data.white+resp.data.blue+resp.data.powder+resp.data.gold+resp.data.silver),
							 black: resp.data.black,
							 white: resp.data.white,
							 blue: resp.data.blue,
							 powder: resp.data.powder,
							 gold: resp.data.gold,
							 silver: resp.data.silver,
							 rose: resp.data.rose,
							 createDate: resp.data.createDate,
							 updateDate: resp.data.updateDate,
							 sort: resp.data.sort,
						 
						 });
					}
					
					
						
					}, 
					
				});
		}

</script>
</html>