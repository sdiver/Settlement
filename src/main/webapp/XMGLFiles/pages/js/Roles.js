/**
 * author:MT
 * since:2015-02-11
 * version:v1.0
 */
function checkToken(){
	if(!($.getCookie("userid")) || !($.getCookie("tokencode"))){
		alert("用户密钥已失效，请重新登录。");
		location.href = "/customermanager/";
		return;
	}

	$.ajax({
		type:"post",
		url:"listproject/checkcode.do",
		dataType:"json",
		async:false,
		data:{
			userid:$.getCookie("userid"),
			access_code:$.getCookie("tokencode")
		},
		success:function(data){
			if(data.result != "1"){
				alert("用户密钥已失效，请重新登录。");
				location.href = "/customermanager/";
				return;
			}

			//函数加载
			DropBoxs("uniPerNum");
			LoadTable(1,$("input[name=uniPerNum]").attr("data-dropbox"));
			//下拉框table事件
			$(".DropBoxs[name=uniPerNum] dd ul li").click(function(){
				LoadTable(1,$(this).attr("data-dropbox"));
			});
		},
		error:function(data){
			alert("用户密钥已失效，请重新登录。");
			location.href = "/customermanager/";
			return;
		}
	});
};

/**
 * 表格加载函数
 * 目标页，页内数
 * 返回：跳转后页码
 */
function LoadTable(tagPage,tagNum){
	//ajax函数
	$.ajax({
		type:"post",
		url:"listproject/listselector.do",
		dataType:"json",
		data:{
			userid:$.getCookie("userid"),
			pageNum:tagPage,
			pagePerNum:tagNum,
			access_code:$.getCookie("tokencode")
		},
		success:function(data){
			if(data.result == "wrong chechcode"){
				alert("用户密钥已失效，请重新登录。");
				location.href = "/customermanager/";
				return;
			} 

			var html1 = "",html2 = "",sumPages = data.pagenum*1;
			tagPage = tagPage*1;

			$("table[name=FLTable] tbody").html(html1);
			$(".pageTurn").html(html2);
			for (var i = 0; i <= data.result.length - 1; i++) {
				html1 += "<tr><td>"+ data.result[i].cumstomerid 
					+"</td><td>"+ data.result[i].depart 
					+"</td><td>"+ data.result[i].customer 
					+"</td><td>"+ data.result[i].application 
					+"</td><td>"+ data.result[i].departmanager 
					+"</td><td><a FLTable_select='"+ data.result[i].cumstomerid +"' FLTable_select_name='"+ data.result[i].customer +"'>查看</a>" 
						+ (($.getCookie("userkey")=="2")?("<a FLTable_set='"+ data.result[i].cumstomerid +"' FLTable_set_name='"+ data.result[i].depart +"' FLTable_select_name='"+ data.result[i].customer +"'>增加删除</a>"):"")
					+"</td></tr>";
			};
			
			html2 = $.countPages(sumPages,tagPage);

			$("table[name=FLTable] tbody").html(html1);
			$(".pageTurn").html(html2);
			
			//table显示项目展示事件绑定
			$("table[name=FLTable] tbody th[data-sort]").click(function(){
				//排序

			});
			$("table[name=FLTable] tbody tr td a[FLTable_select]").click(function(){
				//页面跳转
				location.href = "workspace.html?pageUrl=XMGLPages/FLCX.html&flid="+$(this).attr("FLTable_select")+"&flname="+$(this).attr("FLTable_select_name");
			});
			$("table[name=FLTable] tbody tr td a[FLTable_set]").click(function(){
				//页面跳转
				location.href = "workspace.html?pageUrl=XMGLPages/MyFL.html&flid="+$(this).attr("FLTable_set")+"&flname="+$(this).attr("FLTable_set_name")+"&flname2="+$(this).attr("FLTable_select_name");
			});

			//a标签页面绑定css
			$(".pageTurn a[data-page="+tagPage+"]").addClass("pageTurnChecked");
			//a标签页面跳转事件绑定
			$(".pageTurn a").click(function(){
				//alert($(this).attr("data-page"));
				LoadTable($(this).attr("data-page"),$("input[name=uniPerNum]").attr("data-dropbox"));
			});
			//页面跳转事件绑定
			$("input[name=btn_uniPage]").unbind();
			$("input[name=btn_uniPage]").bind("click",function(){
				if ($("input[name=uniPage]").val() >= 1 && $("input[name=uniPage]").val() <= sumPages) {
					LoadTable($("input[name=uniPage]").val(),$("input[name=uniPerNum]").attr("data-dropbox"));
					$("input[name=uniPage]").val("");
				} else {
					alert("抱歉，请输入1至"+sumPages+"之间的页数。");
					LoadTable(tagPage,$("input[name=uniPerNum]").attr("data-dropbox"));
					$("input[name=uniPage]").val("");
				};
			});
		},
		error:function(data){
			alert("表格加载错误！");

		}
	});
};

function bind(){
	var flag_have_authority=[];
	var flag_roleid="";
	//加载权限列表树
	$.ajax({
		type:"post",
		url:"http://localhost:8080/ydxj/RoleAllowController/ShowAuthority.do",
		dataType:"json",
		async:false,
		success:function(data){
			var zTreeObj,
				setting = {
					view: {
				      selectedMulti: false
				    },
				    check:{
				    enable: true,
				    chkStyle: "checkbox"
				    }
				},
				zTreeNodes=[];
            for(var i=0;i<data.result.length;i++){
            	zTreeNodes.push({
            		"name":data.result[i].authorityInfo,
            		"authorityId":data.result[i].authorityId,
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
		flag_roleid = $(this).parents("td[data-roleId]").attr("data-roleId");
		//获取角色已有权限
	    $.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/listroleauthority.do",
			dataType:"json",
			async:false,
			data:{
                 roleId:flag_roleid
			},
			success:function(data){
				flag_have_authority=[];
				for(var i=0;i<data.authorityinfo.length;i++){
                    flag_have_authority.push({
                    	authorityId:data.authorityinfo[i].authorityId,
                    	authorityInfo:data.authorityinfo[i].authorityInfo
                    });
				}
			},
		    error:function(data){
				alert("系统错误！");
			}
	    });
	    //勾选已有权限
	    var treeObj = $.fn.zTree.getZTreeObj("tree");
	    treeObj.checkAllNodes(false);
		var nodes = treeObj.getNodes();
		for(var i=0;i<nodes.length;i++){
			for(var j=0;j<flag_have_authority.length;j++){
	    		if(flag_have_authority[j].authorityId==nodes[i].authorityId){
	    			treeObj.checkNode(nodes[i],true);        
	    		}
	    	}
	    }	
        /*$.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/ShowAuthority.do",
			dataType:"json",
			async:false,
			success:function(data){
				var zTreeObj,
					setting = {
						view: {
					      selectedMulti: false
					    },
					    check:{
					    enable: true,
					    chkStyle: "checkbox"
					    }
					},
					zTreeNodes=[];
                for(var i=0;i<data.result.length;i++){
                	zTreeNodes.push({
                		"name":data.result[i].authorityInfo,
                		"authorityId":data.result[i].authorityId,
                	    "checked": false
        	        });
                	for(var j=0;j<flag_have_authority.length;j++){
                		if(flag_have_authority[j].authorityId==data.result[i].authorityId){
                			zTreeNodes[i].checked= true ;        
                		}
                	}
                }
                zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
                for(var j=0;j<flag_have_authority.length;j++){
                		if(flag_have_authority[j].authorityId==data.result[i].authorityId){
                			zTreeNodes[i].checked= true ;        
                		}
                	}
			},
		    error:function(data){
				alert("系统错误！");
			}
	    });*/
		$('#k_roleslist').show();
	});
	$('input[name=roleslist_ok]').click(function(){
		//确认操作
        //var flag_roleid = $(this).parents("td[data-roleId]").attr("data-roleId");     
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getCheckedNodes(true);
        var addauthorityId = [],defaultauthorityId = [];

        //角色已有权限与新授权比对
 
        var temp=1;
        for(var i=0;i<flag_have_authority.length;i++){
        	for(var j=0;j<nodes.length;j++){ 		
                if(flag_have_authority[i].authorityId==nodes[j].authorityId){
                    temp=0;
                    //addauthorityId.push(nodes[j].authorityId);
                    break;
                }
                else{
                	temp=1;
                }
        	}
        	if(temp==1){
                defaultauthorityId.push(flag_have_authority[i].authorityId);
        	}
        }
        for(var j=0;j<nodes.length;j++){ 
        	addauthorityId.push(nodes[j].authorityId);
        }	
        //alert(typeof addauthorityId);
        //alert(JSON.stringify(addauthorityId));
    
        $.ajax({
			type:"post",
			url:"RoleAllowController/authority.do",
			dataType:"json",
			async:false,
			data:{
				roleId:flag_roleid,
                authorityId:JSON.stringify(addauthorityId),
                defaultauthorityId:JSON.stringify(defaultauthorityId)
			},
			success:function(data){
				if(data.Roleresult==1){
					alert("成功授权");
				}
				else{
					alert("未成功");
				}
			},
		    error:function(data){
				alert("系统错误！");
			}
	    });    
		$('#k_roleslist').hide();
	});
	$('input[name=roleslist_close]').click(function(){
		//清空值
        //$.fn.zTree.destroy("tree");
		$('#k_roleslist').hide();
	});

	//删除事件
	$('a[data-btn2]').click(function(){
		//alert($(this).parents("td[data-roleId]").attr("data-roleId"));
		$.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/deleteRole.do",
			data:{
				roleId:$(this).parents("td[data-roleId]").attr("data-roleId")
			},
			dataType:"json",
			success:function(data){
				if(data.roleresult=="1"){
				    alert("成功删除角色！");
				    Load();
				}
				else{
	                alert("删除不成功！");
				}
		    },
		    error:function(data){
				alert("系统错误！");
			}
	    });
	});

	//禁用事件
	$('a[data-btn3]').click(function(){
		alert("未开发");
	});
}
//加载角色
function Load(){
	$('#rolelist').html("");
	$.ajax({
		type:"post",
		url:"http://localhost:8080/ydxj/RoleAllowController/ShowRole.do",
		dataType:"json",
		success:function(data){
			var html1="";
			for(var i=0;i<data.roleresult.length;i++){
				html1='<tr>'+
				      '<td >'+ '启用' + '</td>'+
				      '<td>'+ data.roleresult[i].roleinfo.rolerealname + '</td>'+
				      '<td>'+ data.roleresult[i].roleinfo.roleinfo + '</td>'+
				      '<td data-roleId="'+data.roleresult[i].roleinfo.roleId+'"><a data-btn1>授权权限</a><a data-btn2>删除角色</a><a data-btn3>禁用</a></td>'+
				      '</tr>';
				$('#rolelist').append(html1);
			}
			bind();
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
	//新建角色
	$('#new_role').click(function(){

		$('#k_new_role').show();
	});
	$('input[name=new_role_ok]').click(function(){
		//确认操作
		if($('input[name=rolename]').val()!=''&$('input[name=rolerealname]').val()!=''&$('input[name=roleinfo]').val()!=''){
            $.ajax({
			type:"post",
			url:"http://localhost:8080/ydxj/RoleAllowController/NewRole.do",
			dataType:"json",
			data:{
				rolename:$('input[name=rolename]').val(),
				rolerealname:$('input[name=rolerealname]').val(),
				roleinfo:$('input[name=roleinfo]').val()
			},
			success:function(data){
				if(data.roleresult=="1"){
					alert("成功添加角色！");
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
     
            $('input[name=rolename]').val('');
	        $('input[name=rolerealname]').val('');
	        $('input[name=roleinfo]').val('');
			$('#k_new_role').hide();
			}
		else{
			alert("请填写完整信息！");
		}
	});
	$('input[name=new_role_close]').click(function(){
		//清空值
        $('input[name=rolename]').val('');
        $('input[name=rolerealname]').val('');
        $('input[name=roleinfo]').val('');
		$('#k_new_role').hide();
	});

});

