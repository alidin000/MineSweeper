import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Interface extends JFrame implements ActionListener{
    private JButton scoreBoard = new JButton("Score Board");
    public JPanel scorBoardPart = new JPanel();
    public JPanel names = new JPanel();
    private JButton play = new JButton("Play");
    private JLabel username = new JLabel("username:");
    private JLabel text = new JLabel("MINE SWEEPER");
    public ArrayList<JLabel>temp = new ArrayList<>();
    public static JTextField usernameInput = new JTextField(1);
    private JLabel size = new JLabel("difficulty:");
    private JRadioButton  easy = new JRadioButton ("Easy (10x10 10 bombs)");
    private JRadioButton  medium = new JRadioButton ("Medium (20x20 35 bombs)");
    private JRadioButton  hard = new JRadioButton ("Hard (28x28  75 bombs)");
    public GridBagLayout gbl = new GridBagLayout();
    public GridBagConstraints gcon = new GridBagConstraints();
    public Interface()
    {
        super("Mine Sweeper");
        Container container = this.getContentPane();
        container.setLayout(gbl);
        gcon.weightx = 1;
        gcon.weighty = 1;
        gcon.fill=GridBagConstraints.BOTH;
        gcon.insets = new Insets(0,0,5,5);

        //adding text part
        JPanel textPanel = new JPanel();
        gcon.gridx = 0;
        gcon.gridy = 0;
        gcon.gridwidth = 1;
        gcon.gridheight = 2;
        gbl.setConstraints(textPanel, gcon);
        textPanel.setBackground(Color.CYAN);
        textPanel.add(text);
        this.add(textPanel);

        //adding data part
        gcon.gridx = 0;
        gcon.gridy = 2;
        gcon.gridwidth = 1;
        gcon.gridheight = 1;
        gcon.insets = new Insets(0,0,0,5);
        JPanel dataPart = new JPanel();
        ButtonGroup bg=new ButtonGroup();   
        easy.setSelected(true);
        bg.add(easy);
        bg.add(medium);
        bg.add(hard); 
        dataPart.setLayout(new GridLayout(7, 1));
        dataPart.setBackground(Color.ORANGE);
        scorBoardPart.setBackground(Color.MAGENTA);
        scorBoardPart.setLayout(new BorderLayout());
        dataPart.add(username);
        dataPart.add(usernameInput);
        dataPart.add(size);
        dataPart.add(easy);
        dataPart.add(medium);
        dataPart.add(hard);
        play.addActionListener(this);
        dataPart.add(play);
        gbl.setConstraints(dataPart, gcon);
        this.add(dataPart);
        //adding scoreboard part
        scoreBoard.addActionListener(this);
        scorBoardPart.add(scoreBoard,BorderLayout.NORTH);
        try {
            ConnectToDataBase.getResults(temp);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        names.setLayout(new GridLayout(temp.size(),1));
        for(JLabel l : temp)
            names.add(l);
        scorBoardPart.add(names,BorderLayout.CENTER);
        gcon.gridx = 1;
        gcon.gridy = 0;
        gcon.gridwidth = 3;
        gcon.gridheight = 3;
        gcon.insets = new Insets(0,0,0,0);
        gbl.setConstraints(scorBoardPart, gcon);
        this.add(scorBoardPart);

        this.setSize(800,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Interface();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(play))
        {
            if(usernameInput.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Username can't be empty", "Empty UserName", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(easy.isSelected())
                new game(10, 10, 10);
            else if(medium.isSelected())
                new game(20, 20, 35);
            else
                new game(28, 28, 75);
            ConnectToDataBase.insertToRecords(usernameInput.getText(), 0);
            this.dispose();
        }
        if(e.getSource().equals(scoreBoard))
        {
            try {
                ConnectToDataBase.getResults(temp);
                for(JLabel l : temp)
                    scorBoardPart.add(l);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        }
    }
}
