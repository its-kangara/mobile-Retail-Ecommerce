package com.example.chura.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RetailerHome extends AppCompatActivity {

    Button viewdistributors,viewproducts,search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);

        viewdistributors=(Button) findViewById(R.id.vdistributor);
        viewproducts=(Button) findViewById(R.id.vproducts);
        search_button=(Button) findViewById(R.id.search_button);

        viewdistributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewdistributorIntent =new Intent(RetailerHome.this,Retailers.class);
               RetailerHome .this.startActivity( viewdistributorIntent);
            }
        });
        viewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewproductsIntent =new Intent(RetailerHome.this,Productitemlist.class);
                startActivity(viewproductsIntent);

            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(RetailerHome.this, Retailers.class);
                startActivity(searchIntent);
            }
            });



    }
}
