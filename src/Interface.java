import java.awt.*;

import javax.swing.*;

public class Interface extends JFrame{
    private JButton scoreBoard = new JButton("Score Board");
    private JButton play = new JButton("Play");
    private JLabel username = new JLabel("username:");
    private JLabel text = new JLabel("MINE SWEEPER");
    private JTextField usernameInput = new JTextField();
    private JLabel size = new JLabel("size:");
    public Interface()
    {
        super("Mine Sweeper");
        this.setSize(400,360);
        this.setLayout( new FlowLayout());
        // this.setResizable(false);
        this.add(text);
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        p.add(username);
        p.add(usernameInput);
        p.add(size);
        p.add(play);
        this.add(p);
        this.add(scoreBoard);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
