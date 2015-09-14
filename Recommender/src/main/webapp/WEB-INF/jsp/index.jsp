<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta charset="utf-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery.js"></script>
<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script> 

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

    <title>System A</title>


<script type="text/javascript"> 
$(document).ready(function() {
	
    $("#driver22").click(function(event){
    	var url = $( '#myForm' ).attr('action');
    	var radioval = $('input:radio[name=radiochoice]:checked').val();
    	alert(radioval);
    	var entval = $("#enteredQuery").val();
    	//url=url+'?name='+entval;
    	
    	$.ajax({
    	    url: url,
    	    dataType: 'json',
    	    type: 'GET',
    	    async: false,
    	    contentType: "application/json",
    	    complete: function(data){
    	        //alert(data.status);// S1000
    	        //alert(data.description);// Success
    	        
    	        
    	    },
    	    success: function(data){
    	    	alert("Going in Success");
    	    	alert(JSON.stringify(data));
    	    	//alert(data);
    	    	//alert(data.name);
    	    	$("#ShowResults").empty();
    	    	$("#ShowResults").append(data.name);
    	    	
    	    },
    	    error: function() {
    	        alert( 'Something goes wrong!' );
    	    }
    	})
    });
    
    
    
    	
	
    
 });
 



</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/include.js"></script>
  
</head>
<body>
<form id="hiddenform" action="${pageContext.request.contextPath}/Version" method="GET">
<input type="hidden" id="GoToValue" value="Traditional Recommender" >
<input type="hidden" id="GoToMenu" value="Menu" >
<input type="hidden" id="Selection" name="VersionSelection" > 
</form>
<div id="header">
<h2 align="center">System A</h2>
<p align="center">Select appropriate radio button to get results based on 1. Text or 2. Code similarity</p>
<table style="position:absolute; right:15px; background:inherit; color:inherit;">
	<tr class="hidelist">
		<td> <input type="button"   onclick="changeView(this)" value="Switch View" /> </td>
		<td> <p   >||</p> </td>
		<td> <input type="button" value="Menu"   onclick="changeView(this)" /> </td>
	</tr>
	<tr>
		<td> User ID: <input type="text" id="UserID" name="User ID" value='DEMO'/> </td>
		<td><p>||</p></td> 
		<td><input type="button" value="Menu" onclick="changeView(this)" /> </td>
	</tr>
</table>
</div>
<form id="myForm" action="${pageContext.request.contextPath}/results" method="POST" onsubmit="return precheck();">
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
<div id="charthere" style="float: left; border: double #CCFFCC 5px;margin: 5px;">
</div>
<div id="combineview_Q_Ans" style="width: 35%;float: left;">
<div id="menu">
<table class="center">
<tr>
		<td><input type="button" id="buttonText" class="buttonPress" value="Text" onclick="toggleQuestionview(this)"></td>
		<td><input type="button" id="buttonCode" value="Code" onclick="toggleQuestionview(this)"></td>
</tr>
</table>
</div>
<!-- Code to combine chart n result visualization  -->
<div id="QuestionProp" style="border: double #CCFFCC 5px;margin: 5px; height: 700px; overflow-y: auto;">
<ol id="showresults">

</ol>
<ol id="showCode" class="hidelist">

</ol>

</div>

</div>



<!-- Code ENDS  -->


<script>


var text = ${svgjson};

var screenwidth = screen.width;
var screenheight = screen.height

var width = 0.62 * screenwidth,
    height = 700;

var color = d3.scale.category20();

var force = d3.layout.force()
    .charge(-520)
    .linkDistance(130)
    .size([width, height]);

var svg = d3.select("#charthere").append("svg")
    .attr("width", width)
    .attr("height", height);
  
svg.append("rect").attr("x", 25).attr("y", height-(height-8)).attr("width", 155).attr("height", 25).style("fill", "#E6E6FF").style("stroke-width", "0.5").style("stroke", "rgb(0,0,0)");
svg.append("text").attr("x", 30).attr("y", height-(height-25))
.on("click", function(){
		// Determine if current Text is visible
		//alert("Clicked Me!");
		//var active   = switchvisual.active ? false : true,
		  //newOpacity = active ? 0 : 1;
		//alert(active);
		// Hide or show the elements
		var currentstat = d3.select(this).text();
		//alert(currentstat);
		if(currentstat == "Hide Cluster Details")
			{
				d3.selectAll("#switchvisual").style("visibility", "hidden");
				d3.selectAll("#alwaysdisplay").attr("y", 00);
				d3.select(this).text("Show Cluster Details");
				console.log('User is Hiding cluster details' + ": " + Date());
				document.getElementById("interaction_details").innerHTML += '\n User is Hiding cluster details' + ": " + Date();	
			}
		else if(currentstat == "Show Cluster Details")
			{
				d3.selectAll("#switchvisual").style("visibility", "visible");
				d3.selectAll("#alwaysdisplay").attr("y", -20);
				d3.select(this).text("Hide Cluster Details");
				console.log('User is Viewing cluster details' + ": " + Date());
				document.getElementById("interaction_details").innerHTML += '\n User is Viewing cluster details' + ": " + Date();
			}
		
		//d3.select(this).text("Changed");
		// Update whether or not the elements are active
		//switchvisual.active = active;
	})
