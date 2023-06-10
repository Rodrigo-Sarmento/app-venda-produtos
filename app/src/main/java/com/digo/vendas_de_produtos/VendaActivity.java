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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VendaActivity extends AppCompatActivity {
    private ImageView ivVoltarVenda;
    private Button btnAumentarQtd;
    private Button btnDiminuirQtd;
    private Button btnZerarVenda;
    private EditText qtdProduto;
    private AutoCompleteTextView nomeProduto;
    private AutoCompleteTextView nomeCliente;
    private BancoDeDados db;
    private ImageView btnAdicionar;
    private TextView txtListaNome;
    private  TextView txtListaQTD;
    private TextView txtListaPrecoQTD;
    private TextView txtTotalCompra;
    private TextView txtPrecoProduto;
    private TextView txtSaldoCliente;
    private ImageView ivFinalizarVenda;
    double precoTotal = 0.0;

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
        btnAdicionar = findViewById(R.id.imageView3);
        txtListaNome = findViewById(R.id.txtListaNome);
        txtListaQTD = findViewById(R.id.txtListaQTD);
        txtListaPrecoQTD = findViewById(R.id.txtListaPrecoQTD);
        txtTotalCompra = findViewById(R.id.txtTotalCompra);
        btnZerarVenda = findViewById(R.id.btnZerarVenda);
        txtPrecoProduto = findViewById(R.id.txtPrecoProduto);
        txtSaldoCliente = findViewById(R.id.txtSaldoCliente);
        ivFinalizarVenda = findViewById(R.id.imageView5);


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
                        txtSaldoCliente.setText("CLIENTE: R$"+cliente.getCredito());
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
                        txtPrecoProduto.setText("R$: "+produto.getPreco());
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

        ivFinalizarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "VENDA REALIZADA COM SUCESSO!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VendaActivity.class);
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

        btnZerarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VendaActivity.class);
                startActivity(intent);
            }

        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeProduto.getText().toString();

                // Verifica se um nome de produto válido foi digitado
                if (nome.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Digite um nome de produto válido",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Procura o produto no banco de dados pelo nome
                Produto produto = db.getProdutoByNome(nome);

                // Verifica se o produto foi encontrado
                if (produto == null) {
                    Toast.makeText(getApplicationContext(), "Produto não encontrado",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String nomeProduto = produto.getNomeProduto();
                double precoProduto = produto.getPreco();
                double qtProdutos = Double.parseDouble(qtdProduto.getText().toString());
                double precoxQtd = precoProduto * qtProdutos;
                precoTotal += precoxQtd;

                txtTotalCompra.setText("TOTAL: R$"+precoTotal);

                String listPrecos = precoxQtd + "\n";
                txtListaPrecoQTD.append(listPrecos);


                String nomeProdutosAtuais = txtListaNome.getText().toString();
                String novoNomeProdutos = nomeProdutosAtuais + "\n" + nomeProduto;
                txtListaNome.setText(novoNomeProdutos);

                String qtProduto = qtdProduto.getText().toString();
                String novoQtProdutos = qtProduto + "\n";
                txtListaQTD.append(novoQtProdutos);

                // Use o nome e o preço do produto conforme necessário
                // Exemplo: exiba-os em um Toast
                Toast.makeText(getApplicationContext(), "Produto adicionado com sucesso! Nome: " + nomeProduto + ", Preço: " + precoProduto,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}