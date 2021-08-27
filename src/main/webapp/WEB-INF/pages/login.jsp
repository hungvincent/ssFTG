<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>松山奉天宮</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/login.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
$(document).ready(function() {
	if("${message}" != ""){
		alert("${message}");
	}
});
</script>
</head>
<body class="main-bg">
<div class="login-container text-c animated flipInX">
	<div>
		<h1 class="logo-badge text-whitesmoke"><span class="fa fa-user-circle"></span></h1>
	</div>
		<p><img src="images/logo.jpg" style="height:30%; width:30%;" /></p>
	<div class="container-content">
		<form class="margin-t">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="帳號" required="">
			</div>
			<div class="form-group">
				<input type="password" class="form-control" placeholder="密碼" required="">
			</div>
			<button type="submit" class="form-button button-l margin-b">登 入</button>

			<a class="text-darkyellow" href="#"><small>忘 記 密 碼</small></a>
		</form>
		<p class="margin-t text-whitesmoke"><small> Your Name &copy; 2021</small> </p>
	</div>
</div>
</body>
</html>