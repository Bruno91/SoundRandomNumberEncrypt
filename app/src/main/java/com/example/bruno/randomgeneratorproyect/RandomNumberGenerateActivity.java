package com.example.bruno.randomgeneratorproyect;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RandomNumberGenerateActivity extends AppCompatActivity {

    private EditText archivo, cantidad, minimo, maximo, numeros;
    private Button atras, generar;
    private ImageButton enviar, copiar, buscar;
    private RadioButton entero, decimal;
    private RadioGroup grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_number_generate);
        atras = findViewById(R.id.button11);
        generar = findViewById(R.id.button3);
        enviar = findViewById(R.id.imageButton13);
        buscar = findViewById(R.id.imageButton12);
        copiar = findViewById(R.id.imageButton17);
        entero = findViewById(R.id.radioButton);
        decimal = findViewById(R.id.radioButton2);
        grupo = findViewById(R.id.radioGroup);
        archivo = findViewById(R.id.editText11);
        cantidad = findViewById(R.id.editText15);
        minimo = findViewById(R.id.editText17);
        maximo = findViewById(R.id.editText21);
        numeros = findViewById(R.id.editText22);

        int editar;
        Intent intent = getIntent();
        editar = intent.getIntExtra("edicion", 0);
        archivo.setEnabled(false);
        cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
        minimo.setInputType(InputType.TYPE_CLASS_NUMBER);
        maximo.setInputType(InputType.TYPE_CLASS_NUMBER);
        entero.setChecked(true);
        decimal.setChecked(false);
        maximo.setText("0");
        minimo.setText("0");

        if (editar == 0) {
            generar.setEnabled(false);
            enviar.setEnabled(false);
            copiar.setEnabled(false);
            entero.setEnabled(false);
            decimal.setEnabled(false);
            cantidad.setEnabled(false);
            minimo.setEnabled(false);
            maximo.setEnabled(false);
            numeros.setEnabled(false);
        }
        else{
            generar.setEnabled(true);
            entero.setEnabled(true);
            decimal.setEnabled(true);
            numeros.setEnabled(true);
            String path = intent.getStringExtra("archivo_key");
            archivo.setText(path);
        }

        entero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                entero.setChecked(true);
                decimal.setChecked(false);
                cantidad.setEnabled(true);
                minimo.setEnabled(true);
                maximo.setEnabled(true);
                minimo.setText(null);
                maximo.setText(null);
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                minimo.setInputType(InputType.TYPE_CLASS_NUMBER);
                maximo.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        });

        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                entero.setChecked(false);
                decimal.setChecked(true);
                cantidad.setEnabled(true);
                minimo.setEnabled(true);
                maximo.setEnabled(true);
                minimo.setText(null);
                maximo.setText(null);
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                minimo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                maximo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                btnCryptoActivity(v);
                finish();
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                float valor_maximo = Float.intBitsToFloat(Integer.parseInt(maximo.getText().toString()));
                float valor_minimo = Float.intBitsToFloat(Integer.parseInt(minimo.getText().toString()));

                if (valor_maximo < valor_minimo)
                {
                    minimo.setText("0");

                    Toast.makeText(RandomNumberGenerateActivity.this, "El valor minimo no puede ser mayor al maximo", Toast.LENGTH_LONG).show();
                    removeDialog(1);
                }
                else
                {

                }
            }
        });

        copiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = numeros.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  text);
                clipboard.setPrimaryClip(clip);
            }
        });

        enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String textonumeros = numeros.getText().toString();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textonumeros);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnExplorer(v, archivo.getText().toString());
                finish();
            }
        });
    }

    private void btnCryptoActivity(View v) {
        Intent intent = new Intent(this, CryptoActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnExplorer(View v, String s) {
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra("parametro",s);
        intent.putExtra("padre",7);
        startActivity(intent);
        finish();
    }
}
