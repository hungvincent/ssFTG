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
<link rel="stylesheet" href="css/jquery.autocomplete.css" />
<link rel="stylesheet" href="css/main.css" />
<style type="text/css">
.county{width:65px;}
.area{margin-left:10px;}
.zip{width:65px; height:30px; margin-left:10px; color:#000;}
/* #custTbl tr:hover {background-color:#F0F8FF; color:#000000;} */
#custTbl td:hover {cursor: pointer;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
<script src="js/datePicker_TW.js"></script>
<script src="js/json2ext.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script src="js/jquery.twAddrHelper.js"></script>
<script src="js/LunarSolarConverter.js"></script>
<script>
var QRY = "";
var PAY = ["現金", "信用卡", "匯款"];	//訂單明細 付款方式
$(document).ready(function() {
	//console.log($.fn.jquery);
	
	$('body').on('focus',".imgDatepic", function(){
	    $(this).datepickerTW({
			dateFormat: "yy/mm/dd",
			autoClose: true,
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
			buttonImageOnly: true,
			minDate: "-100y"
		});
	});
	
	$("#twzipcode").twzipcode({
		css: ["county", "area", "zip"],
        readonly: false,
        onCountySelect: function() {
			//alert($("select[name='county']").val());
			//alert($("select[name='district']").val());
	    },
        onDistrictSelect: function() {
			$(".cAddress").val($("select[name='county']").val()+$("select[name='district']").val());
        },
        onZipcodeKeyUp: function() {
        	$(".cAddress").val($("select[name='county']").val()+$("select[name='district']").val());
        } 
	});
	setAddressInput($("#address"));
	
	
	$("#custTbl").sortable({
        items: 'tr:not(tr:first-child)',
        cursor: 'pointer',
        axis: 'y',
        dropOnEmpty: false,
        start: function (e, ui) {
            ui.item.addClass("selected");
        },
        stop: function (e, ui) {
            ui.item.removeClass("selected");
            $(this).find("tr").each(function (index) {
                if (index > 0) {
                    $(this).find("td").eq(2).html(index);
                }
            });
        }
    });
	
	$(document).on('click', 'tr.dataRow', function(){
		$('tr.dataRow').css('color', '');
		$('tr.dataRow').css('background-color', '');
		$(this).css('color', '#000000');
		$(this).css('background-color', 'yellow');
		
		//set field value
		setCustVal($(this).closest("tr").children('td').eq(8).text());
	});
	
	initPage();
});

function setCustVal(val){
	$.ajax({ 
		url:'queryCustById',
		data:{ 
			id: val
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				$("#custId").val(data.cust.id);
				$("#addrId").val(data.addr.id);
				$("#actType").val("update");
				$("#groupSort").val(data.cust.groupSort);
				
				$("#custType").val(data.cust.custType);
				$("#name").val(data.cust.name);
				$("#phone1").val(data.cust.phone1);
				$("#phone2").val(data.cust.phone2 != null?data.cust.phone2:"");
				$("#sex").val(data.cust.sex);
				$("#adNum").val(data.cust.adNum != null?data.cust.adNum:"");
				$(".cAddress").val(data.addr.address);
				$("#isMail").val(data.addr.isMail);
				$("#lunarBirth").val(data.cust.lunarBirth);
				$("#solarBirth").val(data.cust.solarBirth);
				$("#birthHour").val(data.cust.birthHour);
				$("#email").val(data.cust.email != null?data.cust.email:"");
				$("#cardNum").val(data.cust.cardNum);
				$("#notes").val(data.cust.notes != null?data.cust.notes:"");
				$("#isActive").val(data.cust.isActive);
			}
		}, 
		error: function() { 
			alert("setCustVal queryCustById error...."); 
		} 
	});
}

function checkNum(obj){
	var val = $.trim($(obj).val());
	if (isNaN(val)) {
		alert("請輸入數字");
		$(obj).val(0);
	}
}

function checkBirth(v){
	if(v.indexOf(".") == -1 && v.length != 6){
		alert("日期格式錯誤");
		$("#lunarBirth").val("");
		$("#solarBirth").val("");
		return;
	}
	
	var ary = [];
	if(v.indexOf(".") != -1){
		ary = v.split(".");
	}else{
		ary.push(v.substring(0,2));
		ary.push(v.substring(2,4));
		ary.push(v.substring(4,6));
	}
	
	if(isNaN(ary[0]) || isNaN(ary[1]) || isNaN(ary[2]) || parseInt(ary[1]) > 12 || parseInt(ary[2]) > 31){
		alert("日期格式錯誤");
		$("#lunarBirth").val("");
		$("#solarBirth").val("");
		return;
	}
	return ary;
}

