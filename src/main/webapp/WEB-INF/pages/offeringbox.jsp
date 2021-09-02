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
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="css/main.css" />
<style type="text/css">
#obTbl tr:hover {background-color:yellow; color:#000000;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>
<script src="js/datePicker_TW.js"></script>
<script>
$(document).ready(function() {
	var today = new Date();
	var yy = today.getFullYear()-1911;
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var dd = String(today.getDate()).padStart(2, '0');

    $(".imgDatepic").datepickerTW({
		dateFormat: "yy/mm/dd",
		autoClose: true,
		changeMonth: true,
		changeYear: true,
		showOn: "button",
		buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
		buttonImageOnly: true
	});
    $(".imgDatepic").val(yy+"/"+mm+"/"+dd);
    
    $("#dig").dialog({
    	autoOpen: false,
    	closeOnEscape: false,
	    modal: true,
	    maxWidth: 500,
        maxHeight: 400,
        width: 500,
        height: 400,
	    title: '明細記錄'
    });
    
    var ary = ["賽錢箱", "金紙", "華陀水", "福壽米"];
    for (var v in ary) {
		$('#obdTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(ary[v]),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("0"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updDetail(this);'>修改</button> ")
	        )
	    );
	}
});


function addDetailRow(){
	$('#obdTbl').append(
        $('<tr></tr>').append(
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:100%;' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50%;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveDetail(this);'>儲存</button>")
        )
    );
}


function updDetail(obj){
	var $td = $(obj).closest("tr").children();
	var summary = $td.eq(0).text();
	var amount = $td.eq(1).text();
	
	$td.eq(0).html("<input type='text' style='color:black; width:100%;' value='"+summary+"' autocomplete='off' />");
	$td.eq(1).html("<input type='text' style='color:black; width:50%;' value='"+amount+"' onblur='checkNum(this);' autocomplete='off' />");
    $td.eq(2).html("<button type='button' class='btn btn-info btn-xs' onclick='saveDetail(this);'>儲存</button>");
}


function saveDetail(obj){
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(0).find('input').val()) == "" || $.trim($td.eq(1).find('input').val()) == ""){
		alert("種類、金額為必填");
		return;
	}
	$td.eq(0).html($td.eq(0).children('input').val());
	$td.eq(1).html($td.eq(1).children('input').val());
	$td.eq(2).html("<button type='button' class='btn btn-info btn-xs' onclick='updDetail(this);'>修改</button> ");
	sumTotal();
}

function sumTotal(){
	var tot = 0;
	$('#obdTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0 && $(this).children("td").eq(1).text() != ""){
    		tot += parseInt($(this).children("td").eq(1).text());
    	}
	});
	$('#total').val(tot);
}

function save(){
	var ary = [];
	$('#obdTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		var o = new Object();
    		o.summary = $(this).children("td").eq(0).text();
    		o.amount = $(this).children("td").eq(1).text();
    		if($(this).children("td").eq(1).text() != "0"){
    			ary.push(o);
    		}
    	}
   	});
	
	if(ary.length == 0){
		alert("請填寫點收金額");
		return;
	}
		
	if(confirm("點收日期"+$('.imgDatepic').val() + "\r\n點收總金額 $"+$('#total').val())){
	 	$.ajax({ 
	 		url:'editOfferingBox',
	 		data:{ 
	 			offeringBoxId: 0,
 				checkDate: $('.imgDatepic').val(),
 				checkName: $('#checkName').val(),
 				total: $("#total").val(),
	 			jsonStr: JSON.stringify(ary)
	 		}, 
	 		type:'post', 
	 		cache:false, 
	 		dataType:'json', 
	 		success:function(data) { 
	 			if(data.status == "success"){ 
	 				window.location.reload();
	 			}
	 			alert(data.message);
	 		}, 
	 		error: function() { 
	 			alert("editOfferingBox error...."); 
	 		} 
	 	});
	}
}

function checkNum(obj){
	var val = $.trim($(obj).val());
	if (isNaN(val)) {
		alert("請輸入數字")
		$(obj).val(0);
	}
}

function updateDetail(obj){
	$("#offeringBoxId").val($(obj).closest("tr").children('td').eq(0).text());
	$('#digTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	$.ajax({ 
		url:'queryDetailByBoxId',
		data:{ 
			id: $("#offeringBoxId").val()
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$('#dig_checkName').html(data.box.checkName);
				$('#dig_checkDate').html(data.box.checkDate);
				$('#dig_total').html(data.box.totalAmount);
				
				for (var key in data.list) {
					$('#digTbl').append(
				        $('<tr style="border:1px solid black; line-height:40px;"></tr>').append(
				            //$('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(data.list[key].id),
				            $('<td style="text-align:center; vertical-align: middle;"></td>').html(data.list[key].summary),
				            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='dig_amt' value='"+data.list[key].amount+"' onblur='checkNum(this);' onchange='sumDigTotal();' autocomplete='off' style='width:60%;'>")
				        )
				    );
				}
			}
			$("#dig").dialog("open");
		}, 
		error: function() { 
			alert("queryDetailByBoxId error...."); 
		} 
	});
}

