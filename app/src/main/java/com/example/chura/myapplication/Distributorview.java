package com.example.chura.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Distributorview extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributorview);
        listView = (ListView) findViewById(R.id.listview1);
       // Toast.makeText(this, "distributor view", Toast.LENGTH_SHORT).show();
            fetch_distibutors();
        }


    public void fetch_distibutors()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Distributorview.this);
                pDialog.setMessage("Fetching Distributors...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("name", "");
                String s = rh.sendGetRequest("http://192.168.43.196/mretail/fetchdistributors.php");
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
               new AlertDialog.Builder(Distributorview.this).setTitle(s).show();
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
                    String distributorname,telephone,product;

    distributorname=jo.getString("disributorname");
    telephone=jo.getString("telephone");
    //product=jo.getString("product");

                    HashMap<String, String> products = new HashMap<>();
                  products.put("disributorname",distributorname);
                    products.put("telephone", telephone);
                    //products.put("product", "skjsdfsd");
                    list.add(products);
                }
                else
                {
                }

            }
        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Distributorview.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(Distributorview.this, list, R.layout.activity_distributorlist,
                new String[]{"disributorname", "telephone"}, new int[]{R.id.textView4,
                R.id.textView3});
        listView.setAdapter(adapter);
    }
}
