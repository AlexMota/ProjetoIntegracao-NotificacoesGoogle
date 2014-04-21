package clientenotificacoesgcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMBrodcastReceiver extends WakefulBroadcastReceiver{

	public static void completeWakefulIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		ComponentName componentName = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
		
		
		
	}

}
