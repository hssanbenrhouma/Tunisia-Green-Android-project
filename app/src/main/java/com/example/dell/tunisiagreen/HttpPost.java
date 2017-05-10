package com.example.dell.tunisiagreen;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpPost extends AsyncTask<String, Void, String> {

    private Context context;

    public HttpPost(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        String idcit = arg0[0];
        String idresp = arg0[1];
        String date = arg0[2];
        String region = arg0[3];
        String description = arg0[4];
        String url = arg0[5];
        String longi = arg0[6];
        String lat = arg0[7];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?idcitoyen=" + URLEncoder.encode(idcit, "UTF-8");
            data += "&idresponsable=" + URLEncoder.encode(idresp, "UTF-8");
            data += "&date=" + URLEncoder.encode(date, "UTF-8");
            data += "&region=" + URLEncoder.encode(region, "UTF-8");
            data += "&description=" + URLEncoder.encode(description, "UTF-8");
            data += "&url=" + URLEncoder.encode(url, "UTF-8");
            data += "&long=" + URLEncoder.encode(longi, "UTF-8");
            data += "&lat=" + URLEncoder.encode(lat, "UTF-8");
System.out.println(data);
            link = "http://localhost/declarer.php" + data;
            URL url1 = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url1.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
       /* String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Signup successfull.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).sh ow();
        }*/
        System.out.println("prexecute");
    }
}