<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
#jyTbl td:hover {cursor: pointer;}
</style>
<script>
$(document).ready(function() {
	$(document).on('click', 'tr.dataRow', function(){
		$('tr.dataRow').css('color', '');
		$('tr.dataRow').css('background-color', '');
		
		if($(this).closest("tr").children('td').eq(0).text() != ""){
			$(this).css('color', '#000000');
			$(this).css('background-color', 'yellow');
			
			$("#jieyuanId").val($(this).closest("tr").children('td').eq(0).text());
			$.ajax({ 
				url:'queryTypeByJieyuanId',
				data:{ 
					id: $("#jieyuanId").val()
				}, 
				type:'post', 
				cache:false, 
				dataType:'json', 
				success:function(data) { 
					if(data.status == "success"){ 
						setJieyuanPriceTbl(data.list);
					}
				}, 
				error: function() { 
					alert("queryTypeByJieyuanId error...."); 
				} 
			});
		}
	});
});

function setJieyuanTbl(json){
	$('#jyTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	$('#jypTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	var cnt = 0;
	for (var key in json) {
		$('#jyTbl').append(
	        $('<tr class="dataRow"></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sessions),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventStart),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventEnd),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].startRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].endRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].inventory),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updJieyuan(this);'>??????</button> ")
	        )
	    );
		//??????????????????????????????
		if(cnt == 0){
			$('#jieyuanId').val(json[key].id);
			$("#jyTbl tr").eq(1).css('color', '#000000');
			$("#jyTbl tr").eq(1).css('background-color', 'yellow');
			
			if(json[key].jieyuanPrice != null){
				setJieyuanPriceTbl(json[key].jieyuanPrice);
			}
		}
		
		cnt++;
	}
}

function setJieyuanPriceTbl(json){
	$('#jypTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	for (var key in json) {
		$('#jypTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display: none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].code),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].price),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updJieyuanPrice(this);'>??????</button> ")
	        )
	    );
	}
}

//??????
function addJieyuanRow(){
	$("#jieyuanId").val("");
	
	$('#jyTbl').append(
        $('<tr class="dataRow"></tr>').append(
            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveJieyuan(this);'>??????</button>")
        )
    );
}

function addJieyuanPriceRow(){
	if($("#jieyuanId").val() == ""){
		alert("???????????????");
		return;
	}
	
	$('#jypTbl').append(
        $('<tr></tr>').append(
       		$('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:150px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:70px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveJieyuanPrice(this);'>??????</button>")
        )
    );
}

//??????
function updJieyuan(obj){
	var $td = $(obj).closest("tr").children();
	var id = $td.eq(0).text();
	var sessions = $td.eq(1).text();
	var eventStart = $td.eq(2).text();
	var eventEnd = $td.eq(3).text();
	var startDate = $td.eq(4).text();
	var endDate = $td.eq(5).text();
	var qty = $td.eq(6).text();
	
	$td.eq(0).html(id);
	$td.eq(1).html("<input type='text' style='color:black; width:70px;' value='"+sessions+"' />");
    $td.eq(2).html("<input type='text' class='imgDatepic' style='color:black; width:100px;' value='"+eventStart+"' readonly='readonly' />");
    $td.eq(3).html("<input type='text' class='imgDatepic' style='color:black; width:100px;' value='"+eventEnd+"' readonly='readonly' />");
    $td.eq(4).html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' value='"+startDate+"' readonly='readonly' />");
    $td.eq(5).html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' value='"+endDate+"' readonly='readonly' />");
    $td.eq(6).html("<input type='text' style='color:black; width:50px;' value='"+inventory+"' onblur='checkNum(this);' autocomplete='off' />");
    $td.eq(7).html("<button type='button' class='btn btn-info btn-xs' onclick='saveJieyuan(this);'>??????</button>");
}

function updJieyuanPrice(obj){
	var $td = $(obj).closest("tr").children();
	
	var id = $td.eq(0).text();
	var code = $td.eq(1).text();
	var name = $td.eq(2).text();
	var price = $td.eq(3).text();
	
	$td.eq(0).html(id),
    $td.eq(1).html("<input type='text' style='color:black; width:50px;' value='"+code+"' />"),
    $td.eq(2).html("<input type='text' style='color:black; width:150px;' value='"+name+"' />"),
    $td.eq(3).html("<input type='text' style='color:black; width:70px;' value='"+price+"' onblur='checkNum(this);' autocomplete='off' />"),
    $td.eq(4).html("<button type='button' class='btn btn-info btn-xs' onclick='saveJieyuanPrice(this);'>??????</button>")
}

//??????
function saveJieyuan(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == ""){
		alert("???????????????");
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
	o.qty = $td.eq(6).children('input').val();
	
	$.ajax({ 
		url:'editJieyuan',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){
				$("#jieyuanId").val(data.jieyuanId);
				
				if(data.types == "Y" && confirm(data.message)){
					$.ajax({ 
						url:'importJieyuanPrice',
						data:{
							id: $("#jieyuanId").val()
						},
						type:'post', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){
								alert(data.message);
								setJieyuanTbl(data.list);
							}
						}, 
						error: function() { 
							alert("importJieyuanPrice error...."); 
						} 
					});
				}else{
					$.ajax({ 
						url:'jieyuan',
						type:'get', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){ 
								setJieyuanTbl(data.list);
							}
						}, 
						error: function() { 
							alert("jieyuan error...."); 
						} 
					});
				}
			}else{
				alert(data.message);
			}
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editJieyuan error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}

function saveJieyuanPrice(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == "" || $.trim($td.eq(2).children('input').val()) == ""){
		alert("????????????????????????");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.jieyuanId = $("#jieyuanId").val();
	o.id = $td.eq(0).text();
	o.code = $td.eq(1).children('input').val();
	o.name = $td.eq(2).children('input').val();
	o.price = $td.eq(3).children('input').val();
	
	$.ajax({ 
		url:'editJieyuanPrice',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$('#jypTbl > tbody > tr').each(function(index, tr) {
			    	if(index > 0){
			    		$(this).remove();
			    	}
			   	});
				setJieyuanPriceTbl(data.types);
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editJieyuanPrice error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- ???????????? -->			
<div id="sec13" style="text-align:left;">
	<input type="hidden" style="color:black;" id="jieyuanId">
	<table style="width:100%; border:0px solid red;">
		<tr>
			<td>
				<span style="font-size:20px; font-weight:bold;">??????</span>
			</td>
			<td style="width:4%;">&nbsp;</td>
			<td>
				<span style="font-size:20px; font-weight:bold;">?????????????????????</span>
			</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td style="width:60%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="jyTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">???????????????</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addJieyuanRow();">??????</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
			<td style="width:2%;">&nbsp;</td>
			<td style="width:38%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="jypTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addJieyuanPriceRow();">??????</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>