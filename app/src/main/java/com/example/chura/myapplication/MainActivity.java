package com.example.chura.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    int counter;
    EditText password, phone;
    TextView registerhere;
    Button login;
    Spinner spinner;
    public  static String user_logged;

    private static int accessChoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = 0;
        login = (Button) findViewById(R.id.button3);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);

        registerhere = (TextView) findViewById(R.id.registerpage);
        final String[] accessor = {"Retailer", "Distributor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accessor);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selected_item = spinner.getSelectedItemPosition();
                String str = String.valueOf(selected_item);

                if (str.equals("0")) {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterRetailers.class);
                    startActivity(registerIntent);
                } else {
                    Intent registerIntent = new Intent(MainActivity.this, Distributorsregister.class);
                    startActivity(registerIntent);

                }


            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel, pword;
                tel = phone.getText().toString().trim();
                pword = password.getText().toString().trim();

                int position = spinner.getSelectedItemPosition();
                switch (position) {
                    case 0:
                        loginRetailer(tel, pword);
                        break;
                    case 1:
                        loginDistributor(tel, pword);
                        break;

                }

                //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();


            }

            public void loginRetailer(final String tel, final String pword) {

                final ProgressDialog pdialog = new ProgressDialog(MainActivity.this);
                pdialog.setMessage("Loggin you in");
                pdialog.show();

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://192.168.43.196/mretail/retailerlog.php ";
                //192.168.43.196
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pdialog.dismiss();

                                if (response.trim().equals("1")) {
                                    user_logged=tel;
                                    Intent homeIntent = new Intent(MainActivity.this, Retailers.class);
                                    MainActivity.this.startActivity(homeIntent);

                                } else {
                                    Toast.makeText(MainActivity.this, "Login failed!! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdialog.dismiss();
                                Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tel", tel);
                        params.put("pword", pword);


                        return params;
                    }
                };
                queue.add(PostRequest);

            }

            public void loginDistributor(final String tel, final String pword) {

                final ProgressDialog pdialog = new ProgressDialog(MainActivity.this);
                pdialog.setMessage("Loggin you in");
                pdialog.show();

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://192.168.43.196/mretail/distributorlog.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pdialog.dismiss();

                                if (response.trim().equals("1")) {
                                    user_logged=tel;
                                    Intent homeIntent = new Intent(MainActivity.this, Distributorhome.class);
                                    MainActivity.this.startActivity(homeIntent);

                                } else {
                                    Toast.makeText(MainActivity.this, "Login failed!! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdialog.dismiss();
                                Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tel", tel);
                        params.put("pword", pword);


                        return params;
                    }
                };
                queue.add(PostRequest);

            }

        });


    }
}
