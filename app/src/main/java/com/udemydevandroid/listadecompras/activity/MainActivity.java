package com.udemydevandroid.listadecompras.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udemydevandroid.listadecompras.R;
import com.udemydevandroid.listadecompras.adapter.AdapterLista;
import com.udemydevandroid.listadecompras.helper.CompraDAO;
import com.udemydevandroid.listadecompras.helper.RecyclerItemClickListener;
import com.udemydevandroid.listadecompras.model.Compra;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerLista;
    private List<Compra> listaCompras = new ArrayList<>();
    private TextView textTotalCompra;
    private Button btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerLista = findViewById(R.id.recyclerLista);
        btnLimpar = findViewById(R.id.btnLimpar);

        recyclerLista.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerLista, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), AdicionarItemActivity.class);
                intent.putExtra("compra", listaCompras.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Confirmar exclusão");
                alert.setMessage("Você deseja excluir este produto?");
                alert.setPositiveButton("Sim", (listener, i) -> {
                    CompraDAO compraDAO = new CompraDAO(MainActivity.this);
                    Compra compra = listaCompras.get(position);
                    if (compraDAO.deletar(compra)) {
                        Toast.makeText(MainActivity.this, "Produto deletado", Toast.LENGTH_SHORT).show();
                        carregarItensListaAdapter();
                    }
                });
                alert.setNegativeButton("Não", (listener, i) -> {

                });
                alert.create();
                alert.show();

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));

        btnLimpar.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Confirmar exclusão");
            alert.setMessage("Deseja limpar a lista?");
            alert.setPositiveButton("Sim", (listener, i) -> {
                CompraDAO compraDAO = new CompraDAO(MainActivity.this);
                if (compraDAO.limparLista()) {
                    Toast.makeText(getApplicationContext(), "Lista limpa", Toast.LENGTH_SHORT).show();
                    carregarItensListaAdapter();
                }
            });
            alert.setNegativeButton("Não", (listener, i) -> {

            });
            alert.create();
            alert.show();
        });


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdicionarItemActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onStart() {
        super.onStart();
        carregarItensListaAdapter();

    }

    private void carregarItensListaAdapter() {

        Locale l = new Locale("pt", "BR");
        CompraDAO listaDAO = new CompraDAO(getApplicationContext());
        listaCompras = listaDAO.listar();
        textTotalCompra = findViewById(R.id.textTotalCompra);

        double somaTotal = listaCompras.stream().mapToDouble(Compra::getTotalCompra).sum();
        String resultado = String.format(l, "R$ %.2f", somaTotal);
        textTotalCompra.setText(resultado);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerLista.setLayoutManager(layoutManager);
        recyclerLista.setHasFixedSize(true);
        recyclerLista.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        AdapterLista adapterLista = new AdapterLista(listaCompras);
        recyclerLista.setAdapter(adapterLista);

    }
}