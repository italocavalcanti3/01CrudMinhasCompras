package com.udemydevandroid.listadecompras.helper;

import com.udemydevandroid.listadecompras.model.Compra;

import java.util.List;

public interface ICompraDAO {

    boolean adicionar(Compra compra);
    boolean deletar(Compra compra);
    boolean atualizar(Compra compra);
    boolean limparLista();
    List<Compra> listar();

}
