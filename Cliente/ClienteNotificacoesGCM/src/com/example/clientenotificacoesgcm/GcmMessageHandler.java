package com.example.clientenotificacoesgcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService {

	String mes;
	private Handler handler;
	public static final String EXTRA_MESSAGE = "mensagem";

	public GcmMessageHandler() {
		super("GcmMessageHandler");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		Bundle extras = intent.getExtras();

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		mes = extras.getString(EXTRA_MESSAGE);
		showToast();
		Log.i("GCM",
				"Received : (" + messageType + ")  "
						+ extras.getString("title"));

		GcmBroadcastReceiver.completeWakefulIntent(intent);

	}

	public void showToast() {
		// TODO Auto-generated method stub
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG)
						.show();
			}
		});

	}

}
