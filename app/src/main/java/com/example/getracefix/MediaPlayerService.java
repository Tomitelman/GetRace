package com.example.getracefix;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MediaPlayerService extends Service {
    private MediaPlayer mediaPlayer ;



    @Override
    public IBinder onBind(Intent intent) {

        return null ;
        // TODO: Return the communication channel to the service.
    }
//defining the media player
    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(this , R.raw.tokyo); //R.raw.name of the song
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
        return START_STICKY ;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
