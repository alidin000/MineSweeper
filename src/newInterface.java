import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class newInterface extends JFrame implements ActionListener {
    private JPanel scoreBoard;
    private JPanel boardControl;
    private JPanel gamePart;
    private JLabel userName;
    private JTextField userInput;
    private JLabel difficulty;
    private JRadioButton easyRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton hardRadioButton;
    private JButton PLayButton;
    private JButton showButton;
    private JComboBox difficultyCombo ;
    private JPanel mainPanel;
    private JTable table1;
    private String diff;
    private  ConnectToDataBase dataBase = new ConnectToDataBase();
    public newInterface()
    {
        super("Mine Sweeper");
        createUIComponents();
        createTable("Easy");
        this.add(mainPanel);
//        scoreBoard.setVisible(false);
        this.setSize(800,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void createTable(String diff) {
        try{
            table1.setModel(new DefaultTableModel(
                    dataBase.getResultss(diff), new String[] {"rank","name","time(seconds)"}
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
    private void createUIComponents() {
        //creating comboBoxes
//        difficultyCombo = new JComboBox();
        difficultyCombo.setModel(new DefaultComboBoxModel(new String[] {"Easy","Medium","Hard"}));

        //creating score table
//        scoreBoard.setVisible(false);

        //grouping the ratio buttons
        ButtonGroup bg=new ButtonGroup();
        easyRadioButton.setSelected(true);
        bg.add(easyRadioButton);
        bg.add(mediumRadioButton);
        bg.add(hardRadioButton);

        //adding actionListener to the buttons
        PLayButton.addActionListener(this);
        showButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(PLayButton))
        {
            if(userInput.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(mainPanel, "Username can't be empty", "Empty UserName", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(easyRadioButton.isSelected())
            {new game(10, 10, 15,userInput.getText(),"Easy");
                diff = "Easy";}
            else if(mediumRadioButton.isSelected())
            {new game(20, 20, 45,userInput.getText(),"Medium");
                diff = "Medium";}
            else
            {new game(28, 28, 95,userInput.getText(),"Hard");
                diff = "Hard";}
            dataBase.insertToRecords(userInput.getText(), 0,diff);
            this.dispose();
        }
        if(e.getSource().equals(showButton))
        {
            int index = difficultyCombo.getSelectedIndex();
            switch (index)
            {
                case 0:
                    createTable("Easy");
                    break;
                case 1:
                    createTable("Medium");
                    break;
                case 2:
                    createTable("Hard");
                    break;
                default:
                    break;
            }
        }
    }
}
