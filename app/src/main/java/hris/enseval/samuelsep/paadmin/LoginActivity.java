package hris.enseval.samuelsep.paadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hris.enseval.samuelsep.paadmin.Api.ApiClient;
import hris.enseval.samuelsep.paadmin.Api.ApiInterface;
import hris.enseval.samuelsep.paadmin.Helper.RealmHelper.UserRealmHelper;
import hris.enseval.samuelsep.paadmin.Model.TokenAuthModel.UserLogin;
import hris.enseval.samuelsep.paadmin.Model.TokenAuthModel.UserLoginResponse;
import hris.enseval.samuelsep.paadmin.Model.User.User;
import hris.enseval.samuelsep.paadmin.Model.User.userBodyParameter;
import hris.enseval.samuelsep.paadmin.NetworkConnection.ConnectivityReceiver;
import hris.enseval.samuelsep.paadmin.Session.SessionManagement;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SessionManagement session;
    EditText edtUsername,edtPassword;
    Button btnLogin;
    String token="TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);


        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());

        // Session Manager
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //=====================================================================================================================

                if(ConnectivityReceiver.isConnected()){

                    //generate new token=====================
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<UserLoginResponse> call = apiService.getToken(new UserLogin("mario","secret"));
                    call.enqueue(new Callback<UserLoginResponse>() {
                        @Override
                        public void onResponse(Call<UserLoginResponse>call, Response<UserLoginResponse> response) {
                            if(response.code() == 400){
                                Toast.makeText(getApplicationContext(), "Error 400: Bad Request", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.code() == 401){
                                Toast.makeText(getApplicationContext(), "Error 401: Not Authorized", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.code() == 200){
                                token = response.body().getToken();
                                //=========================================================
                                btnLogin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if(edtUsername.getText().length()<1 || edtPassword.getText().length()<1) {
                                            Toast.makeText(getApplicationContext(), "NIK or Password Still Empty!!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
//                                            linlaHeaderProgress.setVisibility(View.VISIBLE);
//                                            imgLogo.setVisibility(View.GONE);
                                            ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);

                                            String userName = edtUsername.getText().toString();
                                            String password = edtPassword.getText().toString();
                                            Call<User> call = apiService2.getUserDetail(new userBodyParameter(userName), "Bearer " + token);
                                            call.enqueue(new Callback<User>() {
                                                @Override
                                                public void onResponse(Call<User> call, Response<User> response) {
                                                    User user = response.body();
                                                    try{
                                                        String usernamee = user.getUsername();
                                                        if (userName.equals(usernamee) && password.equals(user.getPassword().replaceAll(" ", ""))) {
//                                                            String userQR = generateQR(user.getEmpNIK());
//
//                                                    int width =300;
//                                                    int height = 300;
//                                                    int smallestDimension = width < height ? width : height;

//                                                    Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//                                                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//                                                    String userQR = CreateQRCode(user.getEmpNIK(), "UTF-8", hintMap, smallestDimension, smallestDimension);

                                                            userRealmHelper.addArticle(user, token);
                                                            session.createLoginSession(userName, password.replaceAll(" ", ""));
                                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                            startActivity(i);
                                                            finish();
//                                                            linlaHeaderProgress.setVisibility(View.GONE);
//                                                            imgLogo.setVisibility(View.VISIBLE);

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Password Not Correct!", Toast.LENGTH_SHORT).show();
//                                                            linlaHeaderProgress.setVisibility(View.GONE);
//                                                            imgLogo.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                    catch(Exception e){
                                                        Toast.makeText(getApplicationContext(), "Username Not Registered!", Toast.LENGTH_SHORT).show();
//                                                        linlaHeaderProgress.setVisibility(View.GONE);
//                                                        imgLogo.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<User> call, Throwable t) {
                                                    // Log error here since request failed
//                                                    Log.e(TAG, t.toString());
                                                    Toast.makeText(getApplicationContext(), "Error: Something went wrong happened :(", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });
                            }
                            //=========================================================
                        }

                        @Override
                        public void onFailure(Call<UserLoginResponse>call, Throwable t) {
                            // Log error here since request failed
//                            Log.e(TAG, t.toString());
                            Toast.makeText(getApplicationContext(),"Error: Something went wrong happened :(",Toast.LENGTH_SHORT).show();
                        }
                    });
                    //=================================================

                }
                else{
                    Toast.makeText(getApplicationContext(),Boolean.toString(ConnectivityReceiver.isConnected()),Toast.LENGTH_SHORT).show();
//                    showToast(ConnectivityReceiver.isConnected());
                }

                //======================================================================================================================



                String user = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(user.equals("samuel") && password.equals("123")){
                    session.createLoginSession(user, password.replaceAll(" ", ""));
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Username or password wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
