package com.example.bruno.randomgeneratorproyect;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.IOException;
import android.content.Intent;

public class AudioActivity extends AppCompatActivity {

    private ImageButton play, stop, record;
    private Button atras;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    private EditText fileText, nameText;

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


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombrearchivo;
                if (nameText.getText().toString().isEmpty()){
                    nombrearchivo = "audio";
                }
                else{
                    nombrearchivo = nameText.getText().toString();
                }

                outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + nombrearchivo + ".mp3";
                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    // make something ...
                } catch (IOException ioe) {
                    // make something
                }
                record.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Grabando audio", Toast.LENGTH_LONG).show();

            }
        });

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
                    // make something
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMainActivity(v, fileText.getText().toString());
            }
        });

    }

    private void btnMainActivity(View v, String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("parametro",s);
        startActivity(intent);
        finish();
    }
}
