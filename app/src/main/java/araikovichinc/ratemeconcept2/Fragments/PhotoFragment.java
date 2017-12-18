package araikovichinc.ratemeconcept2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import araikovichinc.ratemeconcept2.R;

/**
 * Created by Tigran on 22.11.2017.
 */

public class PhotoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyCamFragment myCamFragment = new MyCamFragment();
        fragmentTransaction.replace(R.id.camera_container, myCamFragment);
        fragmentTransaction.commit();
        return v;
    }
}
