
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Timecount extends JPanel {
    private Timer t;
    private int score = 0;
    private int second = 0;
    private int minute = 0;
    private String Minutetemp = "00";
    private String Secondtemp = "00";
    private JLabel g = new JLabel();

    public Timecount() {
        g.setText(Minutetemp + ":" + Secondtemp);
        g.setForeground(Color.red);
        SimpleTimer();
        t.start();
        this.add(g);
    }

    public void SimpleTimer() {
        t = new Timer(1000, (ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                score++;
                if (second == 60) {
                    minute++;
                    second = 0;
                }
                if (minute < 10)
                    Minutetemp = "0" + minute;
                else
                    Minutetemp = "" + minute;

                if (second < 10)
                    Secondtemp = "0" + second;
                else
                    Secondtemp = "" + second;
                g.setText(Minutetemp + ":" + Secondtemp);
            }
        });
    }
    public void stop()
    {
        t.stop();
    }
    public int getScore()
    {
        return score;
    }
    public String getTime()
    {
        return g.getText();
    }
}
