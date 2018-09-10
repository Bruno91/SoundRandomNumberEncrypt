package com.example.bruno.randomgeneratorproyect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.io.InputStreamReader;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptMessageActivity extends AppCompatActivity {

    public Button Atras;
    public ImageButton Enviar, Copiar, VerClave, BuscarClave;
    public EditText clave, mensaje, archivo;
    public Integer editar;

    static final int CUSTOM_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_message);
        Atras = findViewById(R.id.button18);
        Enviar = findViewById(R.id.imageButton8);
        Copiar = findViewById(R.id.imageButton9);
        BuscarClave = findViewById(R.id.imageButton11);
        VerClave = findViewById(R.id.imageButton10);
        mensaje = findViewById(R.id.editText13);
        clave = findViewById(R.id.editText12);
        archivo = findViewById(R.id.editText14);

        VerClave.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String path = intent.getStringExtra("archivo_key");
        editar = intent.getIntExtra("edicion", 0);
        archivo.setText(path);

        if (editar == 1){
            VerClave.setVisibility(View.VISIBLE);
        }

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                btnEncryptActivity(v);
                finish();
            }
        });

        BuscarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                btnExplorer(v, archivo.getText().toString());
                finish();
                VerClave.setVisibility(View.VISIBLE);
            }
        });

        VerClave.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(EncryptMessageActivity.this,
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
                            Toast.makeText(EncryptMessageActivity.this,
                                    "Error al leer el archivo de clave en la memoria",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }

                    }
                }else {
                    Toast.makeText(EncryptMessageActivity.this,
                            "Debe seleccionar un archivo de clave, con el sufijo clave_",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(CUSTOM_DIALOG_ID);
                }
            }
        });

        Enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String mensaje_encriptado = "";
                byte[] mensaje_byte = decode(mensaje.getText().toString());

                String clave_string = clave.getText().toString();
                String[] arraytexto = clave_string.split(", ");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                for(int i = 0; i <= arraytexto.length-1; ++i) {
                    byte b = Byte.valueOf(arraytexto[i]);
                    out.write(b);
                }
                byte[] final_key = out.toByteArray();


                byte[] ivBytes = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

                try {
                    mensaje_encriptado = encrypt(ivBytes, final_key, mensaje_byte);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mensaje_encriptado);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

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
        intent.putExtra("padre",5);
        startActivity(intent);
        finish();
    }

    public String encrypt(byte[] ivBytes, byte[] keyBytes, byte[] bytes) throws Exception{
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        byte[] encValue = cipher.doFinal(bytes);
        return encode(encValue);
    }

     public String encode(byte[] data)
     {
         char[] tbl = {
                 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
                 'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
                 'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
                 'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/',
                 '@', '#', '$', '%', '^', '&', '*', '!', ';', ':', '{', '}', '[',
                 ']', '|', '"', '?', '-', '_', ',', '.', '<', '>', '=' , 'Ñ', 'ñ',
                 'á', 'é', 'í', 'ó', 'ú', 'Á', 'É', 'Í', 'Ó', 'Ú', 'Ü', '(', ')',
                 ' '};

         StringBuilder buffer = new StringBuilder();
         int pad = 0;
         for (int i = 0; i < data.length; i += 3) {

             int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
             if (i + 1 < data.length) {
                 b |= (data[i+1] & 0xFF) << 8;
             } else {
                 pad++;
             }
             if (i + 2 < data.length) {
                 b |= (data[i+2] & 0xFF);
             } else {
                 pad++;
             }

             for (int j = 0; j < 4 - pad; j++) {
                 int c = (b & 0xFC0000) >> 18;
                 buffer.append(tbl[c]);
                 b <<= 6;
             }
         }
         for (int j = 0; j < pad; j++) {
             buffer.append("=");
         }

         return buffer.toString();
     }

     public byte[] decode(String data)
     {
         int[] tbl = {
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54,
                 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2,
                 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30,
                 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
                 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
         byte[] bytes = data.getBytes();
         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
         for (int i = 0; i < bytes.length; ) {
             int b = 0;
             if (tbl[bytes[i]] != -1) {
                 b = (tbl[bytes[i]] & 0xFF) << 18;
             }
             // skip unknown characters
             else {
                 i++;
                 continue;
             }

             int num = 0;
             if (i + 1 < bytes.length && tbl[bytes[i+1]] != -1) {
                 b = b | ((tbl[bytes[i+1]] & 0xFF) << 12);
                 num++;
             }
             if (i + 2 < bytes.length && tbl[bytes[i+2]] != -1) {
                 b = b | ((tbl[bytes[i+2]] & 0xFF) << 6);
                 num++;
             }
             if (i + 3 < bytes.length && tbl[bytes[i+3]] != -1) {
                 b = b | (tbl[bytes[i+3]] & 0xFF);
                 num++;
             }

             while (num > 0) {
                 int c = (b & 0xFF0000) >> 16;
                 buffer.write((char)c);
                 b <<= 8;
                 num--;
             }
             i += 4;
         }
         return buffer.toByteArray();
     }

}
