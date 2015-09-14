<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/include.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
<style>
.center {
    margin-left: auto;
    margin-right: auto;
    
}
#header{
background-color: brown;
color: white;
text-align: center;
width: 100%;
padding: 5px;
}
#footer{
	background-color: brown;
	color: white;
	position:relative;
	text-align: center;
	width: 100%;
	bottom: 0;
}
body {
	background-image:
		url('${pageContext.request.contextPath}/resources/Background.jpg');
	background-size: cover;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body>
<form id="hiddenform" action="${pageContext.request.contextPath}/Version" method="GET">
<input type="hidden" id="GoToMenu" value="Menu" >
<input type="hidden" id="Selection" name="VersionSelection" > 
</form>
<div id="header">
<h2 align="center">Educational Visual Recommender </h2>
<p align="center">Error Information: </p>
<table style="position:absolute; right:15px; background:inherit; color:inherit;">
	<tr>
		<td> <input type="button" value="Menu"   onclick="changeView(this)" /> </td>
	</tr>
</table>
</div>



${ErrorDescription}


<div id="footer" style="clear:both; margin-top:30px;">Copyright © Arizona State University</div>
</body>
</html>