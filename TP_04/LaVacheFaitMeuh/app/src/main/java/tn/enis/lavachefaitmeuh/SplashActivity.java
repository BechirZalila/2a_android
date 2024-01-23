package tn.enis.lavachefaitmeuh;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashIcon = (ImageView) findViewById(R.id.image_view);

        //  Animation simple...
        //Animation simpleAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        //simpleAnimation.setDuration(2000);
        //splashIcon.startAnimation(simpleAnimation);

        //  Ou bien Animation complexe...
        Animation customAnimation = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        splashIcon.startAnimation(customAnimation);

        //  Attendre la fin de l'animation et lancer la seconde activité après
        customAnimation.setAnimationListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        //  Lancement de la seconde activité
        Intent intent = new Intent (this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
