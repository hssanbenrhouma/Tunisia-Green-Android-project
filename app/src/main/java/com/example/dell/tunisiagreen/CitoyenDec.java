package com.example.dell.tunisiagreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

//import com.google.api.client.http.HttpResponse;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CitoyenDec extends AppCompatActivity implements AdapterView.OnItemSelectedListener,LocationListener{

    private ProgressDialog pDialog;
    String message="",email;
    String imgid;
    JSONParser jsonParser = new JSONParser();
    // url to create new user
    private static String url_add_dec = "http://androidhssan.net16.net/fichiersphp/adddeclaration.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    Button btnPrendre,btnDeclarer;
    static int test= 0;
    ImageView imageView;
    Bitmap imageInitiale;
    EditText Desc;
    ProgressDialog dialog = null;
    Bitmap imageBitmap;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Spinner spinner =null;
    Location location;

    GPSTracker gpsTracker;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private Uri fileUri;
    private String filePath;
    protected LocationManager locationManager;

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declaration);
        Intent intent = getIntent();
        gpsTracker= new GPSTracker(getApplicationContext());
        if (intent != null) {
            email=intent.getStringExtra("email");}
        btnPrendre=(Button)findViewById(R.id.btnphoto);
        Desc=(EditText)findViewById(R.id.DescriptionDeclaration);
        imageView=(ImageView)findViewById(R.id.imageviewPrendre);
        spinner= (Spinner) findViewById(R.id.spinnerDec);
        imageInitiale =((BitmapDrawable)imageView.getDrawable()).getBitmap();
        btnDeclarer=(Button)findViewById(R.id.btnDeclarer);
        btnDeclarer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                if (image.equals(imageInitiale))
                {
                    Toast.makeText(getApplicationContext(), "The photo please !!", Toast.LENGTH_LONG).show();

                }
                else {

                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    {

                        new AddDeclaration().execute();


                   }



                    else {
                        showGPSDisabledAlertToUser();

                    }

                }
            }
        });
        btnPrendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                // startActivityForResult(intent, 0);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(1);
                System.out.println("pathuri"+fileUri.getPath());
                // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri.toString());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        });
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList<String> regions = new ArrayList<String>();
        regions.add("Sousse");
        regions.add("Ariana");
        regions.add("Tunis");
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
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = rotateImage((Bitmap) extras.get("data"),0);
            saveImage(imageBitmap,fileUri.getPath());

            // filePath = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
            imageView.setImageBitmap(imageBitmap);

        }

    }
    public void saveImage(Bitmap bitmapImage,String filepath) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filepath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                com.example.dell.tunisiagreen.Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create "
                        + com.example.dell.tunisiagreen.Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");


        return mediaFile;
    }

    class AddDeclaration extends AsyncTask<String, String, String> {
int success,serverResponse;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CitoyenDec.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            String reg = spinner.getSelectedItem().toString();
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            String desc = Desc.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("region", reg));
            params.add(new BasicNameValuePair("description", desc));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("long", Double.toString(longitude)));
            params.add(new BasicNameValuePair("lat", Double.toString(latitude)));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_dec,
                    "GET", params);
            // check for success tag
System.out.println("aaaa"+json.toString());
            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            UploadToServer UptoServ = new UploadToServer("http://androidhssan.net16.net/fichiersphp/UploadImage.php");
            serverResponse = UptoServ.uploadFile(fileUri.getPath(),Integer.toString(success) + ".jpg");

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if ((serverResponse == 200)&&(success!=0)) {
                Toast.makeText(CitoyenDec.this, "sent successfully.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CitoyenDec.this, CitoyenDec.class));


            }
            else
            {

                Toast.makeText(CitoyenDec.this, "Connection problem ",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CitoyenDec.this, CitoyenDec.class));

        }
        }

    }
}
