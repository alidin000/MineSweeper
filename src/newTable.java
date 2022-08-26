import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.SQLException;

public class newTable {
    private JPanel Table;
    private JTable table1;
    private ConnectToDataBase dataBase = new ConnectToDataBase();
    public newTable()
    {
        createTable();
    }
    public JPanel getPanel()
    {
        return Table;
    }
    public JTable getTable()
    {
        return table1;
    }
    {

    }
    private void createTable() {
        try{
            table1.setModel(new DefaultTableModel(
                    dataBase.getResultss(), new String[] {"rank","name","time(seconds)"}
            ));
            TableColumnModel tcmodel = table1.getColumnModel();
            tcmodel.getColumn(0).setMaxWidth(50);
            DefaultTableCellRenderer dftc = new DefaultTableCellRenderer();
            dftc.setHorizontalAlignment(JLabel.CENTER);
            tcmodel.getColumn(0).setCellRenderer(dftc);
            tcmodel.getColumn(1).setCellRenderer(dftc);
            tcmodel.getColumn(2).setCellRenderer(dftc);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        newTable t = new newTable();
        JPanel f = t.getPanel();
        JFrame frame  = new JFrame();
        frame.setContentPane(f);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
