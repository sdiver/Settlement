function bind1(){
	var flag_userid='';
	//获取所有可分配角色
	$.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/ShowRole.do",
			dataType:"json",
			async:false,
			success:function(data){
				var zTreeObj,
					setting = {
					    check:{
					    enable: true,
					    chkStyle: "radio"
					    }
					},
					zTreeNodes=[];
                for(var i=0;i<data.roleresult.length;i++){
                	zTreeNodes.push({
                		"name":data.roleresult[i].roleinfo.rolerealname,
                		"roleId":data.roleresult[i].roleinfo.roleId,
                		"checked": false
        	        });
                }
                zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
			},
		    error:function(data){
				alert("系统错误！");
			}
	    });
    //分配权限事件
	$('a[data-btn1]').click(function(){
		flag_userid = $(this).parents("td[data-userId]").attr("data-userId");
		//var flag_roleid = $(this).parents("td[data-roleId]").attr("data-roleId");
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		//var nodes = treeObj.getNodes();
		//treeObj.cancelSelectedNode(nodes);
        treeObj.checkAllNodes(false);
	
	    $('#k_roleslist').show();
	});
	$('input[name=roleslist_ok]').click(function(){
		//确认操作
        //var flag_roleid = $(this).parents("td[data-roleId]").attr("data-roleId");     
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getCheckedNodes(true);
		//分配角色
	    $.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/AuthorityUser.do",
			dataType:"json",
			async:false,
			data:{
				userId:flag_userid,
                roleId:nodes[0].roleId
			},
			success:function(data){
				if(data.roleresult==1){
					alert("分配角色成功");
					$('#k_roleslist').hide();
					Load();
				}
				else{
					alert("分配角色失败");
					$('#k_roleslist').hide();
					Load();
				}
			},
		    error:function(data){
				alert("系统错误！");
			}
	    });
	});
	$('input[name=roleslist_close]').click(function(){
		//清空值
        //$.fn.zTree.destroy("tree");
		$('#k_roleslist').hide();
	});

	//禁用事件
	$('a[data-btn3]').click(function(){
		alert("未开发");
	});
}
function bind2(){
	//取消角色事件
	$('a[data-btn2]').click(function(){
		//alert($(this).parents("td[data-roleId]").attr("data-roleId"));
		var flag_roleid = $(this).parents("td[data-roleId]").attr("data-roleId");
		var flag_userid = $(this).parents("td[data-userId]").attr("data-userId");
		$.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/defaultAuthorityUser.do",
			data:{
				userId:flag_userid,
                roleId:flag_roleid
			},
			dataType:"json",
			success:function(data){
				if(data.roleresult=="1"){
				    alert("成功取消角色");
				    Load();
				}
				else{
	                alert("取消不成功！");
				}
		    },
		    error:function(data){
				alert("系统错误！");
			}
	    });
	});
}
//加载角色
function Load(){

	$('#rolelist').html("");
	$.ajax({
		type:"post",
		url:"http://localhost:8080/ydxj/RoleAllowController/ShowAuthorityUser.do",
		dataType:"json",
		async:false,
		success:function(data){
			var html1="";
			for(var i=0;i<data.result.length;i++){
				html1='<tr>'+
				      '<td >'+ '启用' + '</td>'+
				      '<td>'+ data.result[i].username + '</td>'+
				      '<td>'+ data.result[i].userzhname + '</td>'+
				      '<td>'+ data.result[i].userarea + '</td>'+
				      '<td>'+ data.result[i].rolerealname + '</td>'+
				      '<td data-userId="'+ data.result[i].userId + '"' +
				      'data-roleId="'+data.result[i].roleId+'"><a data-btn2>取消角色</a><a data-btn3>停用</a></td>'+
				      '</tr>';
				$('#rolelist').append(html1);
			}
			bind2();
	    },
	    error:function(data){
			alert("系统错误！");
		}
	});
	$.ajax({
		type:"post",
		url:"http://localhost:8080/ydxj/RoleAllowController/ShowNoAuthorityUser.do",
		dataType:"json",
		async:false,
		success:function(data){
			var html1="";
			for(var i=0;i<data.result.length;i++){
				html1='<tr>'+
				      '<td >'+ '启用' + '</td>'+
				      '<td>'+ data.result[i].username + '</td>'+
				      '<td>'+ data.result[i].userzhname + '</td>'+
				      '<td>'+ data.result[i].userarea + '</td>'+
				      '<td>'+ '未分配' + '</td>'+
				      '<td data-userId="'+ data.result[i].userId + '"' +
				      'data-roleId="'+data.result[i].roleId+'"><a data-btn1>分配角色</a><a data-btn3>停用</a></td>'+
				      '</tr>';
				$('#rolelist').append(html1);
			}
			bind1();
	    },
	    error:function(data){
			alert("系统错误！");
		}
	});
}

$(document).ready(function() {
	//checkToken();

	//函数加载
	// DropBoxs("uniPerNum");
	//LoadTable(1,$("input[name=uniPerNum]").attr("data-dropbox"));
	//下拉框table事件
	// $(".DropBoxs[name=uniPerNum] dd ul li").click(function(){
	// 	LoadTable(1,$(this).attr("data-dropbox"));
	// });
    Load();
	//新建用户
	$('#new_role').click(function(){
        $('#arealist').html("");
        $.ajax({
		type:"post",
		url:"http://localhost:8080/ydxj/CaseController/listareatwo.do",
		dataType:"json",
		success:function(data){
			var html1="";
			for(var i=0;i<data.arealist.length;i++){
				html1='<option value="'+ data.arealist[i].userareaid +'">'+
				       data.arealist[i].area +
				      '</option>';
				$('#arealist').append(html1);
			}
	    },
	    error:function(data){
			alert("系统错误！");
		}
	});
		$('#k_new_role').show();
	});
	$('input[name=new_role_ok]').click(function(){
		//确认操作
		//alert($('#arealist').find("option:selected").text());
		if($('input[name=username]').val()!=''&$('input[name=userpwd]').val()!=''&$('input[name=userzhname]').val()!=''){
            $.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/UserController/NewUser.do",
			dataType:"json",
			data:{
				username:$('input[name=username]').val(),
				userpwd:$('input[name=userpwd]').val(),
				userzhname:$('input[name=userzhname]').val(),
				userareaid:$('#arealist').val()
			},
			success:function(data){
				if(data.userresult=="1"){
					alert("成功添加用户！");
					Load();
				}
				else{
                    alert("添加不成功！");
				}

		    },
		    error:function(data){
				alert("系统错误！");

			}
		});
     
            $('input[name=username]').val('');
	        $('input[name=userpwd]').val('');
	        $('input[name=userzhname]').val('');
			$('#k_new_role').hide();
			}
		else{
			alert("请填写完整信息！");
		}
	});
	$('input[name=new_role_close]').click(function(){
		//清空值
        $('input[name=username]').val('');
        $('input[name=userpwd]').val('');
        $('input[name=userzhname]').val('');
		$('#k_new_role').hide();
	});

});