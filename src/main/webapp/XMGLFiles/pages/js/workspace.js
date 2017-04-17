/**
 * author:MT
 * since:2014-12-23
 * version:v1.0
 */
//函数

//菜单导航
function workspace_goPages(){
	location.href = "workspace.html?pageUrl=" + $(this).attr("data-url");
};

//ready函数
$(document).ready(function(){
	$(".header>.header_left>div").click(workspace_goPages);
	$("#Content").load($.getUrlParam("pageUrl"));
	
	//注销
	$(".header a[name=logout]").click(function(){
		var logout = confirm("确认注销吗？");
		if (logout == true) {
			$.delCookie('userId');
			$.delCookie('userzhname');
			$.delCookie('userarea');
			$.delCookie('roleId');
			$.delCookie('rolename');
			$.delCookie('rolerealname');
			$.delCookie('userstop');
			$.delCookie('authoritylist');
			location.href = "/ydxj/index.html";
		};
	});

	//数据初始化
	$(".header .header_right1 span[name=UserName]").html("欢迎登录，"+$.getCookie("userzhname")+"！");

	//事件
});
