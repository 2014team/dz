<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
<%@ taglib uri="/WEB-INF/tag/nailPictureFrame.tld" prefix="nw" %>
</head>

<body>
	<div class="x-body">
		<form class="layui-form">
		
		<input type="hidden" id="nailPictureframeId" name="nailPictureframeId" value="${id }" />
			
			
			
				<div class="layui-form-item">
			<label class="layui-form-label">
				<span class="x-red">*</span>画框颜色
			</label>
			<div class="layui-input-inline">
				<select lay-verify="required" id="frameId" name="frameId"  lay-search>
      				<c:forEach items="${nw:getList() }" var="item">
		          		<option value="${item.id }" >${item.colorName}</option>
		          	</c:forEach>
		      </select>
			</div>
		</div>
			
		
		
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>库存量：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="stockNumber" name="stockNumber"
					value="${entity.stockNumber}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>单价：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="price" name="price"
					value="${entity.price}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>总价：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="total" name="total"
					value="${entity.total}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			
			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> </label>
				<button class="layui-btn" lay-filter="save" lay-submit="">
					保存</button>
			</div>
		</form>
	</div>
	<script>
    
 
 		$(function(){
    		 $("#price,#stockNumber").on("input",function(e){
	    	 		var price = $("#price").val();
	    	 		var stock = $("#stockNumber").val();
	    	 		if(price && stock){
	    	 			$("#total").val(stock * price);
	    	 		}
		    	});
    	})
    	
        layui.use(['form','layer'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
          
        
          // 保存
          form.on('submit(save)', function(obj) {
          	data = JSON.parse(JSON.stringify(obj.field));
             //加载动画
				var loading = layer.load(0, {
		            shade: false,
		        });
    			$.ajax({
    				url : '/admin/center/nailPictureframeStock/addStock/save.do',
    				type : "POST",
    				data :data,
    				dataType : "json",
    				success : function(resp) {
    				
	    				//关闭动画
					layer.close(loading);
				
    						if (resp.code == 200) { //这个是从后台取回来的状态值
								layer.msg(resp.msg, {icon : 6,time : 1000
								},function(){
								// 获得frame索引
									var index = parent.layer.getFrameIndex(window.name);
									//关闭当前frame
									parent.layer.close(index);
									//刷新列表
									window.parent.reloadTable(data.id);
								
								});
								
							} else {
								layer.msg(resp.msg, {
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
    
    			return false;
    		});
          
        });
    </script>
</body>

</html>