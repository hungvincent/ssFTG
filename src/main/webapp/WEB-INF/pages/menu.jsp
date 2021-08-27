<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
#clock{
  color:white;
  font: 0.8em sans-serif; 
  background: black; 
  margin: 5px;
  padding: 5px;
  border: solid white 2px; 
  border-radius: 8px;
  width:160px;
  text-align:center;
}

@import url(https://fonts.googleapis.com/css?family=Raleway:300,700);
body {
	font-size: 0.7em;
 	font-size: 0.9vw; /* 支援chrome20 or IE9 以上 */
 	
	background: #048;
	background: -webkit-linear-gradient(left top, #027, #48a);
	background: -moz-linear-gradient(bottom right, #027, #48a);
	background: linear-gradient(to bottom right, #027, #48a);
	color: #fff;
	font-family: 'Raleway', Helvetica, Arial, sans-serif;
	font-size: 18px;
	font-weight: 300;
	line-height: 140%;
	margin: 0;
	min-height: 100vh;
	padding: 0;
	width: 100%;
}
h1.page_title{color:#fff;}
h4.what_to_do{color:#fff;}
h2.menu_title{color:#fff;}
.page_title,
.what_to_do {
	font-weight: 300;
	line-height: 120%;
	text-align: center;
	text-shadow: 0 1px 5px rgba(0,0,0,.8);
	text-transform: uppercase;
}

/* PEN STYLES ========== */
a,
.side_menu {
	-webkit-transition: all 300ms ease-in-out;
	transition: all 300ms ease-in-out;
}

/* MENU CONTAINER ----- */
.side_menu {
	background: rgba(0,20,60,.9);
	height: 100vh;
	left: -250px;
	position: absolute;
	top: 0;
	width: 250px;
}
.side_menu .container {
	padding: 0 1em;
}

/* HAMBURGER STYLES ----- */
.burger_box {
	display: block;
	float: right;
	margin-right: -45px;
}
.burger_box a.menu-icon {
	display: inline-block;
	float: none;
	height: 43px;
	padding: 10px;
	opacity: .5;
	width: 45px;
	z-index: 100;
}
.burger_box a.menu-icon:hover,
.burger_box a.menu-icon.opened {
	opacity: 1;
}
.burger_box a.menu-icon.opened {
	background: rgba(0,20,60,.9);
}
.burger_box .menu-icon_box {
	display: inline-block;
	height: 25px;
	position: relative;
	text-align: left;
	width: 25px;
}
.burger_box .menu-icon_line {
	background: #fff;
	border-radius: 2px;
	display: inline-block;
	height: 3px;
	position: absolute;
	width: 100%;
}
.burger_box .menu-icon_line--1 {
	top: 2px;
}
.burger_box .menu-icon_line--2 {
	top: 10px;
}
.burger_box .menu-icon_line--3 {
	top: 18px;
}
.burger_box .menu-icon_line--1 {
	transition: top 200ms 250ms, transform 200ms;
	-webkit-transition: top 200ms 250ms, -webkit-transform 200ms;
}
.burger_box .menu-icon_line--2 {
	transition: opacity 0ms 300ms;
	-webkit-transition: opacity 0ms 300ms;
}
.burger_box .menu-icon_line--3 {
	transition: top 100ms 300ms, transform 200ms;
	-webkit-transition: top 100ms 300ms, -webkit-transform 200ms;
}
.burger_box .menu-icon.opened .menu-icon_box {
	transform: scale3d(0.9, 0.9, 0.9);
	-webkit-transform: scale3d(0.9, 0.9, 0.9);
}
.burger_box .menu-icon.opened .menu-icon_line {
	top: 10px;
}
.burger_box .menu-icon.opened .menu-icon_line--1 {
	transform: rotate3d(0, 0, 1, 45deg);
	-webkit-transform: rotate3d(0, 0, 1, 45deg);
	transition: top 100ms, transform 200ms 250ms;
	-webkit-transition: top 100ms, -webkit-transform 200ms 250ms;
}
.burger_box .menu-icon.opened .menu-icon_line--2 {
	opacity: 0;
	transition: opacity 200ms;
	-webkit-transition: opacity 200ms;
}
.burger_box .menu-icon.opened .menu-icon_line--3 {
	transform: rotate3d(0, 0, 1, -45deg);
	-webkit-transform: rotate3d(0, 0, 1, -45deg);
	transition: top 200ms, transform 200ms 250ms;
	-webkit-transition: top 200ms, -webkit-transform 200ms 250ms;
}

/* STAGGER LIST ----- */
.list_load {
	display: none;
	list-style: none;
	padding: 0;
	line-height:40px;
}
.list_item {
	margin-left: -20px;
	opacity: 0;
	-webkit-transition: all 200ms ease-in-out;
	transition: all 200ms ease-in-out;
}
.list_item a {
	color: #fff;
	display: block;
	padding: 5px 10px;
	text-decoration: none;
}
.list_item a:hover {
	background: rgba(255,255,255,.2);
}
</style>

<script>
$(document).ready(function() {
	$(document).on('click','.js-menu_toggle.closed',function(e){
		e.preventDefault(); $('.list_load, .list_item').stop();
		$(this).removeClass('closed').addClass('opened');

		$('.side_menu').css({ 'left':'0px' });

		var count = $('.list_item').length;
		$('.list_load').slideDown( (count*.6)*100 );
		$('.list_item').each(function(i){
			var thisLI = $(this);
			timeOut = 100*i;
			setTimeout(function(){
				thisLI.css({
					'opacity':'1',
					'margin-left':'0'
				});
			},100*i);
		});
	});

	$(document).on('click','.js-menu_toggle.opened',function(e){
		e.preventDefault(); $('.list_load, .list_item').stop();
		$(this).removeClass('opened').addClass('closed');

		$('.side_menu').css({ 'left':'-250px' });

		var count = $('.list_item').length;
		$('.list_item').css({
			'opacity':'0',
			'margin-left':'-20px'
		});
		$('.list_load').slideUp(300);
	});
	
	startClock();
});

function startClock(){
	var dt = new Date();
	var yy = dt.getFullYear();
	var mon = dt.getMonth() + 1;
	var dd = dt.getDate();
	var hh = dt.getHours();
	var mm = dt.getMinutes();
	var ss = dt.getSeconds();
	mon = checkTime(mon);
	mm = checkTime(mm);
	ss = checkTime(ss);
	$('#clock').html(yy + "/" + mon + "/" + dd + " " + hh + ":" + mm + ":" + ss);
	var timeoutId = setTimeout(startClock, 500);
}

function checkTime(i){
  if(i < 10) {
    i = "0" + i;
  }
  return i;
}
</script>

<div style="width: 100%; background-color: black; line-height:60px;">
	<div style="text-align:right;">
		<span style="font: 0.8em sans-serif; text-align:right;">登入者：沈詩琦</span>
		<span id="clock"></span>
		<button type="button" class="btn btn-default btn-sm">修 改 密 碼</button>
		<button type="button" class="btn btn-default btn-sm">
			<span class="glyphicon glyphicon-log-out"></span> 登 出
	    </button>
	</div>
</div>

<div class="side_menu">
	<div class="burger_box">
		<div class="menu-icon-container">
			<a href="#" class="menu-icon js-menu_toggle closed">
				<span class="menu-icon_box">
					<span class="menu-icon_line menu-icon_line--1"></span>
					<span class="menu-icon_line menu-icon_line--2"></span>
					<span class="menu-icon_line menu-icon_line--3"></span>
				</span>
			</a>
		</div>
	</div>
	<div class="px-5">
		<h2 class="menu_title">&nbsp;</h2>
		<ul class="list_load">
			<li class="list_item"><a href="counterwork">前檯作業</a></li>
			<li class="list_item"><a href="offeringbox">賽錢箱</a></li>
			<li class="list_item"><a href="params">參數設定</a></li>
			<li class="list_item"><a href="#">報表列印</a></li>
			<li class="list_item"><a href="#">查詢統計</a></li>
			<li class="list_item"><a href="authority">權限設定</a></li>
		</ul>
	</div>
</div>
