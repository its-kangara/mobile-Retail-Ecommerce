package com.example.chura.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class search extends AppCompatActivity {
    ListView listview12;
    EditText search;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        button2= (Button) findViewById(R.id.button2);
        search=(EditText)findViewById(R.id.editText3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch_caregivers(search.getText().toString());

    }
        });



        listview12=(ListView)findViewById(R.id.listview12);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                HashMap<String, String> employees = new HashMap<>(i);
//                String username= employees.get("username");
//                startActivity(new Intent(search.this, book.class));
//
//
//            }
//        });
    }



    public void fetch_caregivers(final String search_string)
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(search.this);
                pDialog.setMessage("searchin.");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String, String> paramms = new HashMap<>();
                paramms.put("search_string", search_string);
                String s = rh.sendGetRequest( "http://192.168.43.196/mretail/searchretailer.php?search_string="+search_string);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
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

//{"result":[{"firstname":"david","phone":"716344331"}]}


                   String  username=jo.getString("firstname");
                   String  phone=jo.getString("phone");
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("firstname", username);
                    employees.put("phone", phone);
                    list.add(employees);






            }





        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(search.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(e))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(search.this, list, R.layout.retailer,
                new String[]{"firstname", "phone"}, new int[]{R.id.textView8,
                R.id.textView2});
        listview12.setAdapter(adapter);
    }
}


