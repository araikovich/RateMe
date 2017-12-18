package araikovichinc.ratemeconcept2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.CustomViewPager;
import araikovichinc.ratemeconcept2.Utils.Pages;

/**
 * Created by Tigran on 22.11.2017.
 */

public class RateFragment extends Fragment implements View.OnClickListener {

    CustomViewPager viewPager;
    ImageButton camBtn, notificationBtn;
    BottomNavigationViewEx bottomNavigationViewEx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Pages.setCurrentPage(0);
        View v = inflater.inflate(R.layout.fragment_rate, container, false);
        viewPager = (CustomViewPager) getActivity().findViewById(R.id.home_view_pager);
        viewPager.setPagingEnabled(true);
        camBtn = (ImageButton) v.findViewById(R.id.ic_btn_camera);
        notificationBtn = (ImageButton)v.findViewById(R.id.ic_btn_notification);
        notificationBtn.setOnClickListener(this);
        camBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_btn_camera:
                viewPager.setCurrentItem(0);
                break;
            case R.id.ic_btn_notification:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
