package araikovichinc.ratemeconcept2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import araikovichinc.ratemeconcept2.Adapters.SectionPageAdapter;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BottomNavigationViewHelper;
import araikovichinc.ratemeconcept2.Utils.Pages;

/**
 * Created by Tigran on 22.11.2017.
 */

public class HomeFragment extends Fragment {

    public final String TAG = "HomeFragment";
    BottomNavigationViewEx bottomNavigationViewEx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        bottomNavigationViewEx = (BottomNavigationViewEx)v.findViewById(R.id.bottom_nav_view_bar);
        setUpBottomNavigationView(bottomNavigationViewEx);
        if(Pages.getCurrentPage() == 2)
            bottomNavigationViewEx.setCurrentItem(2);
        else if (Pages.getCurrentPage() == 0)
            bottomNavigationViewEx.setCurrentItem(0);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MyLogs", ""+ Pages.getCurrentPage());
        if(Pages.getCurrentPage() == 2)
            bottomNavigationViewEx.setCurrentItem(2);
        else if (Pages.getCurrentPage() == 0)
            bottomNavigationViewEx.setCurrentItem(0);
    }

    public void setUpBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "SetUPBottomNavigationView: Starting...");
        BottomNavigationViewHelper.setupBottonNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(bottomNavigationViewEx, getFragmentManager(), this) ;
        if(Pages.getCurrentPage() == -1)
        bottomNavigationViewEx.setCurrentItem(0);
    }


}
