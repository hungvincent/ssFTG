<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setChaodueTbl(json){
	$('#chaTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#chaRemark option").remove();
	$("#chaRemark").append($("<option></option>").attr("value", "").text(" - "));
	
	for (var key in json) {
		$("#chaRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#chaTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='chao_opt' style='width:100%;' onchange='chaoopt(this);'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' autocomplete='off' style='width:40%;'>&nbsp;&nbsp;<select class='obj_opt' style='width:50%;'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='chao_amt' value='0' onchange='sumChaoTot();' autocomplete='off' style='width:100%;'>")
	        )
	    );
	}
	
	$.ajax({ 
		url:'getProChaodue',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".chao_opt").append($("<option></option>").attr("value", "").text(" - "));
				$.each(data.maps, function(key, value) {
			  		$(".chao_opt").append($("<option></option>").attr("value", key).text(value));
				});
			}
		}, 
		error: function() { 
			alert("getProChaodue error...."); 
		} 
	});
	
	//超渡對象
	$(".obj_opt").append($("<option></option>").attr("value", "").text(" - "));
 	$(".obj_opt").append($("<option></option>").attr("value", "寵物").text("寵物"));
 	$(".obj_opt").append($("<option></option>").attr("value", "亡者").text("亡者"));
 	$(".obj_opt").append($("<option></option>").attr("value", "歷代袓先").text("歷代袓先"));
 	$(".obj_opt").append($("<option></option>").attr("value", "冤親債主").text("冤親債主"));
 	$(".obj_opt").append($("<option></option>").attr("value", "嬰靈").text("嬰靈"));
 	$(".obj_opt").append($("<option></option>").attr("value", "地基主").text("地基主"));
}


function chaoopt(obj){
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
					sumChaoTot();
				}
			}, 
			error: function() { 
				alert("getProPrinceStarBySession error...."); 
			} 
		});
	}else{
		$(obj).closest("tr").children('td').eq(6).find("input").val(0);
		sumChaoTot();
	}
}

function sumChaoTot(){
	var tot = 0;
	$(".chao_amt").each(function() {
		if (isNaN($(this).val())) {
			alert("金額請輸入數字");
			$(this).val(0);
		}else{
			tot += parseInt($(this).val());
			$('#chaTotal').val(tot);
		}
	});
}

function addChaoText(v){
	var str = "";
	if($('#chaText').val() != ""){
		str = $('#chaText').val() + "," + v;
	}else{
		str = v;
	}
	$('#chaText').val(str);
}

//確認
function saveChaodueOrder(){
	if(confirm("本次交易總金額 $"+$('#chaTotal').val())){
		//明細
		var ary = [];
		var isOk = false;
		$(".chao_amt").each(function() {
			if(!isNaN($(this).val()) && $(this).closest("tr").children('td').eq(4).find("select").val() != ""){
				var os = new Object();
				os.custId = $(this).closest("tr").children('td').eq(0).text();
				os.age = $(this).closest("tr").children('td').eq(2).text();
				os.sessions = $(this).closest("tr").children('td').eq(4).find("select").val();
				os.txt = $(this).closest("tr").children('td').eq(5).find("input").val();
				os.summary = $(this).closest("tr").children('td').eq(5).find("select").val();
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
		o.total = $("#chaTotal").val();
		o.pay = $("#chaPay").val();
		o.remark = $("#chaText").val();
		o.list = ary;
		
		$.ajax({ 
			url:'editOrderChaodue',
			data:{ 
				jsonStr: JSON.stringify(o)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					setDonTbl(data.list);
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("editOrderChaodue error...."); 
			} 
		});
	}
}
</script>


<!-- 超渡 -->			
<div id="sec10" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="chaTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:15%;">客戶姓名</th>
				<th style="text-align:center; width:10%;">年齡</th>
				<th style="text-align:center; width:10%;">性別</th>
				<th style="text-align:center; width:25%;">參加</th>
				<th style="text-align:center; width:25%;">超渡對象</th>
				<th style="text-align:center; width:15%;">金額</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="chaRemark" onchange="addChaoText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="chaText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<b>付款方式</b>
		<select id="chaPay">
			<option value="現金">現金</option>
			<option value="信用卡">信用卡</option>
			<option value="匯款">匯款</option>
		</select>&nbsp;&nbsp;
		<b>總金額</b>
		<input type="text" id="chaTotal" style="width:15%;" value="0" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveChaodueOrder();">確認</button>
	</div>
</div>