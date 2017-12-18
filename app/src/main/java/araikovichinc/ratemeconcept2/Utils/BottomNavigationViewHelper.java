package araikovichinc.ratemeconcept2.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import araikovichinc.ratemeconcept2.Activities.HomeActivity;
import araikovichinc.ratemeconcept2.Fragments.GalleryFragment;
import araikovichinc.ratemeconcept2.Fragments.GridViewProfileFragment;
import araikovichinc.ratemeconcept2.Fragments.ProfileFragment;
import araikovichinc.ratemeconcept2.Fragments.RateFragment;
import araikovichinc.ratemeconcept2.Fragments.RecyclerVoatingFragment;
import araikovichinc.ratemeconcept2.R;

/**
 * Created by Tigran on 22.11.2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottonNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final BottomNavigationViewEx bottomNavigationViewEx, final FragmentManager fm, final Fragment fragment){
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                FragmentManager fragmentManager = fm;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(Pages.getCurrentPage() == Pages.SETTINGS) {
                    Fragment fragment1 = fragmentManager.findFragmentByTag("settings");
                    fragmentTransaction.remove(fragment1);
                }
                switch (item.getItemId()){
                    case R.id.ic_rate:
                        RateFragment rateFragment = new RateFragment();
                        fragmentTransaction.replace(R.id.container_small, rateFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.ic_photo:
                        fragmentTransaction.replace(R.id.container_big, new GalleryFragment());
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    //case R.id.ic_search:
                        //break;
                    //case R.id.ic_notifications:
                        //break;
                    case R.id.ic_profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentTransaction.replace(R.id.container_small, profileFragment);
                        fragmentTransaction.commit();
                        break;
                }
                return false;
            }
        });

    }
}
