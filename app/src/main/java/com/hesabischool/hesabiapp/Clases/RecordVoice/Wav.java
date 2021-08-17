package com.hesabischool.hesabiapp.Clases.RecordVoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

final class Wav extends AbstractRecorder {
    public Wav(PullTransport pullTransport, File file) {
        super(pullTransport, file);
    }

    @Override public void stopRecording() {
        try {
            super.stopRecording();
            writeWavHeader();
        } catch (IOException e) {
            throw new RuntimeException("Error in applying wav header", e);
        }
    }

    private void writeWavHeader() throws IOException {
        final RandomAccessFile wavFile = randomAccessFile(file);
        wavFile.seek(0); // to the beginning
        wavFile.write(new WavHeader(pullTransport.pullableSource(), file.length()).toBytes());
        wavFile.close();
    }

    private RandomAccessFile randomAccessFile(File file) {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return randomAccessFile;
    }
}