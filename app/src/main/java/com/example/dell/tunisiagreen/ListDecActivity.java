package com.example.dell.tunisiagreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dell on 28/04/2016.
 */
public class ListDecActivity extends AppCompatActivity {
    // Progress Dialog
    String email;
    JSONArray decs = null;
    ArrayList<Declaration> declarations;
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    int success;
    JSONParser jParser = new JSONParser();
    // url to get all products list
    private static String url_get_declarations_region = "http://androidhssan.net16.net/fichiersphp/getdecregion.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_decs = "declarations";
    private static final String TAG_DID = "id";
    private static final String TAG_EMAIL= "emailcit";
    private static final String TAG_DESC = "description";
    private static final String TAG_DATE = "date";
    private static final String TAG_LONG = "long";
    private static final String TAG_LAT = "lat";

     ArrayList<ListItem> Items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_dec);


        Items = new ArrayList<ListItem>();
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            new ListOfDeclarations().execute();

        }
        MyCostumAdapter Adapter = new MyCostumAdapter(Items);
        ListView ls = (ListView)findViewById(R.id.listView);
        ls.setAdapter(Adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                TextView nom;
                nom = (TextView) view1.findViewById(R.id.ListrowNom);
                TextView Date;
                Date = (TextView) view1.findViewById(R.id.ListrowDate);
                ImageView Desc;
                Desc = (ImageView) view1.findViewById(R.id.image);
              //  Toast.makeText(getApplicationContext(), Integer.toString(Items.get(position).getId()), Toast.LENGTH_LONG).show();

           //     Declaration declaration=declarationDB.SelectDecParID(Items.get(position).getId());

                Intent intent = new Intent(ListDecActivity.this, DeclarationConsultActivity.class);
              intent.putExtra("Description", Items.get(position).getDescription());
                intent.putExtra("id", declarations.get(position).getId());
                intent.putExtra("long", declarations.get(position).getLongitute());
             intent.putExtra("latit", declarations.get(position).getLatitude());
                startActivity(intent);
            }
        });
     }

public class MyCostumAdapter extends BaseAdapter {
    ArrayList<ListItem> Items=new ArrayList<ListItem>();

    public MyCostumAdapter(ArrayList<ListItem> Items){
        this.Items=Items;

    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int location) {
        return Items.get(location).nom;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = getLayoutInflater();
        View view1=inflater.inflate(R.layout.list_row,null);
        TextView nom=(TextView)view1.findViewById(R.id.ListrowNom);
        TextView Date=(TextView)view1.findViewById(R.id.ListrowDate);

        nom.setText(Items.get(position).getNom());
       // Desc.setText(Items.get(position).getDescription());
        Date.setText(Items.get(position).getDate());
        ImageView img=(ImageView)findViewById(R.id.imglist);
        try {


            Picasso.with(getApplicationContext()).load("http://androidhssan.net16.net/fichiersphp/images/" + Integer.toString(Items.get(position).getId()) + ".jpg").into(img);
        }
        catch (IllegalArgumentException i)
        {
        Toast.makeText(getApplicationContext(),"data loading problem !",Toast.LENGTH_SHORT).show();
        }

        return view1;

    }

}

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class ListOfDeclarations extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListDecActivity.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",email));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_get_declarations_region, "GET", params);
            // Checking for SUCCESS TAG


            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (success == 1) {
                try {
                    decs = json.getJSONArray(TAG_decs);
                  declarations=new ArrayList<Declaration>();

                    // looping through All Products
                    for (int i = 0; i < decs.length(); i++) {
                        JSONObject c = null;

                        c = decs.getJSONObject(i);

                        // Storing each json item in variable

                        int id = Integer.parseInt(c.getString(TAG_DID));
                        String emailcit = c.getString(TAG_EMAIL);
                        String desc = c.getString(TAG_DESC);
                        String date = c.getString(TAG_DATE);
                        Double longi = Double.parseDouble(c.getString(TAG_LONG));
                        Double lat = Double.parseDouble(c.getString(TAG_LAT));

                        Declaration declaration = new Declaration(id, emailcit, desc, date, longi, lat);

                        declarations.add(declaration);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//finish();
            }else
            {
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            for (int i=0;i<declarations.size();i++)
            {

                Items.add(new ListItem(i,declarations .get(i).getEmail(),declarations.get(i).getDescription(),declarations.get(i).getDate()));

            }



        }

    }

}
