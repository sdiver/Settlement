/**
 * author:MT
 * since:2015-02-11
 * version:v1.0
 */

//分页
function turnTo(){
	$(".main_M>div[data-m]").addClass("hidden");
	$("#"+$(this).attr("data-turnTo")).removeClass("hidden");
	
	//!!----------------
	loadTable_dealCase();
}

//发送到下一步
function send_1(s_eventId,s_caseId,s_step,s_userid,s_touserid,s_img){
	//图片处理
	var p = s_img.replace(/\+/g,"%2F");

	//补充信息
	$.ajax({
		type:'post',
		url:'judgeController/judge.do',
		datatype:'json',
		data:{
			eventId:s_eventId,
            eventinfo:$("textarea[name='text_eventinfo']").val(),
            Pic: s_img
		},
		success:function(data){
			//下一步
			$.ajax({
				type:'post',
				url:'flowController/checknextflow.do',
				datatype:'json',
				data:{
					caseId:s_caseId,
	                step:s_step,
	                userid:s_userid,
	                touserid:s_touserid
				},
				success:function(data){
					if(data.flowresult=="1"){
						alert("操作成功");
						$("#Task>.main_R .close").click();
						loadTable_dealCase();
					} else {
						alert("操作失败");
					};
				},
				error:function(data){}
			});
		},
		error:function(data){}
	});
}

