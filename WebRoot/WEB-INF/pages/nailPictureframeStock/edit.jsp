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
			<span class="x-red">*</span>画框尺寸：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="colorName" name="colorName"
					value="${entity.colorName}"   lay-verify="required"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		<%-- 
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>库存数量：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="stockNumber" name="stockNumber"
					value="${empty entity.stockNumber ? 0 : entity.stockNumber}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div> --%>
		
		<input type="hidden" id="stockNumber" name="stockNumber"
					value="${empty entity.stockNumber ? 0 : entity.stockNumber}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
					
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>黑框库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="black" name="black"
					value="${empty entity.black ? 0 : entity.black}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>白框库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="white" name="white"
					value="${empty entity.white ? 0 : entity.white}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>蓝框库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="blue" name="blue"
					value="${empty entity.blue ? 0 : entity.blue}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>粉框库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="powder" name="powder"
					value="${empty entity.powder ? 0 : entity.powder}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>金框库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="gold" name="gold"
					value="${empty entity.gold ? 0 : entity.gold}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>银框库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="silver" name="silver"
					value="${empty entity.silver ? 0 : entity.silver}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
		
		<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>玫瑰金库存：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="rose" name="rose"
					value="${empty entity.rose ? 0 : entity.rose}"   lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">必选项</div>
		</div>
		
		
			<div class="layui-form-item">
			<label for="L_pass" class="layui-form-label"> 
			<span class="x-red">*</span>排序号：
			</label>
			<div class="layui-input-inline">
				<input type="text" id="sort" name="sort" maxlength="10"
					value="${empty entity.sort? 100:entity.sort}"   lay-verify="required|number"
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
    				url : '/admin/center/nailPictureframeStock/save.do',
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