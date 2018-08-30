package com.example.bruno.randomgeneratorproyect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CryptoActivity extends AppCompatActivity {

    private EditText patharchivo;
    private Button salir, generar, GenerarClave, EncriptarMensaje, DesencriptarMensaje, encriptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);
        patharchivo = findViewById(R.id.editText3);
        generar = findViewById(R.id.button4);
        salir = findViewById(R.id.button7);
        GenerarClave = findViewById(R.id.button21);
        EncriptarMensaje = findViewById(R.id.button22);
        DesencriptarMensaje = findViewById(R.id.button23);

        Intent intent = getIntent();
        final String archivo = intent.getStringExtra("parametro");

        patharchivo.setEnabled(false);
        patharchivo.setText(archivo);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMainActivity(v, patharchivo.getText().toString());
                finish();
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGenerarActivity(v, patharchivo.getText().toString());
                finish();
            }
        });

        GenerarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKeyGenerateActivity(v);
                finish();
            }
        });

        EncriptarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEncryptMessageActivity(v);
                finish();
            }
        });

        DesencriptarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDecryptMessageActivity(v);
                finish();
            }
        });
    }

    private void btnKeyGenerateActivity(View v) {
        Intent intent = new Intent(this, KeyGenerateActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnEncryptMessageActivity(View v) {
        Intent intent = new Intent(this, EncryptMessageActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnDecryptMessageActivity(View v) {
        Intent intent = new Intent(this, DecryptMessageActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnMainActivity(View v, String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }

    private void btnGenerarActivity(View v, String s) {
        Intent intent = new Intent(this, NumberGenerateActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }
}
