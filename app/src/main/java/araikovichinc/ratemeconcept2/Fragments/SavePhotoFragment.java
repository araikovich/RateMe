package araikovichinc.ratemeconcept2.Fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import araikovichinc.ratemeconcept2.APIs.ServerApi;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.BitmapHelper;
import araikovichinc.ratemeconcept2.Utils.ProgressDialogFragment;
import araikovichinc.ratemeconcept2.Utils.ServerResponse;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tigran on 12.12.2017.
 */

public class SavePhotoFragment extends Fragment implements View.OnClickListener {

    ImageView imageView;
    CircleImageView photo;
    Button button;
    EditText editText;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_save_photo, container, false);
        imageView = (ImageView)v.findViewById(R.id.back_to_gallery_btn);
        button = (Button)v.findViewById(R.id.done_img_btn);
        editText = (EditText)v.findViewById(R.id.description_edit_text);
        photo = (CircleImageView) v.findViewById(R.id.save_photo_preview);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);
        bundle = getArguments();
        Glide.with(getContext()).load(bundle.getString("photo_path")).into(photo);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_to_gallery_btn:
                getActivity().onBackPressed();
                break;
            case R.id.done_img_btn:
                SaveTask task = new SaveTask();
                task.execute();
                break;
        }
    }

    class SaveTask extends AsyncTask<Void, Void, Void>{

        String filePath;
        String question;
        int userId;
        ProgressDialogFragment progressDialogFragment;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogFragment = new ProgressDialogFragment();
            progressDialogFragment.show(getFragmentManager(), "tag");
            filePath = bundle.getString("photo_path");
            Log.e("MyLogs", filePath);
            question = editText.getText().toString();
            userId = 1;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.5")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerApi serverApi = retrofit.create(ServerApi.class);
            Bitmap bitmap = BitmapHelper.getBitmapFromPath(filePath);
            String image = BitmapHelper.bitmapToBase64(bitmap);
            Log.e("MyLogs", image);
            Call<ServerResponse> call = serverApi.save(image, userId, question);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    int anser = response.body().getResult();
                    Log.e("MyLogs", "" + anser);
                    if(anser>0){
                        Toast.makeText(getContext(), "Picture Saved", Toast.LENGTH_SHORT).show();
                        Log.e("MeLogs", "Its Ok");
                    }
                    else if(anser == 7)
                        Toast.makeText(getContext(), "Srvre works", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getContext(), "loooser", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    t.getMessage();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialogFragment.dismiss();
        }
    }
}
