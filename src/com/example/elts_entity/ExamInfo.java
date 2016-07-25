package com.example.elts_entity;

import java.io.Serializable;

public class ExamInfo implements Serializable {
	private String subjectTitle;
	private int limitTime;
	private int questionCount;
	private int uid;
	public String getSubjectTitle() {
		return subjectTitle;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}
	
	
	public int getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	
	public ExamInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	public ExamInfo(String subjectTitle, int limitTime, int questionCount, int uid) {
		super();
		this.subjectTitle = subjectTitle;
		this.limitTime = limitTime;
		this.questionCount = questionCount;
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "ExamInfo [subjectTitle=" + subjectTitle + ", limitTime=" + limitTime + ", questionCount="
				+ questionCount + ", uid=" + uid + "]";
	}
	
}
