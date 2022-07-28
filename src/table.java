import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class table extends JFrame {
    JLabel name=new JLabel("Hello");
    JTable scoreBoard = new JTable();
    public table()
    {
        super("tmep");
        Object[] columns={"rank","name","score"};
        DefaultTableModel dt = new DefaultTableModel();
        dt.setColumnIdentifiers(columns);
        scoreBoard.setModel(dt);
        scoreBoard.setBackground(Color.GRAY);
        scoreBoard.setSelectionBackground(Color.ORANGE);
        scoreBoard.setGridColor(Color.RED);
        scoreBoard.setRowHeight(30);
        Object[] row = new Object[12];
        row[0] = "new JLabel();";
        row[1] = "new JLabel(";
        row[2] = "new JLabel();";
        row[3] = "dfsgnew JLabel(";
        row[4] = "new JLabel();";
        row[5] = "new JLabel(";
        row[6] = "new JLabel();";
        row[7] = "dfsgnew JLabel(";
        row[8] = "new JLabel();";
        row[9] = "new JLabel(";
        row[10] = "new JLabel();";
        row[11] = "dfsgnew JLabel(";

        dt.addRow(row);
        JScrollPane scroll = new JScrollPane(scoreBoard);
        this.setSize(800,600);
        this.setLayout(new GridLayout(2,1));
        this.add(name);
        this.add(scroll);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new table();
    }
}
