//创建和初始化地图函数
function initMap(){
	createMap();//创建地图
	addMapControl();//向地图添加控件	
	//addMarker();//向地图中添加mark
	addTrafic();//向地图添加交通接口
};
//创建地图函数
function createMap(){
	var map = new AMap.Map('A_Map',{
		resizeEnable: true,
		dragEnable: true,//地图拖拽
		zoomEnable: true,//地图缩放
		scrollWheel: true,//地图滚轮缩放
		doubleClickZoom: true,//鼠标双击放大
		touchZoom: true,//移动端手势缩放
		keyboardEnable:true,//地图键盘控制
	    zoom: 12,
	    center: [119.250792,26.073567]//new AMap.LngLat(116.39,39.9)
	});
	window.map=map;//将map变量储存为全局变量
};
//添加地图控件
function addMapControl(){
	AMap.plugin(['AMap.ToolBar','AMap.Scale','AMap.OverView'],
    	function(){
    		//集成了缩放、平移、定位等功能按钮在内的组合控件
	        map.addControl(new AMap.ToolBar());
	 		//展示地图在当前层级和纬度下的比例尺
	        map.addControl(new AMap.Scale());
	 		//在地图右下角显示地图的缩略图
	        map.addControl(new AMap.OverView({isOpen:true}));
	        //用来获取和展示用户主机所在的经纬度位置
	        //map.addControl(new AMap.Geolocation());
	        //实现默认图层与卫星图、实施交通图层之间切换的控件
	        //map.addControl(new AMap.MapType());
	});
};
//标注点数组
function addMarker(){

};
function addTrafic(){
	var trafficLayer = new AMap.TileLayer.Traffic(); // 创建交通流量图层实例      
	trafficLayer.setMap(map); // 将图层添加到地图上
};

initMap();	
// marker.addEventListener('click',function(){    
// 	map.removeOverlay(marker);    
// 	marker.dispose(); 
// });	