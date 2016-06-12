/////////////////////////////////////////////////////////////////////////
// CustomList.java - This class provides list to show song information //
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
*  This module provides a list view that is utilized to show the song
*  information on the starting page. Every list item consists of 2 text 
*  fields one for name of the song and other for name of the album. It also
*  provides with a image view to style the song entry in the list
*/


package e14.apps.media;
import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{
private final Activity context;
private final String[] web;
private final String[] album;

Random randomGenerator = new Random();
int randomInt;

public CustomList(Activity context,String[] web,String[] album/*, Integer[] imageId*/) {
super(context, R.layout.song_item, web);
this.context = context;
this.web = web;
this.album = album;

}

@Override
public View getView(int position, View view, ViewGroup parent) {
LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.song_item, null, true);

TextView txtTitle = (TextView) rowView.findViewById(R.id.text1);
TextView txtTitle2 = (TextView) rowView.findViewById(R.id.text2);
ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);

if(web[position].length() > 31)txtTitle.setText(web[position].substring(0, 30));
else txtTitle.setText(web[position]);

if(album[position].length() > 31)txtTitle2.setText(album[position].substring(0,30));
else txtTitle2.setText(album[position]);

// generate colors randomly and show the bitmap
Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.shape);
Paint paint = new Paint();
paint.setAntiAlias(true);
randomInt = randomGenerator.nextInt(10);

switch(randomInt){
case 1:paint.setColor(Color.rgb(120,144,156));
break;
case 2:paint.setColor(Color.rgb(120,144,156));
break;
case 3:paint.setColor(Color.rgb(120,144,156));
break;
case 4:paint.setColor(Color.rgb(120,144,156));
break;
case 5:paint.setColor(Color.rgb(120,144,156));
break;
case 6:paint.setColor(Color.rgb(120,144,156));
break;
case 7:paint.setColor(Color.rgb(120,144,156));
break;
case 8:paint.setColor(Color.rgb(120,144,156));
break;
case 9:paint.setColor(Color.rgb(120,144,156));
break;
default:paint.setColor(Color.rgb(120,144,156));
break;
}

Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

Canvas canvas = new Canvas(mutableBitmap);
canvas.drawCircle(50, 50,25, paint);
paint.setColor(Color.WHITE);
paint.setTextSize(25);
int index=0;

for(int num=0;num<web[position].length();num++)
{
char a =web[position].charAt(num);
int x = a;
if((x > 64) && (x < 123))
{
	index=num;
	break;
}
}

canvas.drawText(web[position].substring(index,index+1).toUpperCase(), 42, 56, paint);
imageView.setAdjustViewBounds(true);
imageView.setImageBitmap(mutableBitmap);
return rowView;
}
}

