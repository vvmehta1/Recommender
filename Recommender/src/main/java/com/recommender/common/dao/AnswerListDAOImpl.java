package com.recommender.common.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.recommender.common.model.Answer_List;



public class AnswerListDAOImpl extends HibernateDaoSupport implements AnswerListDAO{
	
	@Override
	public void save(Answer_List p) {
		getHibernateTemplate().save(p);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer_List> getAnswersByQuestionId(String question_ID) {
		
		List answerList;
		answerList = getHibernateTemplate().find("from Answer_List where question_num=?", question_ID);
		return answerList;
	}
	
	@Override
	public Answer_List getAnswerByAnswerId(String AnswerID) {
		
		List answerList;
		answerList = getHibernateTemplate().find("from Answer_List where answer_id=?", AnswerID);
		return (Answer_List)answerList.get(0);
		
	}
	
	@Override
	public void update(String AnswerID, String answer_text) {
		
		Answer_List a = getAnswerByAnswerId(AnswerID);
		a.setAnswer_text(answer_text);
		getHibernateTemplate().update(a);	
	}
	

}
