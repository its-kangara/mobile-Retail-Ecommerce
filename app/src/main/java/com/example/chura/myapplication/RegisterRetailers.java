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
        import com.basgeekball.awesomevalidation.AwesomeValidation;

        import com.basgeekball.awesomevalidation.ValidationStyle;
        import com.google.common.collect.Range;
        import java.util.Map;
        import java.util.HashMap;

public class RegisterRetailers extends AppCompatActivity {
    Button register;
    EditText firstname,lastname,email,telephone,password,confirmpassword;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_retailers);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        register = (Button) findViewById(R.id.btregister);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fname, lname, mail, tel, passd, confir;
                fname = firstname.getText().toString().trim();
                lname = lastname.getText().toString().trim();
                mail = email.getText().toString().trim();
                tel = telephone.getText().toString().trim();
                passd = password.getText().toString().trim();
                confir = confirmpassword.getText().toString().trim();

                if(fname.equals("")||lname.equals("")||mail.equals("")||tel.equals("")||passd.equals("")||confir.equals("")){

                    Toast pass = Toast.makeText(RegisterRetailers.this, "Fill in all the fields", Toast.LENGTH_LONG);
                    pass.show();
                    return;
                }

                if(!passd.equals(confir)){
                    Toast pass = Toast.makeText(RegisterRetailers.this, "Passwords don't match", Toast.LENGTH_LONG);
                    pass.show();
                    return;

                }
                else


                    RegisterRetailers(fname, lname, mail, tel, passd);



            }


            public void RegisterRetailers(final String fname, final String lname, final String mail, final String tel, final String passd) {

                RequestQueue queue = Volley.newRequestQueue( RegisterRetailers.this);
                String url = "http://192.168.43.196/mretail/regretailer.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(RegisterRetailers.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(RegisterRetailers.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fname", fname);
                        params.put("lname", lname);
                        params.put("mail", mail);
                        params.put("tel", tel);
                        params.put("passd", passd);


                        return params;
                    }
                };


                queue.add(PostRequest);


            }
        });

    }}

