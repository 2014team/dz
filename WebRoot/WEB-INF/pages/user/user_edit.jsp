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
					class="x-red">*</span>买家名称
				</label>
				<div class="layui-input-inline">
					<input type="text" id="userName" name="userName"
						value="${entity.userName }" lay-verify="required"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux"></div>
			</div>

			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> <span
					class="x-red">*</span>排序
				</label>
				<div class="layui-input-inline">
					<input type="text" id="sort" name="sort"
						value="${empty entity.sort ? 1: entity.sort}" lay-verify="required|number"
						autocomplete="off" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">数字(越小越靠前)</div>
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
          crup_save(form,'save','/admin/center/user/save.do');
          
        });
    </script>
</body>

</html>