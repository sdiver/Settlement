//创建和初始化地图函数
function initMap(){
	createMap();//创建地图
	setMapEvent();//设置地图事件
	addMapControl();//向地图添加控件
	addMarkertest();//向地图中添加mark
	addTrafic();//向地图添加交通接口

	//test
	var bounds = window.map.getBounds();
	var sw = bounds.getSouthWest();
	var ne = bounds.getNorthEast();
	var lngSpan = Math.abs(sw.lng - ne.lng);
	var latSpan = Math.abs(ne.lat - sw.lat);
	for(var i = 0; i < 25; i++) {
		var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
		addMarker(point);
	};
}
//创建地图函数
function createMap(){
	var map = new BMap.Map("B_Map");//在容器中创建一个地图
	var point = new BMap.Point(119.257656,26.079759);//定义一个中心点坐标
	map.centerAndZoom(point,15);//设定地图的中心点和坐标并将地图显示在容器中
	window.map=map;//将map变量储存为全局变量
}
//地图控件添加函数
function setMapEvent(){
	map.enableDragging();//启动地图拖拽事件，默认启动
	map.enableScrollWheelZoom();//启动地图滚轮放大缩小
	map.enableDoubleClickZoom();//启动鼠标双击放大，默认启动
	map.enableKeyboard();//启动键盘上下左右移动地图
}
//地图控件添加函数
function addMapControl(){
	//向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
	//向地图添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:0});
	map.addControl(ctrl_ove);
	//像地图添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
}
//标注点数组
function addMarkertest(){
	var point = new BMap.Point(119.257656,26.079759);
	//var myIcon = new BMap.Icon("XMGLFiles/icons/markers.png",new BMap.Size(23,25),{
	//	offset:new BMap.Size(10,25),
	///* imageOffset:new BMap.Size(0,0-index*25) */
	//});
	var marker = new BMap.Marker(point); //创建标注
	map.addOverlay(marker); //将标注添加到地图中
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //点跳动
	window.marker = marker;
}
function addMarker(point){
	var marker = new BMap.Marker(point);
	map.addOverlay(marker); //将标注添加到地图中

	marker.addEventListener("click",tt);
}
function addTrafic(){
	var traffic = new BMap.TrafficLayer();        // 创建交通流量图层实例      
	map.addTileLayer(traffic);                    // 将图层添加到地图上
}

initMap();	
marker.addEventListener('click',function(){    
	map.removeOverlay(marker);    
	marker.dispose(); 
});	

function tt(e){
	var p = e.target;
	alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);
};