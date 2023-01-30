package com.unsera.apptestmysql;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.ViewProcessHolder>  {

    Context context;
    private ArrayList<Mahasiswa> item; //memanggil modelData

    RequestQueue requestQueue;
    StringRequest stringRequest;

    public AdapterProcess(Context context, ArrayList<Mahasiswa> item) {
        this.context = context;
        this.item = item;

        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mhs, parent, false); //memanggil layout list recyclerview
        ViewProcessHolder processHolder = new ViewProcessHolder(view);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(ViewProcessHolder holder, int position) {


        final Mahasiswa data = item.get(position);
        final int panjang = data.getNama().length();
        if (panjang >= 16){
            final String mIsi = data.getNama().substring(0,16);
            holder.txtNamaMahasiswa.setText(mIsi.concat("..."));
        } else {
            final String mIsi = data.getNama();
            holder.txtNamaMahasiswa.setText(mIsi);
        }
        holder.txtNimMahasiswa.setText(data.getNIM());
        holder.txtJurusanMahasiswa.setText(data.getJurusan());

//        String imageData = data.getBlobImage();
//
//        byte[] imageBytes = Base64.decode(imageData, Base64.DEFAULT);
//        Bitmap imageBitMap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//
//        holder.ivProfile.setImageBitmap(imageBitMap);


        Glide.with(context).load("https://dimas.bantani.net.id/api/img/"+data.getNIM()+".jpg")
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivProfile);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myactivity = new Intent(context, ActivityEdit.class);
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                myactivity.putExtra(ActivityEdit.EXTRA_NIM_LAMA, data.getNIM());
                myactivity.putExtra(ActivityEdit.EXTRA_NIM, data.getNIM());
                myactivity.putExtra(ActivityEdit.EXTRA_NAMA, data.getNama());
                myactivity.putExtra(ActivityEdit.EXTRA_JURUSAN, data.getJurusan());
                myactivity.putExtra(ActivityEdit.EXTRA_LINK_POTO, "https://dimas.bantani.net.id/api/img/"+data.getNIM()+".jpg");
                context.startActivity(myactivity);
            }
        });
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringRequest = new StringRequest(Request.Method.POST, MyLink.MY_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.contains("success")) {
                            Toast.makeText(context, "Hapus Data Sukses", Toast.LENGTH_LONG).show();
//                            Intent moveIntent = new Intent(context, MainActivity.class);
//                            context.startActivity(moveIntent);
                        } else {
                            //Displaying an error message on toast
                            Log.e("error message", response.toString());
                            Toast.makeText(context, "Gagal Hapus Data", Toast.LENGTH_LONG).show();
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
                        params.put("request", "deletedata");
                        params.put("deleteby", "NIM");
                        params.put("nim", data.getNIM());
                        Log.e("NIM", data.getNIM());
                        //...
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {

        TextView txtNamaMahasiswa;
        TextView txtNimMahasiswa;
        TextView txtJurusanMahasiswa;
        Button editBtn, btnHapus;
        ImageView ivProfile;

        public ViewProcessHolder(View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.img_item_photo);
            txtNamaMahasiswa = itemView.findViewById(R.id.txtNama);
            txtNimMahasiswa = itemView.findViewById(R.id.txtNIM);
            txtJurusanMahasiswa = itemView.findViewById(R.id.txtJurusan);

            editBtn = itemView.findViewById(R.id.btnEdit);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }

}
