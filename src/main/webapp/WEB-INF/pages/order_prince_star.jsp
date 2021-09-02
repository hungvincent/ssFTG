<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setPrinceStarTbl(json){
	$('#princeTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#princeRemark option").remove();
	$("#princeRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#princeText").val("");
	$("#princePay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#princePay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#princeTotal").val(0);
	
	for (var key in json) {
		$("#princeRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#princeTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='prince_opt' style='width:100%;' onchange='princeopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='prince_amt' value='0' onchange='sumPrinceTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	$.ajax({ 
		url:'getProPrinceStar',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".prince_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".prince_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProPrinceStar error...."); 
		} 
	});
}


function princeopt(obj){
	var val = $(obj).val();
	
	if(val != ""){
		$.ajax({ 
			url:'getProPrinceStarBySession',
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
					sumPrinceTot();
				}
			}, 
			error: function() { 
				alert("getProPrinceStarBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumPrinceTot();
	}
}

function sumPrinceTot(){
	var tot = 0;
	$(".prince_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#princeTotal').val(tot);
		}
	});
}

function addPrinceText(v){
	var str = "";
	if($('#princeText').val() != ""){
		str = $('#princeText').val() + "," + v;
	}else{
		str = v;
	}
	$('#princeText').val(str);
}

//確認
function savePrinceStarOrder(){
	if(confirm("本次交易總金額 $"+$('#princeTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".prince_amt").each(function() {
			if(!isNaN($(this).val()) && $(this).closest("tr").children('td').eq(5).find("select").val() != ""){
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.age = $(this).closest("tr").children('td').eq(2).text();
				os.sessions = $(this).closest("tr").children('td').eq(5).find("select").val();
				os.amount = $(this).val();
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
		o.total = $("#princeTotal").val();
		o.pay = $("#princePay").val();
		o.remark = $("#princeText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderPrinceStar',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec8', 'li_8');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderPrinceStar error...."); 
			} 
		});
	}
}
</script>


<!-- 五年千歲禮斗 -->			
<div id="sec8" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="princeTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:14%;">客戶姓名</th>
				<th style="text-align:center; width:8%;">年齡</th>
				<th style="text-align:center; width:8%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:40%;">斗別</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="princeRemark" onchange="addPrinceText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="princeText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="princePay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="princeTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="savePrinceStarOrder();">確認</button>
	</div>
</div>