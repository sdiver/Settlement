/**
 * author:MT
 * since:2017-3-23
 * version:v1.0
 */

function send_changePwd(){
	var s1 = "userOperate/changePwd.do";
	var s2 = {
		userId:$.getCookie("user_id"),
		userPassword:$("input[name=changePwd_userPassword]").val(),
		newPassword:$("input[name=changePwd_newPassword]").val()
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		data:s2,
		success:function(data){
			if (data.result == 1) {
				alert("密码修改成功！");
				$("input[name=changePwd_userPassword]").val("");
				$("input[name=changePwd_newPassword]").val("");
			} else {
				alert("密码输入错误！");
			}
		},
		error:function(error){}
	});
}

function send_modifyInfo(){
	var s1 = "userOperate/modifyInfo.do";
	var s2 = {
		userId:$.getCookie("user_id"),
		phoneNumber:$("input[name=modifyInfo_phoneNumber]").val(),
		workAddress:$("input[name=modifyInfo_workAddress]").val()
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		data:s2,
		success:function(data){
			if (data.result == 1) {
				alert("个人信息修改成功！");
				$.setCookie('phone_number',$("input[name=modifyInfo_phoneNumber]").val());
				$.setCookie('work_address',$("input[name=modifyInfo_workAddress]").val());
			} else {
				alert("个人信息修改失败！");
			}
		},
		error:function(error){}
	});
}

$(document).ready(function() {
	//个人信息初始化
	$("input[name=modifyInfo_phoneNumber]").val($.getCookie("phone_number"));
	$("input[name=modifyInfo_workAddress]").val($.getCookie("work_address"));

	//个人信息修改按钮
	$("button[name=btn_modifyInfo_ok]").click(function(){
		if ($("input[name=modifyInfo_phoneNumber]").val() == $.getCookie("phone_number") && $("input[name=modifyInfo_workAddress]").val() == $.getCookie("work_address")) {
			alert("您没有修改任何个人信息！");
		} else {
			var confirm_modifyInfo = confirm("确认是否修改个人信息？");
			if (confirm_modifyInfo == true) {
				send_modifyInfo();
			};
		}
	});

	//修改密码按钮
	$("button[name=btn_changePwd_ok]").click(function(){
		if ($("input[name=changePwd_userPassword]").val() == $("input[name=changePwd_newPassword]").val()) {
			alert("新密码不能与旧密码相同！");
		} else {
			var confirm_changePwd = confirm("确认是否修改密码？");
			if (confirm_changePwd == true) {
				send_changePwd();
			};
		}
	});
});