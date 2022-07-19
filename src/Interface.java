import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

public class Interface extends JFrame implements ActionListener{
    private JButton scoreBoard = new JButton("Score Board");
    private JButton play = new JButton("Play");
    private JLabel username = new JLabel("username:");
    private JLabel text = new JLabel("MINE SWEEPER");
    private JTextField usernameInput = new JTextField();
    private JLabel size = new JLabel("difficulty:");
    private JRadioButton  easy = new JRadioButton ("Easy (10x10 10 bombs)");
    private JRadioButton  medium = new JRadioButton ("Medium (20x20 35 bombs)");
    private JRadioButton  hard = new JRadioButton ("Hard (28x28  75 bombs)");
    public Interface()
    {
        super("Mine Sweeper");
        Container container = this.getContentPane();
        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.weightx = 1;
        gcon.weighty = 1;
        gcon.fill=GridBagConstraints.BOTH;
        // this.setResizable(false);

        //adding text part
        JPanel textPanel = new JPanel();
        gcon.gridx = 0;
        gcon.gridy = 0;
        gcon.gridwidth = 2;
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
        
        JPanel dataPart = new JPanel();
        JPanel scorBoardPart = new JPanel();
        ButtonGroup bg=new ButtonGroup();   
        easy.setSelected(true);
        bg.add(easy);
        bg.add(medium);
        bg.add(hard); 
        dataPart.setLayout(new GridLayout(7, 1));
        dataPart.setBackground(Color.ORANGE);
        scorBoardPart.setBackground(Color.MAGENTA);
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
        gcon.gridx = 1;
        gcon.gridy = 2;
        gcon.gridwidth = 1;
        gcon.gridheight = 1;
        gbl.setConstraints(scorBoardPart, gcon);
        scorBoardPart.add(scoreBoard);
        this.add(scorBoardPart);

        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Interface();
        System.out.println("working");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(play))
        {
            if(easy.isSelected())
                new game(10, 10, 10);
            else if(medium.isSelected())
                new game(20, 20, 35);
            else
                new game(28, 28, 75);
            this.dispose();
        }
    }
}
