/**
 * author:MT
 * since:2017-3-15
 * version:v1.0
 */

//全局村列表
var GLOBAL_VILLAGELIST = [];
var GLOBAL_PAGE = 1;

/* 
 * page:跳转页
 */
function loadTable(page){
	var s1 = "settlementSearch/settleStatistics.do";
	var s2 = {
		userId:$.getCookie("user_id"),
		token:$.getCookie("active_code"),
		startTime:$("#startTime").val(),
		endTime:$("#endTime").val(),
		regionId:$("input[name=s_regionId]").val(),
		forensicsType:$("select[name=s_forensicsType]").val(),
		status:$("input[name=s_status]").val(),
		Page:page,
		pagePerNum:$("#pageNum").val()
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		data:s2,
		success:function(data){
			GLOBAL_PAGE = page;
			//数据初始化
			var flag_list = data.settleinfoList;
			var flag_items = data.settlementStatistics.count_case;
			var flag_sum_pay = data.settlementStatistics.sum_pay;

			//列表展示
			var html = "";
			$("#dealData").html("");
			for (var i = 0; i < flag_list.length; i++) {
				html = $("<tr>").appendTo($("#dealData"));

				var html_2 = $("<td>").appendTo(html);
				$("<input>").attr("type","checkbox").attr("check-case_code",flag_list[i].case_code).appendTo(html_2);

				$("<td>").html(flag_list[i].rowno).appendTo(html);
				$("<td>").html(flag_list[i].case_code).appendTo(html);

				//改图标
				$("<td>").html(flag_list[i].caseType).appendTo(html);

				$("<td>").html($.getJsonDate(flag_list[i].case_create)).appendTo(html);
				$("<td>").html(flag_list[i].case_reporter).appendTo(html);
				$("<td>").html(flag_list[i].caseSchedule).appendTo(html);
				$("<td>").html(flag_list[i].caseStatus==""?"-":flag_list[i].caseStatus).appendTo(html);
				$("<td>").html(flag_list[i].sum_pay).appendTo(html);
				$("<td>").html(flag_list[i].town).appendTo(html);
				$("<td>").html(flag_list[i].village).appendTo(html);

				//按钮
				html = $("<td>").appendTo(html);
				$("<a>").attr("data-case_code",flag_list[i].case_code).addClass("btn btn_blue").html("查看").appendTo(html);
			}

			//按钮事件
			$("a[data-case_code]").click(function(){
				send_settleinfo($(this).attr("data-case_code"));
			});

			//统计展示
			var html_label_summary = "汇总信息： 镇：<strong>" + $("input[name=s_regionId]").attr("data-town")
			+ "</strong> 村：<strong>" + $("input[name=s_regionId]").attr("data-village")
			+ "</strong> 案件类型：<strong>" + $("select[name=s_forensicsType]").find(":selected").text() 
			+ "</strong> 案件进度：<strong>" + $("input[name=s_status]").attr("data-t")
			+ "</strong> 案件数：<strong>" + flag_items
			+ "</strong> 件 已理赔金额：<strong>" + flag_sum_pay
			+ "</strong> 元";
			$("label[name=summary]").html(html_label_summary);

			//翻页按钮刷新
			var html_page = "";
			$(".PageButton .pageTurn").html("");
			$($.countPages( parseInt(flag_items/$("#pageNum").val())+1, page)).appendTo($(".PageButton .pageTurn"));
			//页码选中样式绑定
			$(".PageButton .pageTurn a[data-page="+page+"]").addClass("pageTurnChecked");
			//翻页事件绑定
			$(".PageButton .pageTurn a[data-page]").click(function(){
				loadTable($(this).attr("data-page"));
			});

			//全勾选重绑定
			$("input[check-all]").unbind();
			$("input[check-all]").change(function(){
				$("input[check-case_code]").prop("checked",$(this).prop("checked"));
			});

			//跳转按钮重绑定
			$("button[name=btn_pageGoto]").unbind();
			$("button[name=btn_pageGoto]").click(function(){
				var flag_goto = $("input[name=txt_pageGoto]").val();
				if (flag_goto >=1 && flag_goto <= parseInt(flag_items/$("#pageNum").val())+1) {
					loadTable(flag_goto);
				} else {
					alert("请输入1至"+ (parseInt(flag_items/$("#pageNum").val())+1)+"之间的数字。");
				}
				$("input[name=txt_pageGoto]").val("");
			});
		},
		error:function(data){}
	});
}

