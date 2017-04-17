/**
 * author:MT
 * since:2017-3-12
 * version:v1.0
 */

/**
 * 初始化函数
 */
//Imt
function Imt(){
	if($.getCookie('userName')){
		$('input[name=userName]').val($.getCookie('userName'));
	};

	$('button[name=btn_login]').click(function(){
		if($('input[name=userName]').val() == "" || $('input[name=userPassword]').val() == "") {
			alert("请输入用户名与密码！");
		} else {
			send_login();
		}
	});

	//回车提交事件
	$("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	       $('button[name=btn_login]').click();
	    }
	});    
};

function send_login() {
	var s1 = "userOperate/login.do";
	var s2 = {
		userName:$('input[name=userName]').val(),
		userPassword:$('input[name=userPassword]').val()
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		async:true,
		data:s2,
		success:function(data){
			if(data.result == 0) {
            	var flag = "";
            	switch(data.result) {
	            	case 0:
	            		flag = "用户或密码错误";
	            		break;
	            	default:
	            		flag = "未识别登录错误";
            	};
            	alert(flag);
        	} else {
                $.setCookie('userName',$('input[name=userName]').val(),30);

                $.setCookie("pageId",0);

                //INFO
                $.setCookie('active_code',data.userInfo.active_code);    //token
                $.setCookie('phone_number',data.userInfo.phone_number);
                $.setCookie('town',data.userInfo.town);
                $.setCookie('user_ch_name',data.userInfo.user_ch_name);
                $.setCookie('user_id',data.userInfo.user_id);
                $.setCookie('user_name',data.userInfo.user_name);
                $.setCookie('user_type',data.userInfo.user_type);
                $.setCookie('user_type_id',data.userInfo.user_type_id);
                $.setCookie('village',data.userInfo.village);
                $.setCookie('work_address',data.userInfo.work_address);
                $.setCookie('work_area_id',data.userInfo.work_area_id);

                //权限
                var flag_authorityVoList = "";
                for (var i = 0; i < data.authorityVoList.length; i++) {
                	flag_authorityVoList += data.authorityVoList[i].ifAthority.toString();
                }
                $.setCookie('authorityVoList',flag_authorityVoList);

                //alert(parseInt(112111/Math.pow(10,3))%10);

				location.href = "workspace.html?pageUrl=app/pages/mainPage/mainPage.html";
				//location.href = "workspace.html?pageUrl=app/pages/settlementManage/settlementManage.html";
        	}
		},
		error:function(data){
			alert("系统正在维护中！");
		}
	});
}

//ready函数
$(document).ready(function(){	
	//初始化加载
	var slider = new SimpleSlider(document.getElementById('slider_login'));
	
	Imt();
});

