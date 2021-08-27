<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
#srTbl td:hover {cursor: pointer;}
</style>
<script>
$(document).ready(function() {
	$(document).on('click', 'tr.dataRow', function(){
		$('tr.dataRow').css('color', '');
		$('tr.dataRow').css('background-color', '');
		
		if($(this).closest("tr").children('td').eq(0).text() != ""){
			$(this).css('color', '#000000');
			$(this).css('background-color', 'yellow');
			
			$("#stopResolveId").val($(this).closest("tr").children('td').eq(0).text());
			$.ajax({ 
				url:'queryTypeByStopResolveId',
				data:{ 
					id: $("#stopResolveId").val()
				}, 
				type:'post', 
				cache:false, 
				dataType:'json', 
				success:function(data) { 
					if(data.status == "success"){ 
						setStopResolvePriceTbl(data.list);
					}
				}, 
				error: function() { 
					alert("queryTypeByStopResolveId error...."); 
				} 
			});
		}
	});
});

function setStopResolveTbl(json){
	$('#srTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	$('#srtTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	var cnt = 0;
	for (var key in json) {
		$('#srTbl').append(
	        $('<tr class="dataRow"></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sessions),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventStart),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventEnd),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].startRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].endRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].groupInventory),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].groupPrice),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updStopResolve(this);'>修改</button> ")
	        )
	    );
		//代入排序第一筆的種類
		if(cnt == 0){
			$('#stopResolveId').val(json[key].id);
			$("#srTbl tr").eq(1).css('color', '#000000');
			$("#srTbl tr").eq(1).css('background-color', 'yellow');
			
			if(json[key].stopResolvePrice != null){
				setStopResolvePriceTbl(json[key].stopResolvePrice);
			}
		}
		cnt++;
	}
}

function setStopResolvePriceTbl(json){
	$('#srtTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	for (var key in json) {
		$('#srtTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display: none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].code),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].price),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updStopResolvePrice(this);'>修改</button> ")
	        )
	    );
	}
}

//新增
function addStopResolveRow(){
	$("#stopResolveId").val("");
	
	$('#srTbl').append(
        $('<tr class="dataRow"></tr>').append(
            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveStopResolve(this);'>儲存</button>")
        )
    );
}

function addStopResolvePriceRow(){
	if($("#stopResolveId").val() == ""){
		alert("請選擇場次");
		return;
	}
	
	$('#srtTbl').append(
        $('<tr></tr>').append(
       		$('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:100px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:70px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveStopResolvePrice(this);'>儲存</button>")
        )
    );
}

//修改
function updStopResolve(obj){
	var $td = $(obj).closest("tr").children();
	var id = $td.eq(0).text();
	var sessions = $td.eq(1).text();
	var eventStart = $td.eq(2).text();
	var eventEnd = $td.eq(3).text();
	var startDate = $td.eq(4).text();
	var endDate = $td.eq(5).text();
	var groupQty = $td.eq(6).text();
	var groupPrice = $td.eq(7).text();
	
	$td.eq(0).html(id);
	$td.eq(1).html("<input type='text' style='color:black; width:70px;' value='"+sessions+"' />");
    $td.eq(2).html("<input type='text' class='imgDatepic' style='color:black; width:100px;' value='"+eventStart+"' readonly='readonly' />");
    $td.eq(3).html("<input type='text' class='imgDatepic' style='color:black; width:100px;' value='"+eventEnd+"' readonly='readonly' />");
    $td.eq(4).html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' value='"+startDate+"' readonly='readonly' />");
    $td.eq(5).html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' value='"+endDate+"' readonly='readonly' />");
    $td.eq(6).html("<input type='text' value='"+groupInventory+"' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
    $td.eq(7).html("<input type='text' value='"+groupPrice+"' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
    $td.eq(8).html("<button type='button' class='btn btn-info btn-xs' onclick='saveStopResolve(this);'>儲存</button>");
}

function updStopResolvePrice(obj){
	var $td = $(obj).closest("tr").children();
	
	var id = $td.eq(0).text();
	var code = $td.eq(1).text();
	var name = $td.eq(2).text();
	var price = $td.eq(3).text();
	
	$td.eq(0).html(id),
    $td.eq(1).html("<input type='text' style='color:black; width:50px;' value='"+code+"' />"),
    $td.eq(2).html("<input type='text' style='color:black; width:100px;' value='"+name+"' />"),
    $td.eq(3).html("<input type='text' style='color:black; width:70px;' value='"+price+"' onblur='checkNum(this);' autocomplete='off' />"),
    $td.eq(4).html("<button type='button' class='btn btn-info btn-xs' onclick='saveStopResolvePrice(this);'>儲存</button>")
}

//儲存
function saveStopResolve(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == ""){
		alert("場次為必填");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.id = $td.eq(0).text();
	o.sessions = $td.eq(1).children('input').val();
	o.eventStart = $td.eq(2).children('input').val();
	o.eventEnd = $td.eq(3).children('input').val();
	o.startDate = $td.eq(4).children('input').val();
	o.endDate = $td.eq(5).children('input').val();
	o.groupQty = $td.eq(6).children('input').val();
	o.groupPrice = $td.eq(7).children('input').val();
	
	$.ajax({ 
		url:'editStopResolve',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){
				$("#stopResolveId").val(data.stopResolveId);
				
				if(data.types == "Y" && confirm(data.message)){
					$.ajax({ 
						url:'importStopResolvePrice',
						data:{
							id: $("#stopResolveId").val()
						},
						type:'post', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){
								alert(data.message);
								setStopResolveTbl(data.list);
							}
						}, 
						error: function() { 
							alert("importStopResolvePrice error...."); 
						} 
					});
				}else{
					$.ajax({ 
						url:'stopResolve',
						type:'get', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){ 
								setStopResolveTbl(data.list);
							}
						}, 
						error: function() { 
							alert("stopResolve error...."); 
						} 
					});
				}
			}else{
				alert(data.message);
			}
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editStopResolve error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}

function saveStopResolvePrice(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == "" || $.trim($td.eq(2).children('input').val()) == ""){
		alert("代碼、制化為必填");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.stopResolveId = $("#stopResolveId").val();
	o.id = $td.eq(0).text();
	o.code = $td.eq(1).children('input').val();
	o.name = $td.eq(2).children('input').val();
	o.price = $td.eq(3).children('input').val();
	
	$.ajax({ 
		url:'editStopResolvePrice',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$('#srtTbl > tbody > tr').each(function(index, tr) {
			    	if(index > 0){
			    		$(this).remove();
			    	}
			   	});
				setStopResolvePriceTbl(data.types);
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editStopResolvePrice error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- 制解 -->			
<div id="sec5" style="text-align:left;">
	<input type="hidden" style="color:black;" id="stopResolveId">
	<table style="width:100%; border:0px solid red;">
		<tr>
			<td>
				<span style="font-size:20px; font-weight:bold;">場次</span>
			</td>
			<td style="width:4%;">&nbsp;</td>
			<td>
				<span style="font-size:20px; font-weight:bold;">個人祭品費用</span>
			</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td style="width:66%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="srTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">場次</th>
						<th style="text-align:center;">法會開始日期</th>
						<th style="text-align:center;">法會結束日期</th>
						<th style="text-align:center;">開始登記日期</th>
						<th style="text-align:center;">結束登記日期</th>
						<th style="text-align:center;">可報名戶數</th>
						<th style="text-align:center;">戶報名費</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addStopResolveRow();">新增</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
			<td style="width:2%;">&nbsp;</td>
			<td style="width:32%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="srtTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">代碼</th>
						<th style="text-align:center;">制化</th>
						<th style="text-align:center;">祭品費</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addStopResolvePriceRow();">新增</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>