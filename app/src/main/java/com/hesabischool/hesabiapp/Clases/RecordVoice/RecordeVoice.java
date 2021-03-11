package com.hesabischool.hesabiapp.Clases.RecordVoice;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hesabischool.hesabiapp.Interfasces.callForCheange;
import com.hesabischool.hesabiapp.R;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RecordeVoice {
    Recorder recorder;
    ImageView recordButton;
    boolean click=false;
callForCheange call;
    public RecordeVoice(ImageView recordButton,callForCheange callForCheange) {
        this.recordButton = recordButton;
        call=callForCheange;
        setupRecorder();
    }
    public void run()
    {

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!click)
                {//Start
                    recordButton.setImageResource(R.drawable.ic_microphone_red);
               recorder.startRecording();
                }
                else
                {
                    recordButton.setImageResource(R.drawable.ic_microphone);
                    //Stope
                    try {

                        File file=recorder.stopRecording();
                        call.getFileVoice(file);
                        setupRecorder();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    recordButton.post(new Runnable() {
                        @Override public void run() {
                            animateVoice(0);
                        }
                    });
                }
                click=!click;
            }
        });
    }

    private void setupRecorder() {



        recorder = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file());



    }
    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 44100
                )
        );

    }
    private void animateVoice(final float maxPeak) {
     //   recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }
    @NonNull
    private File file() {
        String  uniqueID = UUID.randomUUID().toString();
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), uniqueID+".wav");
    }

}
