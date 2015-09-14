package com.recommender.common.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.recommender.common.dao.AnswerListDAO;
import com.recommender.common.dao.QuestionListDAO;
import com.recommender.common.model.Keyword_Format;
import com.recommender.common.model.Links;
import com.recommender.common.model.Nodes;
import com.recommender.common.model.Question_List;
import com.recommender.common.model.Recommended_Questions;
import com.recommender.common.model.ResultSVG;
import com.recommender.common.model.Result_List;
import com.support.docs.PropertiesFile;

public class Visualization_Aid {
	
		static HashMap<String, Keyword_Format> DocumentConcepts = new HashMap<String, Keyword_Format>();
		static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		static final QuestionListDAO questionListDAO = context.getBean(QuestionListDAO.class);
		static final AnswerListDAO answerListDAO = context.getBean(AnswerListDAO.class);
		static final PropertiesFile file = context.getBean(PropertiesFile.class);
	

public static String jsonSVG_Version1(Recommended_Questions[] filenames){
			
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
					Keyword_Format current = DocumentConcepts.get(filenames[p].getQuestion_no());
					String concept = current.getWord1();
					// If new, add as new group
					
					if(!ConceptMapping.containsKey(concept))
					{
						// Add word
						Nodes word = new Nodes();
						word.setName(concept);
						word.setGroup(p+1);
						word.setType(30);
						word.setDisplaykeyword1(concept);
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
		
		public static String jsonSVG(Recommended_Questions[] filenames){
		
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
				Keyword_Format current = DocumentConcepts.get(filenames[p].getQuestion_no());
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
	
	public static ArrayList<String> wrapjson(Recommended_Questions[] filenames)
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
	}
	
	public static void saveLog(String UserId, String logdetails, String source)
	{ 
		 File folderUID = new File(file.getUser_response_locationstore() + UserId);
		 
		 if (!folderUID.exists()) {
				
			 if (folderUID.mkdir()) {
					System.out.println("Directory is created for UserId: " + UserId);
					
			} 
			else {
					//Errors.append("Failed to create user directory! \n");
					System.out.println("Failed to create user directory for UserId" + UserId);
					return;
				}
		 }
		 
		 if(!logdetails.isEmpty())
			{
			 	File logfile = null;
			 	
			 	if("Visual".equals(source))
			 		logfile = new File(folderUID + "\\LogFileVisual.txt");
			 	else if("Traditional".equals(source))
			 		logfile = new File(folderUID + "\\LogFileTraditional.txt");
				// if file doesnt exists, then create it
				try {
				if (!logfile.exists() && logfile!=null) {
					logfile.createNewFile();
				}
				
				FileWriter fw = new FileWriter(logfile , true);
				//fw.write("\nUser is using " + source + "\n");
				fw.write(logdetails);
				fw.close();
				
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		 
			}
			else
				System.out.println("No log details to save");
		 
	}
	
public static String CopyjsonSVG(Recommended_Questions[] filenames){
		
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
				Keyword_Format current = DocumentConcepts.get(filenames[p].getQuestion_no());
				String concept = current.getWord1();
				// If new, add as new group
				
				if(!ConceptMapping.containsKey(concept))
				{
					// Add word
					Nodes word = new Nodes();
					word.setName(concept);
					word.setGroup(p+1);
					word.setType(30);
					//word.setDisplaykeyword2(concept);
					word.setDisplaytext(concept);
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

}
