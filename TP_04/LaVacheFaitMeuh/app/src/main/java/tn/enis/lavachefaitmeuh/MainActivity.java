package tn.enis.lavachefaitmeuh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * Created by zalila on 27/09/15.
 */
public class MainActivity extends Activity implements View.OnTouchListener {

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
    private ImageSwitcher imageSwitcher;
    private float xCoord = 0;
    private static final float SEUIL = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
        imageSwitcher.setFactory(new MyImageSwitcherFactory());

        imageSwitcher.setOnTouchListener(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        positionCourante = preferences.getInt(POSITION_COURANTE, POSITION_DEPART);
        imageSwitcher.setImageDrawable(getDrawableByIndex(positionCourante));
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
            mp = null;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float delta;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xCoord = event.getX();
                return false;

            case MotionEvent.ACTION_UP:
                delta = event.getX() - xCoord;

                //  Couper le son si besoin est
                if (mp != null) {
                    mp.stop();
                    mp.release();
                    mp = null;
                }

                if (delta > SEUIL) {
                    //  Gauche -> Droite
                    positionCourante = (positionCourante + imagesIds.length - 1) % imagesIds.length;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(POSITION_COURANTE, positionCourante);
                    editor.commit();

                    // Animation personnalisée pour illusion de défilement

                    imageSwitcher.setInAnimation(this, R.anim.entree_gauche);
                    imageSwitcher.setOutAnimation(this, R.anim.sortie_droite);

                    imageSwitcher.setImageDrawable(getDrawableByIndex(positionCourante));

                } else if (delta < -SEUIL) {
                    //  Droite -> Gauche
                    positionCourante = (positionCourante + 1) % imagesIds.length;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(POSITION_COURANTE, positionCourante);
                    editor.commit();

                    // Animation personnalisée pour illusion de défilement

                    imageSwitcher.setInAnimation(this, R.anim.entree_droite);
                    imageSwitcher.setOutAnimation(this, R.anim.sortie_gauche);

                    imageSwitcher.setImageDrawable(getDrawableByIndex(positionCourante));

                } else {
                    mp = MediaPlayer.create(this, sonsIds[positionCourante]);
                    mp.start();
                }
                return true;
            default:
                return false;
        }
    }

    private class MyImageSwitcherFactory implements ViewSwitcher.ViewFactory {
        public View makeView() {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams
                    (new ImageSwitcher.LayoutParams
                            (ImageSwitcher.LayoutParams.MATCH_PARENT,
                             ImageSwitcher.LayoutParams.MATCH_PARENT));
            return imageView;
        }
    }
}
