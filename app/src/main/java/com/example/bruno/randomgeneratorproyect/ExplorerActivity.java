package com.example.bruno.randomgeneratorproyect;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ExplorerActivity extends AppCompatActivity {
    /*
    * Actividad encargada de crear el explorador de archivos.
    * Creador: Denardi Bruno
    * Fecha:   26/03/2018
    */
       Button buttonUp, buttonOpenDialog, atras;
       TextView textFolder;
       public EditText pathArchivo;
       public Integer padre;

        static final int CUSTOM_DIALOG_ID = 0;

        ListView dialog_ListView;

        File root;
        File curFolder;

        private List<String> fileList = new ArrayList<String>();

        /* Metodo que se ejecuta cuando se crea la actividad*/
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_explorer);
            atras = findViewById(R.id.button10);
            pathArchivo = findViewById(R.id.editText4);
            buttonOpenDialog = findViewById(R.id.opendialog);
            pathArchivo.setEnabled(false);
            Intent intent = getIntent();

            /* Guarda el activity padre del que proviene */
            padre = intent.getIntExtra("padre", 0);

            /* Meotodo que se dispara cuando se preciona el boton de abrir carpeta */
            buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    //noinspection deprecation
                    showDialog(CUSTOM_DIALOG_ID);
                }});

            /* Variable encargada de llevar el archivo que se encuentra en el path inicial*/
            root = new File(Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath());

            /* Directorio raiz */
            curFolder = root;

            /* Meotodo que se dispara con click sobre el boton Atras */
            atras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnNumberGenerator(v, pathArchivo.getText().toString());
                }
            });

        }

        /* Metodo Dialog, la misma crear un activity donde se mostraran el listado de carpetas y archivos del sistema */
        @SuppressWarnings("deprecation")
        @Override
        protected Dialog onCreateDialog(int id) {

            Dialog dialog = null;

            switch(id) {
                case CUSTOM_DIALOG_ID:
                    dialog = new Dialog( ExplorerActivity.this);
                    dialog.setContentView(R.layout.dialoglayout);
                    dialog.setTitle("carpetas");

                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);

                    textFolder = dialog.findViewById(R.id.folder);

                    buttonUp = dialog.findViewById(R.id.up);
                    buttonUp.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            ListDir(curFolder.getParentFile());
                        }});

                    //Inserta la vista dialog
                    dialog_ListView = dialog.findViewById(R.id.dialoglist);

                    dialog_ListView.setOnItemClickListener(new OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            File selected = new File(fileList.get(position));
                            if(selected.isDirectory()){
                                ListDir(selected);
                            }else {
                                Toast.makeText(ExplorerActivity.this,
                                        selected.toString() + " seleccionado",
                                        Toast.LENGTH_LONG).show();
                                dismissDialog(CUSTOM_DIALOG_ID);
                            }

                            pathArchivo.setText(selected.toString());

                        }});

                    break;
            }

            return dialog;
        }

    /* Metodo utilizada para agregar conteido al activity que muestra el listado de carpetas y archivos */
    @SuppressWarnings("deprecation")
    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
            // TODO Auto-generated method stub
            super.onPrepareDialog(id, dialog, bundle);

            switch(id) {
                case CUSTOM_DIALOG_ID:
                    ListDir(curFolder);
                    break;
            }

        }

        void ListDir(File f){

            if(f.equals(root)){
                buttonUp.setEnabled(false);
            }else{
                buttonUp.setEnabled(true);
            }

            curFolder = f;
            textFolder.setText(f.getPath());

            File[] files = f.listFiles();
            fileList.clear();
            for (File file : files){
                fileList.add(file.getPath());
            }

            ArrayAdapter<String> directoryList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
            dialog_ListView.setAdapter(directoryList);
        }

    /* Metodo encargado de manejar el retorno a las diferentes activities y su parametros de salida entre activities  */
    private void btnNumberGenerator(View v, String s) {
        Intent intent = null;
        if (padre == 1) { //Activity generar numeros
            intent = new Intent(this, NumberGenerateActivity.class);
        }else{
            if (padre == 2) { //Activity generar clave
                intent = new Intent(this, KeyGenerateActivity.class);
                intent.putExtra("edicion", 1); // Habilitado para leer clave existe desde la generacion de claves
            }else{
                if (padre == 3) { //Activity principal o inicial
                    intent = new Intent(this, MainActivity.class);
                }
                else{
                    if (padre == 4) { //Activity generar clave
                        intent = new Intent(this, KeyGenerateActivity.class);
                        intent.putExtra("edicion", 2); // Habilitado para generar nueva clave en la generacion de claves
                        intent.putExtra("archivo_key", s);
                    }
                    else{
                        if (padre == 5) { //Activity encriptar mensaje
                            intent = new Intent(this, EncryptMessageActivity.class);
                            intent.putExtra("edicion", 1);
                            intent.putExtra("archivo_key", s);
                        }
                        else{
                            if (padre == 6) { //Activity desencriptar mensaje
                                intent = new Intent(this, DecryptMessageActivity.class);
                                intent.putExtra("edicion", 1);
                                intent.putExtra("archivo_key", s);
                            }
                        }
                    }
                }
            }
        }
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }
}

