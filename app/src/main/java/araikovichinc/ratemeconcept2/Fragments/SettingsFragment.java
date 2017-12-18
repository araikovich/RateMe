package araikovichinc.ratemeconcept2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import araikovichinc.ratemeconcept2.Activities.LogInActivity;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.Pages;

/**
 * Created by Tigran on 23.11.2017.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    ImageView backIcon;
    Button logOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        backIcon = (ImageView)v.findViewById(R.id.ic_settings_back);
        logOut = (Button)v.findViewById(R.id.log_out_btn);
        logOut.setOnClickListener(this);
        backIcon.setOnClickListener(this);
        Pages.setCurrentPage(Pages.SETTINGS);
        return v;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_settings_back:
                getActivity().onBackPressed();
                break;
            case R.id.log_out_btn:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", 0);
                editor.commit();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }
    }


}
