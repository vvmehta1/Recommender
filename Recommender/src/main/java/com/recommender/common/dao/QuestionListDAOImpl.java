package com.recommender.common.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.recommender.common.model.Question_List;



public class QuestionListDAOImpl extends HibernateDaoSupport implements QuestionListDAO{
	
	@Override
	public void save(Question_List p) {
		getHibernateTemplate().save(p);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Question_List> getQuestionByQuestionId(String question_ID) {
		
		List question = null;
		
		question = getHibernateTemplate().find("from Question_List where question_no=?", question_ID);
		
		
		
		return question;
	}
	
	@Override
	public void updateQuestion(String QuestionID, String newquestionText)
	{
		Question_List q = getQuestionByQuestionId(QuestionID).get(0);
		q.setQuestion_text(newquestionText);
		getHibernateTemplate().update(q);
	}
	
	

}
