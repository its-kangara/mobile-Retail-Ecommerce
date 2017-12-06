package com.example.chura.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Productitemlist extends AppCompatActivity {

    ListView listView;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private Context mContext;
    RecyclerView.LayoutManager layoutManager;
    String actvity_title;
    SharedPreferences sharedPreferences;
    public List<ItemData> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productslist);
      //  listView=(ListView)findViewById(R.id.listview3);
       // fetch_products();
        fetch_items();
    }

    public void fetch_products()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(Productitemlist.this, "Start", Toast.LENGTH_SHORT).show();
                pDialog = new ProgressDialog(Productitemlist.this);
                pDialog.setMessage("Fetching Products...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> paramms = new HashMap<>();
                paramms.put("name", "");
                String s = rh.sendPostRequest("http://192.168.43.196/mretail/fetchproduct.php", paramms);
                return s;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                Toast.makeText(Productitemlist.this, s, Toast.LENGTH_SHORT).show();
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
                    String productname , productquantity,productprice,productimage;

                    productname=jo.getString("productname");
                    productquantity=jo.getString("productquantity");
                    productprice=jo.getString("productprice");
                    productimage=jo.getString("productimage");


                    HashMap<String, String> products = new HashMap<>();
                    products.put("productname", productname);
                    products.put("productquantity", productquantity);
                    products.put("productprice", productprice);
                    products.put("productimage", productimage);


                    list.add(products);
                }
                else
                {

                }


            }


        } catch (JSONException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Productitemlist.this);
            alertDialogBuilder.setTitle("Attention!").setMessage(String.valueOf(s))
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true).show();
        }

        ListAdapter adapter = new SimpleAdapter(Productitemlist.this, list, R.layout.activity_productitemlist,
                new String[]{"productname", "productquantity","productprice","productimage"}, new int[]{R.id.productname,
                R.id.priceperitem,R.id.quantity,R.id.imageView5});
        listView.setAdapter(adapter);
    }


    public  void fetch_items()
    {



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        //Toast.makeText(mContext, String.valueOf(url), Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.43.196/mretail/fetch_products.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();

//                        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//done here
                        layoutManager = new GridLayoutManager(mContext, 1);
                        recyclerView.setLayoutManager(layoutManager);

                        JSONObject jsonObject = null;
                        ItemData itemdata;


                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("result");


                            /*

                              productname=jo.getString("productname");
                    productquantity=jo.getString("productquantity");
                    productprice=jo.getString("productprice");
                    productimage=jo.getString("productimage");
                             */


                            listitems = new ArrayList<>();
                            for (int i = 0; i < result.length(); i++)
                            {
                                JSONObject jo = result.getJSONObject(i);
                                String item_price=jo.getString("productprice");
                                String image_url="http://192.168.43.196/mretail/uploads/"+jo.getString("productimage");
                                String item_name=jo.getString("productname");
                                String item_description=jo.getString("productquantity");
                                itemdata = new ItemData(item_price, image_url, item_name, item_description);
                                listitems.add(itemdata);
                            }
                            adapter = new MyAdapter(listitems,Productitemlist.this);
                            recyclerView.setAdapter(adapter);



                        } catch (JSONException e) {

//                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                    .setTitleText("Oops...")
//                                    .setContentText(e.toString())
//                                    .show();

                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Productitemlist.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

}
