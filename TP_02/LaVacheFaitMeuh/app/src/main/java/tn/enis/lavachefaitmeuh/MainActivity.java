package tn.enis.lavachefaitmeuh;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zalila on 27/09/15.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView cowImage = (ImageView) findViewById(R.id.cow);

        cowImage.setOnClickListener(this);
        setVolumeControlStream (AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onStop() {
        super.onStop (); //  Nécessaire
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }

    @Override
    public void onClick(View v) {
        if (mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(this, R.raw.vache);
        mp.start();
    }
}