.text("Show Cluster Details").style("font-weight","bold");





svg.append("circle").attr("cx", 40).attr("cy", height-170).attr("r", 25).style("fill", "orange").style("opacity", 0.3);
svg.append("circle").attr("cx", 210).attr("cy", height-170).attr("r", 25).style("fill", "orange");
svg.append("text").attr("x", 10).attr("y", height-130).text("Less Similar").style("font-weight","bold");
svg.append("text").attr("x", 170).attr("y", height-130).text("More Similar").style("font-weight","bold");

svg.append("rect").attr("x", 15).attr("y", height-120).attr("width", 90).attr("height", 7).style("fill", "#c7c7c7");
svg.append("rect").attr("x", 160).attr("y", height-120).attr("width", 100).attr("height", 15).style("fill", "#c7c7c7");

svg.append("text").attr("x", 10).attr("y", height-90).text("Less Occurence").style("font-weight","bold");
svg.append("text").attr("x", 170).attr("y", height-90).text("More Occurence").style("font-weight","bold");

svg.append("circle").attr("cx", 50).attr("cy", height-55).attr("r", 15).style("fill", "lightblue");
svg.append("circle").attr("cx", 210).attr("cy", height-55).attr("r", 30).style("fill", "lightblue");

svg.append("text").attr("x", 10).attr("y", height-10).text("Less Answers").style("font-weight","bold");
svg.append("text").attr("x", 170).attr("y", height-10).text("More Answers").style("font-weight","bold");
  force
      .nodes(text.nodes)
      .links(text.links)
      .start();

  var link = svg.selectAll(".link")
      .data(text.links)
    .enter().append("line")
      .attr("class", "link")
      .style("stroke-width", function(d) { return d.value; });

  var node = svg.selectAll(".node")
      .data(text.nodes)
    .enter().append("g")
      .attr("id",function(d) { return d.name; })
  	  .attr("onclick","testingcall(this);")
      .attr("class", "node");
  
  node.append("circle")
      .attr("r", function(d) { return d.type; })
      .style("fill", function(d) { return color(d.group); })
      .style("opacity", function(d) { return d.opacity; });
  
  //var checktype = function(d){return d.setTypeNode;};
  
  //if(function(d){return d.setTypeNode;} == 0)
 {
	//  node.append("text")  
  	  //	.attr("dy", ".35em")
      //	.attr("text-anchor", "middle")
      //	.text(function(d) { return d.name; });
 }
  //else if(function(d){return d.setTypeNode;} == 1)
 {
	 node.append("text")  
 	  .attr("y", 00)
 	  .attr("id", "alwaysdisplay")
     .attr("text-anchor", "middle")
     .text(function(d) { return d.displaykeyword1; });
 node.append("text")
	.attr("y", 00)
	.attr("text-anchor", "middle")
	.attr("id", "conceptdisplay")
	.text(function(d) { return d.displaytext; });
 node.append("text")
     .attr("y", 00)
     .attr("id", "switchvisual")
     .attr("visibility", "hidden")
     .attr("text-anchor", "middle")
     .text(function(d) { return d.displaykeyword2; });
 node.append("text")
     .attr("y", 20)
     .attr("id", "switchvisual")
     .attr("visibility", "hidden")
     .attr("text-anchor", "middle")
     .text(function(d) { return d.displaykeyword3; });
 }
  node.call(force.drag);


  force.on("tick", function() {
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    node.attr("transform", function(d) { return "translate("+d.x+","+d.y+")"; });
    
    
    	
  });

</script>
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
	    //question_code_div.appendChild(questioncodenode);
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
	    	//var text_code_content = document.createTextNode(extract_answer.answer_code);
	    	var Relevant_code_button = document.createElement('BUTTON');
	    	Relevant_code_button.setAttribute("data-value", result.question_no+"-"+extract_answer.answer_id);
	    	Relevant_code_button.setAttribute("value", "Relevant Code button");
	    	Relevant_code_button.setAttribute("onclick","showHide(this)");
	    	Relevant_code_button.setAttribute("style","margin-left: 10px;");
	    	Relevant_code_button.textContent = "Relevant";
	    	
	    	
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
	    	
	    	//ans_div_hold.appendChild(text_code_content);
	    	ans_div_hold.appendChild(paragraph);
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