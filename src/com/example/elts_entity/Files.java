package com.example.elts_entity;

import java.io.Serializable;
/**
 * 存放客户端的url，考试信息
 * @author liukai
 *
 */

public class Files implements Serializable {
	private String url;
	private String fnExamInfo;
	private String fnQuestion;
	private String fnUser;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFnExamInfo() {
		return fnExamInfo;
	}
	public void setFnExamInfo(String fnExamInfo) {
		this.fnExamInfo = fnExamInfo;
	}
	public String getFnQuestion() {
		return fnQuestion;
	}
	public void setFnQuestion(String fnQuestion) {
		this.fnQuestion = fnQuestion;
	}
	public String getFnUser() {
		return fnUser;
	}
	public void setFnUser(String fnUser) {
		this.fnUser = fnUser;
	}
	public Files(String url, String fnExamInfo, String fnQuestion, String fnUser) {
		super();
		this.url = url;
		this.fnExamInfo = fnExamInfo;
		this.fnQuestion = fnQuestion;
		this.fnUser = fnUser;
	}
	public Files() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Files [url=" + url + ", fnExamInfo=" + fnExamInfo + ", fnQuestion=" + fnQuestion + ", fnUser=" + fnUser
				+ "]";
	}
	
}
