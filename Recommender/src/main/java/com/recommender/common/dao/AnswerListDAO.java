package com.recommender.common.dao;

import java.util.List;

import com.recommender.common.model.Answer_List;



public interface AnswerListDAO {
	
	public void save(Answer_List a);
	
	public List<Answer_List> getAnswersByQuestionId(String QuestionID);
	
	public Answer_List getAnswerByAnswerId(String AnswerID);
	
	public void update(String AnswerID, String answer_text);

}
