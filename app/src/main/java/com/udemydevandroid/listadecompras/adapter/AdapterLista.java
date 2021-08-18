package com.udemydevandroid.listadecompras.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udemydevandroid.listadecompras.R;
import com.udemydevandroid.listadecompras.model.Compra;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.MyViewHolder> {

    List<Compra> listaCompras;

    public AdapterLista(List<Compra> lista) {
        this.listaCompras = lista;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textQuantidade, textNome, textValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textQuantidade = itemView.findViewById(R.id.textQuantidade);
            textNome = itemView.findViewById(R.id.textNome);
            textValor = itemView.findViewById(R.id.textValor);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_compras_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Compra compra = listaCompras.get(position);
        holder.textQuantidade.setText(String.valueOf(compra.getQuantidade()));
        holder.textNome.setText(compra.getNome());
        Locale l = new Locale("pt", "BR");
        String valor = String.format(l, "R$ %.2f", compra.getValor());
        holder.textValor.setText(valor);
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }
}
