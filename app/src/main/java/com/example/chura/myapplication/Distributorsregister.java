
package com.example.chura.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;
import java.util.HashMap;

public class Distributorsregister extends AppCompatActivity {
    Button register;
    EditText distributorname,telephone,password,confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributorsregister);

        distributorname = (EditText) findViewById(R.id.distributorname);
        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        register = (Button) findViewById(R.id.btregister);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  distributername, tel, passd, confir;

                distributername =distributorname.getText().toString().trim();
                tel = telephone.getText().toString().trim();
                passd = password.getText().toString().trim();
                confir = confirmpassword.getText().toString().trim();

                if(distributorname.equals("")||tel.equals("")||passd.equals("")||confir.equals("")){

                    Toast pass = Toast.makeText(Distributorsregister.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    pass.show();

                }

                if(!passd.equals(confir)){
                    Toast pass = Toast.makeText(Distributorsregister.this, "Passwords don't match", Toast.LENGTH_LONG);
                    pass.show();


                }
                else


            Distributorsregister(distributername,tel,passd);




            }


            public void Distributorsregister(final String distributername, final String tel, final String passd) {

                RequestQueue queue = Volley.newRequestQueue(Distributorsregister.this);
                String url = "http://192.168.43.196/mretail/distributorreg.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Distributorsregister.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Distributorsregister.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("distributername", distributername);
                        params.put("tel", tel);
                        params.put("passd", passd);


                        return params;
                    }
                };


                queue.add(PostRequest);


            }
        });

    }}

