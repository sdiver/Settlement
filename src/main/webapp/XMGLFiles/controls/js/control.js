// JavaScript Document
$(document).ready(function() {
	//控件加载
	DropBoxs();
	CheckBoxs();
});

function CheckBoxs(){
	$('input').iCheck({
	    checkboxClass: 'CheckBox',
	    radioClass: 'RadioBox',
	    increaseArea: '0%' // optional
	  });
};