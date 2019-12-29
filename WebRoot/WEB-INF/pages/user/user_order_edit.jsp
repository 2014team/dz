<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>
</head>

<body>
	<div class="x-body">
		<form class="layui-form">
			<input type="hidden" id="orderId" name="orderId" value="${order.orderId }" />
			<input type="hidden" id="packageId" name="packageId" value="${order.packageId }" />
			
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>买家名称
				</label>
				<div class="layui-input-inline">
					<input type="text" id="userName" name="userName"
						value="${user.userName }" lay-verify="required"
						autocomplete="off" class="layui-input" disabled="disabled">
				</div>
				<div class="layui-form-mid layui-word-aux"></div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">模板选择</label>
				<div class="layui-input-block">
				
				<c:choose>
					<c:when test="${0 eq order.template }">
						<input type="radio" name="template" value="0" title="定制模板" lay-filter="template" checked> 
					</c:when>
					<c:when test="${1 eq order.template }">
						<input type="radio"  name="template" value="1" title="选择模板" lay-filter="template" checked>	
					</c:when>
					<c:otherwise>
						<input type="radio" name="template" value="0" title="定制模板" lay-filter="template" checked> 
						<input type="radio"  name="template" value="1" title="选择模板" lay-filter="template">
					</c:otherwise>
				
				</c:choose>				
					
				
				
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>手机号码
				</label>
				<div class="layui-input-inline">
					<input type="text" id="mobile" name="mobile"
						value="${ order.mobile }"
						lay-verify="required|number|phone" autocomplete="off"
						class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux"></div>
			</div>
			<div class="template-div"></div>

			

			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>排序号
				</label>
				<div class="layui-input-inline">
					<input type="text" id="sort" name="sort"
						value="${empty order.sort ? 1 : order.sort }"
						lay-verify="required|number" autocomplete="off"
						class="layui-input">
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

	<div class="template-box" data-value="0" style="display:none;">
		<input type="hidden" id="orderId" name="orderId" value="${order.orderId }" />
		<input type="hidden" id="packageId" name="packageId" value="${order.packageId }" />

		<div class="layui-form-item">
			<label for="L_repass" class="layui-form-label"> <span
				class="x-red">*</span>图纸名称
			</label>
			<div class="layui-input-inline">
				<input type="text" id="packageName" name="packageName"
					value="${order.packageName }" lay-verify="required|packageName"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">1到50个字符</div>
		</div>
		
		<div class="layui-form-item">
			<label for="L_repass" class="layui-form-label"> <span
				class="x-red">*</span>钉子数量
			</label>
			<div class="layui-input-inline">
				<input type="text" id="pins" name="pins"
					value="${empty order.pins ? 150 :  order.pins }" lay-verify="required|number"
					autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">数字</div>
		</div>

		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label"> <span class="x-red">*</span>执行步骤
			</label>
			<div class="layui-input-block">
				<textarea name="desc" placeholder="请输入内容" id="step" name="step"
					class="layui-textarea" lay-verify="required">${order.step }</textarea>
			</div>
		</div>

		<div class="layui-form-item">
			<label for="L_repass" class="layui-form-label"> <span
				class="x-red">*</span>图片
			</label>
			<div class="layui-input-inline">
				<div class="layui-upload-drag" id="upload_image_Id">
					<i class="layui-icon"></i>
					<p>点击上传，或将文件拖拽到此处</p>
				</div>
			</div>

		</div>

	</div>
	<div class="template-box" data-value="1" style="display:none;">
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="x-red">*</span>图纸名称</label>
			<div class="layui-input-inline">
				<select  lay-filter="rightCategoryId"  lay-verify="required" id="packageId" name="packageId"  lay-search>
			      	<option value=""></option>
			      	<c:forEach items="${packageList }" var="item">
			      	
			      		<c:choose>
			      		
			      			<c:when test="${order.packageId eq item.packageId}">
			      				<option value="${item.packageId }" selected="selected">${item.packageName }</option>
			      			</c:when>
			      			<c:otherwise>
			      				<option value="${item.packageId }">${item.packageName }</option>
			      			</c:otherwise>
			      		</c:choose>
			      	
			      	
			      		
			      	</c:forEach>
		      </select>
			</div>
		</div>
		
	</div>

	<script>
    	
    	
    	 $(function(){
    	 	// 图片回显
	    	var imageUrl = '${order.imageUrl}';
	    	if(imageUrl){
	    	 $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+imageUrl+'" width="200">'); //图片链接（base64）
	    	}
	    });
    	
    	
    	
    	// 选择模板使用
    	var templateDiv = '${order.template}';
    	//console.info("templateDiv = "+templateDiv);
    	if(templateDiv == ''){
    		templateDiv = 0
    	}
 		$(".template-div").append($(".template-box[data-value='"+templateDiv+"']"));
 		$(".template-box[data-value='"+templateDiv+"']").show();
     	
     	
     	
     	
     	var files;
        layui.use(['form','layer','upload','table'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer
          ,upload = layui.upload
          table = layui.table;
          
          form.on('radio(template)', function(data){
			 // console.log(data.elem); //得到radio原始DOM对象
			 // console.log(data.value); //被点击的radio的value值
			  
			  $(".template-box").hide()
			  $("body").append($(".template-box"));
			  $(".template-div").append($(".template-box[data-value='"+data.value+"']"));
 			  $(".template-box[data-value='"+data.value+"']").show();
 			  form.render('select'); 
		  });
		  
		  
		  
		  //拖拽上传
		  upload.render({
		    elem: '#upload_image_Id'
		    /* ,url: '/upload/' */
		    ,auto:false
		    ,choose: function(obj){
		      //预读本地文件示例，不支持ie8
		      //console.log(obj)
		      obj.preview(function(index, file, result){
		    
		      //console.log(result,file)
		      files = file
		        $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'" width="200">'); //图片链接（base64）
		      });
		    }
		  });
          
          
          // 保存
          form.on('submit(save)', function(obj) {  
         	 	var data = obj.field;
    			teplateSave(data)
    			return false;
    		});
    		
    		
    	//选择模板保存
    	function teplateSave(obj){
    			var formData = new FormData() 
        		//上传图片
        		if(obj.template == 0){
	   				formData.append('orderId', $("#orderId").val());
	   				formData.append('packageId', $("#packageId").val());
        		}else if(obj.template == 1){
        			formData.append('orderId', obj.orderId);
	   				formData.append('packageId', obj.packageId);
        		}
        		
        		formData.append('userId', ${user.id});
        		formData.append('step', $('#step').val());
        		formData.append('pins', $('#pins').val());
       			formData.append('packageName',obj.packageName);
       			formData.append('sort', obj.sort);
   				formData.append('mobile', obj.mobile);
   				formData.append('template', obj.template);
   				formData.append('file', files);
   				
    		
    		 // 等候加载
    	     x_admin_loading(true);
    	
    		$.ajax({
    				url : '/admin/center/order/save.do',
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
									window.parent.reloadTable(obj);
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
    		}	
    		
        });
    </script>
</body>

</html>