function dump(arr, level) {
    var dumped_text = "";
    if (!level) level = 0;

    //The padding given at the beginning of the line.
    var level_padding = "";
    for (var j = 0; j < level + 1; j++) level_padding += "    ";

    var res = [];
    if (typeof(arr) != 'object') { //Stings/Chars/Numbers etc.
        dumped_text = "===>" + arr + "<===(" + typeof(arr) + ")";
    } else {
        for (var item in arr) {
            var value = arr[item];

            if (typeof(value) == 'object') { //If it is an array,
                dumped_text += level_padding + "'" + item + "' ...\n";
                dumped_text += dump(value, level + 1);
            } else {
                //dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
                res.push(item + "=" + value);
            }
        }
    }
    //return dumped_text;
    return res;
}

function getLunar(ary){
	var solar = new Solar();
    solar.solarYear = parseInt(ary[0])+1911; //西元年
    solar.solarMonth = parseInt(ary[1]);
    solar.solarDay = parseInt(ary[2]);
    var converter = new LunarSolarConverter();
    var lunar = converter.SolarToLunar(solar);
    var content = dump(lunar);
    var year = "", mon = "", day = "", isleap = "";
    for (var i=0; i<content.length; i++) {
    	var k = content[i].split("=")[0];
    	var v = content[i].split("=")[1];
    	if(k == "isleap"){	//閏月
    		isleap = v;
    	}
		if(k == "lunarDay"){
			day = parseInt(v);
    	}
		if(k == "lunarMonth"){
			mon = parseInt(v);
    	}
		if(k == "lunarYear"){
			year = parseInt(v)-1911;
    	}
    }
    return prefixZero(year, 3) + "." + (((isleap=="true")?"+":"")+prefixZero(mon, 2)) + "." + prefixZero(day, 2)
}

function prefixZero(num, length) {
	return (Array(length).join('0') + num).slice(-length);
}

function fmtLunar(v){
	if(v != ""){
		var ary = checkBirth(v);
		$("#lunarBirth").val(prefixZero(ary[0], 3) + "." + prefixZero(ary[1], 2) + "." + prefixZero(ary[2], 2));
	}
}

function solarToLunar(v){
	if(v != ""){
		var ary = checkBirth(v);
		$("#solarBirth").val(prefixZero(ary[0], 3) + "." + prefixZero(ary[1], 2) + "." + prefixZero(ary[2], 2));
	  	$("#lunarBirth").val(getLunar(ary));
	}
}

function cleanField(val){
	$("#custId").val("");
	$("#addrId").val("");
	$("#actType").val("");
	$("#groupSort").val("");
	
	$("#custType").val("01");
	$("#name").val("");
	$("#phone2").val("");
	$("#sex").val("");
	$("#adNum").val("");
	$("#lunarBirth").val("");
	$("#solarBirth").val("");
	$("#birthHour").val("吉");
	$("#email").val("");
	$("#cardNum").val("");
	$("#notes").val("");
	$("#isMail").val("Y");
	$("#isActive").val("Y");
	
	//清除
	if(val == 1){
		$("#phone1").val("");
		$(".cAddress").val("");
		
		$("#associated").val("");
		$('#custTbl > tbody > tr').each(function(index, tr) {
	    	if(index > 0){
	    		$(this).remove();
	    	}
	   	});
		//丁口
		$("#men").text("0");
		$("#women").text("0");
	}
}

function deleteCust(){
	if(confirm('確定刪除?')){
		var ary = [];
		var dt = new Date();
		ary.push(dt.getFullYear()-1911);
		ary.push(dt.getMonth()+1);
		ary.push(dt.getDate());
		
		$.ajax({ 
			url:'deleteCust',
			data:{ 
				lunarNow: getLunar(ary),
				custId: $("#custId").val()
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					setCustTbl(data.custs);
					
// 					$('#custTbl > tbody > tr').each(function(index, tr) {
// 				    	if($(this).children('td').eq(8).text() == $("#custId").val()){
// 				    		$(this).remove();
// 				    	}
// 				   	});
				}else{
					alert(data.message);
				}
			}, 
			error: function() { 
				alert("deleteCust error...."); 
			} 
		});
	}
}

