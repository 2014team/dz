<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
   
   <h2 style="text-align: center; margin: 5px">
 		图钉类型
		<div class="layui-inline">
	   		 <select id="searchKey" name="searchKey" lay-search>
                <option value="">全部</option>
                 	<c:forEach items="${nailconfigList }" var="item">
                 	<option value="${item.id }" >${item.nailType }</option>
                 	</c:forEach>
            </select>
	  </div>
	
	</h2>
	
	
	
	<table class="layui-table";>
		<thead >
			<tr >
				<th style="text-align: center;">颜色</th>
				<th style="text-align: center;">编号</th>
				<th style="text-align: center;">数量</th>
				<th style="text-align: center;">重量</th>
				<th style="text-align: center;">包数</th>
			</tr>
		</thead>
				<c:forEach items="${analysMap}" var="item" varStatus="xh" >
					
					<c:choose>
						<c:when test="${!xh.last}">
							<td><button style='background-color:rgb(${item.value.rgb});width: 80px;height: 25px;border: 0px'>
							</button></td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
							
						<td>${item.value.indexId}</td>
						<td>${item.value.nailNumber}</td>
						<td>${item.value.requreWeight}</td>
						<td>${item.value.requrePieces}</td>
					</tr>
				</c:forEach>
	</table>
	
	
	
	<script>
	
 	    
        layui.use(['form','layer','upload'], function(){
           $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer
          ,upload = layui.upload;
          
          
		}); 
		
       
    	function generator(id){
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
   		
    			$.ajax({
    				url : '/admin/center/nailorder/generator.do',
    				type : "POST",
    				data :{id:id},
    				dataType : "json",
    				success : function(resp) {
	    				//关闭动画
					layer.close(loading);
				
    						if (resp.code == 200) { //这个是从后台取回来的状态值
								layer.msg(resp.msg, {icon : 6,time : 1500
								},function(){
								window.location.href="/admin/center/nailorder/detail/"+id+".do"
									
								});
								
							} else {
								layer.msg(resp.msg, {
									icon : 2,
									time : 1500
								});
							}
    					},
    				error : function(e) {
    					console.info(e);
    					layer.msg("系统异常，稍后再试!", {
    						icon : 2,
    						time : 1000
    					});
    				}
    			});
    }
          
    </script>

</body>
</html>