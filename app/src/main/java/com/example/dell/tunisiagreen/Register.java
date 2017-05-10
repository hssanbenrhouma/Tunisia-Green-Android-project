package com.example.dell.tunisiagreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dell on 04/05/2016.
 */
public class Register extends Activity implements AdapterView.OnItemSelectedListener{
    private ProgressDialog pDialog;
    String message="";
    JSONParser jsonParser = new JSONParser();
   // url to create new user
    private static String region,url_create_user = "http://androidhssan.net16.net/fichiersphp/register_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    Button register;
    Spinner spinner;
    EditText Username,Email,Pass,Cpass;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        register=(Button)findViewById(R.id.buttonreg) ;
        Username=(EditText)findViewById(R.id.usename_reg);
        Email=(EditText)findViewById(R.id.RegEmail);
        Pass=(EditText)findViewById(R.id.password_reg);
        Cpass=(EditText)findViewById(R.id.passconfirm_reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(Email.getText().toString());
                System.out.println(Pass.getText().toString());
                System.out.println(Cpass.getText().toString());
                System.out.println(Username.getText().toString());
                if ((Email.getText().toString().contains("@"))&&(Email.getText().toString().contains("."))&&(Pass.getText().toString().equals(Cpass.getText().toString())) && (Username.getText().toString().length() > 0) && (Pass.getText().toString().length() > 0))
                {
                    new RegisterUser().execute();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials  !", Toast.LENGTH_SHORT).show();
                }
            }

        });
   spinner=(Spinner)findViewById(R.id.spinnerRegiser);
    spinner.setOnItemSelectedListener(this);

    // Spinner Drop down elements
    ArrayList<String> regions = new ArrayList<String>();
    regions.add("Ariana");
        regions.add("Tunis");
        regions.add("Sousse");
    regions.add("Sfax");
    regions.add("Kairouan");
    regions.add("Jendouba");
    regions.add("Gafsa");
    regions.add("Gabes");
    regions.add("Bizerte");
    regions.add("Ben Arous");
    regions.add("Béja");
    regions.add("Sidi Bouzid");
    regions.add("Tozeur");
    regions.add("Siliana");
    regions.add("Tataouine");
    regions.add("Zaghouan");
    regions.add("Médenine");
    regions.add("Kebili");
    regions.add("Nabeul");
    regions.add("Mahdia");
    regions.add("Le Kef");
    regions.add("Monastir");
    regions.add("La Manouba");
    regions.add("Kasserine");

    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, regions);

    // Drop down layout style - list view with radio button
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // attaching data adapter to spinner
    spinner.setAdapter(dataAdapter);

}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    class RegisterUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Registration user..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String username = Username.getText().toString();
String email;
            email = Email.getText().toString();
            String pass = Pass.getText().toString();
            region=spinner.getSelectedItem().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("region", region));
            params.add(new BasicNameValuePair("password", pass));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "GET", params);

            // check log cat fro response
       //     Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                message="registration done !!! Login now !!!";

                  //  Intent i = new Intent(getApplicationContext(), LoginDisplayActivity.class);
                    //startActivity(i);
                    startActivity(new Intent(Register.this, LoginDisplayActivity.class));


                    // closing this screen
                } else {
                    message= "error";
                }
                finish();
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
            pDialog.dismiss();
        }

    }
}
