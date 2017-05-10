package com.example.dell.tunisiagreen;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dell on 25/04/2016.
 */
public class LoginDisplayActivity extends Activity {


    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    // url to get all products list
    private static String url_login_user = "http://androidhssan.net16.net/fichiersphp/Login.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_Role = "role";
    EditText Username,Pass;
    String userReg="",passReg="";


    private Button btnLogin;

    final String EXTRA_EMAIL = "user_login";
    final String EXTRA_PASSWORD = "user_password";
    private Button btnRegister;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        Intent intent = getIntent();
        if (intent != null) {
            userReg = intent.getStringExtra("userReg");
            passReg = intent.getStringExtra("passReg");


        }
        Username.setText(userReg);
        Pass.setText(passReg);
btnLogin=(Button)findViewById(R.id.buttonlogin);
        btnRegister=(Button)findViewById(R.id.buttonregister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDisplayActivity.this, Register.class);
                startActivity(intent);
            }
        });



         Username = (EditText) findViewById(R.id.txt_username);
        Pass = (EditText) findViewById(R.id.txt_password);

        btnLogin = (Button) findViewById(R.id.buttonlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 NetworkInfo network = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

                if (network == null || !network.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_LONG).show();

                    // Le périphérique n'est pas connecté à Internet
                }

                    if ((Username.getText().toString()).equals("") || (Pass.getText().toString()).equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "invalid credentials",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        userReg=Username.getText().toString();
                        passReg=Pass.getText().toString();
                        new LoginUser().execute();


                    }
                    }
                    // Le périphérique est connecté à Internet



            });
    }



    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoginUser extends AsyncTask<String, String, String> {
String role="";
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginDisplayActivity.this);
            pDialog.setMessage("Authentification. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            int   success=0;


                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("email",userReg));
                    params.add(new BasicNameValuePair("password",passReg));
                    // getting JSON string from URL

                    JSONObject json = jParser.makeHttpRequest(url_login_user, "GET", params);
                    // Checking for SUCCESS TAG
                    try {
                            success = json.getInt(TAG_SUCCESS);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    if (success == 1) {
                        try {
                            role = json.getString(TAG_Role);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    } else {

                    }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if (role.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "invalid credentials",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (role.equals("RESPONSABLE")) {

                    Intent intent = new Intent(LoginDisplayActivity.this, SetRespponsable.class);
                    intent.putExtra("email", Username.getText().toString());
                    startActivity(intent);


                }
                if (role.equals("CITOYEN")) {


                    // closing this screen
                    Intent intent = new Intent(getApplicationContext(), SettingsCit.class);
                    intent.putExtra(EXTRA_EMAIL, Username.getText().toString());
                    intent.putExtra(EXTRA_PASSWORD, Pass.getText().toString());

                    startActivity(intent);




                }

            }

        }

    }

}