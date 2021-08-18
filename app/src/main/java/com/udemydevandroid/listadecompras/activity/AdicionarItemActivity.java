package com.udemydevandroid.listadecompras.activity;

import static me.abhinay.input.CurrencySymbols.USA;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udemydevandroid.listadecompras.R;
import com.udemydevandroid.listadecompras.helper.CompraDAO;
import com.udemydevandroid.listadecompras.model.Compra;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import me.abhinay.input.CurrencyEditText;

public class AdicionarItemActivity extends AppCompatActivity {

    private EditText textNome, textQuantidade;
    //private CurrencyEditText textValor;
    private EditText textValor;
    private Compra compraRecuperada;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item);
        textNome = findViewById(R.id.textNome);
        textQuantidade = findViewById(R.id.textQuantidade);
        textValor = findViewById(R.id.textValor);
//        textValor.setDelimiter(false);
//        textValor.setSpacing(false);
//        textValor.setDecimals(true);


        compraRecuperada = (Compra) getIntent().getSerializableExtra("compra");
        if (compraRecuperada != null) {
            textNome.setText(compraRecuperada.getNome());
            textQuantidade.setText(String.valueOf(compraRecuperada.getQuantidade()));
            textValor.setText(compraRecuperada.getValor().toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        CompraDAO listaDAO = new CompraDAO(getApplicationContext());

        if (id == R.id.action_settings) {

            String nome = textNome.getText().toString();
            String valor = textValor.getText().toString();
            String valorText = valor.replace(",", ".");
            String quantidade = textQuantidade.getText().toString();
            if (quantidade.isEmpty()) {
                quantidade = "0";
            }
            BigDecimal valorBig;
            if (valor.isEmpty()) {
                valorBig = new BigDecimal(0);
            } else {
                valorBig = BigDecimal.valueOf(Double.parseDouble(valorText));
            }

            if (compraRecuperada != null) { //ATUALIZA

                if (!nome.isEmpty()) {
                    Compra compra = new Compra();
                    compra.setNome(nome);
                    compra.setQuantidade(Integer.parseInt(quantidade));
                    compra.setValor(valorBig);
                    compra.setId(compraRecuperada.getId());
                    if (listaDAO.atualizar(compra)) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Produto atualizado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
                }

            } else { //SALVA

                if (!nome.isEmpty()) {
                    Compra compra = new Compra();
                    compra.setNome(nome);
                    compra.setQuantidade(Integer.parseInt(quantidade));
                    compra.setValor(valorBig);
                    if (listaDAO.adicionar(compra)) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Produto adicionado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao adicionar o produto", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
                }

            } //Fim salva

            return true;
        } //Fim botao

        return super.onOptionsItemSelected(item);
    } //Fim option

} //Fim acticity
