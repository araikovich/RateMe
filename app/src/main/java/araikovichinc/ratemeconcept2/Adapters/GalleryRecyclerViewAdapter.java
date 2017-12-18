package araikovichinc.ratemeconcept2.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BitmapHelper;
import araikovichinc.ratemeconcept2.Utils.Image;

/**
 * Created by Tigran on 09.12.2017.
 */

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.GalleryViewHolder> {

    ArrayList<String> photos;
    ArrayList<Image> serverPhotos;
    RelativeLayout.LayoutParams params;
    Listener listener;
    Context context;
    int a;


    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public GalleryRecyclerViewAdapter(ArrayList<String> photos, int cardParams, Context context) {
        this.photos = photos;
        params = new RelativeLayout.LayoutParams(cardParams/3, cardParams/3);
        this.context = context;
        a = 1;
    }

    public GalleryRecyclerViewAdapter(ArrayList<Image> photos, int cardParams, Context context, int a) {
        this.serverPhotos = photos;
        params = new RelativeLayout.LayoutParams(cardParams/3, cardParams/3);
        this.context = context;
        this.a = a;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_photo_item, parent, false);
        Log.d("MyLogs", "View Holder Created");
        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.imageView.setLayoutParams(params);
        if(a == 1)
            Glide.with(context).load(photos.get(position)).into(holder.imageView);
        else
            Glide.with(context).load(serverPhotos.get(position).getImageUrl()).into(holder.imageView);
        holder.position = position;
        Log.d("MyLogs", "View Holder Binded");
    }

    @Override
    public int getItemCount() {
        if(a == 1)
            return photos.size();
        else
            return serverPhotos.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        int position;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.gallery_photo_item);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
    }


}
