package e14.apps.media;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread logoTimer = new Thread(){
		
			public void run()
			{
				try{
					int logotimer = 0;
					while(logotimer<2000)
					{
						sleep(100);
						logotimer = logotimer+100;
					}
					startActivity(new Intent("e14.apps.media.MainActivity"));
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		logoTimer.start();
	}

}
