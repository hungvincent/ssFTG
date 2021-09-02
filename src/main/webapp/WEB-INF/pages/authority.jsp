<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>松山奉天宮</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
table.table-bordered{
    border:1px solid black;
    width:95%;
  }
table.table-bordered > tr > th, td{
    border:1px solid black;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
	
});

function addRow(){
	$('table').append(
        $('<tr></tr>').append(
            $('<td style="text-align:center; vertical-align: middle;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:120px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='password' style='color:black; width:120px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='save(this);'>儲存</button>")
        )
    );
}

function update(obj){
	var $td = $(obj).closest("tr").children();
	
	$.ajax({ 
		url:'getSysadminById',
		data:{ 
			id: $td.eq(0).text()
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$td.eq(0).html(data.admin.id);
				$td.eq(1).html("<input type='text' value='"+data.admin.name+"' style='color:black; width:120px;' />");
				$td.eq(2).html("<input type='password' value='"+data.admin.password+"' style='color:black; width:120px;' />");
				//前檯作業
				if(data.admin.authCount == "Y"){
					$td.eq(3).html("<input type='checkbox' value='Y' checked='checked' />");
				}else{
					$td.eq(3).html("<input type='checkbox' value='Y' />");
				}
				//賽錢箱
				if(data.admin.authOfferingBox == "Y"){
					$td.eq(4).html("<input type='checkbox' value='Y' checked='checked' />");
				}else{
					$td.eq(4).html("<input type='checkbox' value='Y' />");
				}
				//參數設定
				if(data.admin.authParamSet == "Y"){
					$td.eq(5).html("<input type='checkbox' value='Y' checked='checked' />");
				}else{
					$td.eq(5).html("<input type='checkbox' value='Y' />");
				}
				//報表列印
				if(data.admin.authReportPrint == "Y"){
					$td.eq(6).html("<input type='checkbox' value='Y' checked='checked' />");
				}else{
					$td.eq(6).html("<input type='checkbox' value='Y' />");
				}
				//查詢統計
				if(data.admin.authQuery == "Y"){
					$td.eq(7).html("<input type='checkbox' value='Y' checked='checked' />");
				}else{
					$td.eq(7).html("<input type='checkbox' value='Y' />");
				}
				//$td.eq(8).html(data.admin.createDate);
				//$td.eq(9).html(data.admin.updateDate);
				if(data.admin.isActive == "Y"){
					$td.eq(10).html("<input type='checkbox' value='Y' checked='checked' />");
				}else{
					$td.eq(10).html("<input type='checkbox' value='Y' />");
				}
				$td.eq(11).html("<button type='button' class='btn btn-info btn-xs' onclick='save(this);'>儲存</button>");
			}
		}, 
		error: function() { 
			alert("getSysadminById error...."); 
		} 
	});
}

function deleteSys(obj){
	if(confirm('確定刪除?')){
		$.ajax({ 
			url:'deleteSysadmin',
			data:{ 
				id: $(obj).closest("tr").children("td").eq(0).text()
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$(obj).closest("tr").remove();
				}else{
					alert(data.message);
				}
			}, 
			error: function() { 
				alert("deleteSysadmin error...."); 
			} 
		});
	}
}

function save(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == "" || $.trim($td.eq(2).children('input').val()) == ""){
		alert("姓名、密碼為必填");
		return;
	}
	
	var obj = new Object();
	obj.id = $td.eq(0).text();
	obj.name = $td.eq(1).children('input').val();
	obj.password = $td.eq(2).children('input').val();
	obj.authCount = $td.eq(3).children('input').prop("checked")?"Y":"N";
	obj.authOfferingBox = $td.eq(4).children('input').prop("checked")?"Y":"N";
	obj.authParamSet = $td.eq(5).children('input').prop("checked")?"Y":"N";
	obj.authReportPrint = $td.eq(6).children('input').prop("checked")?"Y":"N";
	obj.authQuery = $td.eq(7).children('input').prop("checked")?"Y":"N";
	obj.isActive = $td.eq(10).children('input').prop("checked")?"Y":"N";
	
	$.ajax({ 
		url:'editSysadmin',
		data:{ 
			jsonStr: JSON.stringify(obj)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				window.location.reload();
			}else{
				alert(data.message);
				$(obj).attr("disabled", false);
			}
		}, 
		error: function() { 
			alert("editSysadmin error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>
</head>
<body style="font-size:12px;">
<%@ include file="menu.jsp"%>

<div class="container" style="margin-top:20px;">
<!-- 	<div class="form-inline"> -->
<!-- 		姓名：<input type="text" class="form-control input-sm" />&nbsp;&nbsp; -->
<!-- 		<button type="submit" id="qry" class="btn btn-info btn-xs">查詢</button> -->
<!-- 	</div> -->
	<br>
	<table class="table table-bordered">
		<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
			<th style="text-align:center; vertical-align: middle;">序號</th>
			<th style="text-align:center; vertical-align: middle;">姓名</th>
			<th style="text-align:center; vertical-align: middle;">密碼</th>
			<th style="text-align:center; vertical-align: middle;">前檯作業</th>
			<th style="text-align:center; vertical-align: middle;">賽錢箱</th>
			<th style="text-align:center; vertical-align: middle;">參數設定</th>
			<th style="text-align:center; vertical-align: middle;">報表列印</th>
			<th style="text-align:center; vertical-align: middle;">查詢統計</th>
			<th style="text-align:center; vertical-align: middle;">建立日期</th>
			<th style="text-align:center; vertical-align: middle;">修改日期</th>
			<th style="text-align:center; vertical-align: middle;">啟用</th>
			<th style="text-align:center;">
				<button type="button" class="btn btn-info btn-xs" onclick="addRow();">新增</button>
			</th>
		</tr>
		<c:forEach items="${authList}" var="item">
    	<tr>
    		<td style="text-align:center; vertical-align: middle;">${item.id}</td>
    		<td style="text-align:center; vertical-align: middle;">${item.name}</td>
    		<td style="text-align:center; vertical-align: middle;">${item.password}</td>
    		<td style="text-align:center; vertical-align: middle;"><c:if test='${item.authCount=="Y"}'>V</c:if></td>
    		<td style="text-align:center; vertical-align: middle;"><c:if test='${item.authOfferingBox=="Y"}'>V</c:if></td>
    		<td style="text-align:center; vertical-align: middle;"><c:if test='${item.authParamSet=="Y"}'>V</c:if></td>
    		<td style="text-align:center; vertical-align: middle;"><c:if test='${item.authReportPrint=="Y"}'>V</c:if></td>
    		<td style="text-align:center; vertical-align: middle;"><c:if test='${item.authQuery=="Y"}'>V</c:if></td>
    		<td style="text-align:center; vertical-align: middle;"><fmt:formatDate pattern="yyyy/MM/dd HH:mm" value="${item.createDate}" /></td>
    		<td style="text-align:center; vertical-align: middle;"><fmt:formatDate pattern="yyyy/MM/dd HH:mm" value="${item.updateDate}" /></td>
    		<td style="text-align:center; vertical-align: middle;"><c:if test='${item.isActive=="Y"}'>V</c:if></td>
    		<td style="text-align:center; vertical-align: middle;">
    			<button type="button" class="btn btn-info btn-xs" onclick="update(this);">修改</button>&nbsp;&nbsp;
    			<button type="button" class="btn btn-info btn-xs" onclick="deleteSys(this);">刪除</button>
    		</td>
    	</tr>
    	</c:forEach>
	</table>
</div>
</body>
</html>