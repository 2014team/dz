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
          <cite>秘钥列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-primary layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:38px">&#xe669;</i></a>
    </div>
    <div class="x-body">
      <div class="layui-form layui-row demoTable">
           	秘钥：
          <div class="layui-inline">
		    <input class="layui-input" name="secretKey" id="secretKey" autocomplete="off">
		  </div>
		  
		       状态：
          <div class="layui-inline">
		   		 <select id="status" name="status" lay-search>
	                <option value="">全部</option>
                  	<option value="0" >未使用</option>
                  	<option value="1" >已使用</option>
	            </select>
		  </div>
		  
		  <div class="layui-inline">
					<label class="layui-form-label">创建时间：</label>
					<div class="layui-input-inline">
						<input type="text" name="createDateStr" id="createDateStr" placeholder="请选择开始时间 - 结束时间"
							autocomplete="off" class="layui-input" readonly="readonly" style="width: 360px;">
					</div>
				</div>
				
          <button class="layui-btn" lay-submit lay-filter="searchFilter" >搜索</button>
      </div>
      
   	
   	 <!-- 列表 -->	
     <table class="layui-hide" id="table_list" lay-filter="table_list" ></table>
     
     <!-- 头部工具条 -->
	<script type="text/html" id="toolbar">
  		<div class="layui-btn-container">
   			 <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="order_delAll('rendReloadId','/admin/center/nailsecret/delete/batch.do')">批量删除</button>
   			 <button class="layui-btn layui-btn-sm"  onclick="x_admin_show('配置','/admin/center/nailsecret/add.do')"><i class="layui-icon"></i>生成秘钥</button>
  		</div>
	</script>
     
     <!--列表行Bar  -->
     <script type="text/html" id="rowBar">
 		 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
  </body>
  

<!--图片模板  -->
<script type="text/html" id="imageUrlTpl">
  <img alt="{{d.imageUrl}}" src="{{d.imageUrl}}">
</script>
<!-- 序号模板 -->
<script type="text/html" id="indexTpl">
   {{d.LAY_TABLE_INDEX+1}}
</script>


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
			url : '/admin/center/nailsecret/list.do',
			toolbar: '#toolbar',
		    defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
		      title: '提示'
		      ,layEvent: 'LAYTABLE_TIPS'
		      ,icon: 'layui-icon-tips'
		    }],
		    method:"post",
			page : { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
				layout : [ 'limit', 'count', 'prev', 'page', 'next', 'skip' ], //自定义分页布局 //,curr: 5 //设定初始在第 5 页
				limits:[10, 50, 100],
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
					field : 'secretKey' ,
					title : '秘钥' ,
				}
				, {
					field : 'username' ,
					title : '买家名称' ,
					hide:true
				}, {
					field : 'mobile' ,
					title : '手机号码' ,
					hide:true
				}
				, {
					field : 'status' ,
					title : '状态' ,
					templet : function(d) {
					  if(d.status == 0){
					  	return "未使用"
					  }else if(d.status == 1){
					  	return "已使用"
					  }
					}
				}/* , {
					field : 'insertFlag' ,
					title : '操作标识' ,
				} */
				, {
					field : 'createDate' ,
					title : '创建时间' ,
					templet : function(d) {
					return date.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss');
				}, 
				}, {
					field : 'updateDate' ,
					title : '更新时间' ,
					templet : function(d) {
					return date.toDateString(d.updateDate, 'yyyy-MM-dd HH:mm:ss');
				}, 
				}, {
					align:'left', toolbar: '#rowBar',
					title : '操作'
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
				orderd_delete(obj,'/admin/center/nailsecret/delete.do');
		      break;
		      case 'edit':// 编辑
				x_admin_show('编辑','/admin/center/nailsecret/edit/'+obj.data.orderId+'.do');
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
				console.err(e);
				layer.msg("系统异常，稍后再试!", {
					icon : 2,
					time : 1000
				});
			}
		});
	});
}


// 批量删除
function order_delAll(layfilterId,url) {
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
					url : '/admin/center/nailsecret/get.do',
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
						 secretKey: resp.data.secretKey,
						 username: resp.data.username,
						 mobile: resp.data.mobile,
						 status: resp.data.status,
						 createDate: resp.data.createDate,
						 updateDate: resp.data.updateDate
						 
						 });
					}
					
					
						
					}, 
					
				});
		}

</script>
</html>