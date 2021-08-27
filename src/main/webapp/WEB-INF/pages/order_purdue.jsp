<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setPurdueTbl(json){
	$('#purTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#purRemark option").remove();
	$("#purRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#purText").val("");
	$("#purPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#purPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#purTotal").val(0);
	
	for (var key in json) {
		$("#purRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#purTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='pur_opt' style='width:100%;' onchange='puropt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='pur_amt' value='0' onchange='sumPurTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	$.ajax({ 
		url:'getProPurdue',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".pur_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".pur_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProPurdue error...."); 
		} 
	});
}


function puropt(obj){
	var val = $(obj).val();
	
	if(val != ""){
		$.ajax({ 
			url:'getPurdueBySession',
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
					sumPurTot();
				}
			}, 
			error: function() { 
				alert("getPurdueBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumPurTot();
	}
}

function sumPurTot(){
	var tot = 0;
	$(".pur_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#purTotal').val(tot);
		}
	});
}

function addPurdueText(v){
	var str = "";
	if($('#purText').val() != ""){
		str = $('#purText').val() + "," + v;
	}else{
		str = v;
	}
	$('#purText').val(str);
}

//確認
function savePurdueOrder(){
	if(confirm("本次交易總金額 $"+$('#purTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".pur_amt").each(function() {
			if(!isNaN($(this).val()) && parseInt($(this).val()) != 0){
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
		o.total = $("#purTotal").val();
		o.pay = $("#purPay").val();
		o.remark = $("#purText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderPurdue',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec9', 'li_9');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderPurdue error...."); 
			} 
		});
	}
}
</script>


<!-- 普渡 -->			
<div id="sec9" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="purTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:14%;">客戶姓名</th>
				<th style="text-align:center; width:8%;">年齡</th>
				<th style="text-align:center; width:8%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:40%;">種類</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="purRemark" onchange="addPurdueText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="purText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="purPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="purTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="savePurdueOrder();">確認</button>
	</div>
</div>