//按钮集合
function loadBtns_dealCase_table(){
	//btn1-查看
	$("#dealCaselist a[data-btn1]").click(function(){
		var flag_caseId = $(this).parents("td[data-caseId]").attr("data-caseId");
		var flag_eventId = $(this).parents("td[data-eventId]").attr("data-eventId");
		var flag_flowchildint = $(this).parents("td[data-flowchildint]").attr("data-flowchildint");
		var flag_useridzhname = $(this).parents("td[data-useridzhname]").attr("data-useridzhname");

		//显示详细
		$("#Task>.main_M").addClass("q_right_25");
		$("#Task>.main_R").remove();
		var html_main_R = $("<div>").addClass("main_R").appendTo("#Task");
		
		//分页
		$("<div>").addClass("operation").text("待处理操作").appendTo(html_main_R);
		$("<div>").addClass("info").text("基本信息").appendTo(html_main_R);
		$("<div>").addClass("picture").text("案件取证").appendTo(html_main_R);
		$("<div>").addClass("history").text("历史记录").appendTo(html_main_R);
		$("<div>").addClass("close").text("X").appendTo(html_main_R);

		//分页事件
		$("#Task>.main_R .close").click(function(){ //关闭
			$("#Task>.main_R").remove();
			$("#Task>.main_M").removeClass("q_right_25");
		});
		//内容页 id=dealCase_content
		$("<div>").attr("id","dealCase_content").addClass("dealCase_content").appendTo(html_main_R);

		$("#Task>.main_R .operation").click(function(){ //operation
			$.ajax({
				type:'post',
				url:'judgeController/judgeinfomation.do',
				datatype:'json',
				data:{
					caseId:flag_caseId
				},
				success:function(data){
					$("#dealCase_content").empty();
					var html_card,html_card_up,html_card_down;
					var html_buttons;
					var flag_img = "";

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("信息中心建议").appendTo(html_card_up);
					$("<textarea>").attr("name","text_eventinfo").attr("placeholder","填写建议").appendTo(html_card_down);

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("补充证件").appendTo(html_card_up);
					
					//==图片 START	
    				//页面
    				$("<input>").attr("id","getImg").attr("type","file").appendTo(html_card_down);

    				//事件
    				$("#getImg").change(function() {
    					turnToBase64(this, function (data) {  
    						//操作
			                flag_img = data.substring(23);
			            });
    				});
    				function turnToBase64(input_file, get_data) {
    					/*input_file：文件按钮对象*/  
			            /*get_data: 转换成功后执行的方法*/  
			            if (typeof (FileReader) === "undefined") {  
			                alert("抱歉，你的浏览器不支持 FileReader，不能将图片转换为Base64，请使用谷歌浏览器操作！");  
			            } else {  
			                try {  
			                    /*图片转Base64 核心代码*/  
			                    var file = input_file.files[0];  
			                    //这里我们判断下类型如果不是图片就返回 去掉就可以上传任意文件  
			                    if (!/image\/\w+/.test(file.type)) {  
			                        alert("请确保文件为图像类型");  
			                        return false;  
			                    }  
			                    var reader = new FileReader();  
			                    reader.onload = function () {  
			                        get_data(this.result);  
			                    }  
			                    reader.readAsDataURL(file);  
			                } catch (e) {  
			                    alert('图片转Base64出错啦！' + e.toString())  
			                };  
			            };
    				};

					//==图片 END

					//按钮判断
					html_buttons = $("<div>").addClass("buttons").appendTo("#dealCase_content");
					if (data.judgeinfo.length == 0) {
						$("<div data-btn_operation1>").attr("data-step","1").addClass("absolute_0-50").text("待核销").appendTo(html_buttons);
						$("<div data-btn_operation2>").attr("data-step","0").addClass("absolute_50-100").text("整改").appendTo(html_buttons);
					} else {
						if (data.judgeinfo[data.judgeinfo.length-1].judgeinfo.flowId=="A3") { // 下一步退回
							$("<div data-btn_operation1>").attr("data-step","0").addClass("absolute_0-50").text("待核销").appendTo(html_buttons);
							$("<div data-btn_operation3>").attr("data-step","").addClass("absolute_50-100").text("退回").appendTo(html_buttons);
						} else {
							$("<div data-btn_operation1>").attr("data-step","1").addClass("absolute_0-50").text("待核销").appendTo(html_buttons);
							$("<div data-btn_operation2>").attr("data-step","0").addClass("absolute_50-100").text("整改").appendTo(html_buttons);
						};
					};
					//按钮事件绑定
					$("#dealCase_content .buttons div[data-btn_operation1],#dealCase_content .buttons div[data-btn_operation2]").click(function(){ //待核销,整改
						//clean
						$("#k_selectNext").removeClass("hidden");
						$("#k_selectNext input[name='k_selectNext_ok']").unbind();
						$("#k_selectNext input[name='k_selectNext_close']").unbind();
						$("#k_selectNext input[name='k_selectNext_close']").click(function(){
			            	$("#k_selectNext").addClass("hidden");
			            });

						//start
						var flag_step = $(this).attr("data-step");
						var flag_touserid = "";

						//获取人员列表列表
						$.ajax({
							type:'post',
							url:'flowController/nextflow.do',
							datatype:'json',
							data:{
								caseId:flag_caseId,
            					stepid:flag_step
							},
							success:function(data){
								var zTreeObj,
									setting = {
										view: {
									      selectedMulti: false
									    },
									    check:{
									    	enable: true,
									    	chkStyle: "radio"
									    },
									    callback:{
									    	onCheck: onCheck
									    }
									},
									zTreeNodes=[];
					            for(var i=0;i<data.userinfo.length;i++){
					            	zTreeNodes.push({
					            		"name":data.userinfo[i].userzhname,
					            		"touserid":data.userinfo[i].userId,
					            	    "checked": false
					    	        });
					            };
					            function onCheck(e, treeId, treeNode) {
					            	flag_touserid = treeNode.touserid;
					            };
					            zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);

					            //启用确认按钮
					            $("#k_selectNext input[name='k_selectNext_ok']").click(function(){
					            	if (flag_touserid == "") {
					            		alert("未选择发送人");
					            	} else {
					            		//s_eventId,s_caseId,s_step,s_userid,s_touserid
					            		send_1(flag_eventId,flag_caseId,flag_step,$.getCookie("userId"),flag_touserid,flag_img);
					            		$("#k_selectNext").addClass("hidden");
					            	};
					            });
							},
							error:function(data){}
						});
					});
					$("#dealCase_content .buttons div[data-btn_operation3]").click(function(){
						$.ajax({
							type:'post',
							url:'flowController/backflow.do',
							datatype:'json',
							data:{
								caseId:flag_caseId,
            					flowchildint:flag_flowchildint
							},
							success:function(data){
								if(data.flowresult=="1"){
									alert("案件已退回");
								} else {
									alert("案件退回失败");
								};
							},
							error:function(data){}
						});
					});
				},
				error:function(data){}
			});		
		});

		$("#Task>.main_R .info").click(function(){ //info
			$.ajax({
				type:'post',
				url:'judgeController/judgeinfomation.do',
				datatype:'json',
				data:{
					caseId:flag_caseId
				},
				success:function(data){
					$("#dealCase_content").empty();
					var html_card,html_card_up,html_card_down;
					var html_item;

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("基本信息").appendTo(html_card_up);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("报告日期：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].reportDate).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("报告人：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].userzhname).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("所属乡镇：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].area).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("所在村居：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].village).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("详细地址：").addClass("bottom_34").appendTo(html_item);
					$("<textarea>").attr("disabled","disabled").val(data.caseinfo[0].address).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("户主姓名：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].ownername).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("建设类型：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].contribute).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("初发现基本情况：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].firstfound).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("占地面积：").appendTo(html_item);
					$("<input>").attr("type","text").attr("disabled","disabled").val(data.caseinfo[0].acreage).appendTo(html_item);
					html_item = $("<div>").addClass("item").appendTo(html_card_down);
					$("<span>").text("案件备注：").addClass("bottom_34").appendTo(html_item);
					$("<textarea>").attr("disabled","disabled").val(data.caseinfo[0].caseinfo).appendTo(html_item);
				},
				error:function(data){
					alert("基本信息加载失败");
				}
			});
		});

		$("#Task>.main_R .picture").click(function(){ //picture
			$.ajax({
				type:'post',
				url:'judgeController/judgeinfomation.do',
				datatype:'json',
				data:{
					caseId:flag_caseId
				},
				success:function(data){
					$("#dealCase_content").empty();
					var html_card,html_card_up,html_card_down;
					var html_p,html_p1,html_p2,html_p3;

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("案件取证").appendTo(html_card_up);
					html_p = $("<div>").addClass("pics").appendTo(html_card_down);

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("证件1").appendTo(html_card_up);
					html_p1 = $("<div>").addClass("pics").appendTo(html_card_down);

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("证件2").appendTo(html_card_up);
					html_p2 = $("<div>").addClass("pics").appendTo(html_card_down);

					html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
					html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
					html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
					$("<span>").text("证件3").appendTo(html_card_up);
					html_p3 = $("<div>").addClass("pics").appendTo(html_card_down);

					for (var i = 0; i < data.inspection.length; i++) {
						switch(data.inspection[i].picType) {
							case "0":
								$("<img>").attr("src",data.inspection[i].picminiUrl).attr("data-openPic",data.inspection[i].picUrl).appendTo(html_p);
								break;
							case "1":
								$("<img>").attr("src",data.inspection[i].picminiUrl).attr("data-openPic",data.inspection[i].picUrl).appendTo(html_p1);
								break;
							case "2":
								$("<img>").attr("src",data.inspection[i].picminiUrl).attr("data-openPic",data.inspection[i].picUrl).appendTo(html_p2);
								break;
							case "3":
								$("<img>").attr("src",data.inspection[i].picminiUrl).attr("data-openPic",data.inspection[i].picUrl).appendTo(html_p3);
								break;
						};
					};

					//绑定图片点击事件
					$("#dealCase_content img[data-openPic]").click(function(){
						window.open($(this).attr("data-openPic"));
					});
				},
				error:function(data){}
			});
		});

		$("#Task>.main_R .history").click(function(){ //history
			$.ajax({
				type:'post',
				url:'judgeController/judgeinfomation.do',
				datatype:'json',
				data:{
					caseId:flag_caseId
				},
				success:function(data){
					$("#dealCase_content").empty();
					var html_card,html_card_up,html_card_down;
					var html_item;
					var html_p;

					for (var i = 0; i < data.judgeinfo.length; i++) {
						//data.judgeinfo[i]
						html_card = $("<div>").addClass("card").appendTo("#dealCase_content");
						html_card_up = $("<div>").addClass("card_up").appendTo(html_card);
						html_card_down = $("<div>").addClass("card_down").appendTo(html_card);
													//时间
						$("<span>").text("No." + i + "    " ).appendTo(html_card_up);

						html_item = $("<div>").addClass("item").appendTo(html_card_down);
						$("<span>").text("案件状态：").appendTo(html_item);
						$("<input>").attr("type","text").attr("disabled","disabled").val(data.judgeinfo[i].judgeinfo.flowInfo+"："+data.judgeinfo[i].judgeinfo.flowId).appendTo(html_item);
						html_item = $("<div>").addClass("item").appendTo(html_card_down);
						$("<span>").text("流程处理时间：").appendTo(html_item);
						$("<input>").attr("type","text").attr("disabled","disabled").val(data.judgeinfo[i].judgeinfo.operatetime).appendTo(html_item);
						html_item = $("<div>").addClass("item").appendTo(html_card_down);
						$("<span>").text("信息中心建议：").addClass("bottom_34").appendTo(html_item);
						$("<textarea>").attr("disabled","disabled").attr("placeholder","无").val(data.judgeinfo[i].judgeinfo.eventinfo).appendTo(html_item);

						html_item = $("<div>").addClass("item").appendTo(html_card_down);// 加载图片
						if (data.judgeinfo[i].eventpicinfo.length != 0) {
							$("<HR style='FILTER: alpha(opacity=100,finishopacity=0,style=2)' width='100%' color='#454545' SIZE='10' />").appendTo(html_item);
							html_p = $("<div>").addClass("pics").appendTo(html_item);
							for (var j = 0; j < data.judgeinfo[i].eventpicinfo.length; j++) { 
								$("<img>").attr("src",data.judgeinfo[i].eventpicinfo[j].miniPicUrl).attr("data-openPic",data.judgeinfo[i].eventpicinfo[j].picUrl).appendTo(html_p);
							};
						};
					};

					//绑定图片点击事件
					$("#dealCase_content img[data-openPic]").click(function(){
						window.open($(this).attr("data-openPic"));
					});
				},
				error:function(data){}
			});
		});

		$("#Task>.main_R .info").click();
	});
}

