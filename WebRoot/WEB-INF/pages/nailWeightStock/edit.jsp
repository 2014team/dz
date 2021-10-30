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
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>RGB值：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="rgb" name="rgb"
					value="${entity.rgb}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>新编号：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="newSerialNumber" name="newSerialNumber"
					value="${entity.newSerialNumber}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>旧编号：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="oldSerialNumber" name="oldSerialNumber"
					value="${entity.oldSerialNumber}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
		
		<c:choose>
			<c:when test="${not empty entity.id }">
				
				<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> 
				<span class="x-red">*</span>小钉库存(单位千克)：
				</label>
				<div class="layui-input-inline">
					<input type="text" id="stock_1" name="stock_1"
						value="${empty entity.stock_1 ? '0' :entity.stock_1 }"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">必选项</div>
			</div> 
				<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> 
				<span class="x-red">*</span>玫瑰库存(单位千克)：
				</label>
				<div class="layui-input-inline">
					<input type="text" id="stock_2" name="stock_2"
						value="${empty entity.stock_2 ? '0':entity.stock_2}"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">必选项</div>
			</div> 
				<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> 
				<span class="x-red">*</span>砖石库存(单位千克)：
				</label>
				<div class="layui-input-inline">
					<input type="text" id="stock_3" name="stock_3"
						value="${empty entity.stock_3? '0': entity.stock_3}"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">必选项</div>
			</div> 
				<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> 
				<span class="x-red">*</span>大钉库存(单位千克)：
				</label>
				<div class="layui-input-inline">
					<input type="text" id="stock_4" name="stock_4"
						value="${empty entity.stock_4 ? '0' :entity.stock_4 }"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">必选项</div>
			</div> 
			
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> 
				<span class="x-red">*</span>迷你库存(单位千克)：
				</label>
				<div class="layui-input-inline">
					<input type="text" id="stock_5" name="stock_5"
						value="${empty entity.stock_5 ? '0' :entity.stock_5 }"   lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">必选项</div>
			</div> 
			
			</c:when>
			<c:otherwise>
				<input type="hidden" id="stock_1" name="stock_1"
						value="0"   lay-verify="required"
						autocomplete="off" class="layui-input">
				<input type="hidden" id="stock_2" name="stock_2"
						value="0"   lay-verify="required"
						autocomplete="off" class="layui-input">
				<input type="hidden" id="stock_3" name="stock_3"
						value="0"   lay-verify="required"
						autocomplete="off" class="layui-input">
				<input type="hidden" id="stock_4" name="stock_4"
						value="0"   lay-verify="required"
						autocomplete="off" class="layui-input">
						<input type="hidden" id="stock_5" name="stock_5"
						value="0"   lay-verify="required"
						autocomplete="off" class="layui-input">
			</c:otherwise>
		
		</c:choose>
		
		
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>排序：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="sort" name="sort"
					value="${entity.sort}"   lay-verify="required"
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
    				url : '/admin/center/nailWeightStock/save.do',
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