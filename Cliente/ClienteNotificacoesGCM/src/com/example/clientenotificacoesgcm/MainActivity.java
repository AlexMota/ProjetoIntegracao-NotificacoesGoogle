package com.example.clientenotificacoesgcm;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	String SENDER_ID = "Your-Sender-ID";
	static final String TAG = "GCM Demo";

	TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	Context context;

	String regId;



	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDisplay = (TextView) findViewById(R.id.textView1);

		context = getApplicationContext();

		if (checkPlayServices()) {

			gcm = GoogleCloudMessaging.getInstance(this);
			regId = getRegistrationId(context);

			if (regId.isEmpty()) {
				registerInBackground();
			}
			
		}else{
            Log.i(TAG, "No valid Google Play Services APK found.");
		}

	}
	
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

	
	private boolean checkPlayServices() {
		// TODO Auto-generated method stub

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (resultCode != ConnectionResult.SUCCESS) {

			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {

				Log.i(TAG, "This device is not supported.");
				finish();

			}
			return false;

		}

		return true;
	}

	private void storeRegistrationId(Context context, String regId) {
		// TODO Auto-generated method stub
		
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();

		

	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private String getRegistrationId(Context context) {

	    final SharedPreferences prefs = getGcmPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
			

		}

		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);

		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}

		return registrationId;
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regId;

					sendRegistrationIdToBackend();

					storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();

				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				mDisplay.append(msg + "\n");
			}
		}.execute(null, null, null);
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	private static int getAppVersion(Context context) {

		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

	}

	private SharedPreferences getGcmPreferences(Context context) {

		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	private void sendRegistrationIdToBackend() {

	}


}