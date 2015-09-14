<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Tasks</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/include.js"></script>
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
<script type="text/javascript">
function submitform(d)
{
	document.getElementById("task_Selection").value = d.value;
	document.forms["myForm"].submit();
}

</script>
</head>
<body>
<form id="hiddenform" action="${pageContext.request.contextPath}/Version" method="GET">
<input type="hidden" id="GoToMenu" value="Menu" >
<input type="hidden" id="Selection" name="VersionSelection" > 
</form>
<div id="header">
<h2 align="center">Educational Visual Recommender </h2>
<p align="center">Select Task to be completed </p>
<table style="position:absolute; right:15px; background:inherit; color:inherit;">
	<tr>
		<td> <input type="button"   onclick="changeView(this)" value="Menu"/> </td>
	</tr>
</table>
</div>
<form id="myForm" action="${pageContext.request.contextPath}/Task" method="GET">
<table class="center" style="padding:10px;">
	<tr>
		<td style="padding-right:10px;">
			<input type="button" id="Task-1" name="Task-1" onclick="submitform(this)" value="Task-01">
		</td>	
		<td style="padding-left:10px;">
			<input type="button" id="Task-2" name="Task-2" onclick="submitform(this)" value="Task-02">
		</td>
	</tr>
	
</table> 
<input type="hidden" id="task_Selection" name="TaskSelection" > 
</form>



<div id="footer" style="clear:both; margin-top:30px;">Copyright © Arizona State University</div>

</body>
</html>