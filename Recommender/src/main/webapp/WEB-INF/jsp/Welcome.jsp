<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>
<style>
.center {
    margin-left: auto;
    margin-right: auto;
    
}
.hidelist{
display:none;
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
<script type="text/javascript">
function submitform(d)
{
	document.getElementById("Selection").value = d.value;
	document.forms["myForm"].submit();
}


</script>
</head>
<body>
<div id="header">
<h2 align="center">Educational Visual Recommender </h2>
<p align="center">Select button to navigate to appropriate Version of the System</p>
</div>

<form id="myForm" action="${pageContext.request.contextPath}/Version" method="GET" onsubmit="return precheck();">
<table class="center" style="padding:10px;">
	<tr>
		<td style="padding-right:10px;">
			<input type="button" id="Version1" name="Version1" onclick="submitform(this)" value="Visual Recommender">
		</td>	
		<td style="padding-left:10px;">
			<input type="button" id="Version2" name="Version2" onclick="submitform(this)" value="Traditional Recommender">
		</td>
		<td style="padding-left:10px;">
			<input type="button" id="Demo" name="Demo" onclick="submitform(this)" value="Demo">
		</td>
		<td style="padding-left:10px;" class="hidelist">
			<input type="button" id="Demo Visual" name="Demo Visual" onclick="submitform(this)" value="DemoVisual">
		</td>
		<td style="padding-left:10px;">
			<input type="button" id="Feedback" name="Feedback" onclick="submitform(this)" value="Feedback Form">
		</td>
	</tr>
	
</table> 
<input type="hidden" id="Selection" name="VersionSelection" > 
</form>
<div id="footer" style="clear:both; margin-top:30px;">Copyright © Arizona State University</div>

</body>
</html>