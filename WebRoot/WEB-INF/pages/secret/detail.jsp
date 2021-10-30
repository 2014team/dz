<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
</head>

<body>
	<div class="x-body">
		<form class="layui-form">
			
		  
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">秘钥
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.secretKey }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">状态
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.status eq 1? '已使用':'未使用' }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">使用项目名称
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.siteName eq 1? '绕线画':'图钉画' }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">订单ID
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.orderId }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">买家名称
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.username }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">手机号码
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.mobile }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">图纸名称
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="${entity.packageName }"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">创建日期
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="<fmt:formatDate value="${entity.createDate}" pattern="yyyy年MM月dd日HH点mm分ss秒" />"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">使用日期
				</label>
				<div class="layui-input-inline">
					<input type="text"
						value="<fmt:formatDate value="${entity.updateDate}" pattern="yyyy年MM月dd日HH点mm分ss秒" />"  readonly="readonly"
						autocomplete="off" class="layui-input">
				</div>
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
          debugger
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