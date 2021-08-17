package com.hesabischool.hesabiapp.Clases.RecordVoice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
    String  uniqueID;
    public RecordeVoice(ImageView recordButton,callForCheange callForCheange) {
        this.recordButton = recordButton;
        call=callForCheange;



    }
    public void run()
    {

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!click)
                {//Start
                    setupRecorder();
                    recordButton.setVisibility(View.INVISIBLE);

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            recorder.startRecording();
                            recordButton.setImageResource(R.drawable.ic_microphone_red);
                            recordButton.setVisibility(View.VISIBLE);
                        }
                    }, 100);

                }
                else
                {
                    recordButton.setImageResource(R.drawable.ic_microphone);
                    //Stope


                       recordButton.setVisibility(View.INVISIBLE);
                    try {
                        recorder.stopRecording();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), uniqueID+".wav");
                                recordButton.setVisibility(View.VISIBLE);
                                call.getFileVoice(f);
                            }
                        }, 100);




//                    recordButton.post(new Runnable() {
//                        @Override public void run() {
//                            animateVoice(0);
//                        }
//                    });
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

        return
                        new PullableSource.Default(
                                new AudioRecordConfig.Default(
                                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                                        AudioFormat.CHANNEL_IN_MONO, 44100
                                )


        );

    }
    private void animateVoice(final float maxPeak) {
      //  recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }


    @NonNull
    private File file() {
         uniqueID = UUID.randomUUID().toString();
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), uniqueID+".wav");
    }

}
