
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Timecount extends JPanel {
    Timer t;
    int score = 0;
    int second = 0;
    int minute = 0;
    String Minutetemp = "00";
    String Secondtemp = "00";
    JLabel g = new JLabel();

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
                // TODO Auto-generated method stub
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
}