function save(){
	var url = $("#actType").val()==""?"saveCust":"editCust";
	if($("#actType").val() == ""){
		if($.trim($("#name").val()) == "" || $.trim($(".cAddress").val()) == "" || $.trim($("#phone1").val()) == "" || 
						$.trim($("#cardNum").val()) == ""){
			alert("姓名、電話1、地址、會員卡號  為必填");
			return 
		}
	}
	
	var ary = [];
	var dt = new Date();
	ary.push(dt.getFullYear()-1911);
	ary.push(dt.getMonth()+1);
	ary.push(dt.getDate());
	
	var obj = new Object();
	obj.custId = $("#custId").val();
	obj.addrId = $("#addrId").val();
	obj.groupSort = $("#groupSort").val();
	
	obj.custType = $("#custType").val();
	obj.name = $.trim($("#name").val());
	obj.phone1 = $.trim($("#phone1").val());
	obj.phone2 = $.trim($("#phone2").val());
	obj.sex = $("#sex").val();
	obj.adNum = $.trim($("#adNum").val());
	obj.address = $.trim($(".cAddress").val());
	obj.isMail = $("#isMail").val();
	obj.lunarBirth = $.trim($("#lunarBirth").val());
	obj.solarBirth = $.trim($("#solarBirth").val());
	obj.birthHour = $("#birthHour").val();
	obj.email = $.trim($("#email").val());
	obj.cardNum = $.trim($("#cardNum").val());
	obj.notes = $.trim($("#notes").val());
	obj.isActive = $("#isActive").val();
	obj.lunarNow = getLunar(ary);
	
	if($("#actType").val() == "update" && $("#groupSort").val() == 1){
		$.ajax({ 
			url:'queryCustById',
			data:{ 
				id: val
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					var flag = false;
					//修改地址
					if(obj.address != data.addr.address){
						flag = confirm("是否整戶修改地址");
						if(flag){
							
						}
					}
					//修改電話
					if(obj.phone1 != data.cust.phone1){
						flag = confirm("是否整批修改電話");
						if(flag){
							$.ajax({ 
								url: 'editCustByPhone',
								data:{ 
									jsonStr: JSON.stringify(obj)
								}, 
								type:'post', 
								cache:false, 
								dataType:'json', 
								success:function(data) { 
									if(data.status == "success"){ 
										setCustTbl(data.custs);
									}
									alert(data.message);
								}, 
								error: function() { 
									alert("saveCust error...."); 
								} 
							});
						}
					}
					
					if(!flag){
						$.ajax({ 
							url: url,
							data:{ 
								jsonStr: JSON.stringify(obj)
							}, 
							type:'post', 
							cache:false, 
							dataType:'json', 
							success:function(data) { 
								if(data.status == "success"){ 
									$("#custId").val(data.custId);
									$("#addrId").val(data.addrId);
									setCustTbl(data.custs);
								}
								alert(data.message);
							}, 
							error: function() { 
								alert("saveCust error...."); 
							} 
						});
					}
				}
			}, 
			error: function() { 
				alert("save queryCustById error...."); 
			} 
		});
	}else{
		$.ajax({ 
			url: url,
			data:{ 
				jsonStr: JSON.stringify(obj)
			}, 
			type:'post', 
			cache:false, 
			dataType:'json', 
			success:function(data) { 
				if(data.status == "success"){ 
					$("#custId").val(data.custId);
					$("#addrId").val(data.addrId);
					setCustTbl(data.custs);
				}
				alert(data.message);
			}, 
			error: function() { 
				alert("saveCust error...."); 
			} 
		});
	}
}

