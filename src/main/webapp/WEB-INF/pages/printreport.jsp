<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>松山奉天宮</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
	
});

function checkNum(obj){
	var val = $.trim($(obj).val());
	if (isNaN(val)) {
		alert("請輸入數字")
		$(obj).val(0);
	}
}
</script>
</head>
<body>
<%@ include file="menu.jsp"%>

<h1 class="page_title">Side Menu Animation</h1>
<h4 class="what_to_do">(click the menu icon)</h4>


</body>
</html>