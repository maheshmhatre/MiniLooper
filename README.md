Minilooper creates song loops. 
MainActivity
---------------------------------------------------------------------------
Access multimedia audio files from disc using MediaStore package.
Loads all the files names, path on disc, album name, album id in a listView.
User selects a song to be played by touching on it, opens up a new activity.

SongPage
---------------------------------------------------------------------------
UI provides two slidebars to select starting and ending positions of the song being played.
User selects desired starting and ending positions.
A button to keep playing the song in a loop or play only once.
When user hits play the song starts and immediately jumps to starting position. 
The player keep playing the song upto user selected ending point.
At ending point the flag repeat (set by loop button is checked), 
  if true, the loop again goes back to songs starting point.
  else, song stops
  
Hit back button and user can go back on Song list to select different song.  
