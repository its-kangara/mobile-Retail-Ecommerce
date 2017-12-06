package com.example.chura.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.chura.myapplication.MainActivity.user_logged;

public class Details extends AppCompatActivity {
ImageView imageView;
    TextView product;
    EditText  qnty;
    Button button;
    TextView textView7;
    String item_price;


    int p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=(ImageView)findViewById(R.id.imageView7);
        product=(TextView)findViewById(R.id.textView6);
        qnty=(EditText)findViewById(R.id.editText2);
        textView7=(TextView)findViewById(R.id.textView7) ;
        button=(Button)findViewById(R.id.button); 
      //  getSupportActionBar().setTitle("Add to Cart");
/*
                Intent intent= new Intent(context,Details.class);
                intent.putExtra("item_price",itemData.getItem_price());
                intent.putExtra("image_url",itemData.getImage_url());
                intent.putExtra("item_name",itemData.getItem_name());
                intent.putExtra("item_description",itemData.getItem_description());
                context.startActivity(intent);
 */
        Intent intent = getIntent();
        String imgurl=intent.getStringExtra("image_url");

        String productname=intent.getStringExtra("item_name");
         item_price=intent.getStringExtra("item_price");

        product.setText(productname);
        Picasso.Builder builder = new Picasso.Builder(Details.this);
        builder.listener(new Picasso.Listener()
        {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Toast.makeText(Details.this, String.valueOf(exception), Toast.LENGTH_SHORT).show();

            }
        });
        builder.build().load(imgurl).into(imageView);

        qnty.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//               if(!s.equals(""))
//               {
//                   int q = Integer.parseInt(qnty.getText().toString().trim());
//                   int p= Integer.parseInt(item_price);
//                   int total=p*q;
//                   textView7.setText(String.valueOf(total));
//
//               }

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.equals("")||s.length()!=0)
                {

                   try
                   {
                       int q = Integer.parseInt(qnty.getText().toString().trim());
                       int p= Integer.parseInt(item_price);
                       int total=p*q;
                       textView7.setText(String.valueOf(total));


                   }catch (NumberFormatException ex)
                   {
                       textView7.setText("0");
                   }


                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity,product1,total;
                quantity= qnty.getText().toString().trim();
                product1 = product.getText().toString().trim();
                total=textView7.getText().toString().trim();

                Sendpaymentdetails(quantity, product1,total);
            }

            public  void Sendpaymentdetails(final String quantity, final String product1, final String total){
               final ProgressDialog progressDialog = new ProgressDialog(Details.this);
                progressDialog.setMessage("loading..");
                progressDialog.show();

                RequestQueue queue = Volley.newRequestQueue( Details.this);
                String url = "http://192.168.43.196/mretail/mpesa.php";
                StringRequest PostRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(Details.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Details.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams()


                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("quantity", quantity);
                        params.put("item", product1);
                        params.put("amount", total);
                        params.put ("user_logged",user_logged);




                        return params;
                    }
                };


                queue.add(PostRequest);
            }


        });

    }

}
