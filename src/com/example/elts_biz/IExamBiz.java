package com.example.elts_biz;

import java.util.ArrayList;

import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Question;
import com.example.elts_entity.User;

public interface IExamBiz {
	/**
	 * 登陆验证
	 * @param uid：输入的编号
	 * @param password：输入的密码
	 * @return
	 * @throws IdOrPasswordExreption
	 */
	User login(int uid,String passwrod) throws IdOrPasswordExreption;
	/**
	 * 本方法在考试主菜单页面和考试页面调用
	 * @return
	 */
	User getUser();
	/**
	 * 从服务端加载考试题目
	 */
	void loadQuestion();
	/**
	 * 开始考试
	 * 将一组考试题吗打乱
	 * @return
	 */
	ExamInfo beginForm();
	/**
	 * 为考试考试窗口提供一道考题
	 * @param qid
	 * @return
	 */
	Question getQuestion(int qid);
	/**
	 * 保存考生选择的答案，保存到userAnswer属性中
	 * @param qid 考题的索引
	 * @param userAnswer 考生的答案
	 */
	void saveUserAnswers(int qid,ArrayList<String> userAnswer);
	/**
	 * 判题，打分
	 * @return
	 */
	int over();
	
}
