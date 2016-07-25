package com.example.elts_dao;

import java.util.ArrayList;

import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Question;
import com.example.elts_entity.User;

public interface IExamDao {
	ArrayList<Question> loadQuestion();
	
	ExamInfo loadExamInfo();
	/**
	 * ��½��֤ʹ��
	 * @param uid
	 * @return
	 */
	User findUser(int uid);
	
}
