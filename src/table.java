import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;

public class table extends JPanel {
    private JTable scoreBoard = new JTable();
    private ConnectToDataBase dataBase = new ConnectToDataBase();
    public table()
    {
        Object[] columns={"rank","name","time(seconds)"};
        DefaultTableModel dt;
        try {
            dt = new DefaultTableModel(dataBase.getResultss(),columns);
            scoreBoard.setModel(dt);
            scoreBoard.setBackground(Color.GRAY);
            scoreBoard.setSelectionBackground(Color.ORANGE);
            scoreBoard.setGridColor(Color.RED);
            scoreBoard.setRowHeight(30);
            JScrollPane scroll = new JScrollPane(scoreBoard);
            this.add(scroll);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setVisible(false);
    }
    public static void main(String[] args) {
        new table();
    }
}
