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
						autocomplete="off" class="layui-input" ${idParam eq -1  ? '':"disabled" } >
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
						value="${order.mobile }"
						lay-verify="required|number|phone" autocomplete="off" maxlength="11"
						class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux">数字</div>
			</div>
			<div class="template-div"></div>

			

			<%-- <div class="layui-form-item">
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
			</div> --%>
			
			<input type="hidden" id="sort" name="sort" value="1"/>


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
					value="${empty order.packageName? ( empty packageName? '':packageName): order.packageName}" lay-verify="required|packageName"
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
		    <label class="layui-form-label"> <span class="x-red">*</span>执行步骤</label>
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn" id="additional_id">选择文件</button>
		      <label id="additional_label" class="layui-form-label" style="float:none;display:inline-block;text-align:left;width:auto;">${order.stepName }</label>
		    </div>
		  </div>
		  
		  <span id="step" name="step" class="layui-textarea" style="display: none">${order.step}</span>
		  
		<div class="layui-form-item">
			<label for="L_repass" class="layui-form-label"> 
			<!-- <span class="x-red">*</span> -->图片
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
				<select  lay-verify="required" id="packageId" name="packageId"  lay-search>
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
	    	
	    	//买家名称显示图纸名称
	    	 $("#userName").on("input",function(e){
		    	 if(!e.delegateTarget.value){
		    	 $("#packageName").val('');
		    	 	return ;
		    	 }
	    	 	$.ajax({
    				url : '/admin/center/order/get/packageName.do',
    				type : "POST",
					cache : false,
					data : {userName:e.delegateTarget.value},
    				dataType : "json",
    				success : function(data) {
    					$("#packageName").val(data.data);
    				},
    				error : function(e) {
    					console.log(e);
    				}
    			});
			     
			  });
			  //电话号码去除空格
			  $("#mobile").on("input",function(e){
	    	 	$("#mobile").val(($("#mobile").val().replace(/\s+/g,"")))
		    	});
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
     	var stepFile;
        layui.use(['form','layer','upload','table'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer
          ,upload = layui.upload
          table = layui.table;
          
          form.on('radio(template)', function(data){
			  
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
		     ,size: 1024 //限制文件大小，单位 KB
		    ,choose: function(obj){
		      //预读本地文件示例，不支持ie8
		      obj.preview(function(index, file, result){
		      files = file
		        $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'" width="200">'); //图片链接（base64）
		      
                    
		      });
		    }
		  });
		  
		  
		   //选完文件后不自动上传
		  upload.render({
		    elem: '#additional_id'
		    ,auto: true
		    ,url:'/admin/center/order/pins'
			,accept: 'file'
			,size: 1024 * 2
		    ,exts: 'txt' 
		    ,done: function(res){
		    debugger
		      console.log(res)
		      if(res && res.infos){
		      	$("#pins").val(res.infos.num_pins)
		      }	
		    
		    }
		   ,choose: function(obj){
		     obj.preview(function(index, file, result){
		     debugger
		      	stepFile = file
		      	if(stepFile){
		      		$("#additional_label").html(stepFile.name);
		      	}
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
        		
        		formData.append('idParam', ${idParam}+"");
        		formData.append('userId', ${user.id}+"");
        		formData.append('step', $("#step").html());
        		formData.append('pins', $('#pins').val());
       			formData.append('packageName',obj.packageName);
       			formData.append('sort', obj.sort);
   				formData.append('mobile', obj.mobile);
   				formData.append('template', obj.template);
   				formData.append('file', files);
   				formData.append('stepFile', stepFile);
   				formData.append('userName', $("#userName").val());
   				
    		
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
									window.parent.reloadTable(obj,${idParam}+"");
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