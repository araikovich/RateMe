package araikovichinc.ratemeconcept2.Fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import araikovichinc.ratemeconcept2.Adapters.GalleryRecyclerViewAdapter;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BitmapHelper;
import araikovichinc.ratemeconcept2.Utils.CustomViewPager;

/**
 * Created by Tigran on 09.12.2017.
 */

public class GalleryFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    ImageView imageView, backBtn, okBtn;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    GalleryRecyclerViewAdapter adapter;
    ArrayList<String> photos;
    String currentPhotoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_photo_from_storage, container, false);
        CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.home_view_pager);
        viewPager.setPagingEnabled(false);
        recyclerView = (RecyclerView)v.findViewById(R.id.gallery_recycler_view);
        imageView = (ImageView)v.findViewById(R.id.preview_photo);
        collapsingToolbarLayout = (CollapsingToolbarLayout)v.findViewById(R.id.gallery_collapsing_tool_bar);
        appBarLayout = (AppBarLayout)v.findViewById(R.id.gallery_app_bar);
        backBtn = (ImageView)v.findViewById(R.id.add_photo_back_icon);
        okBtn = (ImageView)v.findViewById(R.id.add_photo_confirm_photo);
        backBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setNestedScrollingEnabled(true);
        photos = getPhotosPath();
        currentPhotoPath = photos.get(0);
        Glide.with(getContext()).load(photos.get(0)).into(imageView);
        adapter = new GalleryRecyclerViewAdapter(photos, getResources().getDisplayMetrics().widthPixels, getContext());
        adapter.setListener(new GalleryRecyclerViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                imageView.setImageBitmap(BitmapHelper.getBitmapFromPath(photos.get(position)));
                appBarLayout.setExpanded(true);
                currentPhotoPath = photos.get(position);
            }
        });
        recyclerView.setAdapter(adapter);
        return v;
    }

    private ArrayList<String> getPhotosPath(){
        ArrayList<String> photos = new ArrayList<>();
        String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DISPLAY_NAME };
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null,
                null);
        int fileColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        while (cursor.moveToNext()){
            photos.add(cursor.getString(fileColumn));
        }
        cursor.close();
        return photos;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_photo_back_icon:
                getActivity().onBackPressed();
                break;
            case R.id.add_photo_confirm_photo:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("photo_path", currentPhotoPath);
                SavePhotoFragment fragment = new SavePhotoFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.container_big, fragment).addToBackStack(null).commit();

                break;
        }
    }
}
