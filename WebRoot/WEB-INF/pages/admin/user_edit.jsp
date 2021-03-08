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
              	 <input type="text" id="userName" name="userName"  value="${entity.userName }" lay-verify="required"    autocomplete="off" class="layui-input">
              </div>
            
          </div>
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="password" name=password value="${entity.password }" lay-verify="required"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          
          <div class="layui-form-item">
			<label class="layui-form-label">
				<span class="x-red">*</span>状态
			</label>
			<div class="layui-input-inline">
				<select lay-verify="required" id="vaild" name="vaild"  lay-search>
      				<option value="0"  ${entity.vaild eq 0 ?'selected':''} >有效</option>
      				<option value="1" ${entity.vaild eq 1 ?'selected':''} >禁用</option>
		      </select>
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
          
          
          var url = '/admin/center/account/save.do';
          if($("#id").val()  && $("#id").val() >= 0){
          		url = '/admin/center/account/update.do';
          }
          
        //保存
        
        form.on('submit(save)', function(obj) {
          	data = JSON.parse(JSON.stringify(obj.field));
             //加载动画
				var loading = layer.load(0, {
		            shade: false,
		        });
    			$.ajax({
    				url : url,
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
    						time : 500
    					});
    				}
    			});
    
    			return false;
    		});
        
        
        
          
        });
    </script>
  </body>

</html>