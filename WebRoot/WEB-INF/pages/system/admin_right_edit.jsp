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
           <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span class="x-red">*</span>权限规则</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入内容" class="layui-textarea" id="rightRule" name="rightRule" lay-verify="required|rightRule">${entity.rightRule}</textarea>
		      <div class="layui-form-mid layui-word-aux">
           		    1到100个字符
              </div>
		    </div>
		  </div>
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>权限名称
              </label>
              <div class="layui-input-inline">
                  <input type="text" value="${entity.rightName }" id="rightName" lay-verify="required|rightName"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  1到20个字符
              </div>
          </div>
          
		     <div class="layui-form-item">
		    <label class="layui-form-label"><span class="x-red">*</span>选择框</label>
		    <div class="layui-input-inline">
		   	 <input type="hidden" name="rightCategoryId" id="rightCategoryId">
		      <select  lay-filter="rightCategoryId"  lay-verify="required"  >
		      	<option value=""></option>
		      	<c:forEach items="${categoryList }" var="item">
		      		<option value="${item.categoryId }">${item.categoryName }</option>
		      	</c:forEach>
		      </select>
		    </div>
		  </div>
  
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
              <button  class="layui-btn"  lay-filter="save"  id="save" lay-submit>
             		     保存
              </button>
          </div>
      </form> 
    </div>
    <script>
    
 
        layui.use(['form','layer','layedit'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer
          layedit = layui.layedit;
          
          //自定义验证规则
          form.verify({
            rightRule: function(value){
              if(value.length > 100){
                return '请输入1到100个字符!';
              }
            },
             rightName: function(value){
              if(value.length > 20){
                return '请输入1到20个字符!';
              }
            }
          });
    
    		//保存
    		
    	form.on('submit(save)', function(obj) {
    		var formData = new FormData($("#uploadForm")[0])  //创建一个forData 
   			 formData.append('img', $('#pic_img')[0].files[0]) //把file添加进去  name命名为img
    	
    	
    	
    			$.ajax({
    				url : '/admin/center/system/right/save.do',
    				type : "POST",
    				data : {
    					id:$("#id").val(),
    					rightRule:$("#rightRule").val(),
    					rightName:$("#rightName").val(),
    					rightCategoryId:$("#rightCategoryId").val()
    				},
    				dataType : "json",
    				success : function(data) {
    						if (data.code == 200) { //这个是从后台取回来的状态值
								layer.msg(data.msg, {icon : 6,time : 500
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
									time : 500
								});
							}
    					},
    				error : function(e) {
    					console.log(e);
    					layer.msg("系统异常，稍后再试!", {
    						icon : 2,
    						time : 1000
    					});
    				}
    			});
    
    			return false;
    		});
    
    		// 下列
    		form.on('select(rightCategoryId)', function(data){
		 	 $("#rightCategoryId").val(data.value)
		}); 
          
        });
    </script>
  </body>

</html>