package com.recommender.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="QUESTION_LIST")
public class Question_List {
	
	@Id
	@Column(name="question_no")
	private String question_no;
	
	private String question_text;
	
	private String question_code;
	
	private String question_concepts;

	public String getQuestion_no() {
		return question_no;
	}

	public void setQuestion_no(String question_no) {
		this.question_no = question_no;
	}

	public String getQuestion_text() {
		return question_text;
	}

	public void setQuestion_text(String question_text) {
		this.question_text = question_text;
	}

	public String getQuestion_code() {
		return question_code;
	}

	public void setQuestion_code(String question_code) {
		this.question_code = question_code;
	}

	public String getQuestion_concepts() {
		return question_concepts;
	}

	public void setQuestion_concepts(String question_concepts) {
		this.question_concepts = question_concepts;
	}
	
	@Override
	public String toString(){
		return "Question id="+question_no+", Question Text="+question_text+", Question Code="+question_code+
				", Question Concepts="+question_concepts;
	}
	
	public Question_List()
	{
		
	}

}
