package com.digo.vendas_de_produtos;

public class Cliente {
    int _idCliente;
    String nomeCliente;
    String nomeResponsavel;
    String telefoneResponsavel;
    double credito;

    public int get_idCliente() {
        return _idCliente;
    }

    public void set_idCliente(int _idCliente) {
        this._idCliente = _idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }
}