//读取TABLE
function loadTable_dealCase(){
	$.ajax({
		type:'post',
		url:'judgeController/myjudgelist.do',
		datatype:'json',
		data:{
			userId:"43df61fde28e40d19651d964692232a1",
        	authority:"2"
		},
		success:function(data){
			$("#dealCaselist").empty();
			for (var i = 0; i < data.judgeinfo.length; i++) {
				var html = $("<tr>").appendTo("#dealCaselist");
				$("<td>").text(data.judgeinfo[i].judgeinfo.caseNo).appendTo(html);
				$("<td>").text(data.judgeinfo[i].judgeinfo.operatetime).appendTo(html);
				$("<td>").text(data.judgeinfo[i].judgeinfo.useridzhname).appendTo(html);
				$("<td>").text(data.judgeinfo[i].judgeinfo.village).appendTo(html);
				$("<td>").text(data.judgeinfo[i].judgeinfo.area).appendTo(html);
				$("<td>").text(data.judgeinfo[i].judgeinfo.flowInfo).appendTo(html);
				var html_td = $("<td>").attr("data-caseId",data.judgeinfo[i].judgeinfo.caseId)
									   .attr("data-eventId",data.judgeinfo[i].eventId)
									   .attr("data-flowchildint",data.judgeinfo[i].flowchildint)
									   .attr("data-useridzhname",data.judgeinfo[i].judgeinfo.useridzhname)
									.appendTo(html);

				//按钮添加
				$("<a data-btn1>").text("查看").appendTo(html_td);
			};

			//按钮绑定
			loadBtns_dealCase_table();
		},
		error:function(data){}
	});
}

$(document).ready(function() {
	//数据初始化
	loadTable_dealCase();

	//事件绑定
	$(".main_L>div[data-turnTo]").click(turnTo);
});