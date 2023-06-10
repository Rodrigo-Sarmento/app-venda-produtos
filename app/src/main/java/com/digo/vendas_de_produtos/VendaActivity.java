package com.digo.vendas_de_produtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class VendaActivity extends AppCompatActivity {
    private ImageView ivVoltarVenda;
    private Button btnAumentarQtd;
    private Button btnDiminuirQtd;
    private EditText qtdProduto;
    private AutoCompleteTextView nomeProduto;
    private AutoCompleteTextView nomeCliente;
    private BancoDeDados db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);
        this.db = BancoDeDados.Sharedinstance(this);

        ivVoltarVenda = findViewById(R.id.imageView6);
        btnAumentarQtd = findViewById(R.id.aumentarQTD);
        btnDiminuirQtd = findViewById(R.id.diminuirQTD);
        qtdProduto = findViewById(R.id.entQtdProduto);
        nomeProduto = findViewById(R.id.entNomeProduto);
        nomeCliente = findViewById(R.id.txtNomeComprador);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        nomeProduto.setAdapter(adapter);
        nomeCliente.setAdapter(adapter);
        nomeCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString().trim();

                // Faça a busca no banco de dados apenas se o texto digitado for maior que 0
                if (input.length() > 0) {
                    List<Cliente> clientes = db.searchClientes(input);

                    List<String> nomesClientes = new ArrayList<>();
                    for (Cliente cliente : clientes) {
                        nomesClientes.add(cliente.getNomeCliente());
                    }

                    // Limpe o adaptador antes de adicionar as novas sugestões
                    adapter.clear();

                    // Adicione as sugestões ao adaptador
                    adapter.addAll(nomesClientes);

                    // Notifique o adaptador de que os dados foram atualizados
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //consultando o nome dos produtos enquanto o usuário digita
        nomeProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString().trim();

                // Faça a busca no banco de dados apenas se o texto digitado for maior que 0
                if (input.length() > 0) {
                    List<Produto> produtos = db.searchProdutos(input);

                    List<String> nomesProdutos = new ArrayList<>();
                    for (Produto produto : produtos) {
                        nomesProdutos.add(produto.getNomeProduto());
                    }

                    // Limpe o adaptador antes de adicionar as novas sugestões
                    adapter.clear();

                    // Adicione as sugestões ao adaptador
                    adapter.addAll(nomesProdutos);

                    // Notifique o adaptador de que os dados foram atualizados
                    adapter.notifyDataSetChanged();
                }
            }



            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ivVoltarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });

        btnAumentarQtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = Integer.parseInt(qtdProduto.getText().toString());
                qtd ++;
                qtdProduto.setText(String.valueOf(qtd));
            }

        });

        btnDiminuirQtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = Integer.parseInt(qtdProduto.getText().toString());
                qtd --;
                qtdProduto.setText(String.valueOf(qtd));
            }

        });
    }
}