<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'clientOne.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="http://www.gangzhigou.com/js/jquery-1.7.1.min.js"></script>
	<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>  
	<script type="text/javascript">
		var local = function (){
			var obj = window.location;
			var contextPath=obj.pathname.split("/")[1];
			//http = obj.protocol
			return obj.host +"/"+ contextPath;
		};
		var url = local();
		var ws;
		if ('WebSocket' in window){
			ws = new WebSocket('ws://'+url+'/websocket');
		} else if ('MozWebSocket' in window){
			ws = new MozWebSocket('ws://'+url+'/websocket');
		} else {
			ws = new SockJS('ws://'+url+'/sockjs/websocket');
		}
		ws.onopen = function (event){
			$("#msgcount").html("<font color='red'>"+event.data+"onOpen</font>");
		};
		ws.onmessage = function (event){
			$("#msgcount").html("<font color='red'>"+event.data+"onMessage</font>");
			notifyMe();
		};
		ws.onerror = function(event){
			$("#msgcount").html("<font color='red'>"+event.data+"onError</font>");
		};
		ws.onclose = function(event){
			$("#msgcount").html("<font color='red'>onclose</font>");
		};
		function notifyMe(){
			if (!("Notification" in window)){/*判断该浏览器是否支持 浏览器通知*/
				alert("This browser does not support desktop notification");
			}else if (Notification.permission === "granted"){/*判断该浏览器是否 已开启通知*/
				var notification = new Notification("Hi there!");
			}else if (Notification.permission !== 'denied'){/*判断该浏览器是否 未禁止通知*/
				Notification.requestPermission(function(permission){/*浏览器未开启通知 请求开启通知*/
					if (!('permission' in Notification)) {
						Notification.permission = permission;
					}
					if (permission === 'granted'){/*发送通知*/
						var notification = new Notification("Hi there!");
					}
				});
			}
		}
	</script>
  </head>
  
  <body>
    <div id="msgcount" style="margin:0;padding:0;width: 100%;height:auto;"></div>
  </body>
</html>
