package edu.hitsz.thread;

import edu.hitsz.application.Game;

import javax.sound.sampled.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 专门用于播放炸弹爆炸声的线程.在炸弹道具生效2s后播放爆炸音效
 */
public class BombSupplyThread implements Runnable {

    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Game game;


    // 硬编码的文件地址
    private final String fileName = "src/videos/bomb_explosion.wav";
    private AudioFormat audioFormat;
    private byte[] samples;
    private SourceDataLine dataLine;

    public BombSupplyThread(Game game) {
        this.game = game;
        reverseMusic();
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
        //源数据行SoureDataLine是可以写入数据的数据行
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
        dataLine.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead =
                        source.read(buffer, 0, buffer.length);
                //通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();

    }

    public boolean isAlive() {
        return running.get();
    }

    private void playStop() {
        if (dataLine != null) {
            dataLine.stop();

        }

    }

    public void stop() {
        playStop();
        running.set(false);
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }


    @Override
    public void run() {
        running.set(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (running.get()) {
            // 道具生效2s后炸弹爆炸
            if (!game.getOverFlag()) {
                InputStream stream = new ByteArrayInputStream(samples);
                play(stream);
            }

            running.set(false);
        }
        running.set(false);

    }
}
