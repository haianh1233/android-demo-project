package com.haianh.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HelpActivity extends AppCompatActivity {
    Button btnWeb, btnCall, btnSend, btnCap, btnStart, btnStop;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);

        btnWeb = findViewById(R.id.btnWeb);
        btnCall = findViewById(R.id.btnCall);
        btnSend = findViewById(R.id.btnSend);
        btnCap = findViewById(R.id.btnCap);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnWeb.setOnClickListener(v -> {
            Uri web = Uri.parse("https://google.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, web);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }

        });
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0934756727"));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });
        btnSend.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:"));
            intent.putExtra("sms_body", "hello");
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        });
        btnCap.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, 1);
            }
        });

        btnStart.setOnClickListener(v -> {
            play();
        });

        btnStop.setOnClickListener(v -> {
            stopPlayer();
        });
    }

    private void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        mediaPlayer.start();
    }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}