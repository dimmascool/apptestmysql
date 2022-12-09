package com.unsera.apptestmysql;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ActivityTambah extends AppCompatActivity {

    Context context;
    EditText etNIM,etNama, etJurusan;
    Button btnSimpan;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ActionBar actionBar;
    private String title = "Tambah Mahasiswa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);

        requestQueue = Volley.newRequestQueue(this);

        etNIM = findViewById(R.id.etNIM);
        etNama = findViewById(R.id.etNama);
        etJurusan = findViewById(R.id.etJurusan);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpanBarang();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    private void SimpanBarang(){
        String NIMMhs = etNIM.getText().toString().trim();
        String NamaMhs = etNama.getText().toString().trim();
        String JurusanMhs = etJurusan.getText().toString().trim();

        String link_Simpan = "https://dimas.bantani.net.id/api/api_tambah";

        stringRequest = new StringRequest(Request.Method.POST, link_Simpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server
                if (response.contains("success")) {
                    Toast.makeText(context, "Simpan Data Sukses", Toast.LENGTH_LONG).show();
                } else {
                    //Displaying an error message on toast
                    Toast.makeText(context, "Gagal Simpan Data", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nim",NIMMhs);
                params.put("nama",NamaMhs);
                params.put("jurusan",JurusanMhs);
                //...
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}