<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
  	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
   <body>
   
   <h2 style="text-align: center; margin: 5px">图钉画数量清单
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

</body>
</html>