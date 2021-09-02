<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
$(document).ready(function() {
    $(".imgDate").datepickerTW({
		dateFormat: "yy/mm/dd",
		autoClose: true,
		changeMonth: true,
		changeYear: true,
		showOn: "button",
		buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
		buttonImageOnly: true,
		minDate: new Date()
	});
});

function setParams(jsonMap){
	if(jsonMap['four'] != null){
		$("input[name='four'][value='"+jsonMap['four'].value+"']").prop("checked", true);
	}
	//$("input[name='mackUp'][value='"+jsonMap['mackUp'].value+"']").prop("checked", true);
	if(jsonMap['endDate'] != null){
		$('#endDate').val(jsonMap['endDate'].value);
	}
	if(jsonMap['startDate'] != null){
		$('#startDate').val(jsonMap['startDate'].value);
	}
	if(jsonMap['chairman'] != null){
		$('#chairman').val(jsonMap['chairman'].value);
	}
}

function saveParams(){
	var ary = [];
	var o = new Object();
	o.type = "four";
	o.value = $('input[name=four]:checked').val();
	ary.push(o);
	
	var o2 = new Object();
	o2.type = "endDate";
	o2.value = $('#endDate').val();
	ary.push(o2);
	
	var o3 = new Object();
	o3.type = "startDate";
	o3.value = $('#startDate').val();
	ary.push(o3);
	
	var o4 = new Object();
	o4.type = "chairman";
	o4.value = $('#chairman').val();
	ary.push(o4);
	
	$.ajax({ 
		url:'editSysparams',
		data:{
			jsonStr: JSON.stringify(ary)
		},
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){
				alert(data.message);
			}
		}, 
		error: function() { 
			alert("editSysparams error...."); 
		} 
	});
}
</script>


<!-- 系統參數 -->			
<div id="sec16" style="text-align:left;">
	<div class="form-inline" style="margin-right:200px; margin-left:30px;">
		<b>流水號4改A</b>&nbsp;&nbsp;
		<input type="radio" id="FAY" name="four" value="Y">&nbsp;<label for="FAY">開啟</label>&nbsp;
		<input type="radio" id="FAN" name="four" value="N">&nbsp;<label for="FAN">停用</label><br><br>
<!-- 		<b>流水號空號自動遞補</b>&nbsp;&nbsp; -->
<!-- 		<input type="radio" id="serNoY" name="mackUp" value="Y">&nbsp;<label for="serNoY">開啟</label>&nbsp; -->
<!-- 		<input type="radio" id="serNoN" name="mackUp" value="N">&nbsp;<label for="serNoN">停用</label><br><br> -->
		<b>當年到期日</b>&nbsp;&nbsp;
		<input type='text' class='imgDate' id="endDate" style='color:black; width:100px;' readonly='readonly' /><br><br>
		<b>新年跨年日</b>&nbsp;&nbsp;
		<input type='text' class='imgDate' id="startDate" style='color:black; width:100px;' readonly='readonly' /><br><br>
		<b>主任委員</b>&nbsp;&nbsp;
		<input type='text' id="chairman" style='color:black; width:100px;'autocomplete="off"/><br><br>
		<button type='button' class='btn btn-info btn-xs' onclick='saveParams(this);'>儲存</button>
	</div>
</div>