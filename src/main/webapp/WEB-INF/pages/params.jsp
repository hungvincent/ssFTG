<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>松山奉天宮</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="css/main.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>
<script src="js/datePicker_TW.js"></script>
<script>
$(document).ready(function() {
	$('body').on('focus',".imgDatepic", function(){
	    $(this).datepickerTW({
			dateFormat: "yy/mm/dd",
			autoClose: true,
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
			buttonImageOnly: true,
			minDate: new Date(),
			onSelect:function(){
	        	endText = $('input[name=end]').val();
	        	if(endText != ''){
        			startText = $('input[name=start]').val();
	        		if(startText != ''){
		        		var start = new Date();
	        			start.setFullYear(parseInt(startText.substring(0,3))+1911);
	        			start.setMonth(parseInt(startText.substring(4,6))-1);
	        			start.setDate(startText.substring(7,9));
	        			var end = new Date();
	    				end.setFullYear(parseInt(endText.substring(0,3))+1911);
	    				end.setMonth(parseInt(endText.substring(4,6))-1);
	    				end.setDate(endText.substring(7,9));
	    				
	    				if(end < start){
	    					alert("結束登記日期小於開始登記日期");
	    					$('input[name=end]').val("");
	    				}
	        		}
	        	}
	        }
		});
	});
	
	visible('sec1', 'donation', 'li_1');
});

function initPage(){
	$("#uli li").each(function(index) {
		  //alert(index + ":" + $(this).text());
		$(this).css("background-color", "#808080");
	});
	
	$("#sec1").hide();
	$("#sec2").hide();
	$("#sec3").hide();
	$("#sec4").hide();
	$("#sec5").hide();
	$("#sec6").hide();
	$("#sec7").hide();
	$("#sec8").hide();
	$("#sec9").hide();
	$("#sec10").hide();
	$("#sec11").hide();
	$("#sec12").hide();
	$("#sec13").hide();
	$("#sec14").hide();
	$("#sec15").hide();
	$("#sec16").hide();
}

function visible(div, act, id){
	initPage();
	$("#"+id).css("background-color", "red");
	$("#"+div).show();
	
	$.ajax({ 
		url:act,
		type:'get', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				if(div == 'sec1') setDonTbl(data.list);
				if(div == 'sec2') setConTbl(data.list);
				if(div == 'sec3') setLightTbl(data.list);
				if(div == 'sec4') setGodStarTbl(data.list);
				if(div == 'sec5') setStopResolveTbl(data.list);
				if(div == 'sec6') setPligrimageTbl(data.list);
				if(div == 'sec7') setWenchanTbl(data.list);
				if(div == 'sec8') setPrinceStarTbl(data.list);
				if(div == 'sec9') setPurdueTbl(data.list);
				if(div == 'sec10') setChaodueTbl(data.list);
				if(div == 'sec11') setAnniversaryTbl(data.list);
				if(div == 'sec12') setLianghuangTbl(data.list);
				if(div == 'sec13') setJieyuanTbl(data.list);
				if(div == 'sec14') setBucaikuTbl(data.list);
				if(div == 'sec15') setItemsTbl(data.list);
				if(div == 'sec16') setParams(data.sysMap);
			}
		}, 
		error: function() { 
			alert(act + " error...."); 
		} 
	});
}

function checkNum(obj){
	var val = $.trim($(obj).val());
	if (isNaN(val)) {
		alert("請輸入數字")
		$(obj).val(0);
	}
}
</script>
</head>
<body style="font-size:12px;">
<%@ include file="menu.jsp"%>
<div style="margin-top:20px;" align="center">
<table style="width:85%; border: 0px solid red;">
<tr>
	<td align="center" style="">
		<ul id="uli">
		  <li id="li_1" class="opt" onclick="visible('sec1', 'donation', 'li_1');">油香</li>
		  <li id="li_2" class="opt" onclick="visible('sec2', 'construction', 'li_2');">建設</li>
		  <li id="li_3" class="opt" onclick="visible('sec3', 'light', 'li_3');">點燈</li>
		  <li id="li_4" class="opt" onclick="visible('sec4', 'godStar', 'li_4');">玉皇上帝禮斗</li>
		  <li id="li_5" class="opt" onclick="visible('sec5', 'stopResolve', 'li_5');">制解</li>
		  <li id="li_6" class="opt" onclick="visible('sec6', 'pilgrimage', 'li_6');">進香</li>
		  <li id="li_7" class="opt" onclick="visible('sec7', 'wenchan', 'li_7');">文昌法會</li>
		  <li id="li_8" class="opt" onclick="visible('sec8', 'princeStar', 'li_8');">五年千歲禮斗</li>
		  <li id="li_9" class="opt" onclick="visible('sec9', 'purdue', 'li_9');">普渡</li>
		  <li id="li_10" class="opt" onclick="visible('sec10', 'chaodue', 'li_10');">超渡</li>
		  <li id="li_11" class="opt" onclick="visible('sec11', 'anniversary', 'li_11');">建廟慶典</li>
		  <li id="li_12" class="opt" onclick="visible('sec12', 'lianghuang', 'li_12');">梁皇寶懺</li>
		  <li id="li_13" class="opt" onclick="visible('sec13', 'jieyuan', 'li_13');">解冤法會</li>
		  <li id="li_14" class="opt" onclick="visible('sec14', 'bucaiku', 'li_14');">補財庫</li>
		  <li id="li_15" class="opt" onclick="visible('sec15', 'donateItems', 'li_15');">物品捐獻</li>
		  <li id="li_16" class="opt" onclick="visible('sec16', 'sysParams', 'li_16');">系統參數</li>
		</ul>
	</td>
	<td align="center" style="width:100%;">
		<div style="border: 1px solid #FFF; width:1500px;height:750px; padding:10px;">
			<%@ include file="pro_donation.jsp"%>
			<%@ include file="pro_construction.jsp"%>
			<%@ include file="pro_light.jsp"%>
			<%@ include file="pro_god_star.jsp"%>
			<%@ include file="pro_stop_resolve.jsp"%>
			<%@ include file="pro_pilgrimage.jsp"%>
			<%@ include file="pro_wenchan.jsp"%>
			<%@ include file="pro_prince_star.jsp"%>
			<%@ include file="pro_purdue.jsp"%>
			<%@ include file="pro_chaodue.jsp"%>
			<%@ include file="pro_anniversary.jsp"%>
			<%@ include file="pro_lianghuang.jsp"%>
			<%@ include file="pro_jieyuan.jsp"%>
			<%@ include file="pro_bucaiku.jsp"%>
			<%@ include file="pro_donate_items.jsp"%>
			<%@ include file="sys_parameters.jsp"%>
		</div>
	</td>
</tr>
</table>
</div>
</body>
</html>