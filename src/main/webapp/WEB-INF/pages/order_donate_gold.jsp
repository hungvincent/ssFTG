<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setGoldTbl(json){
	$('#goTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#goRemark option").remove();
	$("#goRemark").append($("<option></option>").attr("value", "").text(" - "));
	$('#goText').val("");
	
	for (var key in json) {
		$("#goRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#goTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='gold' value='0' onblur='checkNum(this);' autocomplete='off' style='width:100%;'>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='go_opt'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='go_opt'></select>"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='go_opt'></select>")
	        )
	    );
	}
	
	//錢、分、厘
	for(var i=0; i<=9; i++){
		$(".go_opt").append($("<option></option>").attr("value", i).text(i));
	}
}


function addGoldText(v){
	var str = "";
	if($('#goText').val() != ""){
		str = $('#goText').val() + "," + v;
	}else{
		str = v;
	}
	$('#goText').val(str);
}

//確認
function saveGoldOrder(){
	//明細
	var ary = [];
	var isOk = false;
	$(".gold").each(function() {
		if((!isNaN($(this).val()) && parseInt($(this).val()) != 0 ) || 
				$(this).closest("tr").children('td').eq(6).find("select").val() != 0 ||
				$(this).closest("tr").children('td').eq(7).find("select").val() != 0 ||
				$(this).closest("tr").children('td').eq(8).find("select").val() != 0){
			var os = new Object();
			os.custId = $(this).closest("tr").children('td').eq(0).text();
			os.age = $(this).closest("tr").children('td').eq(2).text();
			os.liang = $(this).val();
			os.chien = $(this).closest("tr").children('td').eq(6).find("select").val();
			os.fen = $(this).closest("tr").children('td').eq(7).find("select").val();
			os.li = $(this).closest("tr").children('td').eq(8).find("select").val();
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
	o.total = "0";
	o.pay = "";
	o.remark = $("#goText").val();
	o.list = ary;
		
	$.ajax({ 
		url:'editOrderDonateGold',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				visible('sec15', 'li_15');
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editOrderDonateGold error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- 金牌捐獻 -->			
<div id="sec15" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="goTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:20%;">客戶姓名</th>
				<th style="text-align:center; width:10%;">年齡</th>
				<th style="text-align:center; width:10%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:15%;">兩</th>
				<th style="text-align:center; width:10%;">錢</th>
				<th style="text-align:center; width:10%;">分</th>
				<th style="text-align:center; width:10%;">厘</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="goRemark" onchange="addGoldText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="goText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveGoldOrder();">確認</button>
	</div>
</div>