//详细案件
function send_settleinfo(case_code) {
	//打开面板
	$("#div_info").removeClass("hidden");
	$("#div_table").addClass("hidden");

	//send
	var s1 = "settlementSearch/settleinfo.do";
	var s2 = {
		userId:$.getCookie("user_id"),
		token:$.getCookie("active_code"),
		caseCode:case_code
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		data:s2,
		success:function(data){
			var flag_info = data.caseinfo;

			//基础信息
			$("input[name=info_case_code]").val(flag_info.case_code);
			$("input[name=info_sum_pay]").val(flag_info.sum_pay);
			$("input[name=info_user_ch_name]").val(flag_info.user_ch_name);
			$("input[name=info_case_reporter]").val(flag_info.case_reporter);
			$("input[name=info_case_create]").val($.getJsonDate(flag_info.case_create));
			$("input[name=info_case_time]").val($.getJsonDate(flag_info.case_time));
			$("input[name=info_phone_number]").val(flag_info.phone_number);
			$("input[name=info_case_cellphone_number]").val(flag_info.case_cellphone_number);
			$("input[name=info_caseSchedule]").val(flag_info.caseSchedule);
			$("input[name=info_caseStatus]").val(flag_info.caseStatus);
			$("input[name=info_town]").val(flag_info.town);
			$("input[name=info_village]").val(flag_info.village);
			$("input[name=info_case_address]").val(flag_info.case_address);

			//案件tab信息
			//tab 屋主:h 所有者:o
			if (flag_info.is_household != 1) {
				$("a[show=menu_4]").parent().addClass("hidden");
			}
			if (flag_info.is_owner != 1) {
				$("a[show=menu_5]").parent().addClass("hidden");
			}

			//展示区清空
			$("div[name=forensics_type_1]").html("");
			$("div[name=forensics_type_2]").html("");
			$("div[name=forensics_type_3]").html("");
			$("div[name=forensics_type_4]").html("");
			$("div[name=forensics_type_5]").html("");
			$("div[name=forensics_type_6]").html("");
			$("div[name=forensics_type_7]").html("");
			$("div[name=forensics_type_20]").html("");
			$("#menu_2").html("");
			$("<p>").html("事发地址：").appendTo("div[name=forensics_type_1]");
			$("<p>").html("110报案回执单：").appendTo("div[name=forensics_type_2]");
			$("<p>").html("户口簿-户主页：").appendTo("div[name=forensics_type_3]");
			$("<p>").html("身份证信息：").appendTo("div[name=forensics_type_4]");
			$("<p>").html("户主银行卡：").appendTo("div[name=forensics_type_5]");
			$("<p>").html("报案人与户主关系证明：").appendTo("div[name=forensics_type_6]");
			$("<p>").html("财务所有者与户主关系：").appendTo("div[name=forensics_type_7]");

			//完成案件modal清空
			$("#modal_caseExpected .row").remove();

			//展示
			var flag_menu_2 = [];
			for (var i = 0; i < flag_info.forensicsInfoList.length; i++) {
				var html = "",html_up = "",html_down = "";
				var flag_isExist = false;
				switch(flag_info.forensicsInfoList[i].forensics_type) {
					case 1: //事发地址
					case 2: //110报案回执单
					case 3: //户口簿-户主页
					case 4: //身份证正反面 h
					case 5: //户主银行卡 
					case 6: //报案人与户主关系证明 h
					case 7: //财务所有者与户主关系 o
					case 20: //案件笔录
						html = $("<div>").addClass("group group-img").appendTo("div[name=forensics_type_"+ flag_info.forensicsInfoList[i].forensics_type +"]");
					break;
					default:
						for (var j = 0; j < flag_menu_2.length; j++) {
							if (flag_info.forensicsInfoList[i].forensics_id == flag_menu_2[j]) {
								//列表中存在
								html = $("<div>").addClass("group group-img").appendTo("div[name=forensics_id_"+ flag_info.forensicsInfoList[i].forensics_id +"]");

								flag_isExist = true;
							}
						}
						if (flag_isExist == false) { //列表中不存在
							html = $("<div>").addClass("row border_bottom").attr("name","forensics_id_" + flag_info.forensicsInfoList[i].forensics_id).appendTo("#menu_2");
							$("<p>").html("证据ID："+flag_info.forensicsInfoList[i].forensics_id+"，类型："+flag_info.forensicsInfoList[i].species+"，已理赔金额："+flag_info.forensicsInfoList[i].amount_of_pay+"，更新时间："+flag_info.forensicsInfoList[i].update_time).appendTo(html);
							switch (flag_info.forensicsInfoList[i].heading) {
								case 3:
									$("<p>").html("损失金额："+flag_info.forensicsInfoList[i].lost_amount).appendTo(html);
								break;
								case 4:
									$("<p>").html("损失数量："+flag_info.forensicsInfoList[i].lost_amount+"，损失重量："+flag_info.forensicsInfoList[i].lost_weight).appendTo(html);
								break;
							}
							html = $("<div>").addClass("group group-img").appendTo("div[name=forensics_id_"+ flag_info.forensicsInfoList[i].forensics_id +"]");

							//添加至列表中
							flag_menu_2.push(flag_info.forensicsInfoList[i].forensics_id);

							//完成案件modal加载
							var html_modal_caseExpected = $("<div>").addClass("row").appendTo("#modal_caseExpected .card");
							html_modal_caseExpected = $("<div>").addClass("group width_100").appendTo(html_modal_caseExpected);
							$("<p>").html("证据ID："+flag_info.forensicsInfoList[i].forensics_id+"，类型："+flag_info.forensicsInfoList[i].species).appendTo(html_modal_caseExpected);
							$("<label>").html("理赔金额：").appendTo(html_modal_caseExpected);
							$("<input>").attr("type","text").attr("name","modal_caseExpected_pay").attr("data-id",flag_info.forensicsInfoList[i].id).appendTo(html_modal_caseExpected);
							$("input[name=modal_caseExpected_case_code]").val(flag_info.case_code);
						}
				}
				//添加图片、按钮与绑定按钮信息
				$("<img>").attr("src",flag_info.forensicsInfoList[i].photo_mini_url).appendTo(html);
				html_up = $("<button>").addClass("up btn btn_blue").attr("do-photo_url",flag_info.forensicsInfoList[i].photo_url).appendTo(html);
				$("<i>").addClass("fa fa-search-plus").appendTo(html_up);
				html_down = $("<button>").addClass("down btn btn_red").attr("do-open_modal_sendBackReason","")
				.attr("data-forensicsId",flag_info.forensicsInfoList[i].forensics_id)
				.attr("data-caseForensics",flag_info.case_forensics)
				.attr("data-photoId",flag_info.forensicsInfoList[i].photo_id)
				.attr("data-case_code",flag_info.case_code)
				.attr("data-sendBackReason",flag_info.forensicsInfoList[i].sent_back_reason)
				.appendTo(html);
				$("<i>").addClass("fa fa-mail-reply").appendTo(html_down);
			}
			//图片按钮组事件绑定
			$("button[do-photo_url]").click(function(){
				$("img[name=modal_show_photo_img]").attr("src",$(this).attr("do-photo_url"));

				$("#modal_show_photo").removeClass("hidden");
			});
			$("button[do-open_modal_sendBackReason]").click(function(){
				//modal_sendBackReason 退回原因弹窗赋值
				$("input[name=modal_sendBackReason_forensicsId]").val($(this).attr("data-forensicsId"));
				$("input[name=modal_sendBackReason_caseForensics]").val($(this).attr("data-caseForensics"));
				$("input[name=modal_sendBackReason_photoId]").val($(this).attr("data-photoId"));
				$("input[name=modal_sendBackReason_case_code]").val($(this).attr("data-case_code"));
				$("textarea[name=modal_sendBackReason_sendBackReason]").val($(this).attr("data-sendBackReason"));

				$("#modal_sendBackReason").removeClass("hidden");
			});

			

			//案件下载按钮信息绑定
			if (flag_info.zip_url == null) {
				$("button[name=btn_downZIP]").addClass("hidden");
			} else {
				$("button[name=btn_downZIP]").attr("data-zip_url",flag_info.zip_url);
			}
			//退回按钮 完成按钮展示计算
			if (flag_info.case_status != 1) {
				$("button[name=btn_sendBack]").addClass("hidden");
				$("button[name=btn_caseExpected]").addClass("hidden");
			} else {
				$("button[name=btn_sendBack]").removeClass("hidden");
				$("button[name=btn_caseExpected]").removeClass("hidden");
			}

			//退回按钮信息绑定
			$("button[name=btn_sendBack]").attr("data-case_code",flag_info.case_code);

			//完成案件modal - input正则
			$("input[name=modal_caseExpected_pay]").keyup(function(){
				 this.value = this.value.replace(/[^\d]/g,'');
			});
		},
		error:function(error){}
	});
}

//退回原因
function send_sendBackReason() {
	//send
	var s1 = "settlementManage/sendBackReason.do";
	var s2 = {
		userId:$.getCookie("user_id"),
		token:$.getCookie("active_code"),
		forensicsId:$("input[name=modal_sendBackReason_forensicsId]").val(),
		caseForensics:$("input[name=modal_sendBackReason_caseForensics]").val(),
		photoId:$("input[name=modal_sendBackReason_photoId]").val(),
		sendBackReason:$("textarea[name=modal_sendBackReason_sendBackReason]").val()
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		data:s2,
		success:function(data){
			if (data.result == 1) {
				alert("退回原因修改成功！");
				$("#modal_sendBackReason").addClass("hidden");
				//刷新info
				send_settleinfo($("input[name=modal_sendBackReason_case_code]").val());
			} else {
				alert("退回原因修改失败！");
			}
		},
		error:function(error){}
	});
}

//完成案件
function send_caseExpected() {
	var flag_is = true;

	$("input[name=modal_caseExpected_pay]").each(function(){
		if ($(this).val() == "") {
			flag_is = false;
		}
	});

	if (flag_is) {
		//计算传输数据
		var flag_amountOfPay = "",
			flag_id = "";

		$("input[name=modal_caseExpected_pay]").each(function(){
			flag_amountOfPay += $(this).val() + ",";
			flag_id += $(this).attr("data-id") + ",";
		});
		flag_amountOfPay = flag_amountOfPay.substring(0,flag_amountOfPay.length-1);
		flag_id = flag_id.substring(0,flag_id.length-1);

		//send
		var s1 = "settlementManage/caseExpected.do";
		var s2 = {
			userId:$.getCookie("user_id"),
			token:$.getCookie("active_code"),
			caseCode:$("input[name=modal_caseExpected_case_code]").val(),
			amountOfPay:flag_amountOfPay,
			id:flag_id
		};
		$.ajax({
			type:"post",
			url:s1,
			datatype:"json",
			data:s2,
			success:function(data){
				if(data.result == 1) {
					alert("操作成功！");
					$("#modal_caseExpected").addClass("hidden");
				} else {
					alert("操作失败！");
				}
			},
			error:function(error){
				alert("操作失败！");
			}
		});
	} else {
		alert("输入框必填！");
	}
}