function setCustTbl(custs){
	$('#custTbl > tbody > tr').each(function(index, tr) {
    	if(index > 0){
    		$(this).remove();
    	}
   	});
	
	var cid = 0;
	var m = 0, f = 0;
	for(var i=0; i<custs.length; i++){
		if(i == 0){
			cid = custs[i].id;
		}
		
		if(custs[i].sex=="M"){
			m++;
		}else{
			f++;
		}
		$('#custTbl').append(
	        $('<tr class="dataRow" style="line-height:35px; text-align:center;"></tr>').append(
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].groupSort),
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].name),
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].sex=="M"?"男":"女"),
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].age),
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].zodiac),
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].lunarBirth),
	            $('<td style="vertical-align: middle;"></td>').html(custs[i].zhiHua),
	            $('<td style="vertical-align: middle;"></td>').html('<button type="button" class="btn btn-info btn-xs">記錄</button>'),
	            $('<td style="display:none;"></td>').html(custs[i].id)
	        )
	    );
	}
	
	//查詢 需帶入第一筆cust資料
	if(QRY == "Y"){
		$("#custTbl tr").eq(1).css('color', '#000000');
		$("#custTbl tr").eq(1).css('background-color', 'yellow');
		
		setCustVal(cid);
		QRY == "";
	}
	$("#men").text(m);
	$("#women").text(f);
}

function query(){
	if($.trim($("#name").val()) == "" && $.trim($(".cAddress").val()) == "" && $.trim($("#phone1").val()) == ""){
		alert("姓名、電話1、地址 請擇一條件查詢");
		return 
	}
	
	$("#associated option").remove();
	//$("#associated").append($("<option></option>").attr("value", "").text("-"));
	$.ajax({ 
		url:'queryCust',
		data:{ 
			name: $.trim($("#name").val()),
			phone: $.trim($("#phone1").val()),
			address: $.trim($(".cAddress").val())
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				var str = "";
				$.each(data.addrMap, function(key, value) {
					str = key;
					$("#associated").append($("<option></option>").attr("value", key).text(value));
				});
				//查詢後帶入第一筆客戶資料
				if(str != ""){
					changeCusts(str);
					QRY = "Y";
				}else{
					alert("查無資料");
				}
			}else{
				alert(data.message);
			}
		}, 
		error: function() { 
			alert("queryCust error...."); 
		} 
	});
}

function changeCusts(v){
	//農曆年
	var ary = [];
	var dt = new Date();
	ary.push(dt.getFullYear()-1911);
	ary.push(dt.getMonth()+1);
	ary.push(dt.getDate());
	
	$.ajax({ 
		url:'queryCustByGroup',
		data:{ 
			lunarNow: getLunar(ary),
			groupId: v
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				setCustTbl(data.custs);
			}else{
				alert(data.message);
			}
		}, 
		error: function() { 
			alert("queryCustByGroup error...."); 
		} 
	});
}


