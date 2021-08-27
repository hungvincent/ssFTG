<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
function setItemsTbl(json){
	$('#itemTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	$("#itemRemark option").remove();
	$("#itemRemark").append($("<option></option>").attr("value", "").text(" - "));
	$("#itemText").val("");
	
	for (var key in json) {
		$("#itemRemark").append($("<option></option>").attr("value", json[key].name).text(json[key].name));
		
		$('#itemTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].age),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sex=="M"?"男":"女"),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].zhiHua),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<select class='item_opt' onchange='itemopt(this);'></select>&nbsp;&nbsp;<input type='text' autocomplete='off' readonly='readonly' style='width:60%;'>")
	        )
	    );
	}
	
	
	$.ajax({ 
		url:'getProDonateItems',
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$(".item_opt").append($("<option></option>").attr("value", "").text(" - "));
				for(var i=0; i<data.list.length; i++){
					$(".item_opt").append($("<option></option>").attr("value", data.list[i].id).text(data.list[i].summary));
				}
				$(".item_opt").append($("<option></option>").attr("value", 0).text("其他"));
			}
		}, 
		error: function() { 
			alert("getProDonateItems error...."); 
		} 
	});
}


function itemopt(obj){
	if($(obj).val() == 0){
		$(obj).next().attr("readonly", false);
	}else{
		$(obj).next().attr("readonly", true);
		
		$.ajax({ 
			url:'getProDonateItemsById',
			data:{ 
				id: $(obj).val()
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$(obj).next().val(data.items.summary);
				}
			}, 
			error: function() { 
				alert("getProDonateItemsById error...."); 
			} 
		});
	}
}

function addItemText(v){
	var str = "";
	if($('#itemText').val() != ""){
		str = $('#itemText').val() + "," + v;
	}else{
		str = v;
	}
	$('#itemText').val(str);
}

//確認
function saveItemOrder(){
	//明細
	var ary = [];
	var isOk = false;
	$('#itemTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
   			var os = new Object();
   			os.custId = $(this).closest("tr").children('td').eq(0).text();
   			os.age = $(this).closest("tr").children('td').eq(2).text();
   			os.summary = $(this).closest("tr").children('td').eq(5).find("input").val();
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
	o.remark = $("#itemText").val();
	o.list = ary;
		
	$.ajax({ 
		url:'editOrderDonateItems',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				visible('sec16', 'li_16');
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editOrderDonateItems error...."); 
			visible('sec16', 'li_16');
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- 物品捐獻 -->			
<div id="sec16" style="text-align:left;">
	<div style="width:100%; height:400px; overflow-y:scroll;">
		<table id="itemTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:15%;">客戶姓名</th>
				<th style="text-align:center; width:15%;">年齡</th>
				<th style="text-align:center; width:15%;">性別</th>
				<th style="text-align:center; width:15%;">制化</th>
				<th style="text-align:center; width:40%;">種類</th>
			</tr>
		</table>
	</div>
	<br>
	<div class="form-inline">
		<b>備註</b>
		<select id="itemRemark" onchange="addItemText(this.value);" style="width:15%;">
			<option value=""> - </option>
		</select>&nbsp;&nbsp;
		<input type="text" id="itemText" style="width:30%;" autocomplete="off">&nbsp;&nbsp;
		<button type="button" class="btn btn-info btn-xs" onclick="saveItemOrder();">確認</button>
	</div>
</div>