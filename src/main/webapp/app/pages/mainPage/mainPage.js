

$(document).ready(function() {
	//tabs_select
	$("button[name=btn_select]").click(function(){
		flag_select = $(this).attr("data-select");

		$("div[tabs]").addClass("hidden");
		$("#"+flag_select).removeClass("hidden");
	});

	//tab_1
	var slider = new SimpleSlider(document.getElementById('slider_mainPage'));

	$("button[name=btn_set_slider]").click(function(){
		alert("功能开发中！");
	});

	//tab_2
	$("input[name=tab_2_user_name]").val($.getCookie("user_name"));
	$("input[name=tab_2_phone_number]").val($.getCookie("phone_number"));
	$("input[name=tab_2_user_ch_name]").val($.getCookie("user_ch_name"));
	$("input[name=tab_2_user_type]").val($.getCookie("user_type"));
	$("input[name=tab_2_town]").val($.getCookie("town"));
	$("input[name=tab_2_village]").val($.getCookie("village"));
	$("input[name=tab_2_work_address]").val($.getCookie("work_address"));
});