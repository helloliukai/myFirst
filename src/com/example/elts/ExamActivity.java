package com.example.elts;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.elts_biz.IExamBiz;
import com.example.elts_entity.ExamInfo;
import com.example.elts_entity.Question;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class ExamActivity extends BaseActivity{
	
	IExamBiz mBiz;
	ExamInfo mExamInfo;
	Gallery mGallery;
	QuestionAdapter adapter;
	TextView mtvExamInfo,mtvLeftTime;
	EditText metQuestion;
	CheckBox[] mchkOptions;
	int mPotions;//��ǰ�����ڼ����е�����
	Timer mTimer;//��ʱ������ʾ����ʱ�Ͷ�ʱ��
	ArrayList<Integer> mSelectedItems;//���������Ŀ��������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);
		initData();//��ʼ������
		initView();
		beginExam();//��ʼ����
		setListener();
		
	}
	private void setListener() {
		// TODO Auto-generated method stub
		setOnItemSelectedListener();
		for(CheckBox chk:mchkOptions){
			chk.setOnClickListener(this);
		}
	}
	private void setOnItemSelectedListener() {
		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//���ĸ���ѡ���л�ȡѡ��Ĵ𰸣����浽��ǰ������
				mBiz.saveUserAnswers(mPotions, getUserAnswersFromCheckBoxs());
				setSelectedItem();//�޸�Gallery�е�item��ʾ
				mPotions=position;//�������Ŀ������
				showQuestion();//��ʾ��ѡ����Ŀ��ˢ���ĸ���ѡ��
				
			}

			
		});
	}
	protected void setSelectedItem() {
		//�����ǰ�����Ѿ�����
		ArrayList<String> userAnswers=mBiz.getQuestion(mPotions).getUserAnswers();
		if(mSelectedItems.contains(mPotions)){
			
			for(CheckBox chk:mchkOptions){
				
				if(userAnswers.isEmpty()){//���û��ѡ���
					adapter.removeSelectedItem(mPotions);
				}
			}
		}else{//�������û������
			if(!userAnswers.isEmpty()){
				adapter.addSelectedItem(mPotions);
			}
		}
		
	}
	/**
	 * ��ʾ��ѡ����Ŀ��ˢ���ĸ���ѡ��
	 */
	protected void showQuestion() {
		// TODO Auto-generated method stub
		for(CheckBox chk:mchkOptions){
			chk.setChecked(false);
		}
		Question q=mBiz.getQuestion(mPotions);
		metQuestion.setText(q.toString());//��ʾ�µ���Ŀ
		//ȡ�����������Ĵ�
		ArrayList<String> userAnswers=q.getUserAnswers();
		if(userAnswers.isEmpty()){
			//û��������
			return;
		}
		for(int i=0;i<userAnswers.size();i++){
			char answer=userAnswers.get(i).charAt(0);
			//A,B,C,D-0��1��2��3
			mchkOptions[answer-65].setChecked(true);
		}
		
	}
	protected ArrayList<String> getUserAnswersFromCheckBoxs() {
		// TODO Auto-generated method stub
		ArrayList<String> userAnswers=new ArrayList<String>();
		for(int i=0;i<mchkOptions.length;i++){
			if(mchkOptions[i].isChecked()){
				userAnswers.add(mchkOptions[i].getText().toString());
			}
		}
		return userAnswers;
	}
	private void beginExam() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run(){
				mExamInfo=mBiz.beginForm();
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mtvExamInfo.setText(mExamInfo.toString());
						adapter=new QuestionAdapter(mSelectedItems,ExamActivity.this,mExamInfo);
						mGallery.setAdapter(adapter);
					}
					
				});
				startTime();//��ʼ��ʱ
			}

			
		}.start();
	}
	//��ʼ��ʱ
	private void startTime() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();//get current time fome system
		final long endTime=startTime+mExamInfo.getLimitTime()*60*1000;//�������ʱ��
		mTimer=new Timer();
		mTimer.schedule(new TimerTask(){
		long minute,second;
			@Override
			public void run() {
				//��ʾ����ʱ
				long leftTime=endTime-System.currentTimeMillis();
				minute=leftTime/1000/60;//����ʣ��ʱ��ķ�����
				second=leftTime/1000%60;//��������
				runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mtvLeftTime.setText("ʣ��ʱ��"+minute+":"+second);
					}
					
				});
				
			}
			
		}, 0,1000);
		//���ÿ��Խ���ʱ�����Ӧ
		mTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				mTimer.cancel();//ֹͣ�ƻ�����
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						commit();
					}
					
				});
				
			}
			
		},endTime);
	}
	private void initView() {
		// TODO Auto-generated method stub
		mtvExamInfo=findViewById_(R.id.tvExamInto);
		mtvLeftTime=findViewById_(R.id.tvLeftTime);
		metQuestion=findViewById_(R.id.etQuestion);
		mchkOptions=new CheckBox[4];
		mchkOptions[0]=findViewById_(R.id.chkA);
		mchkOptions[1]=findViewById_(R.id.chkB);
		mchkOptions[2]=findViewById_(R.id.chkC);
		mchkOptions[3]=findViewById_(R.id.chkD);
		mGallery=findViewById_(R.id.gallery);
		
		
		
	}
	private void initData() {
		// TODO Auto-generated method stub
		mPotions=0;//��ǰ����
		mBiz=(IExamBiz) getIntent().getSerializableExtra("biz");
		mSelectedItems=new ArrayList<Integer>();
		mTimer=new Timer();
		
	}
	class QuestionAdapter extends BaseAdapter{
		
		ArrayList<Integer> selectedItems;
		Context context;
		ExamInfo examInfo;
		
		
		public QuestionAdapter(ArrayList<Integer> selectedItems, Context context, ExamInfo examInfo) {
			super();
			this.selectedItems = selectedItems;
			this.context = context;
			this.examInfo = examInfo;
		}
		public ArrayList<Integer> getSelectedItems(){
			
			
			return selectedItems;
			
		}
		public void addSelectedItem(Integer position){
			selectedItems.add(position);
			//֪ͨ�����������ݸı䣬
			notifyDataSetChanged();
		}
		public void removeSelectedItem(Integer position){
			selectedItems.remove(position);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return examInfo.getQuestionCount();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View layout=View.inflate(context, R.layout.item_question,null);
			ImageView ivThumb=(ImageView) layout.findViewById(R.id.ivThumb);
			TextView tvQuestion=(TextView) layout.findViewById(R.id.tvQuestion);
			tvQuestion.setText("��"+(position+1));
			
			if(selectedItems.contains(position)){
				//˵����������
				ivThumb.setImageResource(R.drawable.answer24x24);
				
			}else{
				ivThumb.setImageResource(R.drawable.ques24x24);
			}
			
			return layout;
		}
		
	}
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.exam, menu);
		return true;
		
	}
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 if(item.getItemId()==R.id.mi_commit){
			 commit();//����
		 }
		return super.onOptionsItemSelected(item);
	}
	private void commit() {
		// TODO Auto-generated method stub
		int score=mBiz.over();
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.exam_commit32x32)
		.setTitle("����")
		.setMessage(mBiz.getUser().getName()+"�ĳɼ�:"+score+"��")
		.setPositiveButton("����", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.create().show();//���������
	}
	public void onClick(View v){
		if(v instanceof CheckBox){//���������Ǹ�ѡ��
			if(mSelectedItems.contains(mPotions)){//�����������
				for(CheckBox chk:mchkOptions){
					if(chk.isChecked()){
						return;
					}
				}
				adapter.removeSelectedItem(mPotions);
			}else{//���û������
				for(CheckBox chk:mchkOptions){
					if(chk.isChecked()){
						adapter.addSelectedItem(mPotions);
						break;
					}
				}
			}
		}
	}
}
