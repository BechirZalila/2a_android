package tn.enis.lavachefaitmeuh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zalila on 27/09/15.
 */
public class MainActivity extends Activity implements View.OnClickListener, MediaPlayer.OnCompletionListener {

    private static int[] imagesIds = {
            R.drawable.ours, R.drawable.chat, R.drawable.poule, R.drawable.chimpanse,
            R.drawable.vache, R.drawable.chien, R.drawable.ane, R.drawable.elephant,
            R.drawable.grenouille, R.drawable.chevre, R.drawable.oie, R.drawable.cheval,
            R.drawable.chaton, R.drawable.lion, R.drawable.singe, R.drawable.cochon,
            R.drawable.coq, R.drawable.foque, R.drawable.mouton, R.drawable.tigre,
            R.drawable.dinde, R.drawable.baleine, R.drawable.loup
    };

    private static int[] sonsIds = {
            R.raw.ours, R.raw.chat, R.raw.poule, R.raw.chimpanse, R.raw.vache, R.raw.chien,
            R.raw.ane, R.raw.elephant, R.raw.grenouille, R.raw.chevre, R.raw.oie, R.raw.cheval,
            R.raw.chaton, R.raw.lion, R.raw.singe, R.raw.cochon, R.raw.coq, R.raw.foque,
            R.raw.mouton, R.raw.tigre, R.raw.dinde, R.raw.baleine, R.raw.loup
    };

    private SharedPreferences preferences;
    private static final String POSITION_COURANTE = "positionCourante";
    private static final int POSITION_DEPART = 0;
    private MediaPlayer mp;
    private int positionCourante;
    ImageView animalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);

        animalImage = (ImageView) findViewById(R.id.animal);

        animalImage.setOnClickListener(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        positionCourante = preferences.getInt(POSITION_COURANTE, POSITION_DEPART);
        animalImage.setImageDrawable(getDrawableByIndex(positionCourante));
    }

    private Drawable getDrawableByIndex (int index) {
        return getResources().getDrawable(imagesIds[index]);
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        mp = MediaPlayer.create(this, sonsIds[positionCourante]);
        mp.start();

        mp.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        positionCourante = (positionCourante + 1) % imagesIds.length;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(POSITION_COURANTE, positionCourante);
        editor.commit();

        animalImage.setImageDrawable(getDrawableByIndex(positionCourante));
    }
}
