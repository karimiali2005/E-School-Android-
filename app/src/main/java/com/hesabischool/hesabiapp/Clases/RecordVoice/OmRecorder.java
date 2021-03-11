package com.hesabischool.hesabiapp.Clases.RecordVoice;

import java.io.File;

public final class OmRecorder {
    private OmRecorder() {
    }

 /*   public static Recorder pcm(PullTransport pullTransport, File file) {
        return new Pcm(pullTransport, file);
    }*/

    public static Recorder wav(PullTransport pullTransport, File file) {
        return new Wav(pullTransport, file);
    }
}