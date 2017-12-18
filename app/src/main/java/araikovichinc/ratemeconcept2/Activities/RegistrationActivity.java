package araikovichinc.ratemeconcept2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import araikovichinc.ratemeconcept2.APIs.ServerApi;
import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.Pages;
import araikovichinc.ratemeconcept2.Utils.ProgressDialogFragment;
import araikovichinc.ratemeconcept2.Utils.ServerResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView login;
    EditText nameText, emailText, passwordText;
    Button registrationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initReg();
        login.setOnClickListener(this);
        registrationBtn.setOnClickListener(this);
        Pages.setCurrentPage(Pages.SingUp);
    }

    @Override
    public void onClick(View v) {
        RegistrationTask registrationTask;
        switch (v.getId()){
            case R.id.login_text_view:
                Intent intent = new Intent(RegistrationActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.btn_create_acc:
                registrationTask = new RegistrationTask();
                registrationTask.execute();
                break;
        }
    }

    private void initReg(){
        nameText = (EditText)findViewById(R.id.name_text);
        emailText = (EditText)findViewById(R.id.email_text_reg);
        passwordText = (EditText)findViewById(R.id.password_text);
        registrationBtn = (Button) findViewById(R.id.btn_create_acc);
        login = (TextView) findViewById(R.id.login_text_view);
    }

    class RegistrationTask extends AsyncTask<Void, Void, Void>{

        String name, email, password;
        ProgressDialogFragment progressDialogFragment;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name = nameText.getText().toString();
            email = emailText.getText().toString();
            password = passwordText.getText().toString();
            progressDialogFragment = new ProgressDialogFragment();
            progressDialogFragment.show(getSupportFragmentManager(),"tag");

        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.5")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerApi serverApi = retrofit.create(ServerApi.class);
            Call<ServerResponse> call = serverApi.addUser(name, email, password);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", response.body().getResult());
                    editor.commit();
                    Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(RegistrationActivity.this, "Fail!!!!!", Toast.LENGTH_SHORT).show();
                    Log.d("myLog", t.getMessage());
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
