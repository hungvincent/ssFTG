<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setLianghuangTbl(json){
	$('#lianTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#lianRemark option").remove();
	$("#lianRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#lianText").val("");
	$("#lianPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#lianPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#lianTotal").val(0);
	
	for (var key in json) {
		$("#lianRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#lianTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='lian_opt' style='width:100%;' onchange='lianopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='lian_amt' value='0' onchange='sumLianTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	$.ajax({ 
		url:'getProLianghuang',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".lian_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".lian_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProLianghuang error...."); 
		} 
	});
}


function lianopt(obj){
	var val = $(obj).val();
	
	if(val != ""){
		$.ajax({ 
			url:'getLianghuangBySession',
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
					sumLianTot();
				}
			}, 
			error: function() { 
				alert("getLianghuangBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumLianTot();
	}
}

function sumLianTot(){
	var tot = 0;
	$(".lian_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#lianTotal').val(tot);
		}
	});
}

function addLiangText(v){
	var str = "";
	if($('#lianText').val() != ""){
		str = $('#lianText').val() + "," + v;
	}else{
		str = v;
	}
	$('#lianText').val(str);
}

//確認
function saveLianghuangOrder(){
	if(confirm("本次交易總金額 $"+$('#lianTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".lian_amt").each(function() {
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
		o.total = $("#lianTotal").val();
		o.pay = $("#lianPay").val();
		o.remark = $("#lianText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderLianghuang',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec12', 'li_12');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderLianghuang error...."); 
			} 
		});
	}
}
</script>


<!-- 梁皇寶懺 -->			
<div id="sec12" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="lianTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:14%;">客戶姓名</th>
				<th style="text-align:center; width:8%;">年齡</th>
				<th style="text-align:center; width:8%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:40%;">參加</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="lianRemark" onchange="addLiangText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="lianText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="lianPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="lianTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveLianghuangOrder();">確認</button>
	</div>
</div>