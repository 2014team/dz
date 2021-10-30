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
			<span class="x-red">*</span>款式：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="style" name="style"
					value="${entity.style}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>打印尺寸(单位cm)：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="printSize" name="printSize"
					value="${entity.printSize}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>画框尺寸：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="frameSize" name="frameSize"
					value="${entity.frameSize}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
			<div class="layui-form-item">
			<label class="layui-form-label">
				<span class="x-red">*</span>状态
			</label>
			<div class="layui-input-inline">
				<select lay-verify="required" id="status" name="status"  lay-search>
      				<option value="0"  ${entity.status eq 0 ?'selected':''} >可用</option>
      				<option value="1" ${entity.status eq 1 ?'selected':''} >不可用</option>
		      </select>
			</div>
		</div>
          
		
		<c:choose>
			<c:when test="${not empty entity.id }">
				
					<div class="layui-form-item">
					<label for="L_pass" class="layui-form-label"> 
					<span class="x-red">*</span>数量：
					</label>
					<div class="layui-input-inline">
						<input type="text" id="number" name="number"
							value="${entity.number}"   lay-verify="required"
							autocomplete="off" class="layui-input">
					</div>
					<div class="layui-form-mid layui-word-aux">必选项</div>
				</div>
			</c:when>
				
			<c:otherwise>
			
				<div class="layui-input-inline">
						<input type="hidden" id="number" name="number"
							value="${empty entity.number ? 0: entity.number}"   lay-verify="required"
							autocomplete="off" class="layui-input">
					</div>
			</c:otherwise>
		
		</c:choose>
		
		
			
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
    				url : '/admin/center/nailDrawingStock/save.do',
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