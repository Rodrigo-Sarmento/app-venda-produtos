package com.digo.vendas_de_produtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class VendaActivity extends AppCompatActivity {
    private ImageView ivVoltarVenda;
    private Button btnAumentarQtd;
    private Button btnDiminuirQtd;
    private EditText qtdProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        ivVoltarVenda = findViewById(R.id.imageView6);
        btnAumentarQtd = findViewById(R.id.aumentarQTD);
        btnDiminuirQtd = findViewById(R.id.diminuirQTD);
        qtdProduto = findViewById(R.id.entQtdProduto);

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