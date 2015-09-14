package com.recommender.common.test;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.recommender.common.dao.AnswerListDAO;

import com.recommender.common.dao.QuestionListDAO;
import com.recommender.common.model.Answer_List;

import com.recommender.common.model.Question_List;



public class SpringHibernateMain {

	public static void main(String[] args) {

		/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		
		//PersonDAO personDAO = context.getBean(PersonDAO.class);
		QuestionListDAO questionListDAO = context.getBean(QuestionListDAO.class);
		String json=null;
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<String> arrayjson = new ArrayList<String>();
		Question_List answer = new Question_List();
		answer.setQuestion_code("nothing");
		answer.setQuestion_concepts("hola");
		answer.setQuestion_no("Q1.txt");
		answer.setQuestion_text("testing other table");
		
		
		questionListDAO.save(answer);
		System.out.println("Question:: "+ answer);
		
		List<Question_List> list = questionListDAO.getQuestionByQuestionId("Q1.txt");
		for(Question_List p : list){
			System.out.println("QuestionList::"+p);
			try{
				json = mapper.writeValueAsString(p);
				//arrayjson.add(json);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			
		System.out.println(json);
		context.close();*/
		double value = 1.5;
		value++;
		System.out.println("Hello World");
		
	}

}
