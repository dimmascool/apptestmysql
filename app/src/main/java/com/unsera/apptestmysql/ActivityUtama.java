package com.unsera.apptestmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityUtama extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        getSupportActionBar().hide();

        Button btnList = findViewById(R.id.btnListMhs);
        Button btnAboutMe = findViewById(R.id.btnAboutMe);


        btnList.setOnClickListener(this);
        btnAboutMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnListMhs) {
            Intent moveIntent = new Intent(this, MainActivity.class);
            startActivity(moveIntent);
        } else if (v.getId() == R.id.btnAboutMe) {
            Intent moveIntent = new Intent(this, AboutActivity.class);
            startActivity(moveIntent);
        }
    }
}
