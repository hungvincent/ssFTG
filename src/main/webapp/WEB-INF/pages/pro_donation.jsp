<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
$(document).ready(function() {

});

function setDonTbl(json){
	$('#donTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	for (var key in json) {
		$('#donTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].summary),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].inventory),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].price),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].startRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].endRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updDon(this);'>修改</button> ")
	        )
	    );
	}
}

//新增
function addDonRow(){
	$('#donTbl').append(
        $('<tr></tr>').append(
            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:180px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:70px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:70px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveDon(this);'>儲存</button>")
        )
    );
}

//修改
function updDon(obj){
	var $td = $(obj).closest("tr").children();
	var id = $td.eq(0).text();
	var summary = $td.eq(1).text();
	var inventory = $td.eq(2).text();
	var price = $td.eq(3).text();
	var startDate = $td.eq(4).text();
	var endDate = $td.eq(5).text();
	
	$td.eq(0).html(id);
	$td.eq(1).html("<input type='text' style='color:black; width:180px;' value='"+summary+"' />");
    $td.eq(2).html("<input type='text' style='color:black; width:70px;' value='"+inventory+"' onblur='checkNum(this);' autocomplete='off' />");
    $td.eq(3).html("<input type='text' style='color:black; width:70px;' value='"+price+"' onblur='checkNum(this);' autocomplete='off' />");
    $td.eq(4).html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' value='"+startDate+"' readonly='readonly' />");
    $td.eq(5).html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' value='"+endDate+"' readonly='readonly' />");
    $td.eq(6).html("<button type='button' class='btn btn-info btn-xs' onclick='saveDon(this);'>儲存</button>");
}

//儲存
function saveDon(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == ""){
		alert("摘要為必填");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.id = $td.eq(0).text();
	o.summary = $td.eq(1).children('input').val();
	o.inventory = $td.eq(2).children('input').val();
	o.price = $td.eq(3).children('input').val();
	o.startDate = $td.eq(4).children('input').val();
	o.endDate = $td.eq(5).children('input').val();
	
	$.ajax({ 
		url:'editDonation',
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
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editDonation error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- 油香 -->			
<div id="sec1" style="text-align:left;">
	<table style="width:80%; border: 0px solid red;">
		<tr>
			<td>
				<span style="font-size:20px; font-weight:bold;">種類</span>
			</td>
		</tr>
	</table>
	<br>
	<div style="width:80%; height:600px; overflow-y:scroll;">
		<table id="donTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center;">摘要</th>
				<th style="text-align:center;">可報名人數</th>
				<th style="text-align:center;">預設金額</th>
				<th style="text-align:center;">開始登記日期</th>
				<th style="text-align:center;">結束登記日期</th>
				<th style="text-align:center;">
					<button type="button" class="btn btn-info btn-xs" onclick="addDonRow();">新增</button>
				</th>
			</tr>
		</table>
	</div>
</div>