package com.hesabischool.hesabiapp.Clases.RecordVoice;

import java.io.File;
import java.io.IOException;

public interface Recorder {
    void startRecording();

    File stopRecording() throws IOException;

    void pauseRecording();

    void resumeRecording();

    /**
     * Interface definition for a callback to be invoked when a silence is measured.
     */
    interface OnSilenceListener {
        /**
         * Called when a silence measured
         *
         * @param silenceTime The silence measured
         */
        void onSilence(long silenceTime);
    }
}
