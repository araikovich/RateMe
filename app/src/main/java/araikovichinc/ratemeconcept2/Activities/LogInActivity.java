package araikovichinc.ratemeconcept2.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    TextView create_acc;
    EditText emailText, passwordText;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initLogInActivity();
        create_acc.setOnClickListener(this);
        logInButton.setOnClickListener(this);
        Pages.setCurrentPage(Pages.LogIn);
    }

    @Override
    public void onClick(View v) {
        LogInTask logInTask;
        switch (v.getId()){
            case R.id.create_acc:
                Intent intent = new Intent(LogInActivity.this, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.btn_login:
                logInTask = new LogInTask();
                logInTask.execute();
                break;
        }
    }

    private void initLogInActivity(){
        create_acc = (TextView)findViewById(R.id.create_acc);
        emailText = (EditText)findViewById(R.id.email_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        logInButton = (Button)findViewById(R.id.btn_login);
    }

    class LogInTask extends AsyncTask<Void, Void, Void>{
        ProgressDialogFragment progressDialogFragment;
        String email, password;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            email = emailText.getText().toString();
            password = passwordText.getText().toString();
            logInButton.setEnabled(false);
            progressDialogFragment = new ProgressDialogFragment();
            progressDialogFragment.show(getSupportFragmentManager(), "tag");

        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.5")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerApi serverApi = retrofit.create(ServerApi.class);
            Call<ServerResponse> call = serverApi.logIn(email, password);
            call.enqueue(new Callback<ServerResponse>() {

                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    int answer = response.body().getResult();
                    switch (answer){
                        case -1:
                            Toast.makeText(LogInActivity.this, "No user with such email", Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(LogInActivity.this, "Wrong password!!1 Try again!!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    if(answer>0) {
                        SharedPreferences userId = getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = userId.edit();
                        editor.putInt("userId", answer);
                        editor.commit();
                        SharedPreferences check = getSharedPreferences("MyPref", MODE_PRIVATE);
                        Log.e("MyLogs", "" + check.getInt("userId", 0));
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.d("MyLogs", t.getMessage());
                    t.printStackTrace();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialogFragment.dismiss();
            logInButton.setEnabled(true);
        }
    }

}
