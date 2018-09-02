package com.example.bruno.randomgeneratorproyect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class NumberGenerateActivity extends AppCompatActivity {

    private Button atras, generar;
    public EditText archivo, numeros, nombretxt;
    private ImageButton lupa, guardar;
    public static String NumerosAleatorios;

    static final int CUSTOM_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_generate);
        atras = findViewById(R.id.button9);
        lupa = findViewById(R.id.imageButton2);
        guardar = findViewById(R.id.imageButton3);
        generar = findViewById(R.id.button8);
        archivo = findViewById(R.id.editText4);
        numeros = findViewById(R.id.editText5);
        nombretxt = findViewById(R.id.editText7);
        archivo.setEnabled(false);
        numeros.setEnabled(false);

        Intent intent = getIntent();
        String path = intent.getStringExtra("parametro");

        archivo.setText(path);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCryptoActivity(v, archivo.getText().toString());
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] arraypath = archivo.getText().toString().split("/");
                int UltimoSting = arraypath.length;
                String nombre_archivo = arraypath[UltimoSting - 1];

                String[] parsename = nombre_archivo.split("\\.");
                String extension = parsename[1];

                if (!extension.equals("mp3"))
                {
                    Toast.makeText(NumberGenerateActivity.this,
                            "Debe seleccionar un archivo de audio, con el la exntesion .mp3",
                            Toast.LENGTH_LONG).show();
                    removeDialog(1);
                }
                else {
                    String outputFile = archivo.getText().toString();
                    File initialFile = new File(outputFile);
                    InputStream targetStream = null;
                    try {
                        targetStream = new FileInputStream(initialFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] buffer = new byte[66442];
                    try {
                        buffer = inputStreamToByteArray(targetStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    float[] arrayfloat;
                    arrayfloat = byteArrayToInt(buffer);
                    String valores = Arrays.toString(arrayfloat);
                    numeros.setText(valores);
                }
            }
        });

        lupa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnExplorer(v, archivo.getText().toString());
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                String nombrearchivo;
                if (nombretxt.getText().toString().isEmpty()){
                    nombrearchivo = "default";
                }
                else{
                    nombrearchivo = nombretxt.getText().toString();
                }

                NumerosAleatorios = numeros.getText().toString();
                String NombreArchivoAudio = archivo.getText().toString();

                try
                {
                    File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "number_" + nombrearchivo + ".txt");

                    OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));

                    fout.write(NombreArchivoAudio + " / "+ NumerosAleatorios);
                    fout.close();
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
                    Toast.makeText(NumberGenerateActivity.this,
                            "Error al tratar de guardar el archivo en memoria",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(CUSTOM_DIALOG_ID);
                }
            }
        });
    }

    public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[66442];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    private void btnCryptoActivity(View v, String s) {
        Intent intent = new Intent(this, CryptoActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }

    private void btnExplorer(View v, String s) {
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra("parametro",s);
        intent.putExtra("padre",1);
        startActivity(intent);
        finish();
    }

    public static float[] byteArrayToInt(byte[] b)
    {
        int length = b.length;
        float[] value = new float[length];
        for (int i = 0; i < length; i++) {
            value[i] = ((float) b[i])/1000;
        }
        return value;
    }

}