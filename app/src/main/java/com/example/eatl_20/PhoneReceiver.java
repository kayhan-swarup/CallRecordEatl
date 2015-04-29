package com.example.eatl_20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class PhoneReceiver extends BroadcastReceiver{
	
	
	
	
	
	

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(new MyPhoneListener(context), PhoneStateListener.LISTEN_CALL_STATE);		
//		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//		if(state.equals(TelephonyManager.CALL_STATE_OFFHOOK)){
//			Toast.makeText(context, "Received", Toast.LENGTH_LONG).show();
//			
//		}
//		
	}
	
	MediaRecorder audioRecorder=null;
	public void recordAudio(){
		 audioRecorder = new MediaRecorder();
		String outputFileName = Environment.getExternalStorageDirectory().
	              getAbsolutePath() +"/EATL_RECORDINGS";
		audioRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
		audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		audioRecorder.setOutputFile(outputFileName);
		try{
			audioRecorder.prepare();
			audioRecorder.start();
		}catch(Exception e){}
		
		
	}
	public void stopRecording(){
		if(audioRecorder!=null){
			audioRecorder.stop();
			audioRecorder.release();
			audioRecorder = null;
		}
	}
	class MyPhoneListener extends PhoneStateListener {
		Context context;
		public MyPhoneListener(Context context){
			this.context = context;
		}

		boolean received = false;
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			if (state==TelephonyManager.CALL_STATE_OFFHOOK){
				received = true;
				Toast.makeText(context,"Received",Toast.LENGTH_LONG).show();
				recordAudio();
				
				
			}
			else if(state==TelephonyManager.CALL_STATE_IDLE&&received){
				received  = false;
				Toast.makeText(context, "Hanging Up", Toast.LENGTH_LONG).show();
				stopRecording();
			}
		}
		
		
	}
	
	
	

}
