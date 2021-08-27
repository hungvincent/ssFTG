<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setPligrimageTbl(json){
	$('#pilTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#pilRemark option").remove();
	$("#pilRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#pilText").val("");
	$("#pilPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#pilPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#pilTotal").val(0);
	
	
	for (var key in json) {
		$("#pilRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#pilTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' value='"+json[key].solarBirth+"' style='color:black; width:100px;' readonly='readonly' />"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='ad_num' value='"+json[key].adNum+"' autocomplete='off' style='width:100%;'>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='pil_opt' style='width:100%;' onchange='pilopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='pil_amt' value='0' onchange='sumPilTot();' autocomplete='off' style='width:100%;'>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='checkbox' value='Y' />"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='pil_txt' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	//進香
	$.ajax({ 
		url:'getProPilgrimage',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".pil_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".pil_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProPilgrimage error...."); 
		} 
	});
}


function pilopt(obj){
	$.ajax({ 
		url:'getProPilgrimageyBySession',
		data:{ 
			session: $(obj).val().split("-")[0],
			code: $(obj).val().split("-")[1]
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(obj).closest("tr").children('td').eq(5).find("input").val(data.type.price);
				sumPilTot();
			}
		}, 
		error: function() { 
			alert("getProPilgrimageyBySession error...."); 
		} 
	});
}

function sumPilTot(){
	var tot = 0;
	$(".pil_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#pilTotal').val(tot);
		}
	});
}

function addPilText(v){
	var str = "";
	if($('#pilText').val() != ""){
		str = $('#pilText').val() + "," + v;
	}else{
		str = v;
	}
	$('#pilText').val(str);
}

//確認
function savePilgrimageOrder(){
	if(confirm("本次交易總金額 $"+$('#pilTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		var chkField = true;
		$(".pil_amt").each(function() {
			if(!isNaN($(this).val()) && $(this).closest("tr").children('td').eq(4).find("select").val() != ""){
				if($(this).closest("tr").children('td').eq(2).find("input").val() == "" || 
											$(this).closest("tr").children('td').eq(3).find("input").val() == ""){
					chkField = false;
				}
				
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.solarBirth = $(this).closest("tr").children('td').eq(2).find("input").val();
				os.adNum = $(this).closest("tr").children('td').eq(3).find("input").val();
				os.sessions = $(this).closest("tr").children('td').eq(4).find("select").val();
				os.amount = $(this).val();
				os.vegeFood = $(this).closest("tr").children('td').eq(6).find('input').prop("checked")?"Y":"N";
				os.godName = $(this).closest("tr").children('td').eq(7).find("input").val();
				ary.push(os);
				
				if($("#custId").val() == $(this).closest("tr").children('td').eq(0).text()){
					isOk = true;
				}
			}
		});
		
		if(!chkField){
			alert("國曆生日及身份證字號為必填");
			return;
		}
		if(!isOk){
			alert("收據代表人沒有購買商品");
			return;
		}
		
		//主表
		var o = new Object();
		o.custId = $("#custId").val();
		o.groupId = $("#addrId").val();
		o.total = $("#pilTotal").val();
		o.pay = $("#pilPay").val();
		o.remark = $("#pilText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderPilgrimage',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec6', 'li_6');
				}
				alert(data.message);
				$(obj).attr("disabled", false);
			}, 
			error: function() { 
				alert("editOrderPilgrimage error...."); 
				$(obj).attr("disabled", false);
			} 
		});
	}
}
</script>


<!-- 進香 -->			
<div id="sec6" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="pilTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:15%;">客戶姓名</th>
				<th style="text-align:center; width:10%;">國曆生日</th>
				<th style="text-align:center; width:15%;">身份證字號</th>
				<th style="text-align:center; width:20%;">參加</th>
				<th style="text-align:center; width:15%;">金額</th>
				<th style="text-align:center; width:10%;">素食</th>
				<th style="text-align:center; width:15%;">神尊</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="pilRemark" onchange="addPilText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="pilText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="pilPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="pilTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="savePilgrimageOrder();">確認</button>
	</div>
</div>