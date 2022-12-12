package com.unsera.apptestmysql;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.ViewProcessHolder>  {

    Context context;
    private ArrayList<Mahasiswa> item; //memanggil modelData



    public AdapterProcess(Context context, ArrayList<Mahasiswa> item) {
        this.context = context;
        this.item = item;

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

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myactivity = new Intent(context.getApplicationContext(), ActivityEdit.class);
                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                myactivity.putExtra(ActivityEdit.EXTRA_NIM_LAMA, data.getNIM());
                myactivity.putExtra(ActivityEdit.EXTRA_NIM, data.getNIM());
                myactivity.putExtra(ActivityEdit.EXTRA_NAMA, data.getNama());
                myactivity.putExtra(ActivityEdit.EXTRA_JURUSAN, data.getJurusan());
                context.getApplicationContext().startActivity(myactivity);
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
        Button editBtn;

        public ViewProcessHolder(View itemView) {
            super(itemView);

            txtNamaMahasiswa = itemView.findViewById(R.id.txtNama);
            txtNimMahasiswa = itemView.findViewById(R.id.txtNIM);
            txtJurusanMahasiswa = itemView.findViewById(R.id.txtJurusan);

            editBtn = itemView.findViewById(R.id.btnEdit);


        }
    }

}
