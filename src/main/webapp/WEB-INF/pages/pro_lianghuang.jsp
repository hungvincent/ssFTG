<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
#lhTbl td:hover {cursor: pointer;}
</style>
<script>
$(document).ready(function() {
	$(document).on('click', 'tr.dataRow', function(){
		$('tr.dataRow').css('color', '');
		$('tr.dataRow').css('background-color', '');
		
		if($(this).closest("tr").children('td').eq(0).text() != ""){
			$(this).css('color', '#000000');
			$(this).css('background-color', 'yellow');
			
			$("#lianghuangId").val($(this).closest("tr").children('td').eq(0).text());
			$.ajax({ 
				url:'queryTypeByLianghuangId',
				data:{ 
					id: $("#lianghuangId").val()
				}, 
				type:'post', 
				cache:false, 
				dataType:'json', 
				success:function(data) { 
					if(data.status == "success"){ 
						setLianghuangTypeTbl(data.list);
					}
				}, 
				error: function() { 
					alert("queryTypeByLianghuangId error...."); 
				} 
			});
		}
	});
});

function setLianghuangTbl(json){
	$('#lhTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	$('#lhtTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	var cnt = 0;
	for (var key in json) {
		$('#lhTbl').append(
	        $('<tr class="dataRow"></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].sessions),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventStart),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].eventEnd),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].startRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].endRegDate),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updLianghuang(this);'>??????</button> ")
	        )
	    );
		//??????????????????????????????
		if(cnt == 0){
			$('#lianghuangId').val(json[key].id);
			$("#lhTbl tr").eq(1).css('color', '#000000');
			$("#lhTbl tr").eq(1).css('background-color', 'yellow');
			
			if(json[key].lianghuangType != null){
				setLianghuangTypeTbl(json[key].lianghuangType);
			}
		}
		
		cnt++;
	}
}

function setLianghuangTypeTbl(json){
	$('#lhtTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	for (var key in json) {
		$('#lhtTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display: none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].code),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].name),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].inventory),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].price),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updLianghuangType(this);'>??????</button> ")
	        )
	    );
	}
}

//??????
function addLianghuangRow(){
	$("#lianghuangId").val("");
	
	$('#lhTbl').append(
        $('<tr class="dataRow"></tr>').append(
            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:70px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='start' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' class='imgDatepic' name='end' style='color:black; width:100px;' readonly='readonly' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveLianghuang(this);'>??????</button>")
        )
    );
}

function addLianghuangTypeRow(){
	if($("#lianghuangId").val() == ""){
		alert("???????????????");
		return;
	}
	
	$('#lhtTbl').append(
        $('<tr></tr>').append(
       		$('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:150px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:50px;' onblur='checkNum(this);' autocomplete='off' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveLianghuangType(this);'>??????</button>")
        )
    );
}

//??????
function updLianghuang(obj){
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
    $td.eq(6).html("<button type='button' class='btn btn-info btn-xs' onclick='saveLianghuang(this);'>??????</button>");
}

function updLianghuangType(obj){
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
    $td.eq(5).html("<button type='button' class='btn btn-info btn-xs' onclick='saveLianghuangType(this);'>??????</button>")
}

//??????
function saveLianghuang(obj){
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
	
	$.ajax({ 
		url:'editLianghuang',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){
				$("#lianghuangId").val(data.lianghuangId);
				
				if(data.types == "Y" && confirm(data.message)){
					$.ajax({ 
						url:'importLianghuangType',
						data:{
							id: $("#lianghuangId").val()
						},
						type:'post', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){
								alert(data.message);
								setLianghuangTbl(data.list);
							}
						}, 
						error: function() { 
							alert("importLianghuangType error...."); 
						} 
					});
				}else{
					$.ajax({ 
						url:'lianghuang',
						type:'get', 
						cache:false, 
						dataType:'json', 
						success:function(data) { 
							if(data.status == "success"){ 
								setLianghuangTbl(data.list);
							}
						}, 
						error: function() { 
							alert("lianghuang error...."); 
						} 
					});
				}
			}else{
				alert(data.message);
			}
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editLianghuang error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}

function saveLianghuangType(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == "" || $.trim($td.eq(2).children('input').val()) == ""){
		alert("????????????????????????");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.lianghuangId = $("#lianghuangId").val();
	o.id = $td.eq(0).text();
	o.code = $td.eq(1).children('input').val();
	o.name = $td.eq(2).children('input').val();
	o.qty = $td.eq(3).children('input').val();
	o.price = $td.eq(4).children('input').val();
	
	$.ajax({ 
		url:'editLianghuangType',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$('#lhtTbl > tbody > tr').each(function(index, tr) {
			    	if(index > 0){
			    		$(this).remove();
			    	}
			   	});
				setLianghuangTypeTbl(data.types);
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editLianghuangType error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- ???????????? -->			
<div id="sec12" style="text-align:left;">
	<input type="hidden" style="color:black;" id="lianghuangId">
	<table style="width:100%; border:0px solid red;">
		<tr>
			<td>
				<span style="font-size:20px; font-weight:bold;">??????</span>
			</td>
			<td style="width:4%;">&nbsp;</td>
			<td>
				<span style="font-size:20px; font-weight:bold;">??????</span>
			</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td style="width:58%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="lhTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">??????????????????</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addLianghuangRow();">??????</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
			<td style="width:2%;">&nbsp;</td>
			<td style="width:40%;">
				<div style="height:600px; overflow-y:scroll;">
					<table id="lhtTbl" class="table table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">??????</th>
						<th style="text-align:center;">
							<button type="button" class="btn btn-info btn-xs" onclick="addLianghuangTypeRow();">??????</button>
						</th>
					</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>