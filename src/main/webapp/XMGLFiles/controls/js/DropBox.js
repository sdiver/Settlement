//下拉框
function DropBoxs(select,type,flag){
	if (type) {
		$.ajax({
			type:"post",
			url:"dictionarylist/list.do",
			datatype:"text",
			data:{
				no:type
			},
			success:function(data){
				//初始化value
				$("input[name="+select+"]").val(data.list[flag].name).attr("data-dropbox",data.list[flag].num);
				//判断是否初始化列表
				if ($(".DropBoxs[name="+select+"]").length > 0) {
					//初始化列表
					for (var i = 0; i < data.list.length;i++) {
						$("<li>" + data.list[i].name + "</li>").attr("data-dropbox",data.list[i].num).appendTo(".DropBoxs[name="+select+"] dd ul");
					};
					//初始化点击事件
					$(".DropBoxs[name="+select+"] dt").click(function(){
						$(".DropBoxs[name="+select+"] dd").slideToggle(300);
					});
					$(".DropBoxs[name="+select+"] dd ul li").click(function(){
						$(".DropBoxs[name="+select+"] dd").slideUp(300);
						$("input[name="+select+"]").val($(this).html()).attr("data-dropbox",$(this).attr("data-dropbox"));
					});
				};
			},
			error:function(data){
				alert("下拉框初始化失败！");
			}
		});
	} else {
		//初始化点击事件
		$(".DropBoxs[name="+select+"] dt").click(function(){
			$(".DropBoxs[name="+select+"] dd").slideToggle(300);
		});
		$(".DropBoxs[name="+select+"] dd ul li").click(function(){
			$(".DropBoxs[name="+select+"] dd").slideUp(300);
			$("input[name="+select+"]").val($(this).html()).attr("data-dropbox",$(this).attr("data-dropbox"));
		});
	};
}