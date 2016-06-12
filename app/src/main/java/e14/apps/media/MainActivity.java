/////////////////////////////////////////////////////////////////////////
// MainActivity.java - This class shows the list of the songs on       //
//                     the device. Allows user to select a song and    //
//                     when user selects a song it takes the user to   //
//                     next screen Songpage                            //
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	// arrays to store song info
    String[] musicTitle;
	String[] musicPath;
	String[] albumId;
	String[] album;
	Integer[] imageid;
	
	// show songs 
	ListView list;
	int count;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
				
        loadSongFiles();

		// create list adaptor
        CustomList adapter = new CustomList(MainActivity.this, musicTitle,album/*, imageid*/);
        list=(ListView)findViewById(R.id.listView);
             list.setAdapter(adapter);
			 
			 // click on a song from the list
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
          
                    		String songname = musicPath[position];
                    		String aId = albumId[position];
							
							// save the intent for new screen
                    		Intent i=new Intent(MainActivity.this,songpage.class);
                    		i.putExtra("songpath",songname);
                    		
							// go to new page
                    		startActivity(i);
                        }
                    });
    }

	// get song files from media store
   public void loadSongFiles()
   {
	   Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
	    musicPath = new String[cursor.getCount()];
	    musicTitle= new String[cursor.getCount()];
	    albumId = new String[cursor.getCount()];
	    album = new String[cursor.getCount()];
	    count=cursor.getCount();
	    cursor.moveToFirst();
	    do{
	        musicPath[cursor.getPosition()]=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
	        musicTitle[cursor.getPosition()]=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
	        albumId[cursor.getPosition()]=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
	        album[cursor.getPosition()] = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
    
	    }while(cursor.moveToNext());
	   cursor.close();
   }
}


