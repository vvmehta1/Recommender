package com.recommender.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ANSWER_LIST")
public class Answer_List {
	
	@Id
	@Column(name="answer_id")
	private String answer_id;
	
	private String question_num;
	
	private String answer_text;
	
	private String answer_type;
	
	private String answer_code;
	
	private String answer_concepts;

	public String getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}


	public String getAnswer_text() {
		return answer_text;
	}

	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}

	public String getAnswer_type() {
		return answer_type;
	}

	public void setAnswer_type(String answer_type) {
		this.answer_type = answer_type;
	}

	public String getAnswer_code() {
		return answer_code;
	}

	public void setAnswer_code(String answer_code) {
		this.answer_code = answer_code;
	}

	public String getAnswer_concepts() {
		return answer_concepts;
	}

	public void setAnswer_concepts(String answer_concepts) {
		this.answer_concepts = answer_concepts;
	}

	public String getQuestion_num() {
		return question_num;
	}

	public void setQuestion_num(String question_num) {
		this.question_num = question_num;
	}
	
	@Override
	public String toString(){
		return "Answer id="+answer_id+", QuestionNo="+question_num+", Answer Type="+answer_type+
				"Answer Text="+answer_text+", Answer Code="+answer_code+", Answer Concepts="+answer_concepts;
	}
	
	public Answer_List()
	{
		
	}
}
