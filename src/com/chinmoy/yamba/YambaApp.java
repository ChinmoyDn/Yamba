package com.chinmoy.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Application;
import android.util.Log;

public class YambaApp extends Application{
	public static final String TAG = "YambaApp";
	Twitter twitter;
	
	public Twitter getTwitter() {
		return twitter;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			twitter = new Twitter("student","password");
			twitter.setAPIRootUrl("http://yamba.marakana.com/api");
			Log.d(TAG,"created");
		} catch (TwitterException e) {
			Log.e(TAG,"Failed to initialize twitter account",e);
		}
	}
}
