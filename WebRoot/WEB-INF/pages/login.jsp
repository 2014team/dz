<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>后台登录</title>
<link rel="stylesheet" type="text/css" href="/css/login.css">
<script src="/lib/layui/layui.js" charset="utf-8"></script>
<script src="/js/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript">
$(function(){
	if (top.location.href != location.href) {
         top.location.href ="/";
     }
})
</script>

</head>

<body>
	<div id="wrapper" class="login-page">
		<div id="login_form" class="form">
			<form class="login-form">
				<h2>管理登录</h2>
				<input type="text" placeholder="用户名" value="admin" id="userName"
					name="userName" /> <input type="password" placeholder="密码"
					id="password" name="password" />
				<button id="login">登 录</button>
			</form>
		</div>
	</div>

	<script src="/js/jquery.min.js"></script>
	<script type="text/javascript">
	
		layui.use('form', function() {
			var form = layui.form;
		});
	
	
		function check_login() {
			var name = $("#userName").val();
			var pass = $("#password").val();
			if (name != "" && pass != "") {
	
				$.ajax({
					url : "/admin/login/submit.do",
					type : "POST",
					async : false,
					data : {
						userName : name,
						password : pass
					},
					dataType : "json",
					success : function(result) {
						if (result.code == 200) {
							window.location.href = "/admin/center/index.do";
						} else {
							alert(result.msg);
							//layer.msg(result.msg);
						}
					},
					error : function(e) {
						console.err(e);
					}
				});
	
	
	
			} else {
				$("#login_form").removeClass('shake_effect');
				setTimeout(function() {
					$("#login_form").addClass('shake_effect')
				}, 1);
			}
		}
	
		$(function() {
			$("#login").click(function() {
				check_login();
				return false;
			})
			$('.message a').click(function() {
				$('form').animate({
					height : 'toggle',
					opacity : 'toggle'
				}, 'slow');
			});
		})
	</script>
</body>
</html>