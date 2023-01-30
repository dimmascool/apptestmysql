package com.unsera.apptestmysql;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ActivityTambah extends AppCompatActivity {

    Context context;
    EditText etNIM,etNama, etJurusan;
    Button btnSimpan, btnupload;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ActionBar actionBar;
    private String title = "Tambah Mahasiswa";

    ImageView logo;
    Bitmap bitmap;
    String encodeImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        context=ActivityTambah.this;

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
                SaveData();
            }
        });

        logo = findViewById(R.id.img);

        btnupload = findViewById(R.id.btnUpload);
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(ActivityTambah.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Log.e("data", response.toString());
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                logo.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    private void SaveData(){
        String NIMMhs = etNIM.getText().toString().trim();
        String NamaMhs = etNama.getText().toString().trim();
        String JurusanMhs = etJurusan.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(NIMMhs)) {
            isEmptyFields = true;
            etNIM.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(NamaMhs)) {
            isEmptyFields = true;
            etNama.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(JurusanMhs)) {
            isEmptyFields = true;
            etJurusan.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields) {

            stringRequest = new StringRequest(Request.Method.POST, MyLink.MY_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //If we are getting success from server
                    if (response.contains("success")) {
                        Toast.makeText(context, "Data berhasil di simpan", Toast.LENGTH_LONG).show();
                        Intent moveIntent = new Intent(ActivityTambah.this, MainActivity.class);
                        startActivity(moveIntent);
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
            }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("nim",NIMMhs);
                    params.put("nama",NamaMhs);
                    params.put("jurusan",JurusanMhs);
                    params.put("img",encodeImageString);
                    params.put("request","insertnewdata");
                    //...
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

}