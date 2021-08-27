<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setBucaikuTbl(json){
	$('#buTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#buRemark option").remove();
	$("#buRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#buText").val("");
	$("#buPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#buPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#buTotal").val(0);
	
	for (var key in json) {
		$("#buRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#buTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].lunarYear),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='bu_opt' style='width:100%;' onchange='buopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='bu_amt' value='0' onchange='sumBuTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	$.ajax({ 
		url:'getProBucaiku',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".bu_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".bu_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProBucaiku error...."); 
		} 
	});
}


function buopt(obj){
	var val = $(obj).val();
	var lunarYear = $(obj).closest("tr").children('td').eq(4).text();
	
	if(val != ""){
		$.ajax({ 
			url:'getBucaikuBySession',
			data:{ 
				session: val,
				lunarYear: lunarYear
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$(obj).closest("tr").children('td').eq(6).find("input").val(data.type.price);
					sumBuTot();
				}
			}, 
			error: function() { 
				alert("getBucaikuBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumBuTot();
	}
}

function sumBuTot(){
	var tot = 0;
	$(".bu_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#buTotal').val(tot);
		}
	});
}

function addBuText(v){
	var str = "";
	if($('#buText').val() != ""){
		str = $('#buText').val() + "," + v;
	}else{
		str = v;
	}
	$('#buText').val(str);
}

//確認
function saveBucaikuOrder(){
	if(confirm("本次交易總金額 $"+$('#buTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".bu_amt").each(function() {
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
		o.total = $("#buTotal").val();
		o.pay = $("#buPay").val();
		o.remark = $("#buText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderBucaiku',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec14', 'li_14');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderBucaiku error...."); 
			} 
		});
	}
}
</script>


<!-- 補財庫 -->			
<div id="sec14" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="buTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:14%;">客戶姓名</th>
				<th style="text-align:center; width:8%;">年齡</th>
				<th style="text-align:center; width:8%;">性別</th>
				<th style="text-align:center; width:15%;">歲次</th>
				<th style="text-align:center; width:40%;">場次</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="buRemark" onchange="addBuText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="buText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="buPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="buTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveBucaikuOrder();">確認</button>
	</div>
</div>