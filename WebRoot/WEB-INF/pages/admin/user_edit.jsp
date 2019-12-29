<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
   <body>
    <div class="x-body">
        <form class="layui-form">
          <input type="hidden" id="id" name="id"  value="${entity.id }" /> 
          <div class="layui-form-item">
              <label for="L_pass" class="layui-form-label">
                  <span class="x-red">*</span>用户名
              </label>
              <div class="layui-input-inline">
              
              		<c:choose>
              			<c:when test="${empty entity.userName }">
			                  <input type="text" id="userName" name="userName"  value="" lay-verify="required"    autocomplete="off" class="layui-input">
              			</c:when>
              			<c:otherwise>
			                  <input type="text" id="userName" name="userName"  value="${entity.userName }" disabled="disabled" lay-verify="required"       autocomplete="off" class="layui-input">
              			</c:otherwise>
              		</c:choose>
              
            
              </div>
            
          </div>
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="password" name=password value="${entity.password }" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
              <button  class="layui-btn" lay-filter="save" lay-submit="">
                  保存
              </button>
          </div>
      </form>
    </div>
    <script>
    
 
        layui.use(['form','layer'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;
          
        //保存
        crup_save(form,'save','/admin/center/account/save.do');
          
        });
    </script>
  </body>

</html>