package com.udemydevandroid.listadecompras.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Compra implements Serializable {

    private Long id;
    private int quantidade;
    private String nome;
    private BigDecimal valor;
    private double totalCompra;

    public Compra() {}

    public Compra(Long id, int quantidade, String nome, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.nome = nome;
        this.valor = valor;
        this.totalCompra = quantidade * valor.doubleValue();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public double getTotalCompra() {
        double totalCompra = this.quantidade * this.valor.doubleValue();
        return totalCompra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id + '\'' +
                "quantidade=" + quantidade +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                '}';
    }
}
