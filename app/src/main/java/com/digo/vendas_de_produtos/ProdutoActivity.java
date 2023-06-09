package com.digo.vendas_de_produtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {
    private TextView etNomeProduto;
    private Spinner categoria;
    private TextView etPreco;

    private ImageView addProduto;
    private ImageView voltarProduto;
    private TableLayout tabelaProdutos;

    private Button addPreco;
    private Button rmvPreco;

    private BancoDeDados db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_produto);
        this.db = BancoDeDados.Sharedinstance(this);

        categoria = findViewById(R.id.spinnerCategoriaV);
        etNomeProduto = findViewById(R.id.entNomeProduto);
        etPreco = findViewById(R.id.entPrice);
        addProduto = findViewById(R.id.imageViewAddProduto);
        voltarProduto = findViewById(R.id.imageViewVoltarProduto);
        categoria = findViewById(R.id.spinnerCategoriaV);

        addPreco = findViewById(R.id.btnAddPrice);
        rmvPreco = findViewById(R.id.btnRemovePrice);

        tabelaProdutos = findViewById(R.id.tabelaProdutos);
        carregarProdutos();

        List<String> categorias = new ArrayList<>();
        categorias.add("Frutas");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapter);

        addProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadProduto(v);
            }

        });

        addPreco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double preco = Double.parseDouble(etPreco.getText().toString());
                preco += 1;
                etPreco.setText(String.valueOf(preco));
            }
        });

        rmvPreco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double preco = Double.parseDouble(etPreco.getText().toString());
                preco -= 0.5;
                etPreco.setText(String.valueOf(preco));
            }
        });

        voltarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });
    }
    public void CadProduto(View view){
        Produto produto = new Produto();
        produto.setNomeProduto(etNomeProduto.getText().toString());
        produto.setPreco(Double.parseDouble(etPreco.getText().toString()));

        db.insertProduto(produto);
        carregarProdutos();
        Toast.makeText(getApplicationContext(),"Cadastrado com Sucesso o Produto: "+produto.nomeProduto ,
                Toast.LENGTH_LONG).show();
    }

    public void carregarProdutos() {
        tabelaProdutos.removeAllViews();//limpa
        List<Produto> produtos = db.getAllProdutos();

        for (Produto produto : produtos) {
            // Criar uma nova linha para o produto
            TableRow row = new TableRow(this);

            // Criar as células para cada coluna do produto
            TextView nomeProduto = new TextView(this);
            TextView precoUnidade = new TextView(this);

            // Definir os valores das células com os dados do produto
            nomeProduto.setText(produto.getNomeProduto());
            precoUnidade.setText(String.valueOf(produto.getPreco()));

            // Definir os atributos de layout para os TextViews
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, dpToPx(3), 0, 0);
            nomeProduto.setLayoutParams(layoutParams);
            precoUnidade.setLayoutParams(layoutParams);

            nomeProduto.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            precoUnidade.setBackgroundColor(getResources().getColor(android.R.color.transparent, getTheme()));

            nomeProduto.setGravity(Gravity.CENTER_HORIZONTAL);
            precoUnidade.setGravity(Gravity.CENTER_HORIZONTAL);

            nomeProduto.setTextAppearance(this, android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Large);
            precoUnidade.setTextAppearance(this, android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Large);

            // Adicionar as células à linha
            row.addView(nomeProduto);
            row.addView(precoUnidade);

            // Adicionar a linha à tabela
            tabelaProdutos.addView(row);
        }
    }
    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}