<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
$(document).ready(function() {

});

function setConTbl(json){
	$('#conTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#conRemark option").remove();
	$("#conRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#conText").val("");
	$("#conPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#conPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#conTotal").val(0);
	
	for (var key in json) {
		$("#conRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#conTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='con_opt' onchange='conopt(this);'></select>&nbsp;&nbsp;<input type='text' class='con_txt' readonly='readonly' style='width:60%;'>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='con_amt' value='0' onchange='sumConTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	//建設摘要
	$.ajax({ 
		url:'getProConstruction',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".con_opt").append($("<option></option>").attr("value", "").text(" - "));
				for(var i=0; i<data.list.length; i++){
					$(".con_opt").append($("<option></option>").attr("value", data.list[i].id).text(data.list[i].summary));
				}
				$(".con_opt").append($("<option></option>").attr("value", 0).text("其他"));
			}
		}, 
		error: function() { 
			alert("getProConstruction error...."); 
		} 
	});
}


function conopt(obj){
	if($(obj).val() == 0){
		$(obj).next().attr("readonly", false);
	}else{
		$(obj).next().attr("readonly", true);
		
		$.ajax({ 
			url:'getProConstructionById',
			data:{ 
				id: $(obj).val()
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$(obj).next().val(data.con.summary);
					$(obj).closest("tr").children('td').eq(6).find("input").val(data.con.price);
					sumConTot();
				}
			}, 
			error: function() { 
				alert("getProConstructionById error...."); 
			} 
		});
	}
}

function sumConTot(){
	var tot = 0;
	$(".con_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#conTotal').val(tot);
		}
	});
}

function addConText(v){
	var str = "";
	if($('#conText').val() != ""){
		str = $('#conText').val() + "," + v;
	}else{
		str = v;
	}
	$('#conText').val(str);
}

//確認
function saveConOrder(){
	if(confirm("本次交易總金額 $"+$('#conTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".con_txt").each(function() {
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
		o.total = $("#conTotal").val();
		o.pay = $("#conPay").val();
		o.remark = $("#conText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderConstruction',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec2', 'li_2');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderConstruction error...."); 
			} 
		});
	}
}
</script>


<!-- 建設 -->			
<div id="sec2" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="conTbl" class="table table-bordered">
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
		<select id="conRemark" onchange="addConText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="conText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="conPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="conTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveConOrder();">確認</button>
	</div>
</div>