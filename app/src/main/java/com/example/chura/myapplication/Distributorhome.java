package com.example.chura.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Distributorhome extends AppCompatActivity {

    Button Addproduct ,boughtproduct , viewretailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributorhome);


        Addproduct=(Button) findViewById(R.id.Addproduct);
        boughtproduct=(Button) findViewById(R.id.boughtproduct);
        viewretailer=(Button) findViewById(R.id.viewretailers);

        Addproduct.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent AddproductIntent =new Intent(Distributorhome.this,productadd.class);
                                              Distributorhome.this.startActivity(AddproductIntent);

                                          }
                                      }
        );

      boughtproduct.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent boughtproductIntent = new Intent(Distributorhome.this,DistributorProductorslist.class);
              Distributorhome.this.startActivity(boughtproductIntent);

          }
      });

        viewretailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retailerstIntent = new Intent(Distributorhome.this,Retailerlistview.class);
                Distributorhome.this.startActivity(retailerstIntent);

            }
        });




    }
}
