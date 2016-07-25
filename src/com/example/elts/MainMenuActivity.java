package com.example.elts;

import com.example.elts_biz.ExamBiz;
import com.example.elts_biz.IExamBiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainMenuActivity extends BaseActivity {

	TextView mtvWelcome;
	IExamBiz mBiz;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		mBiz=(IExamBiz) getIntent().getSerializableExtra("biz");
		
		initView();
		setListener();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		setExamListener();
		setExitListener();
	}

	private void setExitListener() {
		findViewById(R.id.btnExit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View layout=View.inflate(MainMenuActivity.this, R.layout.exit_prompt, null);
				AlertDialog.Builder builder=new AlertDialog.Builder(MainMenuActivity.this);
				builder.setTitle("�˳�")
				.setView(layout)
				.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).setNegativeButton("ȡ��", null);
				builder.create().show();
			}
		});
	}

	private void setExamListener() {
		findViewById(R.id.btnExam).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//create a dialog to display progress of loading exam question
				final ProgressDialog dialog=new ProgressDialog(MainMenuActivity.this);
				dialog.setTitle("�������ؿ���");
				dialog.setMessage("������");
				dialog.show();
				new Thread(){
					public void run(){
						mBiz.loadQuestion();//���ؿ���
						dialog.dismiss();//close dialog
						Intent intent=new Intent(MainMenuActivity.this, ExamActivity.class);
						intent.putExtra("biz", (ExamBiz)mBiz);
						startActivity(intent);
					}
				}.start();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		mtvWelcome=findViewById_(R.id.tvWelcome);
		mtvWelcome.setText("��ӭ"+mBiz.getUser().getName()+"�μӿ���");
	}

	
}