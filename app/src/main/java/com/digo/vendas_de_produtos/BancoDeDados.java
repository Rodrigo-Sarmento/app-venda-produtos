package com.digo.vendas_de_produtos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {
    private static BancoDeDados instance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BancoVendaProdutos.sqlite";
    private Context context;
    public BancoDeDados(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static BancoDeDados Sharedinstance(Context context){
        if (instance == null){
            instance = new BancoDeDados(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1;
        query1 = "CREATE TABLE IF NOT EXISTS TbCliente (_idCliente INTEGER PRIMARY KEY AUTOINCREMENT, nomeCliente TEXT NOT NULL, nomeResponsavel TEXT NOT NULL, telefoneResponsavel TEXT, credito DOUBLE NOT NULL)";
        db.execSQL(query1);

        String query2;
        query2 = "CREATE TABLE IF NOT EXISTS TbProduto (_idProduto INTEGER PRIMARY KEY AUTOINCREMENT, nomeProduto TEXT NOT NULL,  preco DOUBLE NOT NULL)";
        db.execSQL(query2);
    }

    public void insertCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nomeCliente", cliente.getNomeCliente());
        values.put("nomeResponsavel", cliente.getNomeResponsavel());
        values.put("telefoneResponsavel", cliente.getTelefoneResponsavel());
        values.put("credito", cliente.getCredito());
        db.insert("TbCliente", "", values);
        db.close();
    }

    public void insertProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nomeProduto", produto.getNomeProduto());
        values.put("preco", produto.getPreco());
        db.insert("TbProduto", "", values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Produto> getAllProdutos() {
        List<Produto> produtos = new ArrayList<>();

        // Conexão com o banco de dados e consulta para obter todos os produtos
        String selectQuery = "SELECT * FROM TbProduto;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Percorrer o cursor e criar objetos Produto com os dados obtidos
        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.set_idProduto(cursor.getInt(cursor.getColumnIndex("_idProduto")));
                produto.setNomeProduto(cursor.getString(cursor.getColumnIndex("nomeProduto")));
                produto.setPreco(cursor.getDouble(cursor.getColumnIndex("preco")));

                produtos.add(produto);
            } while (cursor.moveToNext());
        }

        // Fechar o cursor e a conexão com o banco de dados
        cursor.close();
        db.close();

        return produtos;
    }

    @SuppressLint("Range")
    public List<Produto> searchProdutos(String input) {
        List<Produto> produtos = new ArrayList<>();

        // Conexão com o banco de dados e consulta para obter os produtos com nomes que contenham o texto de entrada
        String selectQuery = "SELECT * FROM TbProduto WHERE nomeProduto LIKE '%" + input + "%';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Percorrer o cursor e criar objetos Produto com os dados obtidos
        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.set_idProduto(cursor.getInt(cursor.getColumnIndex("_idProduto")));
                produto.setNomeProduto(cursor.getString(cursor.getColumnIndex("nomeProduto")));
                produto.setPreco(cursor.getDouble(cursor.getColumnIndex("preco")));

                produtos.add(produto);
            } while (cursor.moveToNext());
        }

        // Fechar o cursor e a conexão com o banco de dados
        cursor.close();
        db.close();

        return produtos;
    }

    @SuppressLint("Range")
    public List<Cliente> searchClientes(String input) {
        List<Cliente> clientes = new ArrayList<>();

        // Conexão com o banco de dados e consulta para obter os clientes com nomes que contenham o texto de entrada
        String selectQuery = "SELECT * FROM TbCliente WHERE nomeCliente LIKE '%" + input + "%';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Percorrer o cursor e criar objetos Cliente com os dados obtidos
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.set_idCliente(cursor.getInt(cursor.getColumnIndex("_idCliente")));
                cliente.setNomeCliente(cursor.getString(cursor.getColumnIndex("nomeCliente")));

                clientes.add(cliente);
            } while (cursor.moveToNext());
        }

        // Fechar o cursor e a conexão com o banco de dados
        cursor.close();
        db.close();

        return clientes;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
