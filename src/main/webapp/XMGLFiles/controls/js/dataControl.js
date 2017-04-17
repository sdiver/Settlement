/**
 * author:MT
 * since:2015-03-04
 * version:v1.0
 */
jQuery.extend({
	//xml数据.类型/查询参数/指定返回值(可选)
	getXMLValue:function(type,select,search){
		var val = "无XML数据",flag;
		//alert($(xmlDoc).find(type).children().length);
		$(xmlDoc).find(type).children().each(function(){
			if($(this).find("name").text() == select){
				flag = $(this).find(search?search:"value");
				if (flag.length != 1) {
					val = [];
					flag.each(function(){
						val.push($(this).text());
					});
				} else {
					val = flag.text();
				};
				return false;
			};
		});
		return val;
	},
	//页码计算函数,返回a的队列标签
	countPages:function(p_sum,p_tag){
		var html = "",
		p_start = 0,
		p_end = 0;
		if (p_tag != 1){
			html += "<a data-page='"+1+"'>"+"首页"+"</a>\n";
			html += "<a data-page='"+(p_tag*1-1)+"'>"+"上一页"+"</a>\n";
		};
		if (p_sum <= 10) {
			p_start = 1;
			p_end = p_sum;
		} else {
			if (p_tag - 5 <= 0) {
				p_start = 1;
				p_end = 10;
			} else if (p_tag + 5 > p_sum) {
				p_start = p_sum - 9;
				p_end = p_sum;
			} else {
				p_start = p_tag - 4;
				p_end = p_tag + 5;
			};
		};
		for (var i = p_start; i <= p_end ; i++) {
			html += "<a data-page='"+i+"'>"+i+"</a>\n";
		};
		if (p_tag != p_sum) {
			html += "<a data-page='"+(p_tag*1+1)+"'>"+"下一页"+"</a>\n";
			html += "<a data-page='"+p_sum+"'>"+"尾页"+"</a>";
		};
		return html;
	},
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
	//删除cookie
	delCookie:function(c_name){
		var date = new Date();
		date.setTime(date.getTime()-10000);
		document.cookie = c_name + "=;expire="+date.toGMTString();
	},
	//url取值
	getUrlParam:function(param){
		var reg = new RegExp("(^|&)" + param + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]); return null;
	},
	//json日期格式转换
	getJsonDate:function(Jdate,timeFormat){
		if (!Jdate) {
			return null;
		};
		var date = new Date(JSON.parse(Jdate));
		var year = date.getFullYear(),
		month = (date.getMonth()+1)<10?('0'+(date.getMonth()+1)):(date.getMonth()+1),
		day = date.getDate()<10?('0'+date.getDate()):date.getDate(),
		hh = date.getHours()<10?('0'+date.getHours()):date.getHours(),
		mm = date.getMinutes()<10?('0'+date.getMinutes()):date.getMinutes(),
		ss = date.getSeconds()<10?('0'+date.getSeconds()):date.getSeconds();
		if(timeFormat){
			return year + '-' + month + '-' + day + ' ' + hh + ':' + mm + ':' +ss;
		}
		return year + '-' + month + '-' + day;
	},
	//日期添加后缀转换
	dateFormat:function(date){
		if(!date)
			return null;
		return date += " 00:00:00";
	}
});