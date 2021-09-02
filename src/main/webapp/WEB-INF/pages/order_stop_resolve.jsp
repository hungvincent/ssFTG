<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setStopResolveTbl(json){
	$('#srTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#srSessions option").remove();
	$("#srRemark option").remove();
	$("#srRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#srText").val("");
	$("#srPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#srPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#srTotal").val(0);
	
	for (var key in json) {
		$("#srRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#srTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' checked='checked' value='Y' />"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='stop_amt' value='0' onchange='sumStopTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	//制解場次
	$.ajax({ 
		url:'getProStopResolve',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$("#srSessions").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
					$("#srSessions").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProStopResolve error...."); 
		} 
	});
}


function stopopt(obj){
	//金額歸0
	$('#srTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).children('td').eq(6).find('input').val(0);
    	}
   	});
	
	$.ajax({ 
		url:'getProStopResolveBygetSessions',
		data:{ 
			session: $(obj).val()
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$("#groupPrice").val(data.sr.groupPrice);	//戶報名費
				//個人祭品費用
				$('#srTbl > tbody > tr').each(function(index, tr) {
			    	if(index > 0){
			    		for (var key in data.pm) {
			    			if($(this).children('td').eq(4).text() == key){
			    				$(this).children('td').eq(6).find('input').val(data.pm[key]);
			    			}
						}
			    	}
			   	});
				sumStopTot();
			}
		}, 
		error: function() { 
			alert("getProStopResolveBygetSessions error...."); 
		} 
	});
}

function sumStopTot(){
	var tot = 0;
	$(".stop_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#srTotal').val(tot);
		}
	});
}

function addStopText(v){
	var str = "";
	if($('#srText').val() != ""){
		str = $('#srText').val() + "," + v;
	}else{
		str = v;
	}
	$('#srText').val(str);
}

//確認
function saveStopResolveOrder(){
	if(confirm("本次交易總金額 $"+$('#srTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".stop_amt").each(function() {
			var isOn = $(this).closest("tr").children('td').eq(5).find('input').prop("checked")?"Y":"N";	//參加
			if(!isNaN($(this).val()) && isOn == "Y"){
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.age = $(this).closest("tr").children('td').eq(2).text();
				os.stopResolve = $(this).closest("tr").children('td').eq(4).text();
				os.amount = $(this).val();
				os.sessions = $("#srSessions").val();
				ary.push(os);
				
				if($("#custId").val() == $(this).closest("tr").children('td').eq(0).text()){
					isOk = true;
				}
			}
		});
		
		if(!isOk){
			alert("收據代表人沒有購買商品");
			return;
		}
		
		//主表
		var o = new Object();
		o.custId = $("#custId").val();
		o.groupId = $("#addrId").val();
		o.total = $("#srTotal").val();
		o.pay = $("#srPay").val();
		o.remark = $("#srText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderStopResolve',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec5', 'li_5');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderStopResolve error...."); 
			} 
		});
	}
}
</script>


<!-- 制解 -->			
<div id="sec5" style="text-align:left;">
	<select id="srSessions" onchange="stopopt(this);" style="width:40%; color:red;"></select>&nbsp;&nbsp;
	<b>戶報名費</b>
	<input type="text" id="groupPrice" style="width:15%;" value="0" onblur="checkNum(this);" autocomplete="off">
	<br><br>
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="srTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:30%;">客戶姓名</th>
				<th style="text-align:center; width:10%;">年齡</th>
				<th style="text-align:center; width:10%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:20%;">參加</th>
				<th style="text-align:center; width:15%;">祭品金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="srRemark" onchange="addStopText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="srText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="srPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="srTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveStopResolveOrder();">確認</button>
	</div>
</div>