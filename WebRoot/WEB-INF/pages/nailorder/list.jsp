<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  	<%@ taglib uri="/WEB-INF/tag/nailDrawingStock.tld" prefix="ns" %>
  	<%@ taglib uri="/WEB-INF/tag/nailImageSize.tld" prefix="nz" %>
  	<%@ taglib uri="/WEB-INF/tag/nailPictureFrame.tld" prefix="np" %>
  	<%@ taglib uri="/WEB-INF/tag/nailPictureframeStock.tld" prefix="nnw" %>
  </head>
  
  <style>
.layui-inline{
margin: 8px	
}
</style>
  
   <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">钉子画管理</a>
        <a>
          <cite>订单列表</cite></a>
      </span>
      <a class="layui-btn layui-btn-primary layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:38px">&#xe669;</i></a>
    </div>
    
    <div class="x-body">
	
      <div class="layui-form layui-row demoTable">
          <div class="layui-inline">
		   		 <select id="searchKey" name="searchKey" lay-search>
	                <option value="">全部</option>
                  	<option value="1" >买家名称</option>
                  	<option value="2" >图纸名称</option>
                  	<option value="3" >手机号码</option>
	            </select>
		  </div>
		   <div class="layui-inline">
		    <input class="layui-input" name="searchValue" id="searchValue" autocomplete="off">
		  </div>
		  
          <div class="layui-inline">
	        <select id="checkoutFlagX" name="checkoutFlagX" lay-search>
	                 <option value="">出库</option>
	                   <option value="1" >是</option>
	                   <option value="0" >否</option>
	             </select>
	    	</div>
	    	
          <div class="layui-inline">
	        <select id="comefrom" name="comefrom" lay-search>
	                 <option value="">来源</option>
	                   <option value="0" >后台</option>
	                   <option value="1" >H5</option>
	             </select>
	    	</div>
	    	
          <div class="layui-inline">
		   		 <select id="nailDrawingStockId" name="nailDrawingStockId" lay-search>
	                <option value="">图钉款式</option>
		          	<c:forEach items="${ns:getList() }" var="item">
		          		<option value="${item.id }" >${item.style }</option>
		          	</c:forEach>
	            </select>
		  </div>
          <div class="layui-inline">
		   		 <select id="nailImageSizeId" name="nailImageSizeId" lay-search>
	                <option value="">图片尺寸</option>
		          	<c:forEach items="${nz:getList() }" var="item">
		          		<option value="${item.id }" >尺寸  ${item.size }宽  x 高(${item.width } x ${item.height })</option>
		          	</c:forEach>
	            </select>
		  </div>
		  
		  
	   <div class="layui-inline">
		   		 <select id="frameId" name="frameId" lay-search>
	                <option value="">画框尺寸</option>
		          	<c:forEach items="${nnw:getList()}" var="item">
		          		<option value="${item.id}">${item.colorName}</option>
		          	</c:forEach>
	            </select>
		  </div> 
		  
		  
          <div class="layui-inline">
		   		 <select id="nailPictureFrameId" name="nailPictureFrameId" lay-search>
	                <option value="">画框颜色</option>
		          	<c:forEach items="${np:getList()}" var="item">
		          		<option value="${item.id }" >${item.colorName}</option>
		          	</c:forEach>
	            </select>
		  </div>
		  
		  	<div class="layui-inline">
					<label class="layui-form-label">日期选择：</label>
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
   			   <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="order_delAll('rendReloadId','/admin/center/nailorder/delete/batch.do')">批量删除</button>
   			  <button class="layui-btn layui-btn-sm"  onclick="checkout('rendReloadId','/admin/center/nailorder/checkout.do','出库')">
				<i class="layui-icon">&#xe605;</i>
				出库
			 </button>
   			  <button class="layui-btn layui-btn-sm"  onclick="checkout('rendReloadId','/admin/center/nailorder/cancel/checkout.do','退库')">
				<i class="layui-icon">&#x1006;</i>
				退库
			 </button>
			<button class="layui-btn layui-btn-sm layui-btn layui-btn-normal"  onclick="analys('rendReloadId','/admin/center/nailorder/analys.do','统计分析')">
				<i class="layui-icon">&#xe60a;</i>统计分析
   			  <button class="layui-btn layui-btn-sm"  onclick="x_admin_show('编辑','/admin/center/nailorder/add.do')"><i class="layui-icon"></i>增加</button>
  		</div>
	</script>
     
     <!--列表行Bar  -->
     <script type="text/html" id="rowBar">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
 		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		{{#  if((d.thirdFlag==1 || d.comefrom == 0) && (d.nailConfigId && d.nailConfigId > 0)){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">清单</a>

		{{# } }}


		
		


	</script>
  </body>
  

<form id= 'formid'/>

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
			url : '/admin/center/nailorder/list.do',
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
					/* sort : true,
					width: 60 */
				}
				
				, {
					field : 'username' ,
					title : '买家名称' ,
				}
				, {
					field : 'imageName' ,
					title : '图纸名称' ,
				}
				, {
					field : 'mobile' ,
					title : '手机号码' ,
				} , {
					field : 'comefrom' ,
					title : '来源' ,
					templet : function(d) {
					if(d.comefrom==0 ){
						return "后台";
					}else if(d.comefrom==1){
						return "H5";
					}else{
						return ""
					}
				},  
				}
				, {
					field : 'nailType' ,
					title : '图钉类型' , 
				},  {
					field : 'colorName' ,
					title : '画框颜色' , 
				} ,{
					field : 'sizeName' ,
					title : '画框尺寸' , 
					
				},  {
					field : 'width' ,
					title : '图片宽' ,
					width: 60 
				},  {
					field : 'height' ,
					title : '图片高' , 
					width: 60
					
				}, {
					field : 'style' ,
					title : '款式' , 
					
				}, {
					field : 'checkoutFlag' ,
					title : '出库' , 	
					templet : function(d) {
						if(d.checkoutFlag ==  1){
							return '<label style="color: red;">是</label>'
						}else{
							return '否'
						}
					}
				}, {
					field : 'createDate' ,
					title : '创建时间' ,
					templet : function(d) {
					return date.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss');
				}, 
				},  {
					field : 'updateDate' ,
					title : '更新时间' ,
					hide:true,
					templet : function(d) {
					return date.toDateString(d.updateDate, 'yyyy-MM-dd HH:mm:ss');
				},  
				}/* , {
					align:'left', toolbar: '#rowBar',
					title : '操作',
					width: 180
					
				} */
				,{field:'nailConfigId',templet:'#rowBar',width:180}

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
				orderd_delete(obj,'/admin/center/nailorder/delete.do');
		      break;
		      case 'edit':// 编辑
				x_admin_show('编辑','/admin/center/nailorder/edit/'+obj.data.id+'.do');
		      break;
		      case 'detail':// 编辑
				x_admin_show('清单','/admin/center/nailorder/detail/'+obj.data.id+'.do');
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
   
   // 统计分析
   function analys(layfilterId,url) {
	var selectData = layui.table.checkStatus(layfilterId).data;
		var array = new Array();
		$.each(selectData,function(i,e){
			array.push(e.id);
		 })
		
		// x_admin_show('统计分析','/admin/center/nailorder/choose/analys/'+array+'.do',$(window).width(),$(window).height());
		 x_admin_show('统计分析','/admin/center/nailorder/choose/analys/'+array+'.do');
		 
   }
   
   
  //出库与退库
  function checkout(layfilterId,url,msg) {
	var selectData = layui.table.checkStatus(layfilterId).data;
	if(selectData.length < 1){	
		layer.msg('请选择要'+msg+'的数据！', {icon: 2});
		return false;
	}
	layer.confirm('确认要'+msg+'吗？', function(index) {
	
			//加载动画
   		var loading = layer.load(2, { //icon支持传入0-2
   		    shade: [0.5, 'gray'], //0.5透明度的灰色背景
   		    content: '订单出库中,请稍等操作...',
   		    success: function (layero) {
   		        layero.find('.layui-layer-content').css({
   		            'padding-top': '39px',
   		            'width': '60px'
   		        });
   		    }
   		});
	
	
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
			
				//关闭动画
				layer.close(loading);
					
				if (data.code == 200) { //这个是从后台取回来的状态值
					layer.close(index);
					layer.msg(data.msg, {
						icon : 1,
						time : 2000
					},function(){
						
						//刷新列表
						window.location.reload();
					});
					
					
				} else {
					layer.msg(data.msg, {
						icon : 2,
						time : 2500
					});
				}
			},
			error : function(e) {
				console.error(e);
				layer.msg("系统异常，稍后再试!", {
					icon : 2,
					time : 2000
				});
			}
		});
		
	});
		
   }
   
   
   
    //刷新
	function reloadTable(id,resp){
		if(id){
			editRelaod(id);
		}else{
		   addRelaod();
		}
		
	 	if(resp && resp.data){
			$("#formid").attr("action","/admin/center/nailorder/export/"+resp.data+".do");
			$("#formid").submit();
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
					url : '/admin/center/nailorder/get.do',
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
						 username: resp.data.username,
						 imageName: resp.data.imageName,
						 mobile: resp.data.mobile,
						 comefrom: resp.data.comefrom,
						 nailType: resp.data.nailType,
						 colorName: resp.data.colorName,
						 createDate: resp.data.createDate,
						 thirdFlag: resp.data.thirdFlag,
						 width: resp.data.width,
						 height: resp.data.height,
						 style: resp.data.style,
						 checkoutFlag: resp.data.checkoutFlag,
						 nailConfigId: resp.data.nailConfigId,
						 nailPictureFrameStockId: resp.data.nailPictureFrameStockId,
						 sizeName: resp.data.sizeName,
						 
						 });
						 
						 
						 var event = editRowObj.event;
					    if ("edit" === event && resp.data.comefrom == 1) {
								//获取当前页
								var pageNO = $(".layui-laypage-skip .layui-input").val();
								//执行重载
							     layui.table.reload('rendReloadId', {
							       page: {
							         curr:pageNO //重新从第 1 页开始
							       }
							     }, 'data'); 
					    } 
		
					}
					
					}, 
					
				});
				
		}
   

</script>
</html>