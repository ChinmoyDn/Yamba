package com.chinmoy.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService{
	Twitter twitter;
	public static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Service Created");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		try {
			List<Status> timeline = ((YambaApp)getApplication()).getTwitter().getPublicTimeline();
			for (Status status : timeline) {
				Log.d(TAG, String.format("%s %s", status.user.name,
						status.text));
			}
		} catch (TwitterException e) {
			
			Log.e(TAG,"Failed to access twitter service",e);
		}
		Log.d(TAG, "On Handle Intent");
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "on destroy");
	}


}
