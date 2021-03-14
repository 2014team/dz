<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
</head>

<body>
	<div class="x-body">
		<form class="layui-form">
			<input type="hidden" id="id" name="id" value="${entity.id }" />
			
			

		  
		 <!--  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>操作标识
				</label>
				<div class="layui-input-inline">
					<input type="text" id="insertFlag" name="insertFlag"
						value="11"   lay-verify="required"
						autocomplete="off" class="layui-input" disabled="disabled">
				</div>
				<div class="layui-form-mid layui-word-aux">主要作用：根据此值查询本次生产的秘钥</div>
				
			</div>
		   -->
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>秘钥数量
				</label>
				<div class="layui-input-inline">
					<input type="text" id="secretNumber" name="secretNumber"
						value=""   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">数字</div>
			</div>
			
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>秘钥长度
				</label>
				<div class="layui-input-inline">
					<input type="text" id="secretDigit" name="secretDigit"
						value="6"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">数字范围(6-32),默认6</div>
				
			</div>
		  

			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> </label>
				<button class="layui-btn" lay-filter="save" lay-submit="">
					生成秘钥</button>
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
          	data = JSON.parse(JSON.stringify(obj.field));
             //加载动画
				var loading = layer.load(0, {
		            shade: false,
		        });
    			$.ajax({
    				url : '/admin/center/secret/save.do',
    				type : "POST",
    				data :data,
    				dataType : "json",
    				success : function(data) {
    				
	    				//关闭动画
					layer.close(loading);
				
    						if (data.code == 200) { //这个是从后台取回来的状态值
								layer.msg(data.msg, {icon : 6,time : 1000
								},function(){
								 window.parent.location.reload();//刷新父页面
								x_admin_close();});
								
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