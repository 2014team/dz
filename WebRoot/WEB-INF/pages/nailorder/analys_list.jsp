<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div style="padding:5px;line-height:2;">
	${entity.searchCondition}
</div>


<table class="layui-table"; >
	<thead >
		<tr >
			<th style="text-align: center;">颜色</th>
			<th style="text-align: center;">编号</th>
			<th style="text-align: center;">数量</th>
			<th style="text-align: center;">重量</th>
			<th style="text-align: center;">包数</th>
			<th style="text-align: center;">数量平均值</th>
			<th style="text-align: center;">重量平均值</th>
			<th style="text-align: center;">包数平均值</th>
			<th style="text-align: center;">数量占比</th>
			<th style="text-align: center;">重量占比</th>
			<th style="text-align: center;">包数占比</th>
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
					
					<td>${item.value.nailNumberAvg}</td>
					<td>${item.value.requreWeightAvg}</td>
					<td>${item.value.requrePiecesAvg}</td>
					
					<td>${item.value.nailNumberRatio}</td>
					<td>${item.value.requreWeightRatio}</td>
					<td>${item.value.requrePiecesRatio}</td>
				</tr>
			</c:forEach>
</table>

<div style="padding: 40px 0px"; ;>
	<table class="layui-table";>
	<thead >
		<tr >
			<th style="text-align: center;">款式</th>
			<th style="text-align: center;">数量</th>
		</tr>
	</thead>
			<c:forEach items="${nailConfigMap}" var="item" varStatus="xh" >
						
					<td>${item.value.nailType}</td>
					<td>${item.value.total}</td>
				</tr>
			</c:forEach>
</table>

</div>


	
	
	