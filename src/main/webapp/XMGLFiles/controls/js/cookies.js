/**
 * author:MT
 * since:2015-02-10
 * version:v1.0
 */
jQuery.extend({
	//设置cookie
	setCookie:function(c_name,value,expiredays){
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		document.cookie = c_name + "=" + escape(value) + ((expiredays==null)?"":";expires=" + exdate.toGMTString());
	},
	//获取cookie
	getCookie:function(c_name){
		var c_start = -1,
			c_end = -1;
		if(document.cookie.length > 0){
			c_start = document.cookie.indexOf(c_name + "=");
			if(c_start != -1){
				//检索cookie位置
				c_start = c_start + c_name.length + 1;
				c_end = document.cookie.indexOf(";",c_start);
				if(c_end == -1) c_end = document.cookie.length;
				
				return unescape(document.cookie.substring(c_start,c_end));
			}
		}
		return null;
	},
	delCookie:function(c_name){
		var date = new Date();
		date.setTime(date.getTime()-10000);
		document.cookie = c_name + "=;expire="+date.toGMTString();
	}
});