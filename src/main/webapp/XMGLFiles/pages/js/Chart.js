/**
 * author:MT
 * since:2015-02-11
 * version:v1.0
 */

//分页
function turnTo(){
	$(".main_M>div[data-m]").addClass("hidden");
	$("#"+$(this).attr("data-turnTo")).removeClass("hidden");
}

$(document).ready(function() {
	//事件绑定
	$(".main_L>div[data-turnTo]").click(turnTo);

	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('tu1'));

	// 指定图表的配置项和数据
	var option = {
		title:{
			text: ""
		},
		tooltip:{},
		legend:{
			data:[
				{
					name:"案件数量",
					icon:"circle",
					textStye:{
						color:"red"
					}
				},
				{
					name:"待核销案件",
					icon:"rect",
					textStye:{
						color:"blue"
					}
				},
				{
					name:"整改案件",
					icon:"diamond",
					textStye:{
						color:"green"
					}
				}
			]
		},
		xAxis:{
			data:["惠安县","螺城镇","螺阳镇","黄塘镇","紫山镇","洛阳镇","东园镇","张坂镇","崇武镇","山霞镇","涂赛镇","东岭镇","东桥镇","净峰镇","小闸镇","辋川镇","百崎回族乡"]
		},
		yAxis:{},
		series:[
			{
				name:"案件数量",
				type:"bar",
				data:[200,20,36,10,10,20,17,22,13,20,33,24,17,14,16,19,20]
			},
			{
				name:"待核销案件",
				type:"bar",
				data:[170,10,20,5,4,13,14,6,4,7,20,5,2,6,7,3,12]
			},
			{
				name:"整改案件",
				type:"bar",
				data:[30,2,4,6,4,8,6,8,7,6,8,9,1,4,6,9,2]
			}
		]
	};

	// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
});

