package araikovichinc.ratemeconcept2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import araikovichinc.ratemeconcept2.Adapters.SectionPageAdapter;
import araikovichinc.ratemeconcept2.Fragments.HomeFragment;
import araikovichinc.ratemeconcept2.Fragments.NotificationFragment;
import araikovichinc.ratemeconcept2.Fragments.PhotoFragment;
import araikovichinc.ratemeconcept2.Fragments.ProfileFragment;
import araikovichinc.ratemeconcept2.Fragments.RateFragment;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BottomNavigationViewHelper;
import araikovichinc.ratemeconcept2.Utils.CustomViewPager;
import araikovichinc.ratemeconcept2.Utils.Pages;

/**
 * Created by Tigran on 22.11.2017.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = "HomeActivity";
    CustomViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        if(sharedPreferences.getInt("userId", 0) == 0){
            Log.e("MyLogs", "" + sharedPreferences.getInt("userId", 0));
            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_home);
        Log.d(TAG, "OnCreate: Starting...");
        Pages.setCurrentPage(Pages.RATE);
        if(savedInstanceState == null)
        setUpViewPager();
    }

    @Override
    public void onBackPressed() {
        if(Pages.getCurrentPage() == Pages.SETTINGS){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment1 = manager.findFragmentByTag("settings");
                transaction.remove(fragment1);
            transaction.replace(R.id.container_small, new ProfileFragment());
            transaction.commit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPostResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        if(sharedPreferences.getInt("userId", 0) == 0){
            Log.e("MyLogs", "" + sharedPreferences.getInt("userId", 0));
            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onPostResume();
    }

    public void setUpViewPager(){
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        sectionPageAdapter.addFragment(new PhotoFragment());
        sectionPageAdapter.addFragment(new HomeFragment());
        sectionPageAdapter.addFragment(new NotificationFragment());
        viewPager = (CustomViewPager) findViewById(R.id.home_view_pager);
        viewPager.setAdapter(sectionPageAdapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View v) {

    }
}