function sumDigTotal(){
	var tot = 0;
	$('.dig_amt').each(function(index, tr) {
    	if($(this).val() != ""){
    		tot += parseInt($(this).val());
    	}
	});
	$('#dig_total').html(tot);
}

function saveUpdate(){
	var ary = [];
	$('#digTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		var o = new Object();
    		o.summary = $(this).children("td").eq(0).text();
    		o.amount = $(this).children("td").eq(1).find("input").val();
   			ary.push(o);
    	}
   	});
		
	if(confirm("點收總金額 $"+$('#dig_total').text())){
	 	$.ajax({ 
	 		url:'editOfferingBox',
	 		data:{ 
	 			offeringBoxId: $("#offeringBoxId").val(),
 				checkDate: "",
 				checkName: "",
 				total: $("#dig_total").text(),
	 			jsonStr: JSON.stringify(ary)
	 		}, 
	 		type:'post', 
	 		cache:false, 
	 		dataType:'json', 
	 		success:function(data) { 
	 			if(data.status == "success"){ 
	 				window.location.reload();
	 			}
	 			alert(data.message);
	 		}, 
	 		error: function() { 
	 			alert("editOfferingBox error...."); 
	 		} 
	 	});
	}
}
</script>
</head>
<body style="font-size:12px;">
<%@ include file="menu.jsp"%>
<div style="margin-top:20px;" align="center">
<table style="width:65%; border: 0px solid red;">
<tr>
	<td align="center" style="width:100%;">
		<div style="border: 1px solid #FFF; width:1200px;height:750px; padding:10px;">
		<table style="width:100%; border:0px solid red;">
			<tr>
				<td>
					<span style="font-size:20px; font-weight:bold;">新增項目</span>
				</td>
				<td style="width:4%;">&nbsp;</td>
				<td>
					<span style="font-size:20px; font-weight:bold;">明細記錄</span>
				</td>
			</tr>
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr>
				<td style="width:48%;">
					<div style="height:600px; overflow-y:scroll;">
						<table id="obdTbl" class="table table-bordered">
						<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
							<th style="text-align:center; width: 50%;">種類</th>
							<th style="text-align:center; width: 30%;">金額</th>
							<th style="text-align:center; width: 20%;">
								<button type="button" class="btn btn-info btn-xs" onclick="addDetailRow();">新增</button>
							</th>
						</tr>
						</table>
						<div class="form-inline">
							<b>點收人</b>
							<input type='text' id="checkName" style='color:black; width:25%;' />&nbsp;&nbsp;
							<b>點收日期</b>
							<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />&nbsp;&nbsp;
							<b>總金額</b>
							<input type="text" id="total" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;
							<button type="button" class="btn btn-info btn-xs" onclick="save();">確認</button>
						</div>
					</div>
				</td>
				<td style="width:2%;">&nbsp;</td>
				<td style="width:50%;">
					<div style="height:600px; overflow-y:scroll;">
						<table id="obTbl" class="table table-bordered">
						<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
							<th style="text-align:center;">編號</th>
							<th style="text-align:center;">日期</th>
							<th style="text-align:center;">總金額</th>
							<th style="text-align:center;">經辦人</th>
							<th style="text-align:center;">&nbsp;</th>
						</tr>
						<c:forEach items="${obs}" var="item">
				    	<tr>
				    		<td style="display:none;">${item.id}</td>
				    		<td style="text-align:center; vertical-align: middle;">${item.receiptNo}</td>
				    		<td style="text-align:center; vertical-align: middle;">${item.checkDate}</td>
				    		<td style="text-align:center; vertical-align: middle;">${item.totalAmount}</td>
				    		<td style="text-align:center; vertical-align: middle;">${item.checkName}</td>
				    		<td style="text-align:center; vertical-align: middle;">
				    			<button type="button" class="btn btn-info btn-xs" onclick="updateDetail(this);">修改</button>&nbsp;&nbsp;
				    		</td>
				    	</tr>
				    	</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</table>
		</div>
	</td>
</tr>
</table>
</div>


<div id="dig">
	<input type="hidden" style="color:black;" id="offeringBoxId" value="0">
	<table id="digTbl" style="border:1px solid black; width:100%;">
		<tr style="line-height:35px; text-align:center; background-color:#D3D3D3; color:#333; font-weight:bold;">
			<th style="text-align:center; width: 50%;">種類</th>
			<th style="text-align:center; width: 30%;">金額</th>
		</tr>
	</table>
	<br><br>
	<table style="border:0px solid black; width:100%;">
		<tr>
			<td><b>點收人：<span id="dig_checkName"></span></b></td>
			<td><b>點收日期：<span id="dig_checkDate"></span></b></td>
			<td><b>總金額：<span id="dig_total"></span></b></td>
			<td>
				<button type="button" class="btn btn-info btn-xs" onclick="saveUpdate()();">確認</button>
			</td>
		</tr>
	</table>
</div>
</body>
</html>