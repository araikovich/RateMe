package araikovichinc.ratemeconcept2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Tigran on 22.11.2017.
 */

public class SectionPageAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> mFragmentsList = new ArrayList<>();

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentsList.add(fragment);
    }

    public void removeFragment(int position){
        mFragmentsList.remove(position);
    }
}
