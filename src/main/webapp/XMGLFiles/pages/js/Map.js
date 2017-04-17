/**
 * author:MT
 * since:2015-02-11
 * version:v1.0
 */



$(document).ready(function() {
	var zTreeObj,
		setting = {
		    check:{
		    	enable: true
		    },
		    data:{
		    	simpleData:{
		    		enable: true
		    	}
		    },
		    callback:{
		    	onCheck: onCheck
		    }
		},
		zTreeNodes=[];

    zTreeNodes = [
    	{ id:1, pId:0, name:"人员"},
		{ id:11, pId:1, name:"待审核"},
		{ id:12, pId:1, name:"待核销"},
		{ id:13, pId:1, name:"待整改"},
		{ id:2, pId:0, name:"车辆"},
		{ id:21, pId:2, name:"巡逻中"},
		{ id:22, pId:2, name:"执法中"},
		{ id:111, pId:11, name:"石祥仁"},
		{ id:112, pId:11, name:"陈浩鹏"},
		{ id:121, pId:12, name:"石祥仁"},
		{ id:122, pId:12, name:"陈浩鹏"},
		{ id:131, pId:13, name:"石祥仁"},
		{ id:132, pId:13, name:"陈浩鹏"}
    ];

    

    //树-方法
	function onCheck(e, treeId, treeNode) {
		alert(treeId + " " + treeNode.checked);
	};
    
    zTreeObj = $.fn.zTree.init($("#map_select_show"), setting, zTreeNodes);
});

