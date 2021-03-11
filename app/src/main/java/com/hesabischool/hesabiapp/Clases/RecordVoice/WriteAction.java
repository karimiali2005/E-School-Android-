package com.hesabischool.hesabiapp.Clases.RecordVoice;

import java.io.IOException;
import java.io.OutputStream;

public interface WriteAction {
    /**
     * Implement this behaviour to provide custom Write Action for audio which
     * requires {@code data} to encode. So here One can encode the data
     * according to chosen audio format.
     */
    void execute(AudioChunk audioChunk, OutputStream outputStream) throws IOException;

    /**
     * Use this default implementation to write data directly without any encoding to OutputStream.
     */
    final class Default implements WriteAction {
        @Override public void execute(AudioChunk audioChunk, OutputStream outputStream)
                throws IOException {
            outputStream.write(audioChunk.toBytes());
        }
    }
}