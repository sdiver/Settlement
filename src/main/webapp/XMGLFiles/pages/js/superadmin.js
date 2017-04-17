/**
 * author:MT
 * since:2015-02-09
 * version:v1.1
 */

/**
 * 初始化函数
 */
//Imt
function Imt(){
	$('input[name=SavePwd]').on('ifUnchecked',function(){
		$('input[name=AutoLogin]').iCheck('uncheck');
	});
	$('input[name=AutoLogin]').on('ifChecked',function(){
		$('input[name=SavePwd]').iCheck('check');
	});
	if($.getCookie('username')){
		$('input[name=username]').val($.getCookie('username'));
	};
	if($.getCookie('userpwd')){
		$('input[name=userpwd]').val($.getCookie('userpwd'));
	};
	if($.getCookie('SavePwd') == 'true'){
		$('input[name=SavePwd]').iCheck('check');
	};
	if($.getCookie('AutoLogin') == 'true'){
		$('input[name=AutoLogin]').iCheck('check');
		//$("#form_login").submit();
	};
	$('input[name=signup]').click(function(){
		$.ajax({
			type:'post',
			url:'http://localhost:8080/ydxj/UserController/login.do',
			datatype:'json',
			async:false,
			data:{
				username:$('input[name=username]').val(),
				userpwd:$('input[name=userpwd]').val()
			},
			success:function(data){

				if (data.userresult) {
	            	var flag = "";
	            	switch(data.userresult) {
		            	case 8:
		            		flag = "用户不存在";
		            		break;
	            		case 9:
	            			flag = "密码错误";
		            		break;
		            	case 10:
	            			flag = "用户已停用";
		            		break;
		            	default:
		            		flag = "未识别登录错误";
	            	};
	            	alert(flag);
            	}
            	else {
                    $.setCookie('username',$('input[name=username]').val(),30);


                    $.setCookie('userId',data.userinfo.userId);
					$.setCookie('userzhname',data.userinfo.userzhname);
					$.setCookie('userarea',data.userinfo.userarea);
					$.setCookie('roleId',data.userinfo.roleId);
					$.setCookie('rolename',data.userinfo.rolename);
					$.setCookie('rolerealname',data.userinfo.rolerealname);
					$.setCookie('userstop',data.userinfo.userstop);

					var flag_authoritylist="";
					for(var i=0;i<data.authoritylist.length;i++){
						if(i=="0"){
						    flag_authoritylist=data.authoritylist[i].authorityId;
						}
						else{
							flag_authoritylist=flag_authoritylist+","+data.authoritylist[i].authorityId;
						}
					}
					$.setCookie('authoritylist',flag_authoritylist);

					if($('input[name=SavePwd]').is(':checked')){
						$.setCookie('userpwd',$('input[name=userpwd]').val(),30);
						$.setCookie('SavePwd',String($('input[name=SavePwd]').is(':checked')),30);
						$.setCookie('AutoLogin',String($('input[name=AutoLogin]').is(':checked')),30);
					}
					else{
						$.delCookie('userpwd');
						$.delCookie('SavePwd');
						$.delCookie('AutoLogin');
						$('input[name=AutoLogin]').iCheck('uncheck');
					}
					location.href = "workspace_s.html?pageUrl=XMGLPages/Roles.html";
            	}
			},
			error:function(data){
				alert("系统正在维护");
			}
		});
	});
};
//checkbox
function CheckBoxs(){
	$('input').iCheck({
	    checkboxClass: 'CheckBox',
	    radioClass: 'RadioBox',
	    increaseArea: '0%' // optional
	  });
}

//ready函数
$(document).ready(function(){	
	//初始化加载
	CheckBoxs();
	
	Imt();
});

