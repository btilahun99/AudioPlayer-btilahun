package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button buttonPlay;
    Button buttonPause;
    String audioUrl;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPause = findViewById(R.id.buttonPause);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("url");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                audioUrl = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get audio url.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(audioUrl);
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaPlayer mediaPlayer = null;

                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();

                    Toast.makeText(MainActivity.this, "Audio has been paused", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity.this, "Audio has not played", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void playAudio(String audioUrl){

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();

            Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();
        }
    }
}