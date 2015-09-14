package com.recommender.common.model;

public class Keyword_Format {
	private String word1;
	private double keycount1;
	
	private String displaykeywords;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	
	
	
	public Keyword_Format()
	{
		keyword1 = keyword2 = keyword3 = "";
	}
	
	public String getWord1() {
		return word1;
	}
	public void setWord1(String word1) {
		this.word1 = word1;
	}
	public double getKeycount1() {
		return keycount1;
	}
	public void setKeycount1(double keycount1) {
		this.keycount1 = keycount1;
	}
	public String getDisplaykeywords() {
		return displaykeywords;
	}
	public void setDisplaykeywords(String displaykeywords) {
		this.displaykeywords = displaykeywords;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	
	
	
	
}
