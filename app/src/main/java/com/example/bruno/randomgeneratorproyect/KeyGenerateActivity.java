package com.example.bruno.randomgeneratorproyect;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class KeyGenerateActivity extends AppCompatActivity {

    public Button Atras, BuscarClave, GenerarClave;
    public ImageButton Ver, Guardar, Copiar, BuscarNumero;
    public EditText archivo, clave, archivo_numero;
    public Integer editar;

    static final int CUSTOM_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_generate);
        Atras = findViewById(R.id.button16);
        BuscarClave = findViewById(R.id.button15);
        GenerarClave = findViewById(R.id.button17);
        Ver = findViewById(R.id.imageButton5);
        Guardar = findViewById(R.id.imageButton4);
        Copiar = findViewById(R.id.imageButton6);
        BuscarNumero = findViewById(R.id.imageButton7);
        archivo = findViewById(R.id.editText8);
        archivo_numero = findViewById(R.id.editText9);
        clave = findViewById(R.id.editText10);

        Intent intent = getIntent();
        editar = intent.getIntExtra("edicion", 0);

        Ver.setEnabled(false);
        Guardar.setEnabled(false);
        Copiar.setEnabled(false);
        BuscarNumero.setVisibility(View.INVISIBLE);
        archivo.setEnabled(false);
        archivo_numero.setEnabled(false);
        clave.setEnabled(false);

        if (editar == 1){
            Ver.setEnabled(true);
            Copiar.setEnabled(true);
            archivo.setEnabled(false);
            GenerarClave.setEnabled(false);
            BuscarNumero.setEnabled(false);
            archivo_numero.setEnabled(false);
            Guardar.setVisibility(View.INVISIBLE);
            String path = intent.getStringExtra("parametro");
            archivo.setText(path);
        }
        else{
            if (editar == 2){
                BuscarClave.setEnabled(false);
                archivo.setEnabled(true);
                archivo_numero.setEnabled(true);
                BuscarNumero.setEnabled(false);
                Ver.setVisibility(View.INVISIBLE);
                String path_numeros = intent.getStringExtra("archivo_key");
                archivo_numero.setText(path_numeros);
                Guardar.setEnabled(true);
            }
        }

        clave.setEnabled(false);

        BuscarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ver.setEnabled(true);
                Copiar.setEnabled(true);
                archivo.setEnabled(false);
                GenerarClave.setEnabled(false);
                BuscarNumero.setEnabled(false);
                archivo_numero.setEnabled(false);
                Guardar.setVisibility(View.INVISIBLE);
                btnExplorer(v, archivo.getText().toString());
                finish();
            }
        });

        GenerarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarClave.setEnabled(false);
                archivo.setEnabled(true);
                archivo_numero.setEnabled(true);
                BuscarNumero.setVisibility(View.VISIBLE);
                Ver.setVisibility(View.INVISIBLE);
            }
        });


        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                btnEncryptActivity(v);
                finish();
            }
        });

        Ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String NombreArchivo = archivo.getText().toString();

                if (NombreArchivo != null && !NombreArchivo.isEmpty()) {

                    String[] arraypath = NombreArchivo.split("/");
                    int UltimoSting = arraypath.length;
                    String nombre_archivo = arraypath[UltimoSting - 1];

                    String[] parsename = nombre_archivo.split("_");
                    String sufijo = parsename[0];

                    if (!sufijo.equals("clave")) {
                        Toast.makeText(KeyGenerateActivity.this,
                                "Debe seleccionar un archivo de clave, con el sufijo clave_",
                                Toast.LENGTH_LONG).show();
                        dismissDialog(CUSTOM_DIALOG_ID);
                    } else {
                        Copiar.setEnabled(true);
                        String NombreArchivoKey = archivo.getText().toString();

                        try {
                            File f = new File(NombreArchivoKey);

                            BufferedReader fin = null;
                            fin = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                            String texto = fin.readLine();
                            String[] arraytexto = texto.split(" / ");
                            int Ultimo = arraytexto.length;
                            String key = arraytexto[Ultimo - 1];

                            clave.setText(key);

                        }catch (Exception ex) {
                            Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                            Toast.makeText(KeyGenerateActivity.this,
                                    "Error al leer el archivo de clave en la memoria",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }

                    }
                }else {
                    Toast.makeText(KeyGenerateActivity.this,
                            "Debe seleccionar un archivo de clave, con el sufijo clave_",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(CUSTOM_DIALOG_ID);
                }
            }
        });

        BuscarNumero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnExplorer2(v, archivo.getText().toString());
                finish();
            }
        });

        Guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String NombreArchivoNumero = archivo_numero.getText().toString();

                if (NombreArchivoNumero != null && !NombreArchivoNumero.isEmpty()) {

                    String[] arraypath = NombreArchivoNumero.split("/");
                    int UltimoSting = arraypath.length;
                    String nombre_archivo = arraypath[UltimoSting-1];

                    String[] parsename = nombre_archivo.split("_");
                    String sufijo = parsename[0];

                    if (!sufijo.equals("number")){
                        Toast.makeText(KeyGenerateActivity.this,
                                "Debe seleccionar un archivo de numeros aleatorios, con el sufijo number_",
                                Toast.LENGTH_LONG).show();
                        dismissDialog(CUSTOM_DIALOG_ID);
                    }else {

                        Copiar.setEnabled(true);

                        String NombreArchivoKey;
                        if (archivo.getText().toString().isEmpty()) {
                            NombreArchivoKey = "default";
                        } else {
                            NombreArchivoKey = archivo.getText().toString();
                        }

                        byte[] key = GenerarClave(NombreArchivoNumero);

                        try {
                            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "clave_" + NombreArchivoKey + ".txt");

                            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));

                            fout.write(NombreArchivoNumero + " / ");

                            String clave_string = "";

                            for(int i = 0; i <= key.length-1; ++i) {
                                if (i == key.length-1) {
                                    clave_string = clave_string + String.valueOf(key[i]);
                                }else{
                                    clave_string = clave_string + String.valueOf(key[i] + ", ");
                                }
                            }
                            fout.write(clave_string);
                            fout.close();

                            clave.setText(clave_string);

                        } catch (Exception ex) {
                            Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
                            Toast.makeText(KeyGenerateActivity.this,
                                    "Error al grabar el archivo de clave en la memoria",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                }
                else {
                    Toast.makeText(KeyGenerateActivity.this,
                            "Debe seleccionar un archivo de numeros aleatorios, con el sufijo number_",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(CUSTOM_DIALOG_ID);
                }
            }
        });

        Copiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = clave.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  text);
                clipboard.setPrimaryClip(clip);
            }
        });
    }

    private void btnEncryptActivity(View v) {
        Intent intent = new Intent(this, CryptoActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnExplorer(View v, String s) {
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra("parametro",s);
        intent.putExtra("padre",2);
        startActivity(intent);
        finish();
    }

    private void btnExplorer2(View v, String s) {
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra("parametro",s);
        intent.putExtra("padre",4);
        startActivity(intent);
        finish();
    }

    private byte[] GenerarClave(String s){
        byte[] final_key = null;
        try
        {
            File f = new File(s);

            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String texto = fin.readLine();
            String[] numeros_string = texto.split(" / ");
            int UltimoSting = numeros_string.length;
            String numeros_sin_parsear = numeros_string[UltimoSting-1];

            String numeros_sin_corchetes = numeros_sin_parsear.substring(1, numeros_sin_parsear.length()-2);

            byte[] keyStart = numeros_sin_corchetes.getBytes();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for(int i = 0; i < 16; ++i) {
                Random aleatorio = new Random(System.currentTimeMillis());
                int intAletorio = aleatorio.nextInt(keyStart.length - 1);
                out.write(keyStart[intAletorio]);
            }
            final_key = out.toByteArray();
            fin.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
            Toast.makeText(KeyGenerateActivity.this,
                    "Error al leer el archivo de clave en la memoria",
                    Toast.LENGTH_LONG).show();
            dismissDialog(CUSTOM_DIALOG_ID);
        }
        return final_key;
    }
}
