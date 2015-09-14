<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta charset="utf-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery.js"></script>
 

<style>
.buttonPress{
  
  background-color:yellow;
}
.formatquestion{
margin-top:10px;
background-color:FFE4C4;
}
.center {
    margin-left: auto;
    margin-right: auto;
    
}
.resultdiv{

	
	max-height: 100px;
	overflow-y: auto;
	font-size: 110%;
	font-family="Times New Roman", Georgia, Times, serif;
	padding: 15px;
	
	
}
.answertextformat{
	font-size: 110%;
	font-family="Times New Roman", Georgia, Times, serif;
	margin-top:10px;
	
}
#Contentcolor{
    background-color:White;
    
}
.hidelist{
display:none;
}
.showlist{

display:block;

}
.hidelistdifferent{
display:none;
}
.showlistdifferent{

display:block;
background-color: E5FFCC;

}

.node circle{
  stroke: #fff;
  stroke-width: 1.5px;
}
.node text {
  font: 12px sans-serif;
  font-weight: bold;
  pointer-events: none;
}
.link {
  stroke: #999;
  stroke-opacity: .6;
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

    <title>System B</title>


<script type="text/javascript"> 


</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/include.js"></script>
  
</head>
<body>
<form id="hiddenform" action="${pageContext.request.contextPath}/Version" method="GET">
<input type="hidden" id="GoToValue" value="Visual Recommender" >
<input type="hidden" id="GoToMenu" value="Menu" >
<input type="hidden" id="Selection" name="VersionSelection" > 
</form>

<div id="header">
<h2 align="center" >System B</h2>
<p align="center">Select appropriate radio button to get results based on 1. Text or 2. Code similarity</p>
<table style="position:absolute; right:15px; background:inherit; color:inherit;">
	<!-- <tr>
		<td><input type="button" onclick="changeView(this)" value="Switch View"/></td>
		<td><p>||</p></td>
		<td><input type="button" value="Menu" onclick="changeView(this)" /> </td>
	</tr> -->
	<tr>
		<td> User ID: <input type="text" id="UserID" name="User ID" value='DEMO'/> </td>
		<td><p>||</p></td> 
		<td><input type="button" value="Menu" onclick="changeView(this)" /> </td>
	</tr>
</table>
</div>

<form id="myForm" action="${pageContext.request.contextPath}/TextResults" method="POST" onsubmit="return precheck();">
<table class="center">
	<tr>
	<td>
	
	<input type="radio" id="radiobutton2" name="radiochoice" value="text" checked="checked">Text
	</td>	
	<td>
	<input type="radio" id="radiobutton1" name="radiochoice" value="code">Code

	</td>
	</tr>
    <tr>
    <td><input type="text" name="name" id="enteredQuery" style="width: 500px;"/>
    </td>
    <td>
    <input type="submit" value="Search" id="driver"/>
    </td>
    </tr>
</table>
<input type="hidden" id="UserID_Details" name="UserID_store" >
<input type="hidden" id="LogDetails_SysA" name="UserLog" >  
</form>
<p id="userquery" align="center" style="font-weight: bold;">${query}</p>
<p id="precheckresult" align="center" style="background-color: #FFFF00; font-weight: bold;"></p>
<div id="Contentcolor" class="hidelist">


<div id="SVGHere">

<div id="combineview_Q_Ans" style="width: 95%;float: left;">
<div id="menu">
<table class="center">
<tr>
		<td><input type="button" id="buttonText" class="buttonPress" value="Text" onclick="toggleQuestionview(this)"></td>
		<td><input type="button" id="buttonCode" value="Code" onclick="toggleQuestionview(this)"></td>
</tr>
</table>
</div>
<!-- Code to combine chart n result visualization  -->
<div id="QuestionProp" >
<ol id="showresults">

</ol>
<ol id="showCode" class="hidelist">

</ol>

</div>

</div>

</div>
</div>
<script>
var currentstatus = true;
function setLogView(e) {
    var check = e.value;
    
    if(check == "Clear Log")
    {
    	document.getElementById("interaction_details").innerHTML = "";
    }
    else
    {
    	  if(currentstatus)
      	{
      		document.getElementById("interaction_details").className = "";
      		currentstatus = false;
      	}
      else
      	{
      		document.getElementById("interaction_details").className = "hidelist";
      		currentstatus = true;
      	}
      
    }
}
</script>

<div id="Interaction_Log" class="hidelist" style="clear:both; text-align: center;margin-bottom:20px">
<button type="button" onclick="setLogView(this)">Show/Hide Log</button>
<button type="button" onclick="setLogView(this)" value="Clear Log">Clear Log</button>
<pre id="interaction_details" class="hidelist"></pre>
</div>

<script>
	
	var textquestions = ${finaljson}; 
	//var json = JSON.parse(textquestions);
	for(var i=0; i < textquestions.length; i++)
	{
		var result = textquestions[i].q;
		//alert(result.question_text);
		var newParagraph = document.createElement('LI');
		//var tablerow = document.createElement('tr'); 
		newParagraph.setAttribute("data-value",result.question_no);
		newParagraph.setAttribute("class","formatquestion");
		//var newParagraph = document.createElement('td');
		newParagraph.setAttribute("id",result.question_no);
		
		
		// Button
		var togglebutton = document.createElement('BUTTON');
		togglebutton.setAttribute("data-value", result.question_no);
		togglebutton.setAttribute("value", "Show Answer");
	    togglebutton.setAttribute("onclick","showHide(this)");
	    togglebutton.setAttribute("style","margin-left: 10px;");
	    togglebutton.textContent = "View Answer";
	    
	    
	    var questionno = document.createTextNode('Q: (Text)');//result.question_no
	    newParagraph.appendChild(questionno);
	    newParagraph.appendChild(togglebutton);
	    //newParagraph.appendChild(Relevantbutton);
	    
	    var questiondiv = document.createElement('DIV');
	    questiondiv.setAttribute("class","resultdiv");
	    var questionnode = document.createTextNode(result.question_text);
	    questiondiv.appendChild(questionnode);
	   
	    newParagraph.appendChild(questiondiv); // Appended question node div
	    
	    var newCode = document.createElement('LI');
	    newCode.setAttribute("data-value",result.question_no);
	    newCode.setAttribute("id",result.question_no);
	    newCode.setAttribute("class","formatquestion");
	    
	 	// Button
		var togglebuttoncode = document.createElement('BUTTON');
		togglebuttoncode.setAttribute("data-value", result.question_no);
		togglebuttoncode.setAttribute("onclick","showHide(this)");
		togglebuttoncode.setAttribute("value", "Answer Code");
		togglebuttoncode.setAttribute("style","margin-left: 10px;");
		togglebuttoncode.textContent = "View Answer Code";
		
		
		var questionnocode = document.createTextNode('Q: (Code)');
		newCode.appendChild(questionnocode);
		newCode.appendChild(togglebuttoncode);
		
	    var question_code_div = document.createElement('DIV');
	    question_code_div.setAttribute("class","resultdiv");
	    //var questioncodenode = document.createTextNode(result.question_code);
	    
	    var questioncodenode = result.question_code;
	    // Starts here
	    	var queres="";
	    	for(var itr = 0; itr < questioncodenode.length; itr++)
	    	{
	    		if(questioncodenode.charAt(itr) == ';' || questioncodenode.charAt(itr) == '{' || questioncodenode.charAt(itr) == '}')
	    			queres += questioncodenode.charAt(itr) + '<br>';
	    		else
	    			queres += questioncodenode.charAt(itr);
	    	}
	    	
	    	var queparagraph = document.createElement("p");
	    	queparagraph.innerHTML = queres;
	    	    
	    // Ends here
	    question_code_div.appendChild(queparagraph);
	    
	   
	    newCode.appendChild(question_code_div); // Appended question node div
	    
	    
	    
	    // Code to create answers starts here
	    var answerdiv = document.createElement('DIV');
	    answerdiv.setAttribute("class","hidelistdifferent");
	    
	    var answercodediv = document.createElement('DIV');
	    answercodediv.setAttribute("class","hidelistdifferent");
	    
	    
	 // Code to populate Answers
		var answerarray = textquestions[i].answers;
	    
	 	if(answerarray.length > 0)
	 	{
	 		var listans = document.createElement('UL');
	 		
	 		var listcodeans = document.createElement('UL');
		// go into for loop
		for(var j=0; j < answerarray.length; j++)
		{
			var extract_answer = answerarray[j];
			
	    	var answertextdiv = document.createElement('LI');
	    	answertextdiv.setAttribute("class","answertextformat");
	    	//answertextdiv.textContent = extract_answer.answer_text;
	    	var A = document.createTextNode('A: (Text)');
	    	
	    	
	    	var Relevant_button = document.createElement('BUTTON');
	    	Relevant_button.setAttribute("data-value", result.question_no+"-"+extract_answer.answer_id);
	    	Relevant_button.setAttribute("value", "Relevant button");
	    	Relevant_button.setAttribute("onclick","showHide(this)");
	    	Relevant_button.setAttribute("style","margin-left: 10px;");
	    	Relevant_button.textContent = "Relevant";
	    	
	    	var ans_text_div = document.createElement('DIV');
	    	var ans_text = document.createTextNode(extract_answer.answer_text);
	    	ans_text_div.appendChild(ans_text);
	    	
	    	answertextdiv.appendChild(A);
	    	answertextdiv.appendChild(Relevant_button);
	    	answertextdiv.appendChild(ans_text_div);
	    	listans.appendChild(answertextdiv);
	    	
	    	var ans_code_label = document.createTextNode('A: (Code)');//result.question_no
	    	var answercodediv2 = document.createElement('LI');
	    	var ans_div_hold = document.createElement('DIV');
	    	
	    	var Relevant_code_button = document.createElement('BUTTON');
	    	Relevant_code_button.setAttribute("data-value", result.question_no+"-"+extract_answer.answer_id);
	    	Relevant_code_button.setAttribute("value", "Relevant Code button");
	    	Relevant_code_button.setAttribute("onclick","showHide(this)");
	    	Relevant_code_button.setAttribute("style","margin-left: 10px;");
	    	Relevant_code_button.textContent = "Relevant";
	    	
	    	//var text_code_content = document.createTextNode(extract_answer.answer_code);
	    	//ans_div_hold.appendChild(text_code_content);
	    	// Begin
	    	var text_code_content = extract_answer.answer_code;
	    	var res="";
	    	for(var itr = 0; itr < text_code_content.length; itr++)
	    	{
	    		if(text_code_content.charAt(itr) == ';' || text_code_content.charAt(itr) == '{' || text_code_content.charAt(itr) == '}')
	    			res += text_code_content.charAt(itr) + '<br>';
	    		else
	    			res += text_code_content.charAt(itr);
	    	}
	    	
	    	var paragraph = document.createElement("p");
	    	paragraph.innerHTML = res;
	    	
	    	ans_div_hold.appendChild(paragraph);
	    	
	    	
	    	// End
	    	answercodediv2.setAttribute("class","answertextformat");
	    	//answercodediv2.appendChild(ans_code_label);
	    	//answercodediv2.textContent = extract_answer.answer_code;
	    	answercodediv2.appendChild(ans_code_label);
	    	answercodediv2.appendChild(Relevant_code_button);
	    	answercodediv2.appendChild(ans_div_hold);
	    	listcodeans.appendChild(answercodediv2);
	    	
		}
		answerdiv.appendChild(listans);
		answercodediv.appendChild(listcodeans);
	 	}
	    newParagraph.appendChild(answerdiv);
	    newCode.appendChild(answercodediv);
	    // Code to create answers ends here
	    
	    //tablerow.appendChild(newParagraph);
	    //document.getElementById("showresults").appendChild(tablerow);
	    document.getElementById("showresults").appendChild(newParagraph);
	    document.getElementById("showCode").appendChild(newCode);

	}
	
	
	if(textquestions.length != 0)
	{
		document.getElementById("Contentcolor").className = "";
		//document.getElementById("Interaction_Log").className = "";
		
	}
	else if(textquestions.length == 0)
		document.getElementById("precheckresult").innerHTML = "No results found.";

	
</script>
<div id="footer" style="clear:both;">Copyright © Arizona State University</div>
</body>
</html>