package com.example.bruno.randomgeneratorproyect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton btreload;
    private Button boton2, salir;
    private EditText archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        archivo = findViewById(R.id.editText);
        boton2 = findViewById(R.id.button2);
        salir = findViewById(R.id.button6);
        btreload = findViewById(R.id.imageButton);
        archivo.setEnabled(false);

        Intent intent = getIntent();
        String parametro = intent.getStringExtra("parametro");
        archivo.setText(parametro);

        if (!(archivo.getText().toString().isEmpty()) || (archivo.getText().toString().contains("/")))
        {
            String[] arraypath = archivo.getText().toString().split("/");
            int UltimoSting = arraypath.length;
            String nombre_archivo = arraypath[UltimoSting - 1];

            String[] parsename = nombre_archivo.split("\\.");
            String extension = parsename[1];

            if (!extension.equals("mp3"))
            {
                Toast.makeText(MainActivity.this,
                        "Debe seleccionar un archivo de audio, con el la exntesion .mp3",
                        Toast.LENGTH_LONG).show();
                removeDialog(1);
                archivo.setText("");
            }
        }

        btreload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerExtras(v, archivo.getText().toString());
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcryptoActivity(v, archivo.getText().toString());
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void btaduioActivity(View view) {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
        finish();
    }

    public void btcryptoActivity(View view, String s) {
        Intent intent = new Intent(this, CryptoActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }

    public void recogerExtras(View view, String s){
        Intent intent = new Intent(this, ExplorerActivity.class);
        intent.putExtra("parametro",s);
        intent.putExtra("padre",3);
        startActivity(intent);
        finish();
    }

}
