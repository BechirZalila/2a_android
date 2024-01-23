package tn.enis.lavachefaitmeuh;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.logging.Logger;

/**
 * Created by zalila on 27/09/15.
 */
public class MainActivity extends FragmentActivity {

    public static int[] imagesIds = { R.drawable.bear, R.drawable.cat, R.drawable.chicken, R.drawable.chimpansee,
            R.drawable.cow, R.drawable.dog, R.drawable.donkey, R.drawable.elephant, R.drawable.frog, R.drawable.goat,
            R.drawable.goose, R.drawable.horse, R.drawable.kitten, R.drawable.lion, R.drawable.monkey, R.drawable.pig,
            R.drawable.rooster, R.drawable.seal, R.drawable.sheep, R.drawable.tiger, R.drawable.turkey,
            R.drawable.whale, R.drawable.wolf };

    public static int[] sonsIds = { R.raw.bear, R.raw.cat, R.raw.chicken, R.raw.chimpansee, R.raw.cow, R.raw.dog,
            R.raw.donkey, R.raw.elephant, R.raw.frog, R.raw.goat, R.raw.goose, R.raw.horse, R.raw.kitten, R.raw.lion,
            R.raw.monkey, R.raw.pig, R.raw.rooster, R.raw.seal, R.raw.sheep, R.raw.tiger, R.raw.turkey, R.raw.whale,
            R.raw.wolf };

    private SharedPreferences preferences;
    private static final String POSITION_COURANTE = "positionCourante";
    private static final int POSITION_DEPART = 0;
    private int positionCourante;
    public static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences (MODE_PRIVATE);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ImagePagerAdapter imgAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imagesIds.length);
        viewPager.setAdapter(imgAdapter);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        positionCourante = preferences.getInt(POSITION_COURANTE, POSITION_DEPART);

        viewPager.setCurrentItem(positionCourante);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                //  Couper le son si besoin est

                couperSon ();

                //  Enregistrer la nouvelle position

                positionCourante = position;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(POSITION_COURANTE, positionCourante);
                editor.apply();
            }
        });
    }


    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        private final int taille;

        public ImagePagerAdapter(android.support.v4.app.FragmentManager fm, int _taille) {
            super (fm);
            this.taille = _taille;
        }

        @Override
        public android.support.v4.app.Fragment getItem(final int position) {
            return ImageDetailFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return taille;
        }

    }

    public static void couperSon () {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
