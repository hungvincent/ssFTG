<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setDonTbl(json){
	$('#donTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#donRemark option").remove();
	$("#donRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#donText").val("");
	$("#donPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#donPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#donTotal").val(0);
	
	for (var key in json) {
		$("#donRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#donTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='don_opt' onchange='donopt(this);'></select>&nbsp;&nbsp;<input type='text' class='don_txt' readonly='readonly' style='width:60%;'>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='don_amt' value='0' onchange='sumDonTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	//油香摘要
	$.ajax({ 
		url:'getProDonation',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".don_opt").append($("<option></option>").attr("value", "").text(" - "));
				for(var i=0; i<data.list.length; i++){
					$(".don_opt").append($("<option></option>").attr("value", data.list[i].id).text(data.list[i].summary));
				}
				$(".don_opt").append($("<option></option>").attr("value", 0).text("其他"));
			}
		}, 
		error: function() { 
			alert("getProDonation error...."); 
		} 
	});
}


function donopt(obj){
	if($(obj).val() == 0){
		$(obj).next().attr("readonly", false);
	}else{
		$(obj).next().attr("readonly", true);
		
		$.ajax({ 
			url:'getProDonationById',
			data:{ 
				id: $(obj).val()
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$(obj).next().val(data.don.summary);
					$(obj).closest("tr").children('td').eq(6).find("input").val(data.don.price);
					sumDonTot();
				}
			}, 
			error: function() { 
				alert("getProDonationById error...."); 
			} 
		});
	}
}

function sumDonTot(){
	var tot = 0;
	$(".don_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#donTotal').val(tot);
		}
	});
}

function addDonText(v){
	var str = "";
	if($('#donText').val() != ""){
		str = $('#donText').val() + "," + v;
	}else{
		str = v;
	}
	$('#donText').val(str);
}

//確認
function saveDonOrder(){
	if(confirm("本次交易總金額 $"+$('#donTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".don_txt").each(function() {
			if($(this).val() != ""){
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.age = $(this).closest("tr").children('td').eq(2).text();
				os.summary = $(this).val();
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
		o.total = $("#donTotal").val();
		o.pay = $("#donPay").val();
		o.remark = $("#donText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderDonation',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec1', 'li_1');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderDonation error...."); 
			} 
		});
	}
}
</script>


<!-- 油香 -->			
<div id="sec1" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="donTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:14%;">客戶姓名</th>
				<th style="text-align:center; width:8%;">年齡</th>
				<th style="text-align:center; width:8%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:40%;">摘要</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="donRemark" onchange="addDonText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="donText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="donPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="donTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveDonOrder();">確認</button>
	</div>
</div>