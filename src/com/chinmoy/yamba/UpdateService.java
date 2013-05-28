package com.chinmoy.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {

	public static final String TAG = "UpdateService";
	Twitter twitter;
	boolean running;
	public static final int DELAY = 30;// in seconds

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		running = false;
		Log.d(TAG, "Service Created");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		running = true;
		new Thread() {
			public void run() {
				while (running) {
					try {
						List<Status> timeline =  ((YambaApp)getApplication()).getTwitter().getPublicTimeline();
						for (Status status : timeline) {
							Log.d(TAG, String.format("%s %s", status.user.name,
									status.text));
						}
						Log.d(TAG, "Service Started");
						Thread.sleep(DELAY * 1000);
					} catch (TwitterException e) {
						Log.e(TAG, "Failed because of network failure", e);
					} catch (InterruptedException e) {
						Log.d(TAG, "updater interrupted", e);
					}
				}
			}
		}.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		running = false;
		Log.d(TAG, "Service destroyed");
	}

}
