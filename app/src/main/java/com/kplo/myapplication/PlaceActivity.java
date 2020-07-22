package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlaceActivity extends AppCompatActivity {

    TextView home,place,partylist,myparty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        home = findViewById(R.id.home);
        place = findViewById(R.id.place);
        partylist = findViewById(R.id.partylist);
        myparty = findViewById(R.id.myparty);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, PlaceActivity.class);
                startActivity(intent);

            }
        });
        partylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlaceActivity.this, PartylistActivity.class);
                startActivity(intent);
            }
        });
        myparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, MypartyActivity.class);
                startActivity(intent);

            }
        });

    }
}