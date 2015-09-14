package com.recommender.common.dao;

import java.util.List;

import com.recommender.common.model.Question_List;


public interface QuestionListDAO {
	
	public void save(Question_List a);
	
	public List<Question_List> getQuestionByQuestionId(String QuestionID);
	
	public void updateQuestion(String QuestionID, String questionText);

}
