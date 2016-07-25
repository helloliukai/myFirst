package com.example.elts_dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Files;
import com.example.elts_entity.Question;
import com.example.elts_entity.User;
import com.example.utils.HttpUtils;

import android.content.Context;

public class ExamDao_PullParse extends ExamDaoBase implements IExamDao,Serializable {
	Files mFiles;
	/**
	 * 
	 * @param context
	 */
	public ExamDao_PullParse(Context context){
		mFiles=getFiles(context);
		loadUsers(); //从服务端下载已注册的用户
	}
	@Override
	public ArrayList<Question> loadQuestion() {
		// TODO Auto-generated method stub
		ArrayList<Question> questions=new ArrayList<Question>();
		String url=mFiles.getUrl()+mFiles.getFnQuestion();
		InputStream in=null;
		try {
			in = HttpUtils.getInputStream(url, null, HttpUtils.RequestMethod.GET);
			XmlPullParser parser=XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(in,"utf-8");
			for(int eventType=XmlPullParser.START_DOCUMENT;eventType!=XmlPullParser.END_DOCUMENT;eventType=parser.next()){
				if(eventType==XmlPullParser.START_TAG){
					String tagName=parser.getName();
					if("question".equals(tagName)){
						String answer=parser.getAttributeValue(null,"answer");
						ArrayList<String> answers=new ArrayList<String>();
						for(int i=0;i<answer.length();i++){
							answers.add(answer.charAt(i)+"");
						}
						int score=Integer.parseInt(parser.getAttributeValue(null, "score"));
						int level=Integer.parseInt(parser.getAttributeValue(null, "level"));
						String title=parser.getAttributeValue(null, "title");
						StringBuilder options=new StringBuilder();
						for(int i=4;i<=7;i++){
							options.append(parser.getAttributeValue(i)).append("\n");
						}
						Question q=new Question(answers,level, score, title,options.toString());
						questions.add(q);
					}
				}
			}
			return questions;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			HttpUtils.closeClient();
		}
		return null;
	}

	@Override
	public ExamInfo loadExamInfo(){
		// TODO Auto-generated method stub
		String url=mFiles.getUrl()+mFiles.getFnExamInfo();
		InputStream in=null;
		try {
			in = HttpUtils.getInputStream(url, null, HttpUtils.RequestMethod.GET);
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser parser=factory.newPullParser();//create 解析器用于pull解析
			parser.setInput(in,"utf-8");
			ExamInfo examInfo=new ExamInfo();
			for(int eventType=XmlPullParser.START_DOCUMENT;eventType!=XmlPullParser.END_DOCUMENT;eventType=parser.next()){
				if(eventType==XmlPullParser.START_TAG){
					String tagName=parser.getName();//获取标签名称
					if("exam_info".equals(tagName)){
						examInfo.setLimitTime(Integer.parseInt(parser.getAttributeValue(1)));
						examInfo.setQuestionCount(Integer.parseInt(parser.getAttributeValue(2)));
						return examInfo;
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			HttpUtils.closeClient();
		}
		
		return null;
	}

	@Override
	protected void loadUsers() {
		// TODO Auto-generated method stub
		String url=mFiles.getUrl()+mFiles.getFnUser();
		InputStream in =null;
		try {
			in=HttpUtils.getInputStream(url, null, HttpUtils.RequestMethod.GET);
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(in,"utf-8");
			for(int eventType=XmlPullParser.START_DOCUMENT;eventType!=XmlPullParser.END_DOCUMENT;eventType=parser.next()){
				if(eventType==XmlPullParser.START_TAG){
					String tagName=parser.getName();
					if("user".equals(tagName)){
						int id=Integer.parseInt(parser.getAttributeValue(null, "id"));
						String name=parser.getAttributeValue(null, "name");
						String password=parser.getAttributeValue(null,"password");
						String phone=parser.getAttributeValue(null, "phone");
						String email=parser.getAttributeValue(null, "email");
						
						User user=new User(id, name, password, phone, email);
						mUsers.put(user.getId(), user);
						
					}
				}
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			HttpUtils.closeClient();
		}
	}

}
