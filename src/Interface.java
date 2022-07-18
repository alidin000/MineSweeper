import java.awt.*;

import javax.swing.*;

public class Interface extends JFrame{
    private JButton scoreBoard = new JButton("Score Board");
    private JButton play = new JButton("Play");
    private JLabel username = new JLabel("username:");
    private JLabel text = new JLabel("MINE SWEEPER");
    private JTextField usernameInput = new JTextField();
    private JLabel size = new JLabel("difficulty:");
    private JRadioButton  easy = new JRadioButton ("Easy (13x6 10 bombs)");
    private JRadioButton  medium = new JRadioButton ("Medium (20x9 35 bombs)");
    private JRadioButton  hard = new JRadioButton ("Hard (28x13  75 bombs)");
    public Interface()
    {
        super("Mine Sweeper");
        this.setSize(600,600);
        this.setLayout( new GridLayout(2, 1));
        // this.setResizable(false);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        textPanel.setBackground(Color.CYAN);
        textPanel.add(text);
        this.add(textPanel);
        JPanel p = new JPanel();
        JPanel dataPart = new JPanel();
        JPanel dataPart2 = new JPanel();
        JPanel scorBoardPart = new JPanel();
        ButtonGroup bg=new ButtonGroup();   
        bg.add(easy);
        bg.add(medium);
        bg.add(hard); 
        p.setLayout(new GridLayout(1, 2));
        dataPart.setLayout(new GridLayout(7, 1));
        dataPart2.setLayout(new GridBagLayout());
        dataPart2.setBackground(Color.ORANGE);
        scorBoardPart.setBackground(Color.MAGENTA);
        dataPart.add(username);
        dataPart.add(usernameInput);
        dataPart.add(size);
        dataPart.add(easy);
        dataPart.add(medium);
        dataPart.add(hard);
        dataPart.add(play);
        scorBoardPart.add(scoreBoard);
        dataPart2.add(dataPart);
        p.add(dataPart2);
        p.add(scorBoardPart);
        this.add(p);
        // this.add(scoreBoard);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Interface();
    }
}
