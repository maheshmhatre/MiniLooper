/////////////////////////////////////////////////////////////////////////
// Utilities.java - This class provides list to show song information //
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
*  This module provides various functions to support the view functionality. It 
*  provides time converter function, a function to change the progress bar and 
*  to get the percentage.
*/

package e14.apps.media;

public class Utilities {
	
	// convert milliseconds time to Timer Format Hours:Minutes:Seconds
	public String milliSecondsToTimer(long milliseconds){
		String finalTimerString = "";
		String secondsString = "";
		
		// Convert total duration into time
		   int hours = (int)( milliseconds / (1000*60*60));
		   int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
		   int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
		   // Add hours if there
		   if(hours > 0){
			   finalTimerString = hours + ":";
		   }
		   
		   // Prepending 0 to seconds if it is one digit
		   if(seconds < 10){ 
			   secondsString = "0" + seconds;
		   }else{
			   secondsString = "" + seconds;}
		   
		   finalTimerString = finalTimerString + minutes + ":" + secondsString;
		
		// return timer string
		return finalTimerString;
	}
	
	// get Progress percentage @param currentDuration @param totalDuration
	public int getProgressPercentage(long currentDuration, long totalDuration){
		Double percentage = (double) 0;
		
		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);
		
		// calculating percentage
		percentage =(((double)currentSeconds)/totalSeconds)*100;
		
		// return percentage
		return percentage.intValue();
	}

	//change progress to timer returns current duration in milliseconds
	public int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double)progress) / 100) * totalDuration);
		
		// return current duration in milliseconds
		return currentDuration * 1000;
	}
}
