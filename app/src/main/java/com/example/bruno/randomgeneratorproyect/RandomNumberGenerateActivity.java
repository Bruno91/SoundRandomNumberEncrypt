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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

public class RandomNumberGenerateActivity extends AppCompatActivity {

    private EditText archivo, cantidad, minimo, maximo, numeros;
    private Button atras, generar;
    private ImageButton enviar, copiar, buscar;
    private RadioButton entero, decimal;

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
        cantidad.setText("1");
        enviar.setEnabled(false);
        copiar.setEnabled(false);

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
                minimo.setText("0.00");
                maximo.setText("0.99");
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
            public void onClick(View v) {
                float valor_maximo = Float.valueOf(maximo.getText().toString());
                float valor_minimo = Float.valueOf(minimo.getText().toString());
                int valor_cantidad = Integer.parseInt(cantidad.getText().toString());
                int minima_cantidad = 0;

                if (valor_maximo < valor_minimo) {
                    minimo.setText("0");

                    Toast.makeText(RandomNumberGenerateActivity.this, "El valor minimo no puede ser mayor al maximo", Toast.LENGTH_LONG).show();
                    removeDialog(1);
                }
                else
                {
                    if (valor_cantidad <= minima_cantidad)
                    {
                        Toast.makeText(RandomNumberGenerateActivity.this, "La cantidad de numeros a generar debe ser mayor o igual a 1", Toast.LENGTH_LONG).show();
                        removeDialog(1);
                    }
                    else
                    {
                        String[] arraypath = archivo.getText().toString().split("/");
                        int UltimoSting = arraypath.length;
                        String nombre_archivo = arraypath[UltimoSting - 1];

                        String[] parsename = nombre_archivo.split("\\.");
                        String extension = parsename[1];

                        if (!extension.equals("mp3"))
                        {
                            Toast.makeText(RandomNumberGenerateActivity.this,
                                    "Debe seleccionar un archivo de audio, con el la exntesion .mp3",
                                    Toast.LENGTH_LONG).show();
                            removeDialog(1);
                        }
                        else
                        {
                            copiar.setEnabled(true);
                            enviar.setEnabled(true);

                            String outputFile = archivo.getText().toString();
                            File initialFile = new File(outputFile);
                            InputStream targetStream = null;
                            try {
                                targetStream = new FileInputStream(initialFile);
                            }
                            catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                            byte[] buffer = new byte[66442];
                            try {
                                buffer = inputStreamToByteArray(targetStream);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            if (decimal.isChecked()) {
                                float[] arrayfloat;
                                arrayfloat = byteArrayToFloat(buffer);
                                shuffleArray(arrayfloat);
                                float[] valores_float = new float[valor_cantidad];
                                int contador = 0;
                                int ciclo = 0;
                                do
                                {
                                    if (arrayfloat[ciclo] >= valor_minimo && arrayfloat[ciclo] <= valor_maximo)
                                    {
                                        valores_float[contador] = arrayfloat[ciclo];
                                        contador += 1;
                                    }
                                    ciclo += 1;
                                }
                                while (contador < valor_cantidad);
                                String string_valores = Arrays.toString(valores_float);
                                numeros.setText(string_valores);
                            }
                            else
                            {
                                if (entero.isChecked())
                                {
                                    int[] arrayInt;
                                    arrayInt = byteArrayToInt(buffer);
                                    shuffleArray(arrayInt);
                                    int[] valores_int = new int[valor_cantidad];
                                    int contador = 0;
                                    int ciclo = 0;
                                    do
                                    {
                                        int valor_minimo_int = (int)valor_minimo;
                                        int valor_maximo_int = (int)valor_maximo;
                                        if (arrayInt[ciclo] >= valor_minimo_int && arrayInt[ciclo] <= valor_maximo_int)
                                        {
                                            valores_int[contador] = arrayInt[ciclo];
                                            contador += 1;
                                        }
                                        ciclo += 1;
                                    }
                                    while (contador < valor_cantidad);
                                    String string_valores = Arrays.toString(valores_int);
                                    numeros.setText(string_valores);
                                }

                            }
                        }
                    }
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

    public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[66442];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public static float[] byteArrayToFloat(byte[] b)
    {
        int length = b.length;
        float[] value = new float[length];
        for (int i = 0; i < length; i++) {
            value[i] = ((float) b[i])/1000;
        }
        return value;
    }

    public static int[] byteArrayToInt(byte[] b)
    {
        int length = b.length;
        int[] value = new int[length];
        for (int i = 0; i < length; i++) {
            value[i] = ((int) b[i]);
        }
        return value;
    }

    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    static void shuffleArray(float[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            float a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
