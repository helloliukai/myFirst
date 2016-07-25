package com.example.elts;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BaseActivity extends Activity implements android.view.View.OnClickListener{
	<T extends View> T findViewById_(int id){
		return (T)findViewById(id);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
