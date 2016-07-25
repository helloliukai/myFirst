package com.example.elts;

import com.example.elts_biz.ExamBiz;
import com.example.elts_biz.IExamBiz;
import com.example.elts_biz.IdOrPasswordExreption;
import com.example.elts_entity.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class LoginActivity extends BaseActivity {
	
	EditText metId,metPwd;
	RadioButton mrbSaveAll,mrbSaveId,mrbNotSave;
	IExamBiz mBiz;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initData();
		setListener();
	}
	private void initData() {
		// TODO Auto-generated method stub
		SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
		int id=sp.getInt("id", -1);
		if(id!=-1){
			metId.setText(""+id);
		}
		String pwd=sp.getString("password", "");
		metPwd.setText(pwd);
		new Thread(){
			public void run(){
				mBiz=new ExamBiz(LoginActivity.this);
			}
		}.start();
	}
	private void setListener() {
		// TODO Auto-generated method stub
		existListener();
		loginListener();
		
		
	}
	private void loginListener() {
		findViewById(R.id.btLogin).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strId=metId.getText().toString();
				if(TextUtils.isEmpty(strId)){
					metId.setError("±àºÅ²»ÄÜÎª¿Õ");
					return;
				}
				int id=Integer.parseInt(strId);
				String pwd=metPwd.getText().toString();
				if(TextUtils.isEmpty(pwd)){
					metPwd.setError("ÃÜÂë²»ÄÜÎª¿Õ");
					return;
				}
				try {
					User user=mBiz.login(id, pwd);
					saveLoginInfo(id,pwd);//store data of id and password
					Intent intent=new Intent(LoginActivity.this,MainMenuActivity.class);
					intent.putExtra("biz", (ExamBiz)mBiz);
					startActivity(intent);
					
				} catch (IdOrPasswordExreption e) {
					// TODO Auto-generated catch block
					if(e.getMessage().equals("ÇëÏÈ×¢²á")){
						metId.setError("ÇëÏÈ×¢²á");
					}else if(e.getMessage().equals("ÃÜÂë´íÎó")){
						metPwd.setError("ÃÜÂë´íÎó");
					}
					
				}
				
			}
		});
	}
	protected void saveLoginInfo(int id,String pwd) {
		// TODO Auto-generated method stub
		SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.clear();//Çå³ýall data of editor
		editor.commit();
		if(mrbSaveAll.isChecked()){
			editor.putInt("id", id);
			editor.putString("password", pwd);
			editor.commit();
		}else if(mrbSaveId.isChecked()){
			editor.putInt("id", id);
			editor.commit();
		}
	}
	private void existListener() {
		findViewById(R.id.btExit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View layout=View.inflate(LoginActivity.this, R.layout.exit_prompt, null);
				AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("ÍË³ö")
				.setView(layout)
				.setPositiveButton("ÍË³ö", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).setNegativeButton("È¡Ïû", null);
				builder.create().show();
			}
		});
	}
	private void initView() {
		// TODO Auto-generated method stub
		metId=findViewById_(R.id.etId);
		metPwd=findViewById_(R.id.etPwd);
		mrbNotSave=findViewById_(R.id.rbNotSave);
		mrbSaveAll=findViewById_(R.id.rbSaveAll);
		mrbSaveId=findViewById_(R.id.rbSaveId);
		
	}

	
}
