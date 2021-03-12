<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
   
   <h2 style="text-align: center; margin: 5px">图钉画数量清单
 	 <c:if test="${empty entity.nailCountDetail}"> 
 	<a onclick="generator(${entity.id})"> <button style="float:left;margin-right: 10px" class="layui-btn layui-btn-sm"><i class="layui-icon">&#xe642;</i>清单生成</button></a>
 	 </c:if> 
 	<a href="/admin/center/nailorder/export/${entity.id}.do"> <button style="float:left" class="layui-btn layui-btn-sm"><i class="layui-icon">&#xe67d;</i>下载清单</button></a>
 	
	
	</h2>
	<table class="layui-table";>
		<thead >
			<tr >
				<th colspan="2"></th>
				<th style="text-align: center;">颜色</th>
				<th style="text-align: center;">编号</th>
				<th style="text-align: center;">数量</th>
				<th style="text-align: center;">重量</th>
				<th style="text-align: center;">包数</th>
			</tr>
		</thead>
		<c:forEach items="${nailTotalCount.nailCountDetailMap }" var="item" varStatus="xh">
			<c:choose>
				<c:when test="${xh.index ==0}">
					<tr align="center">
						<td rowspan="4" colspan="2"><img src="${entity.imageUrl }" width="200">
						<br/>${entity.imageName }
						</td>
						<td>
						<c:if test="${not empty item.value.rgb}">
							<button style='background-color:rgb(${item.value.rgb});width: 80px;height: 25px;border: 0px'>
							</button>
						</c:if>
						
						</td>
						<td>${item.value.indexId}</td>
						<td>${item.value.nailNumber}</td>
						<td>${item.value.requreWeight}</td>
						<td>${item.value.requrePieces}</td>
						<!-- <td></td> -->
					</tr>
				</c:when>
				<c:when test="${xh.index %4==0}">
					<tr align="center">
							<c:choose>
								<c:when test="${xh.index == 4}">
									<td rowspan="4" colspan="2">
									<!-- 钉子类型： -->${entity.nailType}<br/>
									<!-- 画框颜色： -->${entity.colorName }<br/>
									<!-- 买家名称： -->${entity.username }
									</td>
								</c:when>
								<c:otherwise>
									 <td rowspan="4" colspan="2" style="border: 0">
									</td> 
								</c:otherwise>
							</c:choose>
						
						<td>
							<c:if test="${not empty item.value.rgb}">
							<button style='background-color:rgb(${item.value.rgb});width: 80px;height: 25px;border: 0px'>
							</button>
							</c:if>
						</td>
						<td>${item.value.indexId}</td>
						<td>${item.value.nailNumber}</td>
						<td>${item.value.requreWeight}</td>
						<td>${item.value.requrePieces}</td>
						<!-- <td></td> -->
					</tr>
				</c:when>
				<c:otherwise>
					<tr align="center">
						<td >
							<c:if test="${not empty item.value.rgb}">
							<button style='background-color:rgb(${item.value.rgb});width: 80px;height: 25px;border: 0px'>
							</button>
						</c:if>
						
						</td>
						<td>${item.value.indexId}</td>
						<td>${item.value.nailNumber}</td>
						<td>${item.value.requreWeight}</td>
						<td>${item.value.requrePieces}</td>
						<!-- <td></td>
						<td></td> -->
					</tr>
				
				</c:otherwise>
			</c:choose>
			
		</c:forEach>
				<tr align="center">
						<td></td>
						<td></td>
						<td>${nailTotalCount.totalNailNumber }</td>
						<td>${nailTotalCount.totalWeight }</td>
						<td>${nailTotalCount.totalrPieces}</td>
						<!-- <td></td>
						<td></td> -->
					</tr>

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