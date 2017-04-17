/**
 * author:MT
 * since:2017-3-23
 * version:v1.0
 */

function send_listContact(){
	var s1 = "contact/listContact.do";
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
			
		},
		error:function(error){}
	});
}

$(document).ready(function() {
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			selectedMulti: false
		},
		callback: {
			onClick: onClick
		}
	};

	send_listContact();
	//icon:"", iconOpen:"", iconClose:""  16x16
	var zNodes =[
		{ id:1, pId:0, name:"展开、折叠 自定义图标不同", open:true},
		{ id:11, pId:1, name:"叶子节点1"},
		{ id:12, pId:1, name:"叶子节点2"},
		{ id:13, pId:1, name:"叶子节点3"},
		{ id:2, pId:0, name:"展开、折叠 自定义图标相同", open:true},
		{ id:21, pId:2, name:"叶子节点1"},
		{ id:22, pId:2, name:"叶子节点2"},
		{ id:233, pId:22, name:"叶子节点3"},
		{ id:3, pId:0, name:"不使用自定义图标", open:true },
		{ id:31, pId:3, name:"叶子节点1"},
		{ id:32, pId:3, name:"叶子节点2"},
		{ id:33, pId:3, name:"叶子节点3"}
	];

	function onClick(event, treeId, treeNode, clickFlag) {
		
	}	

	$.fn.zTree.init($("#treeDemo"), setting, zNodes);


	//刷新按钮
	$("button[name=btn_listContact]").click(function(){
		
	});

	//新增按钮
	$("button[name=btn_addPoliceContact]").click(function(){
		
	});
});