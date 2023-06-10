package com.digo.vendas_de_produtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ClienteActivity extends AppCompatActivity {
    private TextView etNomeCliente;
    private TextView etNomeResponsavel;
    private TextView etTelefoneResponsavel;
    private TextView etCredito;

    private ImageView ivAddCliente;
    private ImageView ivVoltar;


    private BancoDeDados db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_clientes);

        etNomeCliente = findViewById(R.id.entNomeCliente);
        etNomeResponsavel = findViewById(R.id.entNomeResponsavel);
        etTelefoneResponsavel = findViewById(R.id.entTelefoneResponsavel);
        etCredito = findViewById(R.id.entCreditos);
        this.db = BancoDeDados.Sharedinstance(this);

        ivAddCliente = findViewById(R.id.imageViewAddCliente);
        ivVoltar = findViewById(R.id.imageViewVoltarCliente);

        ivAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadCliente(v);
            }

        });

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });

    }

    public void CadCliente(View view){
        if(etNomeCliente.getText().toString().equals("") || etNomeResponsavel.getText().toString().equals("") || etCredito.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Preencha todos os campos com *",
                    Toast.LENGTH_LONG).show();
        }else{
            Cliente cliente = new Cliente();
            cliente.setCredito(Double.parseDouble(etCredito.getText().toString()));
            cliente.setNomeCliente(etNomeCliente.getText().toString());
            cliente.setNomeResponsavel(etNomeResponsavel.getText().toString());
            cliente.setTelefoneResponsavel(etTelefoneResponsavel.getText().toString());

            db.insertCliente(cliente);
            Toast.makeText(getApplicationContext(),"Cadastrado com Sucesso o cliente: "+cliente.nomeCliente ,
                    Toast.LENGTH_LONG).show();
        }

    }

}