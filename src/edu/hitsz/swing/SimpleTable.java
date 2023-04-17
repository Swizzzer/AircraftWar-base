package edu.hitsz.swing;

import edu.hitsz.scoreDAO.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.List;

public class SimpleTable {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPanel;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JButton returnButton;

    public SimpleTable(ScoreDAO scoreStream,List<ScoreStructure> scoreInfos) {
        String[] columnName = {"排名", "时间", "用户名", "得分"};

        String[][]tableData=new String[scoreInfos.size()][];
        for(int i=0;i<scoreInfos.size();i++){
            tableData[i]=new String[]{String.valueOf(i+1),scoreInfos.get(i).getPlayTime(),scoreInfos.get(i).getUserName(),String.valueOf(scoreInfos.get(i).getScore())};
        }
        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    scoreStream.deleteByTime(tableData[row][1]);
                    /**
                     * 每次删除之后刷新表格数据.由于scoreInfos是有序表因此删除后直接读入即可,无需进一步排序
                     * TODO 时间复杂度较高,后续进一步优化
                     */
                    for(int i=0;i<scoreInfos.size();i++){
                        tableData[i]=new String[]{String.valueOf(i+1),scoreInfos.get(i).getPlayTime(),scoreInfos.get(i).getUserName(),String.valueOf(scoreInfos.get(i).getScore())};
                    }
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayoutDemo.cardLayout.first(CardLayoutDemo.cardPanel);

            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("SimpleTable");
        frame.setContentPane(new SimpleTable(null,null).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

