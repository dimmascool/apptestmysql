package com.unsera.apptestmysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.ViewProcessHolder> {

    Context context;
    private ArrayList<Mahasiswa> item; //memanggil modelData

    public AdapterProcess(Context context, ArrayList<Mahasiswa> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang, parent, false); //memanggil layout list recyclerview
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
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {

        TextView txtNamaMahasiswa;
        TextView txtNimMahasiswa;
        TextView txtJurusanMahasiswa;

        public ViewProcessHolder(View itemView) {
            super(itemView);

            txtNamaMahasiswa = itemView.findViewById(R.id.txtNama);
            txtNimMahasiswa = itemView.findViewById(R.id.txtNIM);
            txtJurusanMahasiswa = itemView.findViewById(R.id.txtJurusan);

        }
    }
}
