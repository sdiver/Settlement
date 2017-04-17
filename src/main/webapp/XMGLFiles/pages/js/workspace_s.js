/**
 * author:MT
 * since:2014-12-23
 * version:v1.0
 */
 //高度自适应函数
function SetIframe(){
	var h_frame = $('iframe[name=Main_Iframe]').contents().find('html').height(),
		h_window = $(window).height();
		//alert(h_frame+" "+h_window);
	if (h_frame>h_window) {
		//$('.Content').css('min-height',h_frame);
		$('iframe[name=Main_Iframe]').css('min-height',h_frame);
	}else{
		//$('.Content').css('min-height',h_window);
		$('iframe[name=Main_Iframe]').css('min-height',h_window);
	};
};
function SetHeight(){
	h_window = $(window).height()-110;
	// if (h_window >= 300) {
	// 	$("#SetContent").height(h_window);
	// } else {
	// 	$("#SetContent").height(300);
	// };
	$("#SetContent").height(h_window);
};
//菜单导航
function workspace_goPages(){
	location.href = "workspace_s.html?pageUrl=" + $(this).attr("data-url");
};

//ready函数
$(document).ready(function(){
	// //加载XML
	$(".topMenu .topMenu_right1 span[name=UserName]").html("欢迎登录，"+$.getCookie("userzhname")+"！");

	$(".Workspace .Menu .MenuList .MenuFirst li a").click(workspace_goPages);
	$("#SetContent").load($.getUrlParam("pageUrl"));

	//弹出修改密码框
	$(".topMenu a[name=setPassword]").click(function(){
		$('#setPassword input[type=password]').val("");
		$('#setPassword').show();
	});
	//密码修改确认按钮
	$('input[name=setPassword_ok]').click(function(){
		if ($("input[name=userpassword]").val() == "") {
			alert("请输入原密码！");
			return;
		};
		if ($("input[name=newpassword1]").val() == "" || $("input[name=newpassword2]").val() == "") {
			alert("请输入新密码！");
			return;
		};
		if ($("input[name=newpassword1]").val() != $("input[name=newpassword2]").val()) {
			alert("新密码不一致，请重新输入！");
			$("input[name=newpassword1]").val("");
			$("input[name=newpassword2]").val("");
			return;
		};

		$.ajax({
			type:"post",
			url:"listproject/changepwd.do",
			dataType:"json",
			data:{
				username:$.getCookie('username'),
				userpassword:$("input[name=userpassword]").val(),
				newpassword:$("input[name=newpassword1]").val()
			},
			success:function(data){
				if(data.result == "wrong chechcode"){
					alert("用户密钥已失效，请重新登录。");
					location.href = "/customermanager/";
					return;
				};
				if(data.result == "wrong"){
					alert("原密码错误！");
					return;
				};
				alert("密码修改成功，系统将自动登出！");
				location.href = "/customermanager/";
			},
			error:function(data){
				alert("数据错误，请重试！");
			}
		});
		$('#setPassword').hide();
	});
	$('input[name=setPassword_close]').click(function(){
		$('#setPassword').hide();
	});
	//注销
	$(".topMenu a[name=logout]").click(function(){
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
			location.href = "/ydxj/superadmin.html";
		};
	});
	//高度自适应
	SetHeight();
	$(window).resize(SetHeight);
});
