package araikovichinc.ratemeconcept2.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import araikovichinc.ratemeconcept2.R;

/**
 * Created by Tigran on 27.11.2017.
 */

public class RecyclerProfileAdapter extends RecyclerView.Adapter<RecyclerProfileAdapter.MyViewHolder> {

    List<HashMap<String, Integer>> datas;
    Context context;

    public RecyclerProfileAdapter(List<HashMap<String, Integer>> datas , Context context){
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_profile_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HashMap<String, Integer> map = datas.get(position);
        holder.likesCount.setText(map.get("likes").toString());
        holder.dislikesCount.setText(map.get("dislikes").toString());
        holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(map.get("photo")).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        ImageView photo;
        TextView likesCount, dislikesCount;
        Button setupVoating;

        public MyViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.card_image_photo);
            likesCount = (TextView)itemView.findViewById(R.id.card_like_cont);
            dislikesCount = (TextView)itemView.findViewById(R.id.card_dislike_cont);
            setupVoating = (Button)itemView.findViewById(R.id.card_setup_voating);
            card = (CardView)itemView.findViewById(R.id.my_card);
        }
    }
}
