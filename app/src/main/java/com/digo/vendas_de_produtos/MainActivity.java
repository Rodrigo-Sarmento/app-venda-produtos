package com.digo.vendas_de_produtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btCliente;
    private Button btProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        btCliente = findViewById(R.id.btCliente);
        btProduto = findViewById(R.id.btProduto);

        btCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClienteActivity.class);
                startActivity(intent);
            }
        });

        btProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                startActivity(intent);
            }
        });
    }
}