package araikovichinc.ratemeconcept2.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Tigran on 26.11.2017.
 */

public class GridViewProfileAdapter extends BaseAdapter {

    List<Integer> photos;
    Context mContext;
    int params;

    public GridViewProfileAdapter(Context context, List<Integer> photos , int params){
        mContext = context;
        this.photos = photos;
        this.params = params;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return photos.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(params/3, params/3));
        }
        else {
            imageView = (ImageView)convertView;
        }
        Glide.with(mContext).load(photos.get(position)).into(imageView);
        return imageView;
    }
}
