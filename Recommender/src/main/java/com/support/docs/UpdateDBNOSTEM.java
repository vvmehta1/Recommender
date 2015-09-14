package com.support.docs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.recommender.common.dao.AnswerListDAO;
import com.recommender.common.dao.QuestionListDAO;

public class UpdateDBNOSTEM {
	
	public static void main(String args[])
	{
		method_DB_Update_NOSTEM();
		
	}
	
	public static void method_DB_Update_NOSTEM()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		QuestionListDAO questionListDAO = context.getBean(QuestionListDAO.class);
		AnswerListDAO answerListDAO = context.getBean(AnswerListDAO.class);
		PropertiesFile propsfile = context.getBean(PropertiesFile.class);
		
		final String docsPath = propsfile.getBaseUrl()+propsfile.getDocs(); 
		
		int question_no = 0;
		int no_answers = 0;
		String sCurrentLine;
		int no_of_docs = 1;
		
		BufferedReader br = null;
		String prev = null;
		    		
		  	          while(no_of_docs <= 16739) {
		  	        try{  
		  	        	
		  	        	String file = docsPath+"\\row"+no_of_docs+".txt";
		  	        	
		  	        	br = new BufferedReader(new FileReader(file));
						
		  	        	
						String outputname = null;
						
						while ((sCurrentLine = br.readLine()) != null) {
							
							//System.out.println(sCurrentLine);
							String[] terms = sCurrentLine.split(",");
							terms[0] = terms[0].trim();
							if(prev == null && terms.length > 1)
							{
								
								if(terms[0].equals("question"))
								{
									prev = terms[0];
									outputname = "Q"+question_no+".txt";
									question_no++;
								}
								
								// Code to UPDATE Question into DB
								questionListDAO.updateQuestion(outputname, terms[2]);
								
							}
							else if(prev != null && terms.length > 1)
							{
								
								if(terms[0].equals("answer") || terms[0].equals("answer  accepted-answ"))
								{
									
									outputname = "A"+(question_no-1)+"."+no_answers+".txt";
									answerListDAO.update(outputname, terms[2]);
									
									no_answers++;
								}
								else if(terms[0].equals("question"))
								{
									if (no_answers != 0)
										no_answers = 0;
									// Code to update Question into DB
									outputname = "Q"+question_no+".txt";
									questionListDAO.updateQuestion(outputname, terms[2]);
									question_no++;
								}
							}
							
							
							
							
						}
		  	          }
		  	        catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (br != null)br.close();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
						no_of_docs++;
		  	          // Increment here
		  	          }
		  	        context.close();
		  	        System.out.println("Update COMPLETE!!");
		}
	}


