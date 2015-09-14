package com.support.docs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.recommender.common.dao.AnswerListDAO;
import com.recommender.common.dao.QuestionListDAO;
import com.recommender.common.model.Answer_List;
import com.recommender.common.model.Question_List;

public class FilterStemmed {

	
	/*public static void main(String[] args){
		// TODO Auto-generated method stub

		
		  	          System.out.println("Done");
		    		 
		    		
		  	        	  
		  	          
		    		
		    	}*/
	
	static void filter_docs(PropertiesFile propsfile)
	{
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		//QuestionListDAO questionListDAO = context.getBean(QuestionListDAO.class);
		//AnswerListDAO answerListDAO = context.getBean(AnswerListDAO.class);
		//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\StemDocs"
		final String docsPath = propsfile.getBaseUrl()+propsfile.getStemDocs(); 
		
		int question_no = 0;
		int no_answers = 0;
		String sCurrentLine;
		int no_of_docs = 1;
		
		BufferedReader br = null;
		String prev = null;
		    		
		  	          while(no_of_docs <= 16739) {
		  	        try{  
		  	        	
		  	        	//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\StemDocs
		  	        	String file = propsfile.getBaseUrl()+propsfile.getStemDocs()+"\\row"+no_of_docs+".txt";
		  	        	
		  	        	br = new BufferedReader(new FileReader(file));
						OutputStream outstream;
		  	        	
						String outputname = null;
						
						while ((sCurrentLine = br.readLine()) != null) {
							
							//System.out.println(sCurrentLine);
							String[] terms = sCurrentLine.split(",");
							terms[0] = terms[0].trim();
							if(prev == null && terms.length > 1)
							{
								Question_List temp = null;
								
								if(terms[0].equals("question"))
								{
									prev = terms[0];
									//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\Q\\
									outputname = propsfile.getBaseUrl()+propsfile.getQue()+"\\Q"+question_no+".txt";
									question_no++;
									temp = new Question_List();
								}
								
								// Code to save Question into DB
								temp.setQuestion_no("Q"+(question_no-1)+".txt");
								temp.setQuestion_code(terms[1]);
								temp.setQuestion_text(terms[2]);
								temp.setQuestion_concepts(terms[3]);
								//questionListDAO.save(temp);
														
								outstream = new FileOutputStream(outputname);
								
								Writer output = new OutputStreamWriter(outstream);
								output = new BufferedWriter(output);
								output.write(sCurrentLine);
								
								output.flush();
								output.close();
								
							}
							else if(prev != null && terms.length > 1)
							{
								
								if(terms[0].equals("answer") || terms[0].equals("answer  accepted-answ"))
								{
									Answer_List tempans = new Answer_List();
									tempans.setAnswer_id("A"+(question_no-1)+"."+no_answers+".txt");
									tempans.setQuestion_num("Q"+(question_no-1)+".txt");
									tempans.setAnswer_type(terms[0]);
									tempans.setAnswer_text(terms[2]);
									tempans.setAnswer_code(terms[1]);
									tempans.setAnswer_concepts(terms[3]);
									//answerListDAO.save(tempans);
									
									//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\A\\"
									outputname = propsfile.getBaseUrl()+propsfile.getAns()+"\\A"+(question_no-1)+"."+no_answers+".txt";
									no_answers++;
									
									if(terms.length >3)
									{
										// Append concepts in original questions file man
										String question_file = propsfile.getBaseUrl()+propsfile.getQue()+"\\Q"+(question_no-1)+".txt";
										FileWriter write_question = new FileWriter(question_file,true);
										write_question.write(" "+terms[3]+" ");
										write_question.close();
									}
									
									
									
									outstream = new FileOutputStream(outputname);
									
									Writer output = new OutputStreamWriter(outstream);
									output = new BufferedWriter(output);
									
									//output.write(terms[1]);
									output.write(sCurrentLine);
									output.flush();
									output.close();
								}
								else if(terms[0].equals("question"))
								{
									if (no_answers != 0)
										no_answers = 0;
									
									
									// Code to save Question into DB
									Question_List temp = new Question_List();
									
									
									temp.setQuestion_no("Q"+question_no+".txt");
									temp.setQuestion_code(terms[1]);
									temp.setQuestion_text(terms[2]);
									temp.setQuestion_concepts(terms[3]);
									//questionListDAO.save(temp);
									outputname = propsfile.getBaseUrl()+propsfile.getQue()+"\\Q"+question_no+".txt";
									question_no++;
									
									
									outstream = new FileOutputStream(outputname);
									
									Writer output = new OutputStreamWriter(outstream);
									output = new BufferedWriter(output);
									
									output.write(sCurrentLine);
									
									output.flush();
									output.close();
								}
							}
							
							
							
							
						}
		  	          }
		  	        catch (IOException e) {
							//e.printStackTrace();
						} finally {
							try {
								if (br != null)br.close();
							} catch (IOException ex) {
								//ex.printStackTrace();
							}
						}
						no_of_docs++;
		  	          // Increment here
		  	          }
		  	        context.close();
	}
		 }

		
		