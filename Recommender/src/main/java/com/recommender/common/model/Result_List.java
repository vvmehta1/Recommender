package com.recommender.common.model;

import java.util.ArrayList;
import java.util.List;

public class Result_List {
	
	Question_List q;
	List<Answer_List> answers;
	
	public Question_List getQ() {
		return q;
	}
	public void setQ(Question_List q) {
		this.q = q;
	}
	
	public Result_List()
	{
		answers = new ArrayList<Answer_List>();
	}
	public List<Answer_List> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer_List> answers) {
		this.answers = answers;
	}

}
