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

       Button buttonUp, buttonOpenDialog, atras;
       TextView textFolder;
       public EditText pathArchivo;
       public Integer padre;

        static final int CUSTOM_DIALOG_ID = 0;

        ListView dialog_ListView;

        File root;
        File curFolder;

        private List<String> fileList = new ArrayList<String>();

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_explorer);
            atras = findViewById(R.id.button10);
            pathArchivo = findViewById(R.id.editText4);
            buttonOpenDialog = findViewById(R.id.opendialog);
            pathArchivo.setEnabled(false);
            Intent intent = getIntent();
            padre = intent.getIntExtra("padre", 0);

            buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    //noinspection deprecation
                    showDialog(CUSTOM_DIALOG_ID);
                }});

            root = new File(Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath());

            curFolder = root;

            atras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnNumberGenerator(v, pathArchivo.getText().toString());
                }
            });

        }

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

                    //Prepare ListView in dialog
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

    private void btnNumberGenerator(View v, String s) {
        Intent intent = null;
        if (padre == 1) {
            intent = new Intent(this, NumberGenerateActivity.class);
        }else{
            if (padre == 2) {
                intent = new Intent(this, KeyGenerateActivity.class);
                intent.putExtra("edicion", 1);
            }else{
                if (padre == 3) {
                    intent = new Intent(this, MainActivity.class);
                }
                else{
                    if (padre == 4) {
                        intent = new Intent(this, KeyGenerateActivity.class);
                        intent.putExtra("edicion", 2);
                        intent.putExtra("archivo_key", s);
                    }
                    else{
                        if (padre == 5) {
                            intent = new Intent(this, EncryptMessageActivity.class);
                            intent.putExtra("edicion", 1);
                            intent.putExtra("archivo_key", s);
                        }
                        else{
                            if (padre == 6) {
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