//退回案件
function send_sendBack(case_code) {
	//send
	var s1 = "settlementManage/sendBack.do";
	var s2 = {
		userId:$.getCookie("user_id"),
		token:$.getCookie("active_code"),
		caseCode:case_code
	};
	$.ajax({
		type:"post",
		url:s1,
		datatype:"json",
		data:s2,
		success:function(data){
			if (data.result == 1) {
				alert("案件退回成功！");
			} else {
				alert("案件退回失败！");
			}
			$("button[name=btn_close_info]").click();
		},
		error:function(error){}
	});
}

//读取地区
function send_listRegion() {
	var s1 = "settlementManage/listRegion.do";
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
			//town
			for (var i = 0; i < data.townList.length; i++) {
				$("<option>").val(data.townList[i].id).html(data.townList[i].town).appendTo($("select[name=s_town]"));
			}

			//village
			GLOBAL_VILLAGELIST = data.villageList;
		},
		error:function(error){}
	});
}

//批量下载
function send_downloadFile(case_code) {
	$("form[name=download_form]").remove();
	var form = $("<form>").attr("name","download_form").attr("style","display:none").attr("target","").attr("method","post")
	.attr("action","settlementSearch/downloadFile.do").appendTo("#down");

	$("<input>").attr("type","hidden").attr("name","userId").attr("value",$.getCookie("user_id")).appendTo(form);
	$("<input>").attr("type","hidden").attr("name","token").attr("value",$.getCookie("active_code")).appendTo(form);
	$("<input>").attr("type","hidden").attr("name","caseCode").attr("value",case_code).appendTo(form);

	$("form[name=download_form]").submit();
	$("form[name=download_form]").remove();
}

