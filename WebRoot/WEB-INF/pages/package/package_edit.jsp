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
		      <textarea name="desc" placeholder="请输入内容" id="step" name="step" class="layui-textarea" lay-verify="required">${entity.step }</textarea>
		    </div>
		  </div>
          
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>图片
              </label>
              <div class="layui-input-inline">
                 <div class="layui-upload-drag" id="upload_image_Id">
				  <i class="layui-icon">
				  </i>
				  <p>点击上传，或将文件拖拽到此处</p>
				</div>
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
		     // console.log(obj)
		     // obj.preview(function(index, file, result){
		    
		     // console.log(result,file)
		     // files = file
		      //  $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'" width="200">'); //图片链接（base64）
		      //});
		      
		        var flag = true;
                obj.preview(function(index, file, result){
                    //console.log(file);            //file表示文件信息，result表示文件src地址
                    var img = new Image();
                    img.src = result;
                    img.onload = function () { //初始化夹在完成后获取上传图片宽高，判断限制上传图片的大小。
                        /* if(img.width ==500 || img.height ==500){
                           $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'" width="200">'); //图片链接（base64）
                        }else{
                            flag = false;
                            layer.msg("您上传的小图大小必须是500*500尺寸！");
                            return false;
                        } */
                         $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'" width="200">'); //图片链接（base64）
                    }
                    return flag;
                });
		      
		    }
		  });
		  
        //保存
        form.on('submit(save)', function(obj) {
  			    var  packageName = obj.field.packageName;
  			    var packageId = $('#packageId').val()
        		var formData = new FormData() 
        		//上传图片
   				formData.append('packageId', packageId);
   				formData.append('packageName',$('#packageName').val());
   				formData.append('step', $('#step').val());
   				formData.append('pins', $('#pins').val());
   				formData.append('file', files);
   				
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
    					console.err(e);
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