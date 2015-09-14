/**
 * 
 */

function toggleQuestionview(e) {
    var check = e.value;
    e.className="buttonPress";
    if(check == "Text")
    {
    	console.log('User is viewing Text of Questions');
    	document.getElementById("interaction_details").innerHTML += '\n User is viewing Text of Questions: ' + Date();
    	document.getElementById("showresults").className = "";
    	document.getElementById("showCode").className ="hidelist";
    	document.getElementById("buttonCode").className ="";
    	
    }
    else if(check == "Code")
    {
    	console.log('User is viewing Code of Questions');
    	document.getElementById("interaction_details").innerHTML += '\n User is viewing Code of Questions: ' + Date();
    	document.getElementById("showresults").className = "hidelist";
    	document.getElementById("showCode").className ="";
    	document.getElementById("buttonText").className ="";
    }
    
}

function toggleAnswerview(e) {
    var check = e.value;
    if(check == "Text")
    {
    	document.getElementById("AnsText").className = "";
    	document.getElementById("AnsCode").className ="hidelist";
    	document.getElementById("AnsConcepts").className ="hidelist";
    }
    else if(check == "Code")
    {
    	document.getElementById("AnsText").className = "hidelist";
    	document.getElementById("AnsCode").className ="";
    	document.getElementById("AnsConcepts").className ="hidelist";
    }
    else if(check == "Concepts")
    {
    	document.getElementById("AnsText").className = "hidelist";
    	document.getElementById("AnsCode").className ="hidelist";
    	document.getElementById("AnsConcepts").className ="";
    }
    	
    
}

function toggleMainview(e) {
    var check = e.value;
    if(check == "Question")
    {
    	document.getElementById("QuestionProp").className = "";
    	document.getElementById("AnswerProp").className ="hidelist";
    	
    }
    else if(check == "Answer")
    {
    	document.getElementById("QuestionProp").className = "hidelist";
    	document.getElementById("AnswerProp").className ="";
    }
    
}

function changeView(d)
{
	//alert(d.innerHTML);
	if(d.value == "Switch View")
	{
		var setValue = document.getElementById("GoToValue").value;
	}
	else if(d.value == "Menu")
	{
		var setValue = document.getElementById("GoToMenu").value;
	}
	else
		var setValue = "Error";
	document.getElementById("Selection").value = setValue;
	document.forms["hiddenform"].submit();
}

function swapQuestion(d)
{
	if(d.innerHTML == "Question-1")
	{
		document.getElementById("Question-1").className = "";
    	document.getElementById("Question-2").className ="hidelist";
	}
	else if(d.innerHTML == "Question-2")
		{
			document.getElementById("Question-2").className = "";
    		document.getElementById("Question-1").className ="hidelist";
		}
}
function checkValues()
{
	var ans1 = document.getElementById("Q1Ans").value;
	var ans2 = document.getElementById("Q2Ans").value;
	
	document.getElementById("Check_Answers").innerHTML = "";
	
	if(!ans1 || !ans1.trim())
		document.getElementById("Check_Answers").innerHTML = "Please provide answer to Question 1.  <br>" ;
	if(!ans2 || !ans2.trim())
			document.getElementById("Check_Answers").innerHTML += "Please provide answer to Question 2.  <br>" ;
			
	if(document.getElementById("Check_Answers").innerHTML == "")
		{
			var UserId = prompt("Enter User ID: ");
			
			if (UserId != "") {
			        document.getElementById("UID").value = UserId;
			        return true;
			}
			else
			{
				document.getElementById("Check_Answers").innerHTML += "Please provide User ID  <br>" ;
				return false;
			}
				
		}
	else
		return false;
	
}

// Common code

function precheck() {
    var userentry = document.getElementById("enteredQuery").value;
    var userid = document.getElementById("UserID").value;

    //userentry = userentry.trim();
    if(!userentry || !userentry.trim())
    	{
    		document.getElementById("precheckresult").innerHTML = "Query string cannot be blank.";
    		return false;
    	}
    else if(!userid || !userid.trim())
        {
    		document.getElementById("precheckresult").innerHTML = "Please enter User ID. ";
			return false;	
        }
    else
    	{
    		document.getElementById("UserID_Details").value = userid;
    		var logVal = document.getElementById("interaction_details").innerHTML;
    		document.getElementById("LogDetails_SysA").value += logVal;
    		document.getElementById("LogDetails_SysA").value += '\n User Query: '+ userentry + ": " + Date();
    		var preference = document.querySelector('input[name="radiochoice"]:checked').value;
    		//alert(preference);
    		document.getElementById("LogDetails_SysA").value += '\n User Preference: '+ preference + ": " + Date();
    		
    		return true;
    	}
    
 }
 
function testingcall(d)
{
	
		//alert(d.id);
		var enteredval = d.id;
		console.log('User clicked on '+d.id+' circle');
		document.getElementById("interaction_details").innerHTML += '\n User clicked on '+d.id+' circle: ' + Date();
		
		var children;
		for(var k=0; k<2; k++)
		{
			if(k==0)
				children = document.getElementById("showresults").children;
			else
				children = document.getElementById("showCode").children;
		
		
		for (var i = 0; i < children.length; i++) {
			children[i].style["background"]="#FFE4C4"; //FFE4C4
			var val = children[i].getAttribute("data-value");
			//alert(val);
			if(val == enteredval)
			{
				children[i].style["background"]="yellow";
				children[i].scrollIntoView();
			}
		}
		}
}

function showHide(d) {
	
	var clicked_action = d.getAttribute("data-value");
	//alert(d.value);
    var children = d.parentNode.children;
    if(d.value == "Show Answer") 
	{
    	if(d.innerHTML != "Hide Answer")
    		d.innerHTML = "Hide Answer"
    	else
    		d.innerHTML = "View Answer"		
    	
    	console.log('User viewed Text-answer(s) of Question: '+ clicked_action);
    	document.getElementById("interaction_details").innerHTML += '\n User viewed Text-answer(s) of Question: '+ clicked_action + ": " + Date();	
	}
    else if(d.value == "Answer Code")
    {
    	if(d.innerHTML != "Hide Answer Code")
    		d.innerHTML = "Hide Answer Code"
    	else
    		d.innerHTML = "View Answer Code"
    			
    	console.log('User viewed Code-answer(s) of Question: '+ clicked_action);
    	document.getElementById("interaction_details").innerHTML += '\n User viewed Code-answer(s) of Question: '+ clicked_action + ": " + Date();
    }
    else if(d.value == "Relevant Code button")
    {
    	console.log('User found Code of Question: '+ clicked_action+' to be relevant');
    	document.getElementById("interaction_details").innerHTML += '\n User found Code: '+ clicked_action+' to be relevant' + ": " + Date();
    	if(d.className == "buttonPress")
    		d.className="";
    	else
    		d.className="buttonPress";
    	return;
    }
    else if(d.value == "Relevant button")
    {
    	console.log('User found Text of Question: '+ clicked_action+' to be relevant');
    	document.getElementById("interaction_details").innerHTML += '\n User found Text: '+ clicked_action+' to be relevant' + ": " + Date();
    	if(d.className == "buttonPress")
    		d.className="";
    	else
    		d.className="buttonPress";
    	return;
    }
	for (var i = 0; i < children.length; i++) {
		var compare = children[i].getAttribute("class");
		if(compare == 'hidelistdifferent')
			children[i].className = "showlistdifferent";
		else if(compare == 'showlistdifferent')
			children[i].className = "hidelistdifferent";
  // Do stuff value="Switch View"
}
	
}


