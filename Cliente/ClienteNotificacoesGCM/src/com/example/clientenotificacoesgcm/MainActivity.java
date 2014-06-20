package com.example.clientenotificacoesgcm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener {

	private Socket socket;
	private static final int SERVERPORT = 5000;
	private static final String SERVER_IP = "10.0.2.2";
	Button btnRegId;
	EditText etRegId;
	GoogleCloudMessaging gcm;
	String regid;
	boolean esperando = true;
	String PROJECT_NUMBER = "437541266834";
	public static final String TAG = "Cliente GCM";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnRegId = (Button) findViewById(R.id.btnGetRegId);
		etRegId = (EditText) findViewById(R.id.etRegId);

		btnRegId.setOnClickListener(this);

		// Thread socket
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

					socket = new Socket(serverAddr, SERVERPORT);

				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}).start();
		// Thread socket
	}

	public void getRegId() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(getApplicationContext());
					}
					regid = gcm.register(PROJECT_NUMBER);
					esperando = false;
					msg = "Device registered, registration ID=" + regid;
					try {
						PrintWriter out = new PrintWriter(new BufferedWriter(
								new OutputStreamWriter(socket.getOutputStream())),
								true);
						out.println(regid);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					Log.i("GCM", msg);

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();

				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				etRegId.setText(msg + "\n");
			}
		}.execute(null, null, null);
	}

	@Override
	public void onClick(View v) {
		getRegId();
		
	}

}
