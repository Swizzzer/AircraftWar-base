package edu.hitsz.swing;


import edu.hitsz.application.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class StartMenu {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    private JPanel mainPanel;
    private JButton newEasyGameButton;
    private JButton newHardGameButton;
    private JButton newNormalGameButton;
    private JCheckBox musicCheckBox;
    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPanel");
        frame.setContentPane(new StartMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public StartMenu() {
        newEasyGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello Aircraft War");

                // 获得屏幕的分辨率，初始化 Frame
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                JFrame frame = new JFrame("Aircraft War");
                frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setResizable(false);
                //设置窗口的大小和位置,居中放置
                frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                        WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Game game;
                if(musicCheckBox.isSelected()) {
                    game=new Game(true);
                }
                else {
                    game=new Game(false);
                }
                CardLayoutDemo.cardPanel.add(game);
                game.action();
                CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
            }
        });
        newNormalGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello Aircraft War");

                // 获得屏幕的分辨率，初始化 Frame
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                JFrame frame = new JFrame("Aircraft War");
                frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setResizable(false);
                //设置窗口的大小和位置,居中放置
                frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                        WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                Game game;
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(musicCheckBox.isSelected()) {
                    game=new Game(true);
                }
                else {
                    game=new Game(false);
                }
                CardLayoutDemo.cardPanel.add(game);
                game.action();
                CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
            }
        });
        newHardGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello Aircraft War");

                // 获得屏幕的分辨率，初始化 Frame
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                JFrame frame = new JFrame("Aircraft War");
                frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setResizable(false);
                //设置窗口的大小和位置,居中放置
                frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                        WINDOW_WIDTH, WINDOW_HEIGHT);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                Game game;
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(musicCheckBox.isSelected()) {
                    game=new Game(true);
                }
                else {
                    game=new Game(false);
                }
                CardLayoutDemo.cardPanel.add(game);
                game.action();
                CardLayoutDemo.cardLayout.last(CardLayoutDemo.cardPanel);
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
