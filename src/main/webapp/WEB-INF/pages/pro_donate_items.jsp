<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
$(document).ready(function() {

});

function setItemsTbl(json){
	$('#diTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	for (var key in json) {
		$('#diTbl').append(
	        $('<tr></tr>').append(
	            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(json[key].id),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html(json[key].summary),
	            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='updItems(this);'>修改</button> ")
	        )
	    );
	}
}

//新增
function addItemsRow(){
	$('#diTbl').append(
        $('<tr></tr>').append(
            $('<td style="text-align:center; vertical-align: middle; display:none;"></td>').html(""),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<input type='text' style='color:black; width:270px;' />"),
            $('<td style="text-align:center; vertical-align: middle;"></td>').html("<button type='button' class='btn btn-info btn-xs' onclick='saveItems(this);'>儲存</button>")
        )
    );
}

//修改
function updItems(obj){
	var $td = $(obj).closest("tr").children();
	var id = $td.eq(0).text();
	var summary = $td.eq(1).text();
	
	$td.eq(0).html(id);
	$td.eq(1).html("<input type='text' style='color:black; width:180px;' value='"+summary+"' />");
    $td.eq(2).html("<button type='button' class='btn btn-info btn-xs' onclick='saveItems(this);'>儲存</button>");
}

//儲存
function saveItems(obj){
	$(obj).attr("disabled", true);
	var $td = $(obj).closest("tr").children();
	
	if($.trim($td.eq(1).children('input').val()) == ""){
		alert("種類為必填");
		$(obj).attr("disabled", false);
		return;
	}
	
	var o = new Object();
	o.id = $td.eq(0).text();
	o.summary = $td.eq(1).children('input').val();
	
	$.ajax({ 
		url:'editDonateItems',
		data:{ 
			jsonStr: JSON.stringify(o)
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				setItemsTbl(data.list);
			}
			alert(data.message);
			$(obj).attr("disabled", false);
		}, 
		error: function() { 
			alert("editDonateItems error...."); 
			$(obj).attr("disabled", false);
		} 
	});
}
</script>


<!-- 物品捐贈 -->			
<div id="sec15" style="text-align:left;">
	<table style="width:80%; border: 0px solid red;">
		<tr>
			<td>
				<span style="font-size:20px; font-weight:bold;">捐贈種類</span>
			</td>
		</tr>
	</table>
	<br>
	<div style="width:60%; height:600px; overflow-y:scroll;">
		<table id="diTbl" class="table table-bordered">
			<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
				<th style="text-align:center; width:70%;">種類</th>
				<th style="text-align:center;">
					<button type="button" class="btn btn-info btn-xs" onclick="addItemsRow();">新增</button>
				</th>
			</tr>
		</table>
	</div>
</div>