package com.example.dell.tunisiagreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dell on 11/05/2016.
 */
public class SetRespponsable extends Activity{
   String email,NewPass;
    private ProgressDialog pDialog1,pDialog2;

    JSONParser jsonParser1 = new JSONParser();
    JSONParser jsonParser2 = new JSONParser();

    // url to create new user
    private static String url_add_SuppRes = "http://androidhssan.net16.net/fichiersphp/delete_user.php";
    private static String url_add_MdifPassRes = "http://androidhssan.net16.net/fichiersphp/update_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS1 = "success",TAG_SUCCESS2="success";

    int success1,success2;
    Button btnConsultDeclar,btnChangePassRes,btnSuppCmpRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsandlistdec);
        Intent intent = getIntent();
         if (intent != null) {
           email = intent.getStringExtra("email");

        }

        btnConsultDeclar=(Button)findViewById(R.id.btnwelcometodeclarations) ;
        btnChangePassRes=(Button)findViewById(R.id.btnModifPassResponsable);
        btnSuppCmpRes=(Button)findViewById(R.id.btnSuppCompteResp);
        btnConsultDeclar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetRespponsable.this, ListDecActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });




    }

    public void Buclick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText Pass = new EditText(getApplicationContext());
        Pass.setTextColor(Color.BLACK);
        alert.setMessage("Enter your password");
        alert.setTitle("Password");

        alert.setView(Pass);

        alert.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                 NewPass = Pass.getText().toString();
                new ModifPassResp().execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();


    }

    public void BuclickSuppCompte(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to delete your account");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        new SupprimResp().execute();
                    }
                }
        );

        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    class ModifPassResp extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(SetRespponsable.this);
            pDialog1.setMessage("Loading Modification..");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(true);
            pDialog1.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", NewPass));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser1.makeHttpRequest(url_add_MdifPassRes,
                    "GET", params);
            // check for success tag

            try {
                success1 = json.getInt(TAG_SUCCESS1);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog1.dismiss();
            if(success1!=0)
            {
                Toast.makeText(SetRespponsable.this, "Modification done .",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(SetRespponsable.this, "Connection failed !",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
    class SupprimResp extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(SetRespponsable.this);
            pDialog2.setMessage("Delete..");
            pDialog2.setIndeterminate(false);
            pDialog2.setCancelable(true);
            pDialog2.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));



            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser2.makeHttpRequest(url_add_SuppRes,
                    "GET", params);
            // check for success tag

            try {
                success2 = json.getInt(TAG_SUCCESS2);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog2.dismiss();
            if(success2!=0)
            {
                Toast.makeText(SetRespponsable.this, "Delete Done.",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(SetRespponsable.this, "connection failed !.",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}
