<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  	<%@include file="/WEB-INF/pages/common/jsp_jstl.jsp" %>
  </head>
   <body>
    <div class="x-body">
        <form class="layui-form">
          <input type="hidden" id="id" name="id"  value="${entity.id }" /> 
          <div class="layui-form-item">
              <label for="L_pass" class="layui-form-label">
                  <span class="x-red">*</span>分类ID
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="categoryId" name="categoryId"  value="${entity.categoryId }" lay-verify="required|number"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
           		       数字
              </div>
          </div>
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>分类名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="categoryName" name="categoryName" value="${entity.categoryName }" lay-verify="required|categoryName"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  1到20个字符
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
          //自定义验证规则
          form.verify({
            categoryName: function(value){
              if(value.length > 20){
                return '请输入1到20个字符!';
              }
            }
          });
          
        //保存
        crup_save(form,'save','/admin/center/system/category/save.do');
          
        });
    </script>
  </body>

</html>