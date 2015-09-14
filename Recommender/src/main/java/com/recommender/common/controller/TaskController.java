package com.recommender.common.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.support.docs.PropertiesFile;

@Controller
public class TaskController {

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	PropertiesFile f = context.getBean(PropertiesFile.class);
	
	@RequestMapping(value = "/Task", method = RequestMethod.GET)
	public String get_task(@RequestParam("TaskSelection") String SelectedTask, ModelMap model) {
		
		if("Task-01".equals(SelectedTask))
			return "Task-01";
		else if("Task-02".equals(SelectedTask))
			return "Task-02";
		
		return "Erros";
		

	}
	
	
	@RequestMapping(value = "/SubmitTask", method = RequestMethod.POST)
	public String keep_task_records(@RequestParam MultiValueMap<String,String> parameters, ModelMap model) {
		
		StringBuffer Errors = new StringBuffer();
		System.out.println("Inside Submit Task");
		String navigation = null;
		if(parameters.containsKey("TaskNumber"))
		{
			navigation = parameters.get("TaskNumber").get(0);
		}
		/*
		if(parameters.containsKey("UserId"))
		{
				 String UserId = parameters.get("UserId").get(0);
				 
				 File folderUID = new File(f.getUser_response_locationstore() + UserId);
				 
				 if (!folderUID.exists()) {
						
					 if (folderUID.mkdir()) {
							System.out.println("Directory is created for UserId" + UserId);
					} 
					else {
							Errors.append("Failed to create user directory! \n");
							System.out.println("Failed to create user directory for UserId" + UserId);
							return "Errors";
							
						}
				 }
				 
				 // Task Number
				 if(parameters.containsKey("TaskNumber"))
				 {
					 String task_no = parameters.get("TaskNumber").get(0);
					 
					 File taskFolder = new File(folderUID + "\\" + task_no);
					 
					 if (!taskFolder.exists()) {
							
						 if (taskFolder.mkdir()) {
								System.out.println("Directory is created for Task" + task_no);
						} 
						else {
								Errors.append("Failed to create task directory! \n");
								System.out.println("Failed to create task directory for Task" + task_no);
								return "Errors";
								
							}
					 }
					 
					// Store files
						if(parameters.containsKey("Question_1_Text") && parameters.containsKey("question1_answer"))
						{
							File Q1 = new File(taskFolder + "\\" + task_no+".txt");
							
							// if file doesnt exists, then create it
							try {
							if (!Q1.exists()) {
								Q1.createNewFile();
							}
							
							FileWriter fw = new FileWriter(Q1,true);
							String Question1Text = parameters.get("Question_1_Text").get(0);
							String Question1Ans = parameters.get("question1_answer").get(0);
							fw.write(Question1Text + "," + Question1Ans + "\n");
							fw.close();
							
							}
							catch (IOException e) {
								e.printStackTrace();
							}
					 
						}
						else
						{
							System.out.println("Problem with saving answer to Q1");	
							Errors.append("Problem with saving answer to Q1  \n");
						}
						
						if(parameters.containsKey("Question_2_Text") && parameters.containsKey("question2_answer"))
						{
							File Q1 = new File(taskFolder + "\\" + task_no+".txt");
							
							// if file doesnt exists, then create it
							try {
							if (!Q1.exists()) {
								Q1.createNewFile();
							}
							
							FileWriter fw = new FileWriter(Q1,true);
							String Question2Text = parameters.get("Question_2_Text").get(0);
							String Question2Ans = parameters.get("question2_answer").get(0);
							fw.write(Question2Text + "," + Question2Ans + "\n");
							fw.close();
							
							}
							catch (IOException e) {
								e.printStackTrace();
							}
					 
						}
						else
						{
							System.out.println("Problem with saving answer to Q2");	
							Errors.append("Problem with saving answer to Q2  \n");
						}
					 
					 
				 }
				 else
				 {
					 System.out.println("Task Information Missing. ");
					 Errors.append("Task Information missing  \n");
					 return "Errors";
				 }
				 
		}
		else
			Errors.append("User ID Missing \n");
			*/
		if(Errors.length() == 0)
		{
			if(navigation != null)
			{
				if("Task01".equals(navigation))
					return "Task-02";
				else if("Task02".equals(navigation))
					return "Acknowledgement";
			}
		
			return "Feedback";
		}
		
		else
		{
			model.addAttribute("ErrorDescription", Errors);
			return "Errors";
		}
			
		

	}
	
	
}
