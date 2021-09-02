<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
#puTbl td:hover {cursor: pointer;}
</style>
<script>
$(document).ready(function() {
	$(document).on('click', 'tr.dataRow', function(){
		$('tr.dataRow').css('color', '');
		$('tr.dataRow').css('background-color', '');
		
		if($(this).closest("tr").children('td').eq(0).text() != ""){
			$(this).css('color', '#000000');
			$(this).css('background-color', 'yellow');
			
			$("#purdueId").val($(this).closest("tr").children('td').eq(0).text());
			$.ajax({ 
				url:'queryTypeByPurdueId',
				data:{ 
					id: $("#purdueId").val()
				}, 
				type:'post', 
				cache:false, 
				dataType:'json', 
				success:function(data) { 
					if(data.status == "success"){ 
						setPurdueTypeTbl(data.list);
					}
				}, 
				error: function() { 
					alert("queryTypeByPurdueId error...."); 
				} 
			});
		}
	});
});

function setPurdueTbl(json){
	$('#puTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	$('#putTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	var cnt = 0;
	for (var key in json) {
		$('#puTbl').append(
	        $('<tr class="dataRow"></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sessions),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventStart),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventEnd),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].startRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].endRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updPurdue(this);'>修改</button> ")
	        )
	    );
		//代入排序第一筆的種類
		if(cnt == 0){
			$('#purdueId').val(json[key].id);
			$("#puTbl tr").eq(1).css('color', '#000000');
			$("#puTbl tr").eq(1).css('background-color', 'yellow');
			
			if(json[key].purdueType != null){
				setPurdueTypeTbl(json[key].purdueType);
			}
		}
		
		cnt++;
	}
}

function setPurdueTypeTbl(json){
	$('#putTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	for (var key in json) {
		$('#putTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display: none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].code),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].inventory),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].price),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updPurdueType(this);'>修改</button> ")
	        )
	    );
	}
}

//新增
function addPurdueRow(){
	$("#purdueId").val("");
	
	$('#puTbl').append(
        $('<tr class="dataRow"></tr>').append(
            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:70px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='savePurdue(this);'>儲存</button>")
        )
    );
}

function addPurdueTypeRow(){
	if($("#purdueId").val() == ""){
		alert("請選擇場次");
		return;
	}
	
	$('#putTbl').append(
        $('<tr></tr>').append(
       		$('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:150px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='savePurdueType(this);'>儲存</button>")
        )
    );
}

//修改
function updPurdue(obj){
	var $td = $(obj).closest("tr").children();
	var id = $td.eq(0).text();
	var sessions = $td.eq(1).text();
	var eventStart = $td.eq(2).text();
	var eventEnd = $td.eq(3).text();
	var startDate = $td.eq(4).text();
	var endDate = $td.eq(5).text();
	
	$td.eq(0).html(id);
	$td.eq(1).html("<input type='text' style='color:black; width:70px;' value='"+sessions+"' />");
	$td.eq(2).html("<input type='text' class='imgDatepic' style='color:black; width:100px;' value='"+eventStart+"' readonly='readonly' />");
    $td.eq(3).html("<input type='text' class='imgDatepic' style='color:black; width:100px;' value='"+eventEnd+"' readonly='readonly' />");
    $td.eq(4).html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' value='"+startDate+"' readonly='readonly' />");
    $td.eq(5).html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' value='"+endDate+"' readonly='readonly' />");
    $td.eq(6).html("<button type='button' class='btn btn-info btn-xs' onclick='savePurdue(this);'>儲存</button>");
}

function updPurdueType(obj){
	var $td = $(obj).closest("tr").children();
	
	var id = $td.eq(0).text();
	var code = $td.eq(1).text();
	var name = $td.eq(2).text();
	var qty = $td.eq(3).text();
	var price = $td.eq(4).text();
	
	$td.eq(0).html(id),
    $td.eq(1).html("<input type='text' style='color:black; width:50px;' value='"+code+"' />"),
    $td.eq(2).html("<input type='text' style='color:black; width:150px;' value='"+name+"' />"),
    $td.eq(3).html("<input type='text' style='color:black; width:50px;' value='"+qty+"' onblur='checkNum(this);' autocomplete='off' />"),
    $td.eq(4).html("<input type='text' style='color:black; width:50px;' value='"+price+"' onblur='checkNum(this);' autocomplete='off' />"),
    $td.eq(5).html("<button type='button' class='btn btn-info btn-xs' onclick='savePurdueType(this);'>儲存</button>")
}

//儲存
function savePurdue(obj){
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
	
	$.ajax({ 
		url:'editPurdue',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){
				$("#purdueId").val(data.purdueId);
				
				if(data.types == "Y" && confirm(data.message)){
					$.ajax({ 
						url:'importPurdueType',
						data:{
							id: $("#purdueId").val()
						},
						type:'post', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){
								alert(data.message);
								setPurdueTbl(data.list);
							}
						}, 
						error: function() { 
							alert("importPurdueType error...."); 
						} 
					});
				}else{
					$.ajax({ 
						url:'purdue',
						type:'get', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){ 
								setPurdueTbl(data.list);
							}
						}, 
						error: function() { 
							alert("purdue error...."); 
						} 
					});
				}
			}else{
				alert(data.message);
			}
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editPurdue error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}

function savePurdueType(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == "" || $.trim($td.eq(2).children('input').val()) == ""){
		alert("代碼、種類為必填");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.purdueId = $("#purdueId").val();
	o.id = $td.eq(0).text();
	o.code = $td.eq(1).children('input').val();
	o.name = $td.eq(2).children('input').val();
	o.qty = $td.eq(3).children('input').val();
	o.price = $td.eq(4).children('input').val();
	
	$.ajax({ 
		url:'editPurdueType',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$('#putTbl > tbody > tr').each(function(index, tr) {
			    	if(index > 0){
			    		$(this).remove();
			    	}
			   	});
				setPurdueTypeTbl(data.types);
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editPurdueType error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- 普渡 -->			
<div id="sec9" style="text-align:left;">
	<input type="hidden" style="color:black;" id="purdueId">
	<table style="width:100%; border:0px solid red;">
		<tr>
			<td>
				<span style="font-size:20px; font-weight:bold;">場次</span>
			</td>
			<td style="width:4%;">&nbsp;</td>
			<td>
				<span style="font-size:20px; font-weight:bold;">種類</span>
			</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td style="width:58%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="puTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">場次</th>
						<th style="text-align:center;">法會開始日期</th>
						<th style="text-align:center;">法會結束日期</th>
						<th style="text-align:center;">開始登記日期</th>
						<th style="text-align:center;">結束登記日期</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addPurdueRow();">新增</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
			<td style="width:2%;">&nbsp;</td>
			<td style="width:40%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="putTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">代碼</th>
						<th style="text-align:center;">種類</th>
						<th style="text-align:center;">數量</th>
						<th style="text-align:center;">金額</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addPurdueTypeRow();">新增</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>