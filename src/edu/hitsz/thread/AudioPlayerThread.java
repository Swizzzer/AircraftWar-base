/**
 * 本次commit将AudioPlayerThread改为实现Runnable接口从而修复了Windows平台下游戏结束后仍存在间歇性射击音的bug.
 * 初步判断为Thread.stop()方法引起的问题
 * TODO 将thread软件包下另外两个类也改为对Runnable接口的实现

 */
package edu.hitsz.thread;

import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioPlayerThread implements Runnable {
    private Thread worker;
    private String fileName;
    private AudioFormat audioFormat;
    private byte[] samples;
    private final AtomicBoolean running=new AtomicBoolean(false);

    private SourceDataLine dataLine = null;

    public AudioPlayerThread(String fileName) {
        this.fileName = fileName;
        reverseMusic();
    }
    public void start(){
        worker=new Thread(this);
        worker.start();
    }
    public void stop(){
        playStop();
        running.set(false);
    }


    public void reverseMusic() {
        try {
            File musicPath = new File(fileName);
            if (musicPath.exists()) {
                //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
                AudioInputStream stream = AudioSystem.getAudioInputStream(musicPath);
                //用AudioFormat来获取AudioInputStream的格式
                audioFormat = stream.getFormat();
                samples = getSamples(stream);
            }
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SourceDataLine是可以写入数据的数据行
        dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (dataLine != null) {
            dataLine.start();
        }
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead =
                        source.read(buffer, 0, buffer.length);
                //通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    if (dataLine != null) {
                        dataLine.write(buffer, 0, numBytesRead);
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (dataLine != null) {
            dataLine.drain();
            dataLine.close();

        }


    }
    public boolean isAlive(){
        return running.get();
    }
    private void playStop(){
        if (dataLine != null) {
            dataLine.stop();

        }

    }


    @Override
    public void run() {
        running.set(true);
        while(running.get()){
            InputStream stream = new ByteArrayInputStream(samples);
            play(stream);
            running.set(false);

        }

    }
}