package edu.hitsz.application;

import edu.hitsz.swing.SimpleTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuestionGUI extends JFrame {
    private final JLabel questionLabel;
    private final JTextField inputField;
    private final JButton saveButton;
    private final JPanel panel;
    private final JFrame frame;
    /**
     * 用户输入的字符串,默认情况下为null
     */
    private String userInput;
    public final int WINDOW_WIDTH  = 512;
    public final int WINDOW_HEIGHT = 16;

    public QuestionGUI(String question, InputCallback callback) {


        super("Question GUI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        questionLabel = new JLabel(question);
        inputField = new JTextField(20);
        saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener(callback));

        panel = new JPanel();
        panel.add(questionLabel);
        panel.add(inputField);
        panel.add(saveButton);
        frame = new JFrame("飞机已坠毁,游戏结束");
        frame.setContentPane(panel);
        // 必须输入用户名后通过确认按钮关闭
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, ((int)screenSize.getHeight()/2),
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private class SaveButtonListener implements ActionListener {

        InputCallback callback;

        public SaveButtonListener(InputCallback callback) {
            this.callback = callback;

        }

        public void actionPerformed(ActionEvent e) {
            userInput = inputField.getText();
            if (userInput.isEmpty()) {
                JOptionPane.showMessageDialog(QuestionGUI.this, "Please enter a non-empty input!");
            } else {
                JOptionPane.showMessageDialog(QuestionGUI.this, "Input saved: " + userInput);
                callback.onInput(QuestionGUI.this);// 回调,进行输入用户名后的处理
                frame.dispose();
            }

        }
    }

    public String getUserInput() {
        return userInput;

    }


}
