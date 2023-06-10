package com.digo.vendas_de_produtos;

public class Produto {
    int _idProduto;
    String nomeProduto;
    Double preco;


    public int get_idProduto() {
        return _idProduto;
    }

    public void set_idProduto(int _idProduto) {
        this._idProduto = _idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
