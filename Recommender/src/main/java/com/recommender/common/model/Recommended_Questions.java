package com.recommender.common.model;

public class Recommended_Questions {
	
	private String question_no;
	private double similarity;
	private int no_of_answers;
	
	public String getQuestion_no() {
		return question_no;
	}
	public void setQuestion_no(String question_no) {
		this.question_no = question_no;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	public int getNo_of_answers() {
		return no_of_answers;
	}
	public void setNo_of_answers(int no_of_answers) {
		this.no_of_answers = no_of_answers;
	}

}
