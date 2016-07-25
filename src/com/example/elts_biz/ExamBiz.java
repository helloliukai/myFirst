package com.example.elts_biz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.example.elts.R;
import com.example.elts_dao.ExamDao_JsonParser;
import com.example.elts_dao.ExamDao_PullParse;
import com.example.elts_dao.IExamDao;
import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Question;
import com.example.elts_entity.User;


import android.content.Context;
/**
 * 业务逻辑层
 * @author Administrator
 *
 */
public class ExamBiz implements IExamBiz,Serializable{
	User mUser;//当前登录成功的考生
	ArrayList<Question> mQuestions;
	ExamInfo mExamInfo;
	IExamDao mDao;
	public ExamBiz(Context context){
		String parserMode=context.getString(R.string.parse_mode);
		if("json".equals(parserMode)){
			mDao=new ExamDao_JsonParser(context);
			
		}else if("pull_xml".equals(parserMode)){
			mDao=new ExamDao_PullParse(context);
		}else if("sax_xml".equals(parserMode)){
			
		}else if("txt".equals(parserMode)){
			
		}
	}
	
	@Override
	public User login(int uid, String passwrod) throws IdOrPasswordExreption {
		// TODO Auto-generated method stub
		User user=mDao.findUser(uid);
		if(user==null){
			//说明用户不存在
			throw new IdOrPasswordExreption("请先注册");
		}
		if(!user.getPassword().equals(passwrod)){
			throw new IdOrPasswordExreption("密码错误");
		}
		mUser=user;
		return user;
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return mUser;
	}

	@Override
	public void loadQuestion() {
		mQuestions=mDao.loadQuestion();
	}

	@Override
	public ExamInfo beginForm() {
		Collections.shuffle(mQuestions,new Random());
		for(int i=0;i<mQuestions.size();i++){
			Question q=mQuestions.get(i);
			String title=q.getTitle();
			title=(i+1)+title.substring(title.indexOf("."));
			q.setTitle(title);
		}
		mExamInfo=mDao.loadExamInfo();
		
		mExamInfo.setUid(mUser.getId());
		return mExamInfo;
	}

	@Override
	public Question getQuestion(int qid) {
		// TODO Auto-generated method stub
		return mQuestions.get(qid);
	}

	@Override
	public void saveUserAnswers(int qid, ArrayList<String> userAnswer) {
		Question q=getQuestion(qid);
		q.setUserAnswers(userAnswer);
		

	}

	@Override
	public int over() {
		int total=0;
		for(Question q:mQuestions){
			if(q.getAnswers().equals(q.getUserAnswers())){
				total+=q.getScore();
			}
		}
		return total;
	}

}