function initPage(){
	$("#uli li").each(function(index) {
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

function visible(div, id){
	if($("#custId").val() == "" || $("#addrId").val() == ""){
		alert("請選擇客戶資料");
		return;
	}
	
	initPage();
	$("#"+id).css("background-color", "red");
	//農曆年
	var ary = [];
	var dt = new Date();
	ary.push(dt.getFullYear()-1911);
	ary.push(dt.getMonth()+1);
	ary.push(dt.getDate());
	
	$.ajax({ 
		url:'queryCustByGroup',
		data:{ 
			lunarNow: getLunar(ary),
			groupId: $("#addrId").val()
		}, 
		type:'post', 
		cache:false, 
		dataType:'json', 
		success:function(data) { 
			if(data.status == "success"){ 
				if(div == 'sec1') setDonTbl(data.custs);
				if(div == 'sec2') setConTbl(data.custs);
				if(div == 'sec3') setLightTbl(data.custs);
				if(div == 'sec4') setGodStarTbl(data.custs);
				if(div == 'sec5') setStopResolveTbl(data.custs);
				if(div == 'sec6') setPligrimageTbl(data.custs);
				if(div == 'sec7') setWenchanTbl(data.custs);
				if(div == 'sec8') setPrinceStarTbl(data.custs);
				if(div == 'sec9') setPurdueTbl(data.custs);
				if(div == 'sec10') setChaodueTbl(data.custs);
				if(div == 'sec11') setAnniversaryTbl(data.custs);
				if(div == 'sec12') setLianghuangTbl(data.custs);
				if(div == 'sec13') setJieyuanTbl(data.custs);
				if(div == 'sec14') setBucaikuTbl(data.custs);
				if(div == 'sec15') setGoldTbl(data.custs);
				if(div == 'sec16') setItemsTbl(data.custs);
				
				$("#"+div).show();
			}else{
				alert(data.message);
			}
		}, 
		error: function() { 
			alert("visible queryCustByGroup error...."); 
		} 
	});
}
</script>
</head>
<body style="font-size:12px;">
<%@ include file="menu.jsp"%>
<div style="margin-top:20px;" align="center">
<table style="width:72%; border: 0px solid red;">
<tr>
	<td align="center" style="width:47%;">
		<div style="border: 1px solid #FFF; width:620px;height:750px; padding:10px;">
			<div style="width:100%; text-align:left; line-height:40px;">
				<input type="hidden" id="custId" />
				<input type="hidden" id="addrId" />
				<input type="hidden" id="actType" /> <!-- 新增 or 修改 -->
				<input type="hidden" id="groupSort" />
				
				<div class="form-inline">
					<b>類別</b>
					<select id="custType">
						<option value=""> - </option>
						<option value="01" selected="selected">一般香客</option>
						<option value="02">網路會員</option>
					</select>&nbsp;&nbsp;
					<b>姓名</b>
					<input type="text" id="name" style="width:43%;" autocomplete="off">&nbsp;&nbsp;
					<b>電話1</b>
					<input type="text" id="phone1" style="width:22%;" onblur="checkNum(this);" autocomplete="off">
				</div>
				<div class="form-inline">
					<b>性別</b>
					<select id="sex">
						<option value=""> - </option>
						<option value="M">男</option>
						<option value="F">女</option>
					</select>&nbsp;&nbsp;
					<b style="margin-left:45px;">身份證號</b>
					<input type="text" id="adNum" style="width:25%;" autocomplete="off">&nbsp;&nbsp;&nbsp;&nbsp;
					<b style="margin-left:66px;">電話2</b>
					<input type="text" id="phone2" style="width:22%;" onblur="checkNum(this);" autocomplete="off">
				</div>
				<div class="form-inline">
					<b>客戶地址</b>
					<span id="twzipcode"></span>&nbsp;&nbsp;
					<b>郵寄</b>
					<select id="isMail">
						<option value=""> - </option>
						<option value="Y" selected="selected">是</option>
						<option value="N">否</option>
					</select>
					<button type="button" id="qry" class="btn btn-info btn-xs" style="margin-left:170px;" onclick="query();">查詢</button>
				</div>
				<div class="form-inline">
					<div id="address">
					    <input type="hidden" class="cCountry" style="width: 25px;" value="TW" />
					    <input type="hidden" class="cZip" style="width: 35px;" />
					    <input type="text" class="form-control input-sm cAddress" style="width:72%; margin-left:75px;" autocomplete="off" />
					    <button type="button" class="btn btn-info btn-xs" style="margin-left:10px;" onclick="cleanField(1);">清除</button>
				    </div>
				</div>
				<div class="form-inline">
					<b>農曆生日</b>
					<input type="text" id="lunarBirth" style="width:18%;" onblur="fmtLunar(this.value);" autocomplete="off">&nbsp;&nbsp;
					<b>國曆生日</b>
					<input type="text" id="solarBirth" style="width:18%;" onblur="solarToLunar(this.value);" autocomplete="off">&nbsp;&nbsp;
					<b>時辰</b>
					<select id="birthHour">
						<option value=""> - </option>
						<option value="吉" selected="selected">吉00-00</option>
						<option value="子">子23-01</option>
						<option value="丑">丑01-03</option>
						<option value="寅">寅03-05</option>
						<option value="卯">卯05-07</option>
						<option value="辰">辰07-09</option>
						<option value="巳">巳09-11</option>
						<option value="午">午11-13</option>
						<option value="未">未13-15</option>
						<option value="申">申15-17</option>
						<option value="酉">酉17-19</option>
						<option value="戌">戌19-21</option>
						<option value="亥">亥21-23</option>
					</select>
				</div>
				<div class="form-inline">
					<b>電子信箱</b>
					<input type="text" id="email" style="width:28%;" autocomplete="off">&nbsp;&nbsp;
					<b>會員卡號</b>
					<input type="text" id="cardNum" style="width:28%;" onblur="checkNum(this);" autocomplete="off">&nbsp;&nbsp;
				</div>
				<div class="form-inline">
					<b>備註</b>
					<input type="text" id="notes" style="width:55%;" autocomplete="off">&nbsp;&nbsp;
					<b>啟用</b>
					<select id="isActive">
						<option value=""> - </option>
						<option value="Y" selected="selected">是</option>
						<option value="N">否</option>
					</select>&nbsp;&nbsp;
					<button type="button" class="btn btn-info btn-xs" onclick="cleanField(0);">新增</button>
					<button type="button" class="btn btn-info btn-xs" onclick="deleteCust();">刪除</button>
					<button type="button" class="btn btn-info btn-xs" onclick="save();">儲存</button>
				</div><br>
				<div class="form-inline">
					<select id="sysYear" onchange="alert(this.value);">
						<c:forEach items="${years}" var="y">
						<option value="${y.substring(0,3)}">${y}</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					<b>關聯戶</b>
					<select style="width:300px;" id="associated" onchange="changeCusts(this.value);">
						<option value=""> - </option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
					<b>人數：&nbsp;&nbsp;<span id="men">0</span>丁&nbsp;&nbsp;<span id="women">0</span>口</b>
				</div>
			</div>
			<br>
			<div style="width:100%; height:350px; overflow-y: scroll;">
				<table id="custTbl" class="table-bordered">
					<tr style="line-height:35px; text-align:center; background-color:#F7F7F7; color:#333; font-weight:bold;">
						<th style="text-align:center;">序號</th>
						<th style="text-align:center;">姓名</th>
						<th style="text-align:center;">性別</th>
						<th style="text-align:center;">年齡</th>
						<th style="text-align:center;">生肖</th>
						<th style="text-align:center;">農曆生日</th>
						<th style="text-align:center;">制化</th>
						<th style="text-align:center;">功能</th>
					</tr>
				</table>
			</div>
		</div>
	</td>
	<td align="center" style="width:6%;">
		<ul id="uli">
		  <li id="li_1" class="opt" onclick="visible('sec1', 'li_1');">油香</li>
		  <li id="li_2" class="opt" onclick="visible('sec2', 'li_2');">建設</li>
		  <li id="li_3" class="opt" onclick="visible('sec3', 'li_3');">點燈</li>
		  <li id="li_4" class="opt" onclick="visible('sec4', 'li_4');">玉皇上帝禮斗</li>
		  <li id="li_5" class="opt" onclick="visible('sec5', 'li_5');">制解</li>
		  <li id="li_6" class="opt" onclick="visible('sec6', 'li_6');">進香</li>
		  <li id="li_7" class="opt" onclick="visible('sec7', 'li_7');">文昌法會</li>
		  <li id="li_8" class="opt" onclick="visible('sec8', 'li_8');">五年千歲禮斗</li>
		  <li id="li_9" class="opt" onclick="visible('sec9', 'li_9');">普渡</li>
		  <li id="li_10" class="opt" onclick="visible('sec10', 'li_10');">超渡</li>
		  <li id="li_11" class="opt" onclick="visible('sec11', 'li_11');">建廟慶典</li>
		  <li id="li_12" class="opt" onclick="visible('sec12', 'li_12');">梁皇寶懺</li>
		  <li id="li_13" class="opt" onclick="visible('sec13', 'li_13');">解冤法會</li>
		  <li id="li_14" class="opt" onclick="visible('sec14', 'li_14');">補財庫</li>
		  <li id="li_15" class="opt" onclick="visible('sec15', 'li_15');">金牌捐獻</li>
		  <li id="li_16" class="opt" onclick="visible('sec16', 'li_16');">物品捐獻</li>
		</ul>
	</td>
	<td align="center" style="width:47%;">
		<div style="border: 1px solid #FFF; width:750px;height:750px; padding:10px;">
			<%@ include file="order_donation.jsp"%>
			<%@ include file="order_construction.jsp"%>
			<%@ include file="order_light.jsp"%>
			<%@ include file="order_god_star.jsp"%>
			<%@ include file="order_stop_resolve.jsp"%>
			<%@ include file="order_pilgrimage.jsp"%>
			<%@ include file="order_wenchan.jsp"%>
			<%@ include file="order_prince_star.jsp"%>
			<%@ include file="order_purdue.jsp"%>
			<%@ include file="order_chaodue.jsp"%>
			<%@ include file="order_anniversary.jsp"%>
			<%@ include file="order_lianghuang.jsp"%>
			<%@ include file="order_jieyuan.jsp"%>
			<%@ include file="order_bucaiku.jsp"%>
			<%@ include file="order_donate_items.jsp"%>
			<%@ include file="order_donate_gold.jsp"%>
		</div>
	</td>
</tr>
</table>
</div>
</body>
</html>