package tn.enis.lavachefaitmeuh;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by zalila on 15/10/15.
 */
public class ImageDetailFragment extends android.support.v4.app.Fragment {

    private static final String IMAGE_DATA_EXTRA = "resId";
    private int numImage;

    public ImageView getImageView() {
        return imageView;
    }

    private ImageView imageView;
    //private MediaPlayer mp;

    static ImageDetailFragment newInstance (int _numImage) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, _numImage);
        f.setArguments(args);
        return f;
    }

    //  Constructeur par défaut vide
    public ImageDetailFragment () {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        numImage = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new ImageView (this.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams
                (new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final int resId = MainActivity.imagesIds [numImage];
        imageView.setImageResource(resId);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Couper le son si besoin est

                MainActivity.couperSon();

                MainActivity.mp = MediaPlayer.create(getContext(), MainActivity.sonsIds[numImage]);
                MainActivity.mp.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        //  Couper le son si besoin est

        MainActivity.couperSon();
    }
}
