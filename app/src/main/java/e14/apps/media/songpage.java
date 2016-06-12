/////////////////////////////////////////////////////////////////////////
// Songpage.java - This class plays song, also provides tracker        //
//					functionality that allows user to select start and //
//                  stop point of the selected song                    //
// ver 1.0                                                             //
// Mahesh Mhatre, MS Computer Engineering        					   //
// Syracuse University, 315 412-8489, mrmhatre@syr.edu                 //
//---------------------------------------------------------------------//
// Application: Songlooper                                             //
// Platform:    Eclipse Juno, lenovo E450, Windows 7      			   //
/////////////////////////////////////////////////////////////////////////

/*
*  Module Operations:
*  -------------------
*  This module shows a selected song and provides facility to select a desired 
*  start and end point of the song. Provides with Loop button which helps
*  user to repeat a selection. Also gives two buttons to start and stop the song.
*/

package e14.apps.media;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class songpage extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener{
	
	MediaPlayer mp;
	TextView startText;
	TextView endText;
	TextView status;
	String song;
	boolean repeat;
	private SeekBar startBar;
	private SeekBar endBar;
	Button startBtn;
	Button stopBtn;
	private Utilities utils;
	private Handler mHandler;
	int  currentDuration;
	int endPosition;
	long totDur;
	boolean reverse;
	boolean explisitStop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songpage);
		
		currentDuration = 0;
		endPosition = 0;
		explisitStop = false;
				
		mp = new MediaPlayer();
		mHandler = new Handler();
		startText = (TextView)findViewById(R.id.textStartTime);
		endText = (TextView)findViewById(R.id.textEndTime);
		status = (TextView)findViewById(R.id.textViewStatus);
		startBar = (SeekBar) findViewById(R.id.seekBarStart);
		endBar = (SeekBar)findViewById(R.id.seekBarStop);

		// Listeners
		startBar.setOnSeekBarChangeListener(this); // Important
		endBar.setOnSeekBarChangeListener(this); // Important
		mp.setOnCompletionListener(this); // Important
		
		repeat = false;
		reverse = false;
		
		 Intent mIntent = getIntent();
		 song = mIntent.getStringExtra("songpath");
		 
		 // init song object
		 initSong();

		 // update progress bars
			int pro_st = startBar.getProgress();
			int pro_sp = endBar.getProgress();
			
		double dur_st = (double)mp.getDuration()/1000.00;
		
		double curPos_st = ((double)pro_st/100.00)*dur_st;
		 int mins_st = (int)curPos_st / 60;
		 double rem_st = (int)curPos_st%60;
		 double time_st = (double)mins_st + (rem_st/100.00);
		updateStartSeeker(time_st);
		
		double curPos_sp = ((double)pro_sp/100.00)*dur_st;
		 int mins_sp = (int)curPos_sp / 60;
		 double rem_sp = (int)curPos_sp%60;
		 double time_sp = (double)mins_sp + (rem_sp/100.00);
		 updateEndSeeker(time_sp);
		 
		 
		 // stop button
	        stopBtn = (Button)findViewById(R.id.buttonStop);
	        stopBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startBar.setEnabled(true);
					endBar.setEnabled(true);
					mp.stop();
					initSong();
				}
			});
	        
	        // start button
	        startBtn = (Button)findViewById(R.id.buttonStart);
	        startBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startBar.setEnabled(false);
					endBar.setEnabled(false);
						playSong();
				}
			});
	        
	        Button loopBtn = (Button)findViewById(R.id.buttonLoop);
	        loopBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				if(!repeat)
				{
					repeat = true;
					Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
				}
				else
				{
					repeat = false;
					Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
				}
				}
			});
	}

	@Override
	public void finish() {
		mp.stop();
		super.finish();
	}

	public void stopSong()
	{
		if(!repeat)
		{
			startBar.setEnabled(true);
			endBar.setEnabled(true);
			mp.stop();
			initSong();
		}
		else
		{
			mp.stop();
			initSong();
			playSong();
		}
	}
	
	public void updateStartSeeker(double pro)
	{
		String fstring = String.format("%.2f", pro);
		startText.setText(fstring);	
	}
	
	public void updateEndSeeker(double pro)
	{
		String fstring = String.format("%.2f", pro);
		endText.setText(fstring);
	}
	
	public void initSong()
	{
		try{
			 mp.reset();
			 mp.setDataSource(song);
			 mp.prepare();
			 
			 totDur = mp.getDuration();
		}
		catch(IOException e)
		 {
			Log.v(getString(R.string.app_name), e.getMessage());
		 }	
	}
	
	public void playSong()
	{
		currentDuration = startBar.getProgress();
		endPosition = endBar.getProgress();
			 
			 int totalDuration = mp.getDuration();
			 
			 double per = (double)currentDuration * (double)totalDuration / 100;

			 mp.start();
			 // forward or backward to certain seconds
			 mp.seekTo((int)per);
			 int seconds = (int)(per/1000);
			 int mins = seconds / 60;
			 double rem = seconds%60;
			 double time = (double)mins + (rem/100.00);
			 startText.setText(Double.toString(time));
			 
			// Updating progress bar
			updateProgressBar();	
	}
	
	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		long curDur;
		double stopPer;
		double cursec;
		double endsec;
		int min_cs,sec_cs,min_es,sec_es;
		
		   public void run() {

			   curDur = mp.getCurrentPosition();

			   stopPer = (double)endPosition * (double)totDur / 100;
			   
			   cursec = curDur/1000;
			   endsec = stopPer/1000;
			   
			   min_cs = (int)cursec/60;
			   sec_cs = (int)cursec%60;
			   min_es = (int)endsec/60;
			   sec_es = (int)endsec%60;
			   
			   status.setText("Playing "+Integer.toString(min_cs)+":"+Integer.toString(sec_cs)+" of "+Integer.toString(min_es)+":"+Integer.toString(sec_es));

			   if((int)stopPer < (int)curDur)
			   {
				   stopSong();
				   status.setText(Long.toString(totDur));
				   stopPer = 0;
				   curDur = 0;
			   }
			   
			   // Running this thread after 1000 milliseconds

				   mHandler.postDelayed(this, 1000);
		   }
		};
		
		public void updateProgressBar() {
	        mHandler.postDelayed(mUpdateTimeTask, 1000);        
	    }	
	
	
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		if(repeat)
			{
			initSong();
			playSong();
			}	
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		int pro_st = startBar.getProgress();
		int pro_sp = endBar.getProgress();
		
	double dur_st = (double)mp.getDuration()/1000.00;
	
	double curPos_st = ((double)pro_st/100.00)*dur_st;
	 int mins_st = (int)curPos_st / 60;
	 double rem_st = (int)curPos_st%60;
	 double time_st = (double)mins_st + (rem_st/100.00);
	updateStartSeeker(time_st);
	
	double curPos_sp = ((double)pro_sp/100.00)*dur_st;
	 int mins_sp = (int)curPos_sp / 60;
	 double rem_sp = (int)curPos_sp%60;
	 double time_sp = (double)mins_sp + (rem_sp/100.00);
	 updateEndSeeker(time_sp);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		int pro_st = startBar.getProgress();
		int pro_sp = endBar.getProgress();
		
	double dur_st = (double)mp.getDuration()/1000.00;
	
	double curPos_st = ((double)pro_st/100.00)*dur_st;
	 int mins_st = (int)curPos_st / 60;
	 double rem_st = (int)curPos_st%60;
	 double time_st = (double)mins_st + (rem_st/100.00);
	updateStartSeeker(time_st);
	
	double curPos_sp = ((double)pro_sp/100.00)*dur_st;
	 int mins_sp = (int)curPos_sp / 60;
	 double rem_sp = (int)curPos_sp%60;
	 double time_sp = (double)mins_sp + (rem_sp/100.00);
	 updateEndSeeker(time_sp);
	 
	 if(pro_st > pro_sp)
	 {
		 Toast.makeText(getApplicationContext(), "Stop position is before start", Toast.LENGTH_SHORT).show();
		 reverse = true;
		 startBtn.setEnabled(false);
	 }
	 else
	 {
		 reverse = false;
		 startBtn.setEnabled(true);
	 }
		
	}

}