$(document).ready(function() {
	//数据初始化
	send_listRegion();
	loadTable(GLOBAL_PAGE);

	//每页显示个数事件
	$("#pageNum").change(function(){
		loadTable(GLOBAL_PAGE);
	});
	//查询按钮
	$("button[name=btn_search]").click(function(){
		loadTable(GLOBAL_PAGE);
	});
	//清空按钮
	$("button[name=btn_clean]").click(function(){
		location.reload();
	});

	//地区选择变化 改：地区权限
	$("select[name=s_town]").change(function(){
		if ($(this).val() == 0) {
			$("select[name=s_village]").addClass("hidden");
			$("input[name=s_regionId]").val(0).attr("data-town","所有").attr("data-village","所有");
		} else if ($(this).val() == 1000) {
			$("select[name=s_village]").addClass("hidden");
			$("input[name=s_regionId]").val(1000).attr("data-town","惠安县").attr("data-village","所有");
		} else {
			$("select[name=s_village]").removeClass("hidden").html("");
			$("input[name=s_regionId]").val($(this).val()).attr("data-town",$(this).find(":selected").text()).attr("data-village","所有");
			//村列表初始化
			$("<option>").val($(this).val()).html("所有").appendTo($("select[name=s_village]"));
			for (var i = 0; i < GLOBAL_VILLAGELIST.length; i++) {
				if ( parseInt($(this).val()/100) == parseInt(GLOBAL_VILLAGELIST[i].id/100) ) {
					$("<option>").val(GLOBAL_VILLAGELIST[i].id).html(GLOBAL_VILLAGELIST[i].village).appendTo($("select[name=s_village]"));
				}
			}
		}
	});
	$("select[name=s_village]").change(function(){
		$("input[name=s_regionId]").val($(this).val()).attr("data-village",$(this).find(":selected").text());
	});

	//案件类型变化
	$("div[name=s_status_1] select").change(function(){
		switch($("div[name=s_status_1] select").val()) {
			case "4":
				$("div[name=s_status_2]").addClass("hidden");
				$("input[name=s_status]").val(4).attr("data-t","所有案件");
			break;
			case "3":
				$("div[name=s_status_2]").addClass("hidden");
				$("input[name=s_status]").val(3).attr("data-t","已结案案件");
			break;
			case "5":
				$("div[name=s_status_2]").removeClass("hidden");
				$("div[name=s_status_2] select").prop("selectedIndex",0);
				$("input[name=s_status]").val(5).attr("data-t","未结案案件");
			break;
		}
	});
	$("div[name=s_status_2] select").change(function(){
		$("input[name=s_status]").val($(this).val()).attr("data-t", ($(this).val()=="5")?"未结案案件":($(this).val()=="1"?"已上报案件":"退回案件") );
	});

	//批量按钮
	$("button[name=downloadFile]").click(function(){
		var flag = "";
		$("input[check-case_code]").each(function(){
			if ($(this).is(":checked")) {
				flag += $(this).attr("check-case_code") + ",";
			}
		});
		if (flag.length == 0) {
			alert("未勾选需要下载的案件！");
		} else {
			flag = flag.substring(0,flag.length-1);
			send_downloadFile(flag);
			//alert(flag);
		}
	});

	//详细案件界面
	$.tabs("div_info_tabs");
	$("button[name=btn_close_info]").click(function(){
		$("#div_info").addClass("hidden");
		$("#div_table").removeClass("hidden");
		loadTable(GLOBAL_PAGE);
	});
	$("button[name=btn_caseExpected]").click(function(){ //案件完成
		$("#modal_caseExpected").removeClass("hidden");
	});
	$("button[name=btn_sendBack]").click(function(){ //案件退回
		send_sendBack($(this).attr("data-case_code"));
	});
	$("button[name=btn_downZIP]").click(function(){ //案件下载
		window.open($(this).attr("data-zip_url"));
	});
	//退回原因modal
	$("button[name=modal_sendBackReason_btn_close]").click(function(){ //退回理由关闭弹窗
		$("#modal_sendBackReason").addClass("hidden");
	});
	$("button[name=modal_sendBackReason_btn_ok]").click(function(){ //退回理由关闭弹窗
		send_sendBackReason();
	});
	$("button[name=modal_sendBackReason_btn_clean]").click(function(){ //退回理由关闭弹窗
		$("textarea[name=modal_sendBackReason_sendBackReason]").val("");
		send_sendBackReason();
	});
	//案件单张图片展示modal
	$("span[name=modal_show_photo_btn_close]").click(function(){ //图片关闭弹窗
		$("#modal_show_photo").addClass("hidden");
	});
	$("span[name=modal_show_photo_btn_plus]").click(function(){ //图片放大
		alert($("#modal_show_photo img").css("height"));
	});
	$("span[name=modal_show_photo_btn_minus]").click(function(){ //图片缩小
		alert($("#modal_show_photo img").css("width"));
	});
	$("span[name=modal_show_photo_btn_suitable]").click(function(){ //图片合适尺寸
		$("#modal_show_photo img").css();
	});
	$("span[name=modal_show_photo_btn_origin]").click(function(){ //图片原始图片
		$("#modal_show_photo img").css();
	});
	//案件完成modal
	$("button[name=modal_caseExpected_btn_close]").click(function(){ //关闭弹窗
		$("#modal_caseExpected").addClass("hidden");
	});
	$("button[name=modal_caseExpected_btn_ok]").click(send_caseExpected); //完成按钮弹窗
});