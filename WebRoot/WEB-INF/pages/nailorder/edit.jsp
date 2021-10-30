<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@include file="/WEB-INF/pages/common/head_layui.jsp"%>

</head>

<body>
	<div class="x-body">
	
		<form class="layui-form" >
			<input type="hidden" id="id" name="id" value="${entity.id }" />
		  
		<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>买家名称
				</label>
				<div class="layui-input-inline">
					<input type="text" id="username" name="username"
						value="${entity.username}"   lay-verify="required"
						autocomplete="off" class="layui-input" maxlength="50">
				</div>
				<div class="layui-form-mid layui-word-aux">建议50字符以内</div>
			</div>
	
		
		<div class="layui-form-item">
				<label class="layui-form-label"><!-- <span class="x-red">*</span> -->图钉类型</label>
				<div class="layui-input-block">
					<c:forEach items="${nailconfigList }" var="item">
					<c:choose>
						<c:when test="${not empty entity and entity.nailConfigId eq item.id}">
							<input type="radio"  name="nailConfigId" value="${item.id}" title="${item.nailType}"  checked>	
						</c:when>
						<c:otherwise>
							<input type="radio" name="nailConfigId" value="${item.id}" title="${item.nailType}" > 
						</c:otherwise>
					</c:choose>
					</c:forEach>
					
				</div>
			</div>	
		<div class="layui-form-item">
				<label class="layui-form-label"><!-- <span class="x-red">*</span> -->画框颜色</label>
				<div class="layui-input-block">
					<c:forEach items="${nailPictureFrameList }" var="item">
					<c:choose>
						<c:when test="${not empty entity and entity.nailPictureFrameId eq item.id}">
							<input type="radio"  name="nailPictureFrameId" value="${item.id}" title="${item.colorName}"  checked>	
						</c:when>
						<c:otherwise>
							<input type="radio" name="nailPictureFrameId" value="${item.id}" title="${item.colorName}" > 
						</c:otherwise>
					</c:choose>
					</c:forEach>
					
				</div>
			</div>	
			
			
			
		<div class="layui-form-item">
			<label class="layui-form-label">画框尺寸</label>
			<div class="layui-input-inline">
				<select  id="nailPictureFrameStockId" name="nailPictureFrameStockId"  lay-search>
			      	<option value=""></option>
			      	<c:forEach items="${nailPictureframeStockList }" var="item">
			      		<c:choose>
			      			<c:when test="${entity.nailPictureFrameStockId eq item.id}">
			      				<option value="${item.id }" selected="selected">${item.colorName }</option>
			      			</c:when>
			      			<c:otherwise>
			      				<option value="${item.id }">${item.colorName }</option>
			      			</c:otherwise>
			      		</c:choose>
			      	</c:forEach>
		      </select>
			</div>
		</div>	
			
			
		<%-- 
		<div class="layui-form-item">
				<label class="layui-form-label"><!-- <span class="x-red">*</span> -->画框尺寸</label>
				<div class="layui-input-block">
					<c:forEach items="${nailPictureframeStockList }" var="item">
					<c:choose>
						<c:when test="${not empty entity and entity.nailWeightStockZiseId eq item.id}">
							<input type="radio"  name="nailPictureFrameId" value="${item.id}" title="${item.colorName}"  checked>	
						</c:when>
						<c:otherwise>
							<input type="radio" name="nailPictureFrameId" value="${item.id}" title="${item.colorName}" > 
						</c:otherwise>
					</c:choose>
					</c:forEach>
					
				</div>
			</div>  --%>
		
		  
		  <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <!-- <span
					class="x-red">*</span> -->手机号码
				</label>
				<div class="layui-input-inline">
					<input type="text" id="mobile" name="mobile"
						value="${empty entity.mobile ?'' : entity.mobile}"   
						autocomplete="off" class="layui-input" maxlength="15">
				</div>
				<div class="layui-form-mid layui-word-aux">数字</div>
			</div>
			
			
			
			
			
			<div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                <span class="x-red">*</span> GIF图
              </label>
              <div class="layui-input-inline">
                 <div class="layui-upload-drag" id="upload_image_Id">
				  <i class="layui-icon">
				  </i>
				  <p>点击上传，或将文件拖拽到此处</p>
				</div>
          </div>
          
          <c:if test="${not empty entity.comefrom and entity.comefrom eq 1}">
	          <div class="layui-form-mid layui-word-aux">来源H5自动上传,不让修改GIF图</div>
          </c:if>
          
          <input type="hidden" id="imageUrl" name="imageUrl" value="${entity.imageUrl }"></input>
          </div>
          
          
          <div class="layui-form-item">
				<label for="L_pass" class="layui-form-label"> <span
					class="x-red">*</span>图纸名称
				</label>
				<div class="layui-input-inline">
					<input type="text" id="imageName" name="imageName" readonly="readonly"
						value="${entity.imageName}"   lay-verify="required"
						autocomplete="off" class="layui-input" maxlength="100">
				</div>
				<div class="layui-form-mid layui-word-aux">此值自动获取GIF图片名,创建之后不可修改,具有唯一性(建议100字符以内)。</div>
			</div>
			
			
			
			<div class="layui-form-item">
			<label class="layui-form-label">图纸款式</label>
			<div class="layui-input-inline">
				<select  id="nailDrawingStockId" name="nailDrawingStockId"  lay-search>
			      	<option value=""></option>
			      	<c:forEach items="${nailDrawingStockServiceList }" var="item">
			      		<c:choose>
			      			<c:when test="${entity.nailDrawingStockId eq item.id}">
			      				<option value="${item.id }" selected="selected">${item.style }</option>
			      			</c:when>
			      			<c:otherwise>
			      				<option value="${item.id }">${item.style }</option>
			      			</c:otherwise>
			      		</c:choose>
			      	</c:forEach>
		      </select>
			</div>
		</div>
			

			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label"> </label>
				<button class="layui-btn" lay-filter="saveOrDownload" lay-submit="">保存并下载图钉清单</button>
				<button class="layui-btn" lay-filter="save" lay-submit="">仅保存</button>
			</div>
			
			  <div>
			 
			 
            
            
        </div>
			
		</form>
	</div>
	
	<script>
	
	
	
	
	
            
             //将base64转换为文件
   		 function dataURLtoFile (dataurl, filename) { 
	    	var arr = dataurl.split(','),
	        mime = arr[0].match(/:(.*?);/)[1],
	        bstr = atob(arr[1]),
	        n = bstr.length,
	        u8arr = new Uint8Array(n);
		    while (n--) {
		        u8arr[n] = bstr.charCodeAt(n);
		    }
	    	return new File([u8arr], filename, { type: mime });
            
		}
	
	 $(function(){
	    	var imageUrl = '${entity.imageUrl}';
	    	if(imageUrl){
	    	 $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+imageUrl+'">'); //图片链接（base64）
	    	}
	    	
	    	 $("#mobile").on("input",function(e){
	    	 	$("#mobile").val(($("#mobile").val().replace(/\s+/g,"")))
		    	});
	    	
	    });
    
 	    
 	    var files;
        layui.use(['form','layer','upload'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer
          ,upload = layui.upload;
          
           //自定义验证规则
		  form.verify({
		 	 nailConfigId_verify: function(value, item){ //value：表单的值、item：表单的DOM对象
		  		var verifyName = $(item).attr('name'),
                        verifyType = $(item).attr('type'),
                        formElem = $(item).parents('.layui-form'),
                        verifyElem = formElem.find('input[name=' + verifyName +']'),
                        isTrue = verifyElem.is(':checked'),
                        focusElem = verifyElem.next().find('i.layui-icon');
                    if (!isTrue || !value) {
                        focusElem.css(verifyType == 'radio' ? {
                            "color": "#FF5722"} : {"border-color": "#FF5722"});
                        //对非输入框设置焦点
                        focusElem.first().attr("tabIndex", "1").css("outline", "0")
                            .blur(function () {
                                focusElem.css(verifyType == 'radio' ? {
                                    "color": ""
                                } : {
                                    "border-color": ""
                                });
                            }).focus();
                        return '必填项不能为空';
                    }
		  	},
		  	 nailPictureFrameId_verify: function(value, item){ //value：表单的值、item：表单的DOM对象
		  		var verifyName = $(item).attr('name'),
                        verifyType = $(item).attr('type'),
                        formElem = $(item).parents('.layui-form'),
                        verifyElem = formElem.find('input[name=' + verifyName +']'),
                        isTrue = verifyElem.is(':checked'),
                        focusElem = verifyElem.next().find('i.layui-icon');
                    if (!isTrue || !value) {
                        focusElem.css(verifyType == 'radio' ? {
                            "color": "#FF5722"} : {"border-color": "#FF5722"});
                        //对非输入框设置焦点
                        focusElem.first().attr("tabIndex", "1").css("outline", "0")
                            .blur(function () {
                                focusElem.css(verifyType == 'radio' ? {
                                    "color": ""
                                } : {
                                    "border-color": ""
                                });
                            }).focus();
                        return '必填项不能为空';
                    }
		  	}
		  
		}); 
		
		var comefrom =${entity.comefrom}+'';
        
        if(comefrom != 1){
          
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
			      
			      
			      if(file && file.name && file.name.lastIndexOf(".") != -1){
			      	var fileNam = file.name.substring(0,file.name.lastIndexOf("."));
			      	$("#imageName").val(fileNam);
			      	$("#username").val(fileNam);
			      }
			      
			      files = file
			        $('.layui-upload-drag').html('<img class="layui-upload-img" src="'+result+'">'); //图片链接（base64）
			     
			       
			     
			      });
			    }
			  });
		  }
		  

		   
		 
		   
            
            
		  
        
          // 保存
          form.on('submit(save)', function(obj) {
          
          
          	data = JSON.parse(JSON.stringify(obj.field));
          	var formData = new FormData() 
   			formData.append('id', $('#id').val());
   			formData.append('username', data.username);
   			formData.append('nailConfigId',data.nailConfigId?data.nailConfigId:0);
   			formData.append('nailPictureFrameId',data.nailPictureFrameId?data.nailPictureFrameId:0);
   			formData.append('imageName',data.imageName);
   			formData.append('mobile', data.mobile);
   			formData.append('imageUrl', data.imageUrl);
   			formData.append('nailDrawingStockId', data.nailDrawingStockId?data.nailDrawingStockId:0);
   			formData.append('step', data.step);
   			formData.append('file', files);
   			formData.append('nailPictureFrameStockId', data.nailPictureFrameStockId?data.nailPictureFrameStockId:0);
   			
   			
          	
             //加载动画
				/* var loading = layer.load(0, {
		            shade: false,
		        }); */
		        
	        	//加载动画
		   		var loading = layer.load(2, { //icon支持传入0-2
		   		    shade: [0.5, 'gray'], //0.5透明度的灰色背景
		   		    content: '保存并下载中,请稍等操作...',
		   		    success: function (layero) {
		   		        layero.find('.layui-layer-content').css({
		   		            'padding-top': '39px',
		   		            'width': '60px'
		   		        });
		   		    }
		   		});
		   		
		   		var url = '/admin/center/nailorder/save.do';
   		
   				if($('#id').val() && $('#id').val() > 0){
   					url = '/admin/center/nailorder/update.do';
   				}
   				
   		
    			$.ajax({
    				url : url,
    				type : "POST",
    				data :formData,
    				processData : false, // 使数据不做处理
        			contentType : false, // 不要设置Content-Type请求头
    				dataType : "json",
    				success : function(resp) {
	    				//关闭动画
					layer.close(loading);
				
    						if (resp.code == 200) { //这个是从后台取回来的状态值
								layer.msg(resp.msg, {icon : 6,time : 1500
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
									time : 1500
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
    		
          // 保存
          form.on('submit(saveOrDownload)', function(obj) {
          
          
          	data = JSON.parse(JSON.stringify(obj.field));
          	
          	   if(!data.nailConfigId || !data.nailPictureFrameId){
          		layer.msg('请选择仅保存按钮', {
									icon : 2,
									time : 1500
								});
					return false;		
          	}
          	
          	
          	var formData = new FormData() 
   			formData.append('id', $('#id').val());
   			formData.append('username', data.username);
   			formData.append('nailConfigId',data.nailConfigId?data.nailConfigId:0);
   			formData.append('nailPictureFrameId',data.nailPictureFrameId?data.nailPictureFrameId:0);
   			formData.append('nailPictureFrameStockId', data.nailPictureFrameStockId?data.nailPictureFrameStockId:0);
   			formData.append('imageName',data.imageName);
   			formData.append('mobile', data.mobile);
   			formData.append('imageUrl', data.imageUrl);
   			formData.append('nailDrawingStockId', data.nailDrawingStockId?data.nailDrawingStockId:0);
   			formData.append('step', data.step);
   			formData.append('file', files);
   			//var resultImage = dataURLtoFile(newBase64, Date.now() + '.gif');
   			//formData.append('resultImageFile', resultImage);
   			
             //加载动画
				/* var loading = layer.load(0, {
		            shade: false,
		        }); */
		        
	        	//加载动画
		   		var loading = layer.load(2, { //icon支持传入0-2
		   		    shade: [0.5, 'gray'], //0.5透明度的灰色背景
		   		    content: '保存并下载中,请稍等操作...',
		   		    success: function (layero) {
		   		        layero.find('.layui-layer-content').css({
		   		            'padding-top': '39px',
		   		            'width': '60px'
		   		        });
		   		    }
		   		});
		   		
		   		var url = '/admin/center/nailorder/save.do';
   		
   				if($('#id').val() && $('#id').val() > 0){
   					url = '/admin/center/nailorder/update.do';
   				}
   				
   		
    			$.ajax({
    				url : url,
    				type : "POST",
    				data :formData,
    				processData : false, // 使数据不做处理
        			contentType : false, // 不要设置Content-Type请求头
    				dataType : "json",
    				success : function(resp) {
	    				//关闭动画
					layer.close(loading);
				
    						if (resp.code == 200) { //这个是从后台取回来的状态值
								layer.msg(resp.msg, {icon : 6,time : 1500
								},function(){
								// 获得frame索引
									var index = parent.layer.getFrameIndex(window.name);
									//关闭当前frame
									parent.layer.close(index);
									//刷新列表
									window.parent.reloadTable(data.id,resp);
									
								});
								
							} else {
								layer.msg(resp.msg, {
									icon : 2,
									time : 1500
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