<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setJieyuanTbl(json){
	$('#jieTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#jieRemark option").remove();
	$("#jieRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#jieText").val("");
	$("#jiePay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#jiePay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#jieTotal").val(0);
	
	for (var key in json) {
		$("#jieRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#jieTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].lunarYear),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='jie_opt' style='width:100%;' onchange='jieopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='jie_amt' value='0' onchange='sumJieTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	$.ajax({ 
		url:'getProJieyuan',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".jie_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".jie_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProJieyuan error...."); 
		} 
	});
}


function jieopt(obj){
	var val = $(obj).val();
	var lunarYear = $(obj).closest("tr").children('td').eq(4).text();
	
	if(val != ""){
		$.ajax({ 
			url:'getJieyuanBySession',
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
					sumJieTot();
				}
			}, 
			error: function() { 
				alert("getJieyuanBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumJieTot();
	}
}

function sumJieTot(){
	var tot = 0;
	$(".jie_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#jieTotal').val(tot);
		}
	});
}

function addJieText(v){
	var str = "";
	if($('#jieText').val() != ""){
		str = $('#jieText').val() + "," + v;
	}else{
		str = v;
	}
	$('#jieText').val(str);
}

//確認
function saveJieyuanOrder(){
	if(confirm("本次交易總金額 $"+$('#jieTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".jie_amt").each(function() {
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
		o.total = $("#jieTotal").val();
		o.pay = $("#jiePay").val();
		o.remark = $("#jieText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderJieyuan',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec13', 'li_13');
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderJieyuan error...."); 
			} 
		});
	}
}
</script>


<!-- 解冤法會 -->			
<div id="sec13" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="jieTbl" class="table table-bordered">
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
		<select id="jieRemark" onchange="addJieText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="jieText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="jiePay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="jieTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveJieyuanOrder();">確認</button>
	</div>
</div>