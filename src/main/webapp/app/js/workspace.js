/**
 * author:MT
 * since:2014-12-23
 * version:v1.0
 */
//函数

//菜单导航
function workspace_goPages(){
	location.href = "workspace.html?pageUrl=" + $(this).attr("data-url");
	$.setCookie("pageId",$(this).attr("data-id"));
};

//注销操作
function doLogout(){
	$.delCookie('active_code');
	$.delCookie('phone_number');
	$.delCookie('town');
	$.delCookie('user_ch_name');
	$.delCookie('user_id');
	$.delCookie('user_name');
	$.delCookie('user_type');
	$.delCookie('user_type_id');
	$.delCookie('village');
	$.delCookie('work_address');
	$.delCookie('work_area_id');
	$.delCookie('authorityVoList');

	location.href = "index.html";
}

//循环判断token
function send_checkToken(){
	try{
		if ($.getCookie("user_id")==null || $.getCookie("active_code")==null || $.getCookie("user_id")=="" || $.getCookie("active_code")=="") {
			alert("用户身份验证已过期，请重新登录！");
			doLogout();
		} else {
			//send
			var s1 = "userOperate/checkToken.do";
			var s2 = {
				userId:$.getCookie("user_id"),
				token:$.getCookie("active_code")
			};
			$.ajax({
				type:"post",
				url:s1,
				datatype:"json",
				data:s2,
				success:function(data){
					if (data == 0) {
						alert("用户身份验证已过期，请重新登录！");
						doLogout();
					}
				},
				error:function(error){
					alert("用户身份验证已过期，请重新登录！");
					doLogout();
				}
			});
		}
	} catch(err) {
		alert("用户身份验证已过期，请重新登录！");
		doLogout();
	}
		
}

//ready函数
$(document).ready(function(){
	//token验证 5min
	send_checkToken();
	setInterval("send_checkToken()",300000);

	//页面加载
	$("#Content").load($.getUrlParam("pageUrl"));
	
	//注销
	$("div[name=logout]").click(function(){
		var logout = confirm("确认注销吗？");
		if (logout == true) {
			doLogout();
		};
	});
	//跳转到设置页面
	$("div[name=setting]").click(workspace_goPages);


	//数据初始化
	$(".header .header_right1 span[name=UserName]").html("欢迎登录，"+$.getCookie("user_type")+"："+$.getCookie("user_ch_name")+"！");

	//菜单初始化
	//菜单配置文件
	var menus = [
		{
			id:"0",
			name:"首页",
			icon:"app/img/arrow.png",
			url:"app/pages/mainPage/mainPage.html",
			hasChild:false
		},
		{
			id:"1",
			name:"案件查询",
			icon:"app/img/arrow.png",
			url:"app/pages/settlementManage/settlementManage.html",
			hasChild:false
		},
		{
			id:"2",
			name:"通讯录管理",
			icon:"app/img/arrow.png",
			url:"app/pages/contact/contact.html",
			hasChild:false
		}
		// {
		// 	id:"1",
		// 	name:"test1",
		// 	icon:"app/img/setting.png",
		// 	url:"",
		// 	hasChild:true,
		// 	isOpen:true,
		// 	childs:[
		// 		{
		// 			id:"10",
		// 			name:"test10",
		// 			icon:"app/img/setting.png",
		// 			url:""
		// 		},
		// 		{
		// 			id:"11",
		// 			name:"test11",
		// 			icon:"app/img/setting.png",
		// 			url:""
		// 		}
		// 	]
		// }
	];

	//menu计算
	for (var i = 0; i < menus.length; i++) {
		var menu_child = $("<div>").addClass("menu_child").appendTo("#menu");

		var menu_up =  $("<div>").css("background","url("+menus[i].icon+")")
						.css("background-position","3px center")
						.css("background-repeat","no-repeat")
						.attr("data-id",menus[i].id)
						.attr("data-url",menus[i].url)
						.addClass("menu_up").appendTo(menu_child);
		$("<span>").html(menus[i].name).appendTo(menu_up);

		//二级菜单
		// if (menus[i].hasChild == true) {
		// 	menu_up = $("<div>").attr("data-menu_btn",menus[i].id).appendTo(menu_up); //div按钮

		// 	var menu_down = $("<div>").attr("data-menu_btn_show",menus[i].id).addClass("menu_down").appendTo(menu_child);
		// 	menu_up.attr("data-isOpen",menus[i].isOpen);
		// 	if (menus[i].isOpen == false) {
		// 		menu_down.addClass("hidden");
		// 	}
		// 	menu_down = $("<ul>").appendTo(menu_down);
		// 	for (var j = 0; j < menus[i].childs.length; j++) {
		// 		$("<li>").html(menus[i].childs[j].name).css("background","url(app/img/arrow.png)").appendTo(menu_down);
		// 	}
		// }
	}

	//菜单选中
	$(".menu_up[data-id="+ $.getCookie("pageId") +"]").addClass("checked");

	//页面跳转
	$(".menu .menu_up").click(workspace_goPages);

	// 二级菜单动画事件
	// $("div[data-menu_btn]").click(function(){
	// 	var flag_btn = $(this).attr("data-menu_btn");
	// 	var flag_isOpen = $(this).attr("data-isOpen");

	// 	$("div[data-menu_btn_show="+flag_btn+"]").slideToggle();
	// 	if (flag_isOpen == "true") {
	// 		alert(flag_isOpen);
	// 		$(".menu_up>div").addClass("close");
	// 	} else {
	// 		alert(flag_isOpen);
	// 		$(".menu_up>div").removeClass("close");
	// 	}
	// });

	var menu_child = $("<div>").addClass("menu_child").appendTo("#menu");
});
