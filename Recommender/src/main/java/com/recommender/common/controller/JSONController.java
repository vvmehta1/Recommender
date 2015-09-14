package com.recommender.common.controller;

import java.util.ArrayList;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.recommender.common.model.Recommended_Questions;
import com.support.docs.Preprocessing;
import com.support.docs.PropertiesFile;
import com.support.docs.Recommender;

@Controller
public class JSONController {
	//@RequestMapping("/*")initsystem
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	//QuestionListDAO questionListDAO = context.getBean(QuestionListDAO.class);
	//AnswerListDAO answerListDAO = context.getBean(AnswerListDAO.class); 
	//PropertiesFile f = context.getBean(PropertiesFile.class);
	//HashMap<String, Keyword_Format> DocumentConcepts;
	PropertiesFile f = Visualization_Aid.file;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndex(ModelMap model) {
		try{
			Visualization_Aid.DocumentConcepts = Preprocessing.setupPreprocesses(f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Welcome";

	}
	
	@RequestMapping(value = "/initsystem", method = RequestMethod.GET)
	public String getIndex_init(ModelMap model) {
		try{
			Visualization_Aid.DocumentConcepts = Preprocessing.setupPreprocesses(f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Welcome";

	}
	
	@RequestMapping(value = "/results", method = RequestMethod.POST)
	public String getVisualizationResults(@RequestParam("radiochoice") String radiochoice, @RequestParam("name") String name, @RequestParam("UserLog") String logdetails, @RequestParam("UserID_store") String UserID, ModelMap model) {
		Recommended_Questions[] resultsview = null;
		String queryString = name.toLowerCase();
		try{
			resultsview = Recommender.getResults(radiochoice, queryString, f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ArrayList<String> throwviewside;
		throwviewside = Visualization_Aid.wrapjson(resultsview);
		model.addAttribute("finaljson", throwviewside);
		
		String svgtry = Visualization_Aid.CopyjsonSVG(resultsview);
		/*if(!logdetails.isEmpty() && !UserID.isEmpty())
			Visualization_Aid.saveLog(UserID, logdetails, "Visual");*/
		if("text".equals(radiochoice))
			model.addAttribute("prevSelection", "Text Selected");
		else if("code".equals(radiochoice))
			model.addAttribute("prevSelection", "Code Selected");
		if(!UserID.isEmpty())
			model.addAttribute("UserDetailPresent", UserID);
		model.addAttribute("svgjson", svgtry);
		model.addAttribute("query", "Query: "+name);
		
		return "index";

	}
	
	@RequestMapping(value = "/Demo", method = RequestMethod.POST)
	public String getDemoResults(@RequestParam("radiochoice") String radiochoice, @RequestParam("name") String name, @RequestParam("Source") String source, ModelMap model) {
		if(source.isEmpty())
		{
			model.addAttribute("ErrorDescription", "\nNo Source Present\n");
			return "Errors";
		}
		Recommended_Questions[] resultsview = null;
		String queryString = name.toLowerCase();
		try{
			resultsview = Recommender.getResults(radiochoice, queryString, f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ArrayList<String> throwviewside = null;
		if(resultsview != null)
			throwviewside = Visualization_Aid.wrapjson(resultsview);
		model.addAttribute("finaljson", throwviewside);
		
		String svgtry=null;
		if(resultsview != null)
		{
		if("Demo".equals(source))
			svgtry = Visualization_Aid.CopyjsonSVG(resultsview);
		else if("Demo Visual".equals(source))
			svgtry = Visualization_Aid.jsonSVG_Version1(resultsview);
		}
		
		
		model.addAttribute("svgjson", svgtry);
		model.addAttribute("query", "Query: "+name);
		
		
		if("Demo".equals(source))
			return "Demo";
		else if("Demo Visual".equals(source))
			return "DemoVisual_1";
		else
		{
			model.addAttribute("ErrorDescription", "\nNo Source Present\n");
			return "Errors";
		}
	

	}
	
	@RequestMapping(value = "/TextResults", method = RequestMethod.POST)
	public String getTextResults(@RequestParam("radiochoice") String radiochoice, @RequestParam("name") String name, @RequestParam("UserLog") String logdetails, @RequestParam("UserID_store") String UserID,ModelMap model) {
		Recommended_Questions[] resultsview = null;
		String queryString = name.toLowerCase();
		try{
			resultsview = Recommender.getResults(radiochoice, queryString, f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ArrayList<String> throwviewside;
		throwviewside = Visualization_Aid.wrapjson(resultsview);
		/*if(!logdetails.isEmpty() && !UserID.isEmpty())
			Visualization_Aid.saveLog(UserID, logdetails, "Traditional");*/
		if("text".equals(radiochoice))
			model.addAttribute("prevSelection", "Text Selected");
		else if("code".equals(radiochoice))
			model.addAttribute("prevSelection", "Code Selected");
		if(!UserID.isEmpty())
			model.addAttribute("UserDetailPresent", UserID);
		model.addAttribute("finaljson", throwviewside);
		model.addAttribute("query", "Query: "+name);
		
		return "Traditional Recommender";

	}
	
	
	
	
	@RequestMapping(value = "/testing", method = RequestMethod.GET)
	public String getIndex_nopreprocessing(ModelMap model) {
		
		return "Welcome";

	}
	
	@RequestMapping(value = "/Version", method = RequestMethod.GET)
	public String getVersion(@RequestParam("VersionSelection") String VersionSelection, ModelMap model) {
		
		System.out.println("Processing Swap Request to "+VersionSelection);
		if("Visual Recommender".equals(VersionSelection))
			return "index";
		else if("Traditional Recommender".equals(VersionSelection))
			return "Traditional Recommender";
		else if("Menu".equals(VersionSelection))
			return "Welcome";
		else if("Feedback Form".equals(VersionSelection))
			return "Feedback";
		else if("Demo".equals(VersionSelection))
			return "Demo";
		else if("DemoVisual".equals(VersionSelection))
			return "DemoVisual_1";
		else
			return "Errors";

	}
	
	/*String jsonSVG(Recommended_Questions[] filenames){
		
		String arrayjson = new String();
		//int lowvalue = 0;
		if(filenames.length > 0)
		{
			List<Nodes> concepts = new ArrayList<Nodes>();
			List<Links> connectors = new ArrayList<Links>();
			ObjectMapper mapper = new ObjectMapper();
			int count;
			int bins, fillbin=1;
			int countbeans = 1;
			if(filenames.length > 15)
				{
					count = 15;
					//lowvalue = (int)(filenames[count-1].getSimilarity() * 10);
					
				}
			else
			{
				count = filenames.length;
				//lowvalue = (int)(filenames[count-1].getSimilarity() * 10);
			}
			bins = count/3;
				
			
			HashMap<String, Integer> ConceptMapping =  new HashMap<String, Integer>();
			HashMap<String, Integer> GroupMapping =  new HashMap<String, Integer>();
			HashMap<Integer, Double> GroupMaxCount =  new HashMap<Integer, Double>();
			
			int currentindex = 0; // This variable keeps track of concept locations in nodes part of JSON
			
			for(int p=0; p < count; p++)
			{
				Keyword_Format current = Visualization_Aid.DocumentConcepts.get(filenames[p].getQuestion_no());
				String concept = current.getWord1();
				// If new, add as new group
				
				if(!ConceptMapping.containsKey(concept))
				{
					// Add word
					Nodes word = new Nodes();
					word.setName(concept);
					word.setGroup(p+1);
					word.setType(30);
					word.setDisplaykeyword2(concept);
					word.setOpacity(1.0);
					word.setSetTypeNode(0);
					GroupMapping.put(concept, p+1);
					
					concepts.add(word);
					ConceptMapping.put(concept, currentindex);
					currentindex++;
					
					// Add Document
					Nodes doc = new Nodes();
					doc.setName(filenames[p].getQuestion_no());
					//doc.setDisplaytext(current.getDisplaykeywords());
					doc.setGroup(p+1);
					doc.setSetTypeNode(1);
					doc.setDisplaykeyword1(current.getKeyword1());
					doc.setDisplaykeyword2(current.getKeyword2());
					doc.setDisplaykeyword3(current.getKeyword3());
					GroupMaxCount.put((p+1), current.getKeycount1());
					doc.setType(32+( 2 * filenames[p].getNo_of_answers())); // Setting Size of the bubbles
					double setopac = 0.0;
					if(countbeans > bins)
					{
						fillbin++;
						countbeans = 1;
					}
					if(fillbin == 1)
					{
						setopac = 1.0;// (int)(filenames[p].getSimilarity() * 10.0)- lowvalue; // Setting Link strength to similarity of Query
						countbeans++;
					}
					else if (fillbin == 2)
					{
						setopac = 0.65;
						countbeans++;
					}
					else
						setopac = 0.30;
					doc.setOpacity(setopac);
					concepts.add(doc);
					// Create Link between the two
					Links link = new Links();
					link.setSource(currentindex);
					link.setTarget(ConceptMapping.get(concept));
					link.setGroup(p+1);
					link.setValue(current.getKeycount1());
										
					// Add link
					connectors.add(link);
					currentindex++;	
				}
				else
				{
					// Create Node (doc)
					Nodes doc = new Nodes();
					doc.setName(filenames[p].getQuestion_no());
					//doc.setDisplaytext(current.getDisplaykeywords());
					doc.setDisplaykeyword1(current.getKeyword1());
					doc.setDisplaykeyword2(current.getKeyword2());
					doc.setDisplaykeyword3(current.getKeyword3());
					doc.setSetTypeNode(1);
					int retrieved_group = GroupMapping.get(concept);
					doc.setGroup(retrieved_group);
					doc.setType(32+( 2 * filenames[p].getNo_of_answers())); // Set dynamic size of bubbles
					if(GroupMaxCount.containsKey(retrieved_group))
					{
						double currentthis = current.getKeycount1();
						double comparewith = GroupMaxCount.get(retrieved_group);
						if(currentthis > comparewith)
							GroupMaxCount.put(retrieved_group, currentthis);
					}
					double setopac = 0.0;
					if(countbeans > bins)
					{
						fillbin++;
						countbeans = 1;
					}
					if(fillbin == 1)
					{
						setopac = 1.0;// (int)(filenames[p].getSimilarity() * 10.0)- lowvalue; // Setting Link strength to similarity of Query
						countbeans++;
					}
					else if (fillbin == 2)
					{
						setopac = 0.65;
						countbeans++;
					}
					else
						setopac = 0.30;
					doc.setOpacity(setopac);
					concepts.add(doc);
					
					// Link node to the main concept
					Links link = new Links();
					link.setSource(currentindex);
					link.setTarget(ConceptMapping.get(concept));
					//link.setValue(5);
					link.setGroup(retrieved_group);
					link.setValue(current.getKeycount1());
					connectors.add(link);
					
					currentindex++;	
					
					
				}
			}
			
			for(Links calcLinkStrength : connectors)
			{
				double currentlinkStrength = calcLinkStrength.getValue();
				
				int groupid = calcLinkStrength.getGroup();
				double maxgroupval = GroupMaxCount.get(groupid);
				
				
				double newvalue = currentlinkStrength * 12 / maxgroupval;
				
				calcLinkStrength.setValue(newvalue);	
			}
			
			// Link all the concepts together
			int iterations = 0;
			
			//String prev_word = null;
			int prev_location = 0;
			int first_location = 0;
			for(String getword : ConceptMapping.keySet())
			{
				
				if(iterations == 0)
				{
					//prev_word = getword;
					prev_location = ConceptMapping.get(getword);
					first_location = prev_location;
					iterations++;
					continue;
				}
				int currentIndex = ConceptMapping.get(getword);
				
				
				Links link = new Links();
				link.setSource(currentIndex);
				link.setTarget(prev_location);
				link.setValue(2);
				connectors.add(link);
				//prev_word = getword;
				prev_location = currentIndex;
				
				iterations++;
			}
			// Closing the graph. Connecting first n last keyword
			Links link = new Links();
			link.setSource(prev_location);
			link.setTarget(first_location);
			link.setValue(2);
			connectors.add(link); // Uncomment this line to close (connect) graph
			
			// Create ResultSVG and convert to json and add into model
			ResultSVG finalresult = new ResultSVG();
			finalresult.setNodes(concepts);
			finalresult.setLinks(connectors);
			
			try{
				arrayjson = mapper.writeValueAsString(finalresult);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			
		}
		
		return arrayjson;
	}
	
	ArrayList<String> wrapjson(Recommended_Questions[] filenames)
	{
		ArrayList<String> arrayjson = new ArrayList<String>();
		String json=null;
		ObjectMapper mapper = new ObjectMapper();
		int count;
		if(filenames.length > 15)
			count = 15;
		else
			count = filenames.length;
		for(int p=0; p < count; p++)
		{
			List question, answers;
			Result_List resultview = new Result_List();
			question = questionListDAO.getQuestionByQuestionId(filenames[p].getQuestion_no());
			Question_List q = (Question_List)question.get(0);
			resultview.setQ(q);
			answers = answerListDAO.getAnswersByQuestionId(filenames[p].getQuestion_no());
			filenames[p].setNo_of_answers(answers.size());
			resultview.setAnswers(answers);
			try{
				json = mapper.writeValueAsString(resultview);
				arrayjson.add(json);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
					
		}
	
		System.out.println(arrayjson);
		return arrayjson;
	}*/
	
	
}
	
	
	
	