<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setGodStarTbl(json){
	$('#gsTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#godRemark option").remove();
	$("#godRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#godText").val("");
	$("#godPay option").remove();
	for (var i=0; i<PAY.length; i++) {
		$("#godPay").append($("<option></option>").attr("value", PAY[i]).text(PAY[i]));
	}
	$("#godTotal").val(0);
	
	for (var key in json) {
		$("#godRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#gsTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='god_opt' style='width:100%;' onchange='godopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='god_amt' value='0' onchange='sumGodTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	//斗別
	$.ajax({ 
		url:'getProGodStar',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".god_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".god_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProGodStar error...."); 
		} 
	});
}


function godopt(obj){
	var val = $(obj).val();
	
	if(val != ""){
		$.ajax({ 
			url:'getProGodStarBySession',
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
					sumGodTot();
				}
			}, 
			error: function() { 
				alert("getProGodStarBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumGodTot();
	}
}

function sumGodTot(){
	var tot = 0;
	$(".god_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#godTotal').val(tot);
		}
	});
}

function addGodText(v){
	var str = "";
	if($('#godText').val() != ""){
		str = $('#godText').val() + "," + v;
	}else{
		str = v;
	}
	$('#godText').val(str);
}

//確認
function saveGodStarOrder(){
	if(confirm("本次交易總金額 $"+$('#godTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".god_amt").each(function() {
			if(!isNaN($(this).val()) && $(this).closest("tr").children('td').eq(5).find("select").val() != ""){
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.age = $(this).closest("tr").children('td').eq(2).text();
				os.sessions = $(this).closest("tr").children('td').eq(5).find("select").val();
				os.amount = $(this).val();
				os.summary = $("#men").text()+"丁" + $("#women").text()+"口";
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
		o.total = $("#godTotal").val();
		o.pay = $("#godPay").val();
		o.remark = $("#godText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderGodStar',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					visible('sec4', 'li_4');
				}
				alert(data.message);
				$(obj).attr("disabled", false);
			}, 
			error: function() { 
				alert("editOrderGodStar error...."); 
				$(obj).attr("disabled", false);
			} 
		});
	}
}
</script>


<!-- 玉皇上帝禮斗 -->			
<div id="sec4" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="gsTbl" class="table table-bordered">
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
		<select id="godRemark" onchange="addGodText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="godText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="godPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="godTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveGodStarOrder();">確認</button>
	</div>
</div>