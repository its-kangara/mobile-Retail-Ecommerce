package com.example.chura.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Retailerlistview extends AppCompatActivity {
 ListView retailerlistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailerlistview);
        retailerlistview=(ListView) findViewById(R.id.retailerlistview);
        fetch_retailers();
    }

    public void fetch_retailers()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Retailerlistview.this);
                pDialog.setMessage("Fetching Retailers...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("name", "");
                String s = rh.sendGetRequest("http://192.168.43.196/mretail/fetchretailers.php");
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                new AlertDialog.Builder(Retailerlistview.this).setTitle(s).show();
                showthem(s);
            }


        }
        GetJSON jj = new GetJSON();
        jj.execute();
    }


    private void showthem(String s) {

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String succes="0";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);


                succes=jo.getString("success");
                if (succes.equals("1"))
                {
                    String retailername,telephone;

                    retailername=jo.getString("retailername");
                    telephone=jo.getString("telephone");

                    HashMap<String, String> products = new HashMap<>();
                    products.put("retailername",retailername);
                    products.put("telephone", telephone);

                    list.add(products);
                }
                else
                {
                }

            }
        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Retailerlistview.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(Retailerlistview.this, list, R.layout.activity_retailersview,
                new String[]{"retailername", "telephone"}, new int[]{R.id.textView8,
                R.id.textView2});
        retailerlistview.setAdapter(adapter);
    }
}
