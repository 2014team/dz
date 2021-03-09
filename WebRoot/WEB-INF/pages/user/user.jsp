<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
</head>

<body>
	<div class="x-nav">
		<span class="layui-breadcrumb"> <a href="">首页</a> <a href="">绕线画管理</a>
			<a> <cite>用户列表</cite></a>
		</span> <a class="layui-btn layui-btn-primary layui-btn-small"
			style="line-height:1.6em;margin-top:3px;float:right"
			href="javascript:location.replace(location.href);" title="刷新"> <i
			class="layui-icon" style="line-height:38px">&#xe669;</i></a>
	</div>
	<div class="x-body">
		<div class="layui-row demoTable">
			买家名称：
			<div class="layui-inline">
				<input class="layui-input" name="userName" id="userName"
					autocomplete="off">
			</div>
			<button class="layui-btn" type="button" id="search_id">搜索</button>
			<button class="layui-btn" type="button" id="clean_search_input">置空搜索框</button>
			<!--  </form>  -->
		</div>


		<!-- 列表 -->
		<table class="layui-hide" id="table_list" lay-filter="table_list"></table>

		<!-- 头部工具条 -->
		<script type="text/html" id="toolbar">
  		<div class="layui-btn-container">
   			 <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="crup_delAll('rendReloadId','/admin/center/user/delete/batch.do')">批量删除</button>
			<button class="layui-btn layui-btn-sm"  onclick="x_admin_show('编辑','/admin/center/user/order/edit/-1.do')"><i class="layui-icon"></i>增加买家</button>  		
	</div>
	</script>

		<!--列表行Bar  -->
		<script type="text/html" id="rowBar">
		 <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		<button class="layui-btn layui-btn-warm layui-btn-xs"  lay-event="userOrderEdit">添加</button>
 		 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
</body>


<!--序号模板-->
<script type="text/html" id="indexTpl">
  {{d.LAY_TABLE_INDEX+1}}
</script>

