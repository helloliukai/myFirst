package com.example.elts_dao;

import java.io.Serializable;
import java.util.HashMap;
import com.example.elts.R;
import com.example.elts_entity.Files;
import com.example.elts_entity.User;
import android.content.Context;
import android.content.res.Resources;

public abstract class ExamDaoBase implements Serializable {
	HashMap<Integer,User> mUsers; //�洢���ص���ע���û�
	public ExamDaoBase(){
		mUsers=new HashMap<Integer,User>();
	}
	/**
	 * ������ע���û�
	 */
	protected abstract void loadUsers();
	/**
	 * ��ȡ���صķ���˵�ַ��ExamInfo��Question��Users���ļ���
	 * @param context
	 * @return
	 */
	protected Files getFiles(Context context){
		Resources res=context.getResources();
		String fnRootUrl=res.getString(R.string.root_url);
		//get type of analysis files from configuration files.
		String parseMode=res.getString(R.string.parse_mode);
		String fnExamInfo=null;
		String fnQuestion=null;
		String fnUser=null;
		if("txt".equals(parseMode)){
			fnExamInfo=res.getString(R.string.exam_info_txt);
			fnQuestion=res.getString(R.string.question_txt);
			fnUser=res.getString(R.string.user_txt);
		}else if("json".equals(parseMode)){
			fnExamInfo=res.getString(R.string.exam_info_json);
			fnQuestion=res.getString(R.string.question_json);
			fnUser=res.getString(R.string.user_json);
		}else if("pull_xml".equals(parseMode)){
			fnExamInfo=res.getString(R.string.exam_info_xml);
			fnQuestion=res.getString(R.string.question_xml);
			fnUser=res.getString(R.string.user_xml);
		}else if("sax_xml".equals(parseMode)){
			fnExamInfo=res.getString(R.string.exam_info_xml);
			fnQuestion=res.getString(R.string.question_xml);
			fnUser=res.getString(R.string.user_xml);
		}
		Files files=new Files(fnRootUrl, fnExamInfo, fnQuestion, fnUser);
		return files;
	}
	/**
	 * ҵ���߼����������������е�½��֤
	 * @param uid
	 * @return
	 */
	public User findUser(int uid){
		
		return mUsers.get(uid);
		
	}
}
