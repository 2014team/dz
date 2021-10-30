<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
    <div class="x-body">
        <form class="layui-form">
          <input type="hidden" id="packageId" name="packageId"  value="${entity.packageId }" /> 

          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>图纸名称
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="packageName" name="packageName" value="${entity.packageName }" lay-verify="required|packageName"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  1到50个字符
              </div>
          </div>
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>钉子数量
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="pins" name="pins" value="${empty entity.pins?150:entity.pins }" lay-verify="required|number"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  数字
              </div>
          </div>
          
          <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"> <span class="x-red">*</span>执行步骤</label>
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn" id="additional_id">选择文件</button>
		      <label id="additional_label"  class="layui-form-label" style="float:none;display:inline-block;text-align:left;width:auto;">${entity.stepName }</label>
		    </div>
		  </div>
	
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <!-- <span class="x-red">*</span> -->图片
              </label>
              <div class="layui-input-inline">
                 <div class="layui-upload-drag" id="upload_image_Id">
				  <i class="layui-icon">
				  </i>
				  <p>点击上传，或将文件拖拽到此处</p>
				</div>
          </div>
          
          <input type="hidden" id="sort" name="sort" value="1"/>
          
          <input type="hidden" id="step" name="step"
					class="layui-textarea" value="${entity.step }"></input>
             
          </div>
          
          
          
		<div class="layui-form-item">
			<label class="layui-form-label">类别名称</label>
			<div class="layui-input-inline">
				<select  id="categoryId" name="categoryId"  lay-search>
			      	<option value=""></option>
			      	<c:forEach items="${categoryList }" var="item">
			      		<c:choose>
			      			<c:when test="${entity.categoryId eq item.id}">
			      				<option value="${item.id }" selected="selected">${item.categoryName }</option>
			      			</c:when>
			      			<c:otherwise>
			      				<option value="${item.id }">${item.categoryName }</option>
			      			</c:otherwise>
			      		</c:choose>
			      	</c:forEach>
		      </select>
			</div>
		</div>
		
          
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
              <button  class="layui-btn" lay-filter="save" lay-submit="" >
              	    保存
              </button>
          </div>
      </form>
    </div>
    <script>
    
	    $(function(){
	    	var imageUrl = '${entity.imageUrl}';
	    	if(imageUrl){
	    	 $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+imageUrl+'" width="200">'); //图片链接（base64）
	    	}
	    });
    
 		var files;
 		var stepFile;
        layui.use(['form','layer','upload'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer
          ,upload = layui.upload;
          //自定义验证规则
          form.verify({
            packageName: function(value){
              if(value.length > 50){
                return '请输入1到50个字符!';
              }
            }
          });
          //拖拽上传
		  upload.render({
		    elem: '#upload_image_Id'
		    /* ,url: '/upload/' */
		     ,size: 1024 //限制文件大小，单位 KB
		    ,auto:false
		    ,choose: function(obj){
		      //预读本地文件示例，不支持ie8
		      console.log(obj)
		      obj.preview(function(index, file, result){
		    
		      console.log(result,file)
		      files = file
		        $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'" width="200">'); //图片链接（base64）
		      });
		    }
		  });
		  
		  
		  //选完文件后不自动上传
		  upload.render({
		    elem: '#additional_id'
		    //,url: '/upload/'
		    ,auto: false
		    //,multiple: true
			 ,accept: 'file'
		    ,exts: 'txt' 
		   // ,bindAction: '#test9'
		    ,done: function(res){
		      console.log(res)
		    }
		   ,choose: function(obj){
		     obj.preview(function(index, file, result){
		      	stepFile = file
		      	if(stepFile){
		      		$("#additional_label").html(stepFile.name);
		      	}
		      });
		     
		    }
		  });
		  
        //保存
        form.on('submit(save)', function(obj) {
  			    var  packageName = obj.field.packageName;
  			    var categoryId = obj.field.categoryId;
  			    var packageId = $('#packageId').val()
        		var formData = new FormData() 
        		//上传图片
   				formData.append('packageId', packageId);
   				formData.append('packageName',$('#packageName').val());
   				formData.append('step', $('#step').val());
   				formData.append('pins', $('#pins').val());
   				formData.append('file', files);
   				formData.append('stepFile', stepFile);
   				formData.append('categoryId', categoryId);
   				
   				 // 等候加载
    	    	 x_admin_loading(true);
    			$.ajax({
    				url : '/admin/center/package/save.do',
    				type : "POST",
					cache : false,
					data : formData,
				    processData : false, // 使数据不做处理
        			contentType : false, // 不要设置Content-Type请求头
    				dataType : "json",
    				success : function(data) {
    				//移除等待加载
    				x_admin_loading(false);
    						if (data.code == 200) { //这个是从后台取回来的状态值
								layer.msg(data.msg, {icon : 6,time : 1000
								}, function() {
									// 获得frame索引
									var index = parent.layer.getFrameIndex(window.name);
									//关闭当前frame
									parent.layer.close(index);
									//刷新列表
									//window.parent.location.reload();
									//刷新列表
									window.parent.reloadTable(packageId,packageName)
								});
								
							} else {
								layer.msg(data.msg, {
									icon : 2,
									time : 1000
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
        });
    </script>
  </body>

</html>