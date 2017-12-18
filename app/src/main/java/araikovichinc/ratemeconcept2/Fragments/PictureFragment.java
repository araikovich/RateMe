package araikovichinc.ratemeconcept2.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BitmapHelper;

/**
 * Created by Tigran on 03.12.2017.
 */

public class PictureFragment extends Fragment implements View.OnClickListener {

    ImageView photo, pictureDontLike, pictureLike;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_picture, container, false);
        photo = (ImageView)v.findViewById(R.id.taken_picture);
        pictureDontLike = (ImageView)v.findViewById(R.id.picture_dotn_like);
        pictureLike = (ImageView)v.findViewById(R.id.picture_next);
        pictureDontLike.setOnClickListener(this);
        pictureLike.setOnClickListener(this);
        Bundle bundle = getArguments();
        Bitmap bitmap = BitmapHelper.byteToBitmap(bundle.getByteArray("photo"));
        if(bundle.getInt("camId") == 0)
            bitmap = BitmapHelper.rotate(bitmap, 90);
        else
            bitmap = BitmapHelper.rotate(bitmap, 270);
        photo.setImageBitmap(bitmap);
        return v;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picture_dotn_like:
                getActivity().onBackPressed();
                break;
            case R.id.picture_next:
                break;
        }
    }
}
