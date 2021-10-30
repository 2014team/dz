<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  		<%@ taglib uri="/WEB-INF/tag/nailPictureFrame.tld" prefix="nf" %>
  </head>
  
   <body>
   
   <form class="layui-form layui-form-pane" id="rendReloadId" >
   
   <div>
	   <input type="hidden" name="arrayId" value="${entity.array}">
   </div>

	   <h2 style="text-align: center; margin: 5px">
	 		统计分析
	 	</h2>
	 	<div class="x-body">
	 	
	 			<div class="layui-inline">
					<label class="layui-form-label">日期选择：</label>
					<div class="layui-input-inline">
						<input type="text" name="createDateStr" id="createDateStr" placeholder="请选择开始时间 - 结束时间"
							autocomplete="off" class="layui-input" readonly="readonly" style="width: 360px;">
					</div>
				</div>
	 	
	 	
				 <div class="layui-inline">
			   		 <select id="nailConfigId" name="nailConfigId" lay-search>
		                <option value="">全部</option>
		                 	<c:forEach items="${nailconfigList }" var="item">
		                 	<option value="${item.id }" >${item.nailType }</option>
		                 	</c:forEach>
		            </select>
			  	</div> 
			  	
	 	
				 <div class="layui-inline">
			   		 <select id="nailPictureFrameStockId" name="nailPictureFrameStockId" lay-search>
		                <option value="">画框尺寸</option>
		                 	<c:forEach items="${nailPictureframeStockList }" var="item">
		                 	<option value="${item.id }" >${item.colorName }</option>
		                 	</c:forEach>
		            </select>
			  	</div> 
			  	
			  	 	<div class="layui-inline">
		   		 <select id="nailPictureFrameId" name="nailPictureFrameId" lay-search>
	                <option value="">画框颜色</option>
		          	<c:forEach items="${nf:getList() }" var="item">
		          		<option value="${item.id }" >${item.colorName}</option>
		          	</c:forEach>
	            </select>
		  </div>
		  
			  	
          <div class="layui-inline">
	        <select id="checkoutFlagX" name="checkoutFlagX" lay-search>
	                 <option value=""> 出库</option>
	                   <option value="1" >是</option>
	                   <option value="0" >否</option>
	             </select>
	    	</div>
	    	
	   
	    	
			<button type="button" class="layui-btn"
					style="position: absolute;" lay-submit lay-filter="searchFilter">搜索</button>
			
			
			<div class="layui-btn-container xbtpt10">
   			  <button type="button" lay-submit class="layui-btn layui-btn-sm" onclick="exportCount('/admin/center/nailorder/export/nail.do')">
				<i class="layui-icon">&#xe605;</i>
				图钉统计导出
			</button>
   			  <button type="button" lay-submit class="layui-btn layui-btn-sm" onclick="exportCount('/admin/center/nailorder/export/drawing.do')">
				<i class="layui-icon">&#xe605;</i>
				图纸导出统计
			 </button> 
  			</div>
		</div>
		
		
		</div>
		
	</form>
	
	
	
	<div id="table_list">
	<div style="padding:5px;line-height:2;">
		<%@ include file="/WEB-INF/pages/nailorder/analys_list.jsp" %>
	</div>
	<div style="padding:20px;line-height:2;">
		说明：<br/>
		1、默认当天数据<br/>
		2、计算：<br/>①涉及到小数点四舍五入处理。<br/>
		②保留两位小数。	
		<br/>
		3、计算公式<br/>
		数量平均值 = 数量 / 订单量      。	重量平均值 = 重量 / 订单量    。 		包数平均值	= 包数 / 订单量<br>
		数量占比= 数量 / 总计数量      。		重量占比 = 重量 / 总计重量    。			包数占比 = 包数/ 总计包数
	</div>
	
	<script>
	
 	    
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
		
		
		
					
		 form.on('submit(searchFilter)', function (data) {
				data = JSON.parse(JSON.stringify(data.field));
				
			     var loading = layer.load(2, { //icon支持传入0-2
		   		    shade: [0.5, 'gray'], //0.5透明度的灰色背景
		   		    content: '统计分析中,请稍等操作...',
		   		    success: function (layero) {
		   		        layero.find('.layui-layer-content').css({
		   		            'padding-top': '39px',
		   		            'width': '60px'
		   		        });
		   		    }
		   		});
			     
			     
			     $.ajax({
				url : "/admin/center/nailorder/analys/list.do",
				type : "POST",
				data : data,
				dataType : "text",
				success : function(data) {
					//关闭动画
					layer.close(loading);
					$("#table_list").html(data);
					$("#arrayId").html(data);
				},
				
				});
	   	 });  
	    
          
		}); 
		
       //导出统计
       function exportCount(url){
       		$("#rendReloadId").attr("action",url).submit();
       }
    
          
    </script>

</body>
</html>