package com.recommender.common.model;

public class Nodes {
	
	String name;
	int group;
	int type;
	double opacity;
	String displaytext;
	int setTypeNode; // 0 for node of type keyword and 1 for node of type result document
	String displaykeyword1;
	String displaykeyword2;
	String displaykeyword3;
	
	public Nodes()
	{
		displaykeyword1 = displaykeyword2 = displaykeyword3 = "";
	}
	
	
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getOpacity() {
		return opacity;
	}
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	public String getDisplaytext() {
		return displaytext;
	}
	public void setDisplaytext(String displaytext) {
		this.displaytext = displaytext;
	}
	public int getSetTypeNode() {
		return setTypeNode;
	}
	public void setSetTypeNode(int setTypeNode) {
		this.setTypeNode = setTypeNode;
	}
	public String getDisplaykeyword1() {
		return displaykeyword1;
	}
	public void setDisplaykeyword1(String displaykeyword1) {
		this.displaykeyword1 = displaykeyword1;
	}
	public String getDisplaykeyword2() {
		return displaykeyword2;
	}
	public void setDisplaykeyword2(String displaykeyword2) {
		this.displaykeyword2 = displaykeyword2;
	}
	public String getDisplaykeyword3() {
		return displaykeyword3;
	}
	public void setDisplaykeyword3(String displaykeyword3) {
		this.displaykeyword3 = displaykeyword3;
	}

}
