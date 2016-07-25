package com.example.elts_biz;

import java.util.ArrayList;

import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Question;
import com.example.elts_entity.User;

public interface IExamBiz {
	/**
	 * ��½��֤
	 * @param uid������ı��
	 * @param password�����������
	 * @return
	 * @throws IdOrPasswordExreption
	 */
	User login(int uid,String passwrod) throws IdOrPasswordExreption;
	/**
	 * �������ڿ������˵�ҳ��Ϳ���ҳ�����
	 * @return
	 */
	User getUser();
	/**
	 * �ӷ���˼��ؿ�����Ŀ
	 */
	void loadQuestion();
	/**
	 * ��ʼ����
	 * ��һ�鿼���������
	 * @return
	 */
	ExamInfo beginForm();
	/**
	 * Ϊ���Կ��Դ����ṩһ������
	 * @param qid
	 * @return
	 */
	Question getQuestion(int qid);
	/**
	 * ���濼��ѡ��Ĵ𰸣����浽userAnswer������
	 * @param qid ���������
	 * @param userAnswer �����Ĵ�
	 */
	void saveUserAnswers(int qid,ArrayList<String> userAnswer);
	/**
	 * ���⣬���
	 * @return
	 */
	int over();
	
}
