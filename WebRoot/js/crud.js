
/**
 * 删除
 * obj：删除对象
 * url：后台url
 * 
 */
function crup_delete(obj, url,delId) {
	layer.confirm('确认要删除吗？', function(index) {
		$.ajax({
			url : url,
			type : "POST",
			data : {
				"id" : delId
			}, //这个是传给后台的值
			dataType : "json",
			success : function(data) {
				if (data.code == 200) { //这个是从后台取回来的状态值
					obj.del();
					layer.close(index);
					layer.msg(data.msg, {
						icon : 1,
						time : 1000
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
	});
}


/**
 * 批量删除
 * layfilterId  table id
 * url 后台url
 */
function crup_delAll(layfilterId,url) {
	var selectData = layui.table.checkStatus(layfilterId).data;
	if(selectData.length < 1){	
		layer.msg('请选择要删除的数据！', {icon: 2});
		return false;
	}
	layer.confirm('确认要删除吗？', function(index) {
		var array = new Array();
		$.each(selectData,function(i,e){
			array.push(e.id);
		 })
		$.ajax({
			url : url,
			type : "POST",
			data : {"array":JSON.stringify(array)},
			dataType : "json",
			success : function(data) {
				if (data.code == 200) { //这个是从后台取回来的状态值
					layer.close(index);
					layer.msg(data.msg, {
						icon : 1,
						time : 1000
					},function(){
						
						//刷新列表
						window.location.reload();
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
		
	});
		
   }


/**
 * 保存
 * 
 * form  layui中form
 * monitor:监听id
 * url：后台url
 * 
 */
function crup_save(form,monitor, url) {
	//监听提交
	form.on('submit('+monitor+')', function(obj) {
		$.ajax({
			url : url,
			type : "POST",
			data : obj.field, //这个是传给后台的值
			dataType : "json",
			success : function(data) {
				if (data.code == 200) { //这个是从后台取回来的状态值
					layer.msg(data.msg, {icon : 6,time : 500
					}, function() {
						// 获得frame索引
						var index = parent.layer.getFrameIndex(window.name);
						//关闭当前frame
						parent.layer.close(index);
						//刷新列表
						window.parent.location.reload();
					});
					
				} else {
					layer.msg(data.msg, {
						icon : 2,
						time : 500
					});
				}
			},
			error : function(e) {
				console.log(e);
				layer.msg("系统异常，稍后再试!", {
					icon : 2,
					time : 500
				});
			}
		});

		return false;
	});
}