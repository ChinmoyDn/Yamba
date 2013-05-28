package com.chinmoy.yamba;

import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {

	public static final String TAG = "StatusActivity";
	Button button;
	EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		et = (EditText) findViewById(R.id.editText1);
		
	}



	@Override
	public void onClick(View v) {
		final String statusString = et.getText().toString();
		
		new PostToTwitter().execute(statusString);
		Log.d(TAG,"Button Clicked statusString: " + statusString);
		
	}
	
	public class PostToTwitter extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			try {
//				Twitter twitter = new Twitter("student", "password");
//				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				 ((YambaApp)getApplication()).getTwitter().setStatus(params[0]);
				return "Successfully posted: "+params[0];
			} catch (TwitterException e) {
				Log.e(TAG,"Exception caught ",e);
				e.printStackTrace();
				return "Failed posting: "+params[0];
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);		
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}
	}
	
	
	//menu 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, UpdateService.class);
		Intent intentRefresh = new Intent(this, RefreshService.class);
		switch(item.getItemId()){
		case R.id.start_service:
			startService(intent);
			return true;
		case R.id.stop_service:
			stopService(intent);
			return true;
		case R.id.refresh:
			startService(intentRefresh);
			return true;
		case R.id.prefs:
			startActivity(new Intent(this, Preferences.class));
		default:
			return false;
		}
	}
}
