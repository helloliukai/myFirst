package com.example.elts_dao;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Question;

import android.content.Context;

public class ExamDao_JsonParser extends ExamDaoBase implements IExamDao,Serializable {
	
	public ExamDao_JsonParser(Context context){
		
	}
	@Override
	public ArrayList<Question> loadQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExamInfo loadExamInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void loadUsers() {
		// TODO Auto-generated method stub

	}

}
