
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timecount extends JPanel {
    Timer t;
    int score = 0;
    int second= 0;
    int minute= 0;
    String Minutetemp = "00";
    String Secondtemp = "00";
    JLabel g = new JLabel();
    public Timecount()
    {
        g.setText(Minutetemp+":"+Secondtemp);
        g.setForeground(Color.red);
        SimpleTimer();
        t.start();
        this.add(g);
    }
    public void SimpleTimer()
    {
        t = new Timer(1000,(ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                second++;
                score ++;
                if(second == 60)
                {
                    minute++;
                    second = 0;
                }
                if(minute < 10)
                Minutetemp = "0"+minute;
                else 
                Minutetemp =""+ minute;
                
                if(second < 10)
                Secondtemp = "0"+second;
                else 
                Secondtemp =""+ second;
                g.setText(Minutetemp+":"+Secondtemp);
            }
        });
    }
    public static void main(String[] args) {
        JFrame n = new JFrame();
        n.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        n.setSize(600,600);
        n.add(new Timecount());
        n.setVisible(true);
    }
}
