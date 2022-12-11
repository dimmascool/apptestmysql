package com.unsera.apptestmysql;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    Context context;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ArrayList<Mahasiswa> mItems;
    FloatingActionButton floatinButton;

    ActionBar actionBar;
    private String title = "List Mahasiswa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.list_mhs, null);
//        Button editBtn = view.findViewById(R.id.btnEdit);
//
//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
//            }
//        });


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
        context=MainActivity.this;
        mRecyclerView = findViewById(R.id.recyclerview);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new AdapterProcess(context, mItems);
        mRecyclerView.setAdapter(mAdapter);
        floatinButton = findViewById(R.id.btnFloatingTambah);
        floatinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(MainActivity.this, ActivityTambah.class);
                startActivity(moveIntent);
            }
        });



        loadjson();
    }

    //proses mengambil data
    private void loadjson(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MyLink.MY_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Mahasiswa brg = new Mahasiswa();
                        brg.setNama(jsonObject.getString("nama"));
                        brg.setJurusan(jsonObject.getString("jurusan"));
                        brg.setNIM(jsonObject.getString("nim"));

                        mItems.add(brg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
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



}