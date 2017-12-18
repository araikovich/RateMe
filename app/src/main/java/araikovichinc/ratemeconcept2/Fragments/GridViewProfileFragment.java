package araikovichinc.ratemeconcept2.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import araikovichinc.ratemeconcept2.Adapters.GridViewProfileAdapter;
import araikovichinc.ratemeconcept2.R;

/**
 * Created by Tigran on 26.11.2017.
 */

public class GridViewProfileFragment extends Fragment {

    GridView gridView;
    List<Integer> photos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_photos_profile_grid, container, false);
        gridView = (GridView)v.findViewById(R.id.grid_view);
        MyTask myTask = new MyTask();
        myTask.execute();
        return v;
    }

    class MyTask extends AsyncTask<Void, Void, Void>{
        GridViewProfileAdapter adapter;
        int imageWidth;
        @Override
        protected Void doInBackground(Void... params) {
            photos = new ArrayList<>();
            photos.add(R.drawable.test_image_profile);
            photos.add(R.drawable.photo1);
            photos.add(R.drawable.photo2);
            photos.add(R.drawable.photo3);
            int gridWidth = getResources().getDisplayMetrics().widthPixels;
            adapter = new GridViewProfileAdapter(getActivity(), photos, gridWidth);
            imageWidth = gridWidth/3;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            gridView.setColumnWidth(imageWidth);
            gridView.setAdapter(adapter);
        }
    }
}
