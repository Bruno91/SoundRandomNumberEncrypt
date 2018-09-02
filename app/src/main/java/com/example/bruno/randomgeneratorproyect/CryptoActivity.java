package com.example.bruno.randomgeneratorproyect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CryptoActivity extends AppCompatActivity {
    /*
     * Actividad encargada de manejar el menu de opciones que se encuentran disponibles para usar
     * el audio, los numeros aleatorios y claves generadas con las diferentes funcionalidades
     * Creador: Denardi Bruno
     * Fecha:   28/03/2018
     */

    private EditText patharchivo;
    private Button salir, generar, GenerarClave, EncriptarMensaje, DesencriptarMensaje, numerosaleatorios;

    /* Metodo que se dispara cuando se crea el activity */
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
        numerosaleatorios = findViewById(R.id.button20);

        Intent intent = getIntent();
        final String archivo = intent.getStringExtra("parametro");

        patharchivo.setEnabled(false);
        patharchivo.setText(archivo);

        /* Metodo encargado de retornar a la activity previa cuando se presiona el boton atras*/
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMainActivity(v, patharchivo.getText().toString());
                finish();
            }
        });

        /* Metodo encargado de ir a la pantalla generar numeros aleatorios cuando se presiona el boton homonimo*/
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGenerarActivity(v, patharchivo.getText().toString());
                finish();
            }
        });

        /* Metodo encargado de ir a la pantalla generadora de claves cuando se presiona el boton generar claves*/
        GenerarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKeyGenerateActivity(v);
                finish();
            }
        });

        /*
        *  Meotodo encargado de redirigir a la pantalla encargada de encriptar mensajes de texto plano
        *  cuando se preiona el boton encriptar texto
        */
        EncriptarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEncryptMessageActivity(v);
                finish();
            }
        });

        /*
         *  Meotodo encargado de redirigir a la pantalla encargada de desencriptar mensajes de texto plano
         *  cuando se preiona el boton encriptar texto
         */
        DesencriptarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDecryptMessageActivity(v);
                finish();
            }
        });

        numerosaleatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRandomNumberGenerateActivity(v);
                finish();
            }
        });
    }

    /* Metodo creado para manejar el movimiento al activity correspondiente */
    private void btnKeyGenerateActivity(View v) {
        Intent intent = new Intent(this, KeyGenerateActivity.class);
        startActivity(intent);
        finish();
    }

    /* Metodo creado para manejar el movimiento al activity correspondiente */
    private void btnEncryptMessageActivity(View v) {
        Intent intent = new Intent(this, EncryptMessageActivity.class);
        startActivity(intent);
        finish();
    }

    /* Metodo creado para manejar el movimiento al activity correspondiente */
    private void btnDecryptMessageActivity(View v) {
        Intent intent = new Intent(this, DecryptMessageActivity.class);
        startActivity(intent);
        finish();
    }

    /* Metodo creado para manejar el movimiento al activity correspondiente */
    private void btnMainActivity(View v, String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }

    /* Metodo creado para manejar el movimiento al activity correspondiente */
    private void btnGenerarActivity(View v, String s) {
        Intent intent = new Intent(this, NumberGenerateActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }

    private void btnRandomNumberGenerateActivity(View v) {
        Intent intent = new Intent(this, RandomNumberGenerateActivity.class);
        startActivity(intent);
        finish();
    }
}
