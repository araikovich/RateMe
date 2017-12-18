package araikovichinc.ratemeconcept2.Fragments;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.IOException;
import java.util.ArrayList;

import araikovichinc.ratemeconcept2.APIs.ServerApi;
import araikovichinc.ratemeconcept2.Adapters.GalleryRecyclerViewAdapter;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BottomNavigationViewHelper;
import araikovichinc.ratemeconcept2.Utils.CustomViewPager;
import araikovichinc.ratemeconcept2.Utils.Images;
import araikovichinc.ratemeconcept2.Utils.Pages;
import araikovichinc.ratemeconcept2.Utils.StatResponse;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tigran on 23.11.2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    CustomViewPager viewPager;
    ImageView imageView;
    RecyclerView recyclerView;
    ArrayList<araikovichinc.ratemeconcept2.Utils.Image> photos;
    GalleryRecyclerViewAdapter adapter;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Pages.setCurrentPage(2);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.e("MyLogs", ""+ Pages.getCurrentPage());
        viewPager = (CustomViewPager) getActivity().findViewById(R.id.home_view_pager);
        progressBar = (ProgressBar)v.findViewById(R.id.prof_progress);
        viewPager.setPagingEnabled(false);
        imageView = (ImageView) v.findViewById(R.id.ic_settings_profile);
        imageView.setOnClickListener(this);
        photos = new ArrayList<>();
        recyclerView = (RecyclerView)v.findViewById(R.id.profile_recycler);
        GetPhotosTask getPhotosTask = new GetPhotosTask();
        getPhotosTask.execute();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_settings_profile:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_small, new SettingsFragment(), "settings");
                transaction.commit();
                break;
        }
    }

    class GetPhotosTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
            adapter = new GalleryRecyclerViewAdapter(photos, getResources().getDisplayMetrics().widthPixels, getContext(), 2);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.5")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerApi serverApi = retrofit.create(ServerApi.class);
            Call<Images> call = serverApi.getPhotos(1);
            call.enqueue(new Callback<Images>() {
                @Override
                public void onResponse(Call<Images> call, Response<Images> response) {
                    photos = response.body().getImages();
                    Log.e("MyLogs", photos.get(0).getImageUrl());
                    try {
                        adapter = new GalleryRecyclerViewAdapter(photos, getResources().getDisplayMetrics().widthPixels, getContext(), 2);
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<Images> call, Throwable t) {
                    Toast.makeText(getContext(), "Connection error!!!!!", Toast.LENGTH_LONG).show();
                }
            });
            Call<StatResponse> call1 = serverApi.getStat(1);
            call1.enqueue(new Callback<StatResponse>() {
                @Override
                public void onResponse(Call<StatResponse> call, Response<StatResponse> response) {

                }

                @Override
                public void onFailure(Call<StatResponse> call, Throwable t) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }

}