<!--序号模板-->
<script type="text/html" id="mobileTpl">
{{}}
{{#  if(d.orderCount == 0 ){ }}
  <i class="layui-icon" status='true' style="color: #ddd;">&#xe623;</i>{{d.userName}}
{{#  } else { }}
  <i class="layui-icon x-show" status='true'>&#xe623;</i>{{d.userName}}
{{#  } }} 
  
</script>




<script type="text/javascript">

	var editRowObj;

	layui.use('table', function() {
		var table = layui.table;

		  table.render({
			elem : '#table_list',
			url : '/admin/center/user/list.do',
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
				 {checkbox: true,},
				 {
					field : 'userName',
					title : '买家名称',
					templet: '#mobileTpl',
					sort : true
				}
				
				, {
					field : '',
					title : '图纸名称',
				}
				, {
					field : 'orderCount',
					title : '订单数',
					sort : true
				}
				, {
					field : 'sort',
					title : '排序',
					sort : true
				}
				, {
					align:'left', toolbar: '#rowBar',
					title : '操作'
				}

			] ]
			  ,id: 'rendReloadId'
			  ,rowStyle:function(row){			  
			  	return "cate-id='"+row.id+"' fid='0'"
			  }
			  ,rowChildStyle:function(row){
		
			  	var html='';
			  	for(var item in row.orderList){
					if(row.orderList[item].minImageUrl && null != row.orderList[item].minImageUrl){
						html += '<tr data-index="0" cate-id="o' + row.orderList[item].orderId + '" fid="' + row.id + '" style="display: none;"><td data-field="0"></td><td data-field="indexId" data-content=""><div class="layui-table-cell laytable-cell-1-indexId">&nbsp;&nbsp;&nbsp;&nbsp;├' + row.orderList[item].mobile + '</div></td>  <td data-field="packageName"><div class="layui-table-cell laytable-cell-1-sort">' + row.orderList[item].packageName + '</div></td> <td data-field="mobile"><div class="laytable-cell-1-sort">&nbsp;&nbsp;<img src="' + row.orderList[item].minImageUrl + '" width="50" height="50"/></div></td> <td data-field="sort"><div class="layui-table-cell laytable-cell-1-sort">' + row.orderList[item].sort + '</div></td><td data-field="4" align="left" data-off="true"><div class="layui-table-cell laytable-cell-1-4"> <a class="layui-btn layui-btn-xs" lay-event="orderEdit_' + row.orderList[item].orderId + '">编辑</a> <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="orderDel_' + row.orderList[item].orderId + '_' + row.id + '">删除</a> </div></td></tr>'
					}else{
						html += '<tr data-index="0" cate-id="o' + row.orderList[item].orderId + '" fid="' + row.id + '" style="display: none;"><td data-field="0"></td><td data-field="indexId" data-content=""><div class="layui-table-cell laytable-cell-1-indexId">&nbsp;&nbsp;&nbsp;&nbsp;├' + row.orderList[item].mobile + '</div></td>  <td data-field="packageName"><div class="layui-table-cell laytable-cell-1-sort">' + row.orderList[item].packageName + '</div></td> <td data-field="mobile"><div class="laytable-cell-1-sort">&nbsp;&nbsp;</div></td> <td data-field="sort"><div class="layui-table-cell laytable-cell-1-sort">' + row.orderList[item].sort + '</div></td><td data-field="4" align="left" data-off="true"><div class="layui-table-cell laytable-cell-1-4"> <a class="layui-btn layui-btn-xs" lay-event="orderEdit_' + row.orderList[item].orderId + '">编辑</a> <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="orderDel_' + row.orderList[item].orderId + '_' + row.id + '">删除</a> </div></td></tr>'
					}
				}

				return html
			  }
		});
		
	
		//表格展开
		$('body').on('click','.x-show',function () {
	        if($(this).attr('status')=='true'){
	            $(this).html('&#xe625;'); 
	            $(this).attr('status','false');
	            cateId = $(this).parents('tr').attr('cate-id');
	            $("tbody tr[fid="+cateId+"]").show();
	       }else{
	            cateIds = [];
	            $(this).html('&#xe623;');
	            $(this).attr('status','true');
	            cateId = $(this).parents('tr').attr('cate-id');
	            getCateId(cateId);
	            for (var i in cateIds) {
	                $("tbody tr[cate-id="+cateIds[i]+"]").hide().find('.x-show').html('&#xe623;').attr('status','true');
	            }
	       }
	    })
		
		/* 搜索 */
		$('#search_id').on('click', function(){
           var userName = $('#userName').val();
		      //执行重载
		      table.reload( 'rendReloadId',{
		      	method:"post",
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		            "userName": userName
		        }
		      }, 'data');
      
        }); 
        
        
        $("#userName").bind("keydown",function(e){
		　　// 兼容FF和IE和Opera
		　　var theEvent = e || window.event;
		　　var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
		　　 if (code == 13) {
			　　var userName = $('#userName').val();
			      //执行重载
			      table.reload( 'rendReloadId',{
			      	method:"post",
			        page: {
			          curr: 1 //重新从第 1 页开始
			        }
			        ,where: {
			            "userName": userName
			        }
			      }, 'data');
			　　}
		});
		
		
		//监听行工具条
		table.on('tool(table_list)', function(obj) {
			editRowObj = obj;
			//console.info(obj);
			var pageNO = $(".layui-laypage-skip .layui-input").val();
			 var data = obj.data;
			 //console.log(obj.event)
			 if(obj.event){
			 var event = obj.event;
			 	if(event == 'del'){//删除
			 		crup_delete(obj,'/admin/center/user/delete.do',data.id);
			 	}else if(event == 'edit'){//编辑
			 		x_admin_show('编辑','/admin/center/user/edit/'+data.id+'.do',600,260);
			 	}else if(event == 'userOrderEdit'){//新增
			 		x_admin_show('新增','/admin/center/user/add/order/'+data.id+'.do');
			 	}else if((event.search("orderDel_") != -1 )){//列表订单删除
				 	 order_delete('/admin/center/order/delete.do',obj);
			 	}else if((event.search("orderEdit_") != -1 )){//列表订单编辑
				 	var delId =  event.slice(10);
				 	 x_admin_show('编辑','/admin/center/user/order/edit/'+delId+'.do');
			 	}
			 }
			
		});
		
		
	});
	
	
	// 订单删除
	function order_delete(url,obj) {
		
		var delId =  obj.event.split('_');
	
		layer.confirm('确认要删除吗？', function(index) {
		$.ajax({
			url : url,
			type : "POST",
			data : {
				"id" : delId[1]
			}, //这个是传给后台的值
			dataType : "json",
			success : function(data) {
				//删除行
				$("tr[cate-id=o"+delId[1]+"]").remove();
				console.log($("tr[fid="+delId[2]+"]"))
				var orderCount = $("tr[fid="+delId[2]+"]").length;
				if(orderCount == 0){
					$("tr[cate-id="+delId[2]+"] .x-show").removeClass("x-show").css('color','#ddd').attr('status','true').html('&#xe623;')
					
				}
				
				$("tr[cate-id="+delId[2]+"] td[data-field='orderCount'] div").html(orderCount)
				
				
				if (data.code == 200) { //这个是从后台取回来的状态值
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
		
	//置空搜索框
	$("#clean_search_input").on('click',function(){
			$("#userName").val('');
	});
	
	
	//刷新
	function reloadTable(obj,idParam){
		if(idParam && -1 ==idParam){
		 layui.table.reload('rendReloadId', {
		  page: {
		    curr: 1 //重新从第 1 页开始
		  }
		}); //只重载数据
		
		}else{
			if(obj.orderId){
			$.ajax({
				url : '/admin/center/order/get/'+obj.orderId+'.do',
				type : "GET",
				dataType : "json",
				success : function(resp) {
				editRowObj.update({
					 },resp.data);
					
				}, 
				
			});
		
		}else if(obj.userName){
			$.ajax({
				url : '/admin/center/user/list.do',
				type : "POST",
				data:{"userName":obj.userName},
				dataType : "json",
				success : function(resp) {
				var orderObj = (resp.data[0].orderList)[0];
				
				var isShow ;
				try{
					isShow = $("tr[fid="+ orderObj.id +"]").first()[0].style.display;
				}catch(err){
					isShow = "none";
				}
				$("tr[cate-id="+ orderObj.id +"] td[data-field=userName] .layui-icon").addClass("x-show").css('color','')
				
				var	html = '<tr data-index="0" cate-id="o' + orderObj.orderId + '" fid="' + orderObj.id + '" style="display:'+isShow+' ;"><td data-field="0"></td><td data-field="indexId" data-content=""><div class="layui-table-cell laytable-cell-1-indexId">&nbsp;&nbsp;&nbsp;&nbsp;├' + orderObj.mobile + '</div></td>  <td data-field="packageName"><div class="layui-table-cell laytable-cell-1-sort">' + orderObj.packageName + '</div></td> <td data-field="mobile"><div class="laytable-cell-1-sort">&nbsp;&nbsp;</div></td> <td data-field="sort"><div class="layui-table-cell laytable-cell-1-sort">' + orderObj.sort + '</div></td><td data-field="4" align="left" data-off="true"><div class="layui-table-cell laytable-cell-1-4"> <a class="layui-btn layui-btn-xs" lay-event="orderEdit_' + 	orderObj.orderId + '">编辑</a> <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="orderDel_' + orderObj.orderId + '_' + orderObj.id + '">删除</a> </div></td></tr>'
				if(orderObj.minImageUrl && null != orderObj.minImageUrl){
				  html = '<tr data-index="0" cate-id="o' + orderObj.orderId + '" fid="' + orderObj.id + '" style="display:'+isShow+' ;"><td data-field="0"></td><td data-field="indexId" data-content=""><div class="layui-table-cell laytable-cell-1-indexId">&nbsp;&nbsp;&nbsp;&nbsp;├' + orderObj.mobile + '</div></td>  <td data-field="packageName"><div class="layui-table-cell laytable-cell-1-sort">' + orderObj.packageName + '</div></td> <td data-field="mobile"><div class="laytable-cell-1-sort">&nbsp;&nbsp;<img src="' + orderObj.minImageUrl + '" width="50" height="50"/></div></td> <td data-field="sort"><div class="layui-table-cell laytable-cell-1-sort">' + orderObj.sort + '</div></td><td data-field="4" align="left" data-off="true"><div class="layui-table-cell laytable-cell-1-4"> <a class="layui-btn layui-btn-xs" lay-event="orderEdit_' + 	orderObj.orderId + '">编辑</a> <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="orderDel_' + orderObj.orderId + '_' + orderObj.id + '">删除</a> </div></td></tr>'
				}
					editRowObj.update({
					 orderCount: resp.data[0].orderCount
					 });
					 editRowObj.addAfter(html);
				}, 
				
			});
		  }
		}
	
		
	}
	
</script>

</html>