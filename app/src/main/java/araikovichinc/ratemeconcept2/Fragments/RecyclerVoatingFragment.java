package araikovichinc.ratemeconcept2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import araikovichinc.ratemeconcept2.Adapters.RecyclerProfileAdapter;
import araikovichinc.ratemeconcept2.R;

/**
 * Created by Tigran on 27.11.2017.
 */

public class RecyclerVoatingFragment extends Fragment {

    RecyclerProfileAdapter adapter;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_profile_on_voting, container, false);
        rv = (RecyclerView)v.findViewById(R.id.recycler_view);
        HashMap<String, Integer> map = new HashMap<>();
        List<HashMap<String, Integer>> list = new ArrayList<>();
        map.put("likes", 25);
        map.put("dislikes", 44);
        map.put("photo", R.drawable.photo1);
        list.add(map);
        adapter = new RecyclerProfileAdapter(list, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        return v;
    }
}
