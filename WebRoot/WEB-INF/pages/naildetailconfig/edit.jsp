<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
</head>

<body>
	<div class="x-body">
		<form class="layui-form">
			<input type="hidden" id="id" name="id" value="${entity.id }" />
		  
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>RGB值
				</label>
				<div class="layui-input-inline">
					<input type="text" id="rgb" name="rgb"
						value="${entity.rgb}"   lay-verify="required"
						autocomplete="off" class="layui-input" maxlength="11">
				</div>
				<div class="layui-form-mid layui-word-aux">建议11字符以内</div>
			</div>
			
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>新编号
				</label>
				<div class="layui-input-inline">
					<input type="text" id="newSerialNumber" name="newSerialNumber"
						value="${entity.newSerialNumber}"   lay-verify="required"
						autocomplete="off" class="layui-input" maxlength="10">
				</div>
				<div class="layui-form-mid layui-word-aux">建议10字符以内</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>旧编号
				</label>
				<div class="layui-input-inline">
					<input type="text" id="oldSerialNumber" name="oldSerialNumber"
						value="${entity.oldSerialNumber}"   lay-verify="required"
						autocomplete="off" class="layui-input" maxlength="10">
				</div>
				<div class="layui-form-mid layui-word-aux">建议10字符以内</div>
			</div>
		  
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>（小钉）每包克数
				</label>
				<div class="layui-input-inline">
					<input type="text" id="nailSmallWeight" name="nailSmallWeight"
						value="${empty entity.nailSmallWeight ?'' : entity.nailSmallWeight}"   lay-verify="required|number"
						autocomplete="off" class="layui-input" maxlength="10">
				</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>（大钉）每包克数
				</label>
				<div class="layui-input-inline">
					<input type="text" id=nailBigWeight name="nailBigWeight"
						value="${empty entity.nailBigWeight ?'' : entity.nailBigWeight}"   lay-verify="required|number"
						autocomplete="off" class="layui-input" maxlength="10">
				</div>
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
          	data = JSON.parse(JSON.stringify(obj.field));
             //加载动画
				var loading = layer.load(0, {
		            shade: false,
		        });
    			$.ajax({
    				url : '/admin/center/naildetailconfig/save.do',
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
    					console.err(e);
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