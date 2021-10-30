<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
</head>

<body>
	<div class="x-body">
		<form class="layui-form">
			<input type="hidden" id="id" name="id" value="${order.orderId }" />
			
			<div class="layui-form-item">
		    <label class="layui-form-label"><span class="x-red">*</span>手机号码</label>
		    <div class="layui-input-inline">
		      <select   lay-verify="required" id="mobile" name="mobile">
		      	 <option value=""></option> 
		      	<c:forEach items="${userList }" var="item">
		      		<c:choose>
		      			<c:when test="${order.mobile eq item.mobile}">
		      				<option value="${item.mobile }" selected="selected">${item.mobile }</option>
		      			</c:when>
		      			<c:otherwise>
		      				<option value="${item.mobile }">${item.mobile }</option>
		      			</c:otherwise>
		      		</c:choose>
		      	</c:forEach>
		      </select>
		    </div>
		  </div>

			<div class="layui-form-item">
		    <label class="layui-form-label"><span class="x-red">*</span>套餐列表</label>
		    <div class="layui-input-inline">
		      <select  lay-filter="rightCategoryId"  lay-verify="required" id="packageId" name="packageId">
		      	<option value=""></option>
		      	<c:forEach items="${packageList }" var="item">
		      		<c:choose>
		      			<c:when test="${order.packageId eq item.packageId}">
		      				<option value="${item.packageId }" selected="selected">${item.packageName }</option>
		      			</c:when>
		      			<c:otherwise>
		      				<option value="${item.packageId }">${item.packageName }</option>
		      			</c:otherwise>
		      		</c:choose>
		      	</c:forEach>
		      </select>
		    </div>
		  </div>
		  
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>排序号
				</label>
				<div class="layui-input-inline">
					<input type="text" id="sort" name="sort"
						value="${empty order.sort ? 1 :   order.sort}"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">数字</div>
				
			</div>
		  

			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> </label>
				<button class="layui-btn" lay-filter="save" lay-submit="">
					保存</button>
			</div>
		</form>
	</div>
	<script>
    
 
        layui.use(['form','layer'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
          
          
          // 保存
          form.on('submit(save)', function(obj) {
    			$.ajax({
    				url : '/admin/center/order/save.do',
    				type : "POST",
    				data : {
    					id:$("#id").val(),
    					sort:$("#sort").val(),
    					mobile:$("#mobile").val(),
    					packageId:$("#packageId").val()
    				},
    				dataType : "json",
    				success : function(data) {
    						if (data.code == 200) { //这个是从后台取回来的状态值
								layer.msg(data.msg, {icon : 6,time : 1000
								}, function() {
									// 获得frame索引
									var index = parent.layer.getFrameIndex(window.name);
									//关闭当前frame
									parent.layer.close(index);
									//刷新列表
									window.parent.location.reload();
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
    
    			return false;
    		});
          
        });
    </script>
</body>

</html>