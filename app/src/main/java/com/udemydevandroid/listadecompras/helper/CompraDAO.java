package com.udemydevandroid.listadecompras.helper;

import static com.udemydevandroid.listadecompras.helper.DBHelper.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udemydevandroid.listadecompras.model.Compra;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO implements ICompraDAO {

    SQLiteDatabase escreve;
    SQLiteDatabase le;

    public CompraDAO(Context context) {
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean limparLista() {
        escreve.delete(TABLE_NAME, null, null);
        return true;
    }

    @Override
    public boolean adicionar(Compra compra) {
        ContentValues cv = new ContentValues();
        cv.put("id", compra.getId());
        cv.put("quantidade", compra.getQuantidade());
        cv.put("nome", compra.getNome());
        cv.put("valor", compra.getValor().doubleValue());

        escreve.insert(TABLE_NAME, null, cv);
        return true;
    }

    @Override
    public boolean deletar(Compra compra) {
        String[] args = {compra.getId().toString()};
        escreve.delete(TABLE_NAME, "id=?", args);
        return true;
    }

    @Override
    public boolean atualizar(Compra compra) {
        ContentValues cv = new ContentValues();
        cv.put("id", compra.getId());
        cv.put("quantidade", compra.getQuantidade());
        cv.put("nome", compra.getNome());
        cv.put("valor", compra.getValor().doubleValue());
        String[] args = {compra.getId().toString()};
        escreve.update(TABLE_NAME, cv, "id=?", args);
        return true;
    }

    @Override
    public List<Compra> listar() {
        List<Compra> listaCompras = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        @SuppressLint("Recycle") Cursor c = le.rawQuery(sql, null);
        while (c.moveToNext()) {
            @SuppressLint("Range") Long id = c.getLong(c.getColumnIndex("id"));
            @SuppressLint("Range") int quantidade = c.getInt(c.getColumnIndex("quantidade"));
            @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome"));
            @SuppressLint("Range") BigDecimal valor = BigDecimal.valueOf(c.getDouble(c.getColumnIndex("valor")));
            Compra compra = new Compra(id, quantidade, nome, valor);
            //Adicionando listagem
            listaCompras.add(compra);
        }
        return listaCompras;
    }
}
