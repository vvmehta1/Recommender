<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/include.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task-01</title>
<style>
.center {
    margin-left: auto;
    margin-right: auto;
    
}
.hidelist{
display:none;
}
.showlist{

display:block;

}
#Check_Answers{
background:yellow;

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
<h2 align="center">Task-01</h2>
<p align="center">Provide answers to following questions </p>
<table class="hidelist" style="position:absolute; right:15px; background:inherit; color:inherit;">
	<tr>
		<td> <input type="button" value="Menu"   onclick="changeView(this)" /> </td>
	</tr>
</table>
</div>


<table class="hidelist" style="margin-left:10px; height:20px;">
	<tr>
		<td> <p    onclick="swapQuestion(this)" >Question-1</p> </td>
		<td> <p   >||</p> </td>
		<td> <p    onclick="swapQuestion(this)" >Question-2</p> </td>
	</tr>
</table>
<div style="margin-left:10px; width:250px;">
<p id="Check_Answers"> </p>
</div>
<form id="myForm" action="${pageContext.request.contextPath}/SubmitTask" method="POST" onsubmit="return checkValues();">
	
	<input type="hidden" name = "TaskNumber" id="TaskNumber" value="Task01" >
	<input type="hidden" name = "UserId" id="UID" >
	<input type="hidden" name = "Question_1_Text" id="Question_1_Text" value="1A" >
	<input type="hidden" name = "Question_2_Text" id="Question_2_Text" value="2A" >
	<div id="Question-1" style="margin-left:10px;">
   	<p >XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</p>
   	<textarea name="question1_answer" id="Q1Ans" style="height:280px; width:95%;" > </textarea>
 	</div>
 	
 	<div id="Question-2" style="margin-left:10px;" >
   	<p >XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX </p>
   	<textarea name="question2_answer" id="Q2Ans" style="height:280px; width:95%;" > </textarea>
 	</div>
 	
   <div align="center">
   <input type="submit" value="Submit Answers" />
    </div>
    
</form>



<div id="footer" style="clear:both; margin-top:30px;">Copyright © Arizona State University</div>
</body>
</html>