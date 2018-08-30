package com.example.bruno.randomgeneratorproyect;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {
    /*
     * Actividad encargada de generar los arhcivos de audio, la misma tiene la posibilidad
     * de generar archivos mp3
     * Creador: Denardi Bruno
     * Fecha:   12/04/2018
     */

    private ImageButton play, stop, record;
    private Button atras;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    private EditText fileText, nameText;

    /* Metodo que se ejecuta cuando se crea la actividad */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        record = findViewById(R.id.record);
        fileText = findViewById(R.id.editText2);
        nameText = findViewById(R.id.editText6);
        atras = findViewById(R.id.button5);
        stop.setEnabled(false);
        play.setEnabled(false);
        fileText.setEnabled(false);

        /* Metodo que se encarga de grabar audio cuando realizan click sobre el microfono verde */
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombrearchivo;
                if (nameText.getText().toString().isEmpty()){
                    nombrearchivo = "default";
                }
                else{
                    nombrearchivo = nameText.getText().toString();
                }

                outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "audio_" + nombrearchivo + ".mp3";
                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    Log.e("Ficheros", "Error al tratar de grabar el archivo de audio");
                    Toast.makeText(AudioActivity.this,
                            "Error al tratar de grabar el archivo de audio",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(1);
                } catch (IOException ioe) {
                    Log.e("Ficheros", "Error al tratar de grabar el archivo de audio");
                    Toast.makeText(AudioActivity.this,
                            "Error al tratar de grabar el archivo de audio",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(1);
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Grabando audio", Toast.LENGTH_LONG).show();

            }
        });

        /* Metodo encargado de frenar el grabado de audio cuando se presiona el boton || */
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                record.setEnabled(true);
                stop.setEnabled(false);
                fileText.setText(outputFile);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio grabado correctamente", Toast.LENGTH_LONG).show();
            }
        });

        /* Meotdo encargado de reproducir el audio grabado cuando se presiona el boton > */
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Reproduciendo audio grabado", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("Ficheros", "Error al tratar de reproducir el archivo de audio");
                    Toast.makeText(AudioActivity.this,
                            "Error al tratar de reproducir el archivo de audio",
                            Toast.LENGTH_LONG).show();
                    dismissDialog(1);
                }
            }
        });

        /* Metodo que se encarga de retornar a la activity anterior cuando se presiona el boton atras */
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMainActivity(v, fileText.getText().toString());
            }
        });

    }

    /* Metodo creado para manejar el movimiento a la clase main o incial desde el activity que graba sonido */
    private void btnMainActivity(View v, String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }
}
