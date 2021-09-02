<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setLightTbl(json){
	$('#ligTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#lightRemark option").remove();
	$("#lightRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#lightText").val("");
	$("#lightPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#lightPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#lightTotal").val(0);
	
	for (var key in json) {
		$("#lightRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#ligTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='light_opt' style='width:100%;' onchange='lightopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='light_amt' value='0' onchange='sumLightTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	//燈種
	$.ajax({ 
		url:'getProLight',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".light_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".light_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProLight error...."); 
		} 
	});
}


function lightopt(obj){
	var val = $(obj).val();
	
	if(val != ""){
		$.ajax({ 
			url:'getProLightBySession',
			data:{ 
				session: val.split("-")[0],
				code: val.split("-")[1]
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$(obj).closest("tr").children('td').eq(6).find("input").val(data.type.price);
					sumLightTot();
				}
			}, 
			error: function() { 
				alert("getProLightBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumLightTot();
	}
}

function sumLightTot(){
	var tot = 0;
	$(".light_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#lightTotal').val(tot);
		}
	});
}

function addLightText(v){
	var str = "";
	if($('#lightText').val() != ""){
		str = $('#lightText').val() + "," + v;
	}else{
		str = v;
	}
	$('#lightText').val(str);
}

//確認
function saveLightOrder(){
	if(confirm("本次交易總金額 $"+$('#lightTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".light_opt").each(function() {
			if($(this).val() != ""){
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.age = $(this).closest("tr").children('td').eq(2).text();
				os.sessions = $(this).val();
				os.amount = $(this).closest("tr").children('td').eq(6).find("input").val();
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
		o.total = $("#lightTotal").val();
		o.pay = $("#lightPay").val();
		o.remark = $("#lightText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderLight',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec3', 'li_3');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderLight error...."); 
			} 
		});
	}
}
</script>


<!-- 點燈 -->			
<div id="sec3" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="ligTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:14%;">客戶姓名</th>
				<th style="text-align:center; width:8%;">年齡</th>
				<th style="text-align:center; width:8%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:40%;">燈種</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="lightRemark" onchange="addLightText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="lightText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="lightPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="lightTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveLightOrder();">確認</button>
	</div>
</div>