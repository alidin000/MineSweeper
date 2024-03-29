import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;


public class game extends JFrame implements MouseListener, ActionListener {

    //external classes
    private ConnectToDataBase dataBase = new ConnectToDataBase();
    private Constants constants = new Constants();
    private Timecount timeCount = new Timecount();


    private int row;
    private int col;

    private String name;
    private String diff;

    private int bombs;
    private int flagCount;

    //board
    private JButton[][] board;

    //bomb data
    private int[][] bombLocation;
    private int[][] flags;
    private int[][] revealed;
    private int[][] countBombs;
    
    //JPanel variables
    private JPanel bombPanel = new JPanel();
    private JPanel scorePanel = new JPanel();
    private JPanel message = new JPanel();

    //JLabel variables
    private JLabel bombCount = new JLabel("0/8",SwingConstants.CENTER);
    private JLabel smile = new JLabel("(-_-)",SwingConstants.CENTER);
    private JLabel winMessage = new JLabel("Congratulations you won!!!");
    private JLabel lossMessage = new JLabel("Ooops mine exploded!");

    //String variables
    private String[] options = {"restart","menu","exit"};

    // booleans variables
    private boolean gameWon = false;
    private boolean mineHit = false;
    private boolean fisrtMove = true;

    public game(int row, int col, int b, String name, String diff) {
        super("MineSweeper");
        this.row = row;
        this.col = col;
        this.name = name;
        this.diff = diff;

        flagCount = 0;
        board = new JButton[row][col];
        revealed = new int[row][col];
        countBombs = new int[row][col];
        bombLocation = new int[row][col];
        flags = new int[row][col];
        bombPanel.setLayout(new GridLayout(row, col));
        

        //setting board
        for (int index = 0; index < row; index++) {
            for (int i = 0; i < col; i++) {
                board[index][i] = new JButton("");
                board[index][i].setFocusable(false);
                board[index][i].setRolloverEnabled(false);
                board[index][i].setBorder(BorderFactory.createEmptyBorder());
                board[index][i].addActionListener(this);
                board[index][i].addMouseListener(this);
                board[index][i].setFont(new Font("Arial", Font.PLAIN, (int) (40-(row-10)*0.7)));

                if (index % 2 == 0) {
                    if (i % 2 == 0) {
                        board[index][i].setBackground(new Color(0, 204, 0));
                    } else {
                        board[index][i].setBackground(new Color(0, 255, 51));
                    }
                } else {
                    if (i % 2 == 0) {
                        board[index][i].setBackground(new Color(0, 255, 51));
                    } else {
                        board[index][i].setBackground(new Color(0, 204, 0));
                    }
                }
                bombPanel.add(board[index][i]);
            }
        }
        bombs = b;
        bombCount.setText(flagCount+"/"+bombs);

        //scorePanel edition
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints gc=new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill=GridBagConstraints.BOTH;
        gc.insets= new Insets(5,20,5,10);
        scorePanel.setLayout(g);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 2;
        g.setConstraints(bombCount, gc);
        bombCount.setOpaque(true);
        bombCount.setBorder(new LineBorder(Color.BLACK,3));
        scorePanel.add(bombCount);
        gc.gridx = 2;
        g.setConstraints(smile, gc);
        smile.setOpaque(true);
        smile.setBorder(new LineBorder(Color.BLACK,3));
        scorePanel.add(smile);
        gc.gridx = 4;
        g.setConstraints(timeCount, gc);

        timeCount.setForeground(Color.RED);
        timeCount.setOpaque(true);
        timeCount.setBorder(new LineBorder(Color.BLACK,3));

        scorePanel.add(timeCount);
        scorePanel.setBackground(Color.GRAY);

        message.setLayout(new GridBagLayout());
        message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.add(scorePanel,BorderLayout.NORTH);
        this.add(bombPanel,BorderLayout.CENTER);
        this.setVisible(true);
    }

    //Image resizing
    private ImageIcon imageScaling(int h, int w, ImageIcon i) {
        ImageIcon image;
        Image img = i.getImage();
        Image imgScaled = img.getScaledInstance(h, w, Image.SCALE_SMOOTH);
        image = new ImageIcon(imgScaled);
        return image;
    }

    // creating bombs
    private void createBombs(int r, int c) {
        int b = bombs;
        Random rand = new Random();
        while (b > 0) {
            int i = rand.nextInt(row);
            int j = rand.nextInt(col);
            boolean safeZone = (i == r && j == c) 
                            || (i == r-1 && j == c+1)
                            || (i == r+1 && j == c-1)
                            || (i == r-1 && j == c-1)
                            || (i == r && j == c-1)
                            || (i == r-1 && j == c)
                            || (i == r && j == c+1)
                            || (i == r+1 && j == c+1)
                            || (i == r+1 && j == c);
            if (bombLocation[i][j] > 0 || safeZone)
                continue;
            bombLocation[i][j]++;
            if (i - 1 >= 0 && bombLocation[i - 1][j] == 0)
                countBombs[i - 1][j]++;
            if (i + 1 < row && bombLocation[i + 1][j] == 0)
                countBombs[i + 1][j]++;
            if (j - 1 >= 0 && bombLocation[i][j - 1] == 0)
                countBombs[i][j - 1]++;
            if (j + 1 < col && bombLocation[i][j + 1] == 0)
                countBombs[i][j + 1]++;
            if (j + 1 < col && i + 1 < row && bombLocation[i + 1][j + 1] == 0)
                countBombs[i + 1][j + 1]++;
            if (i - 1 >= 0 && j - 1 >= 0 && bombLocation[i - 1][j - 1] == 0)
                countBombs[i - 1][j - 1]++;
            if (i + 1 < row && j - 1 >= 0 && bombLocation[i + 1][j - 1] == 0)
                countBombs[i + 1][j - 1]++;
            if (j + 1 < col && i - 1 >= 0 && bombLocation[i - 1][j + 1] == 0)
                countBombs[i - 1][j + 1]++;
            b--;
        }
    }

    // flood fill
    private void floodFill(int r, int c) {
        HashSet<String> visited = new HashSet<>();
        Stack<String> dfs = new Stack<>();
        String cord = r + "," + c;
        visited.add(cord);
        dfs.push(cord);
        revealed[r][c]++;
        while (dfs.size() > 0) {
            String[] arr = dfs.pop().split(",");
            int rr = Integer.parseInt(arr[0]);
            int cc = Integer.parseInt(arr[1]);
            if (countBombs[rr][cc] > 0) {
                revealed[rr][cc]++;
                continue;
            }
            if (rr - 1 >= 0 && !visited.contains((rr - 1) + "," + cc) && flags[rr - 1][cc] < 1) {
                dfs.push((rr - 1) + "," + cc);
                revealed[rr - 1][cc]++;
                visited.add((rr - 1) + "," + cc);
            }
            if (cc - 1 >= 0 && !visited.contains((rr) + "," + (cc - 1)) && flags[rr][cc - 1] < 1) {
                dfs.push((rr) + "," + (cc - 1));
                revealed[rr][cc - 1]++;
                visited.add((rr) + "," + (cc - 1));
            }
            if (rr + 1 < countBombs.length && !visited.contains((rr + 1) + "," + (cc)) && flags[rr + 1][cc] < 1) {
                dfs.push((rr + 1) + "," + cc);
                revealed[rr + 1][cc]++;
                visited.add((rr + 1) + "," + cc);
            }
            if (cc + 1 < countBombs[0].length && !visited.contains((rr) + "," + (cc + 1)) && flags[rr][cc + 1] < 1) {
                dfs.push((rr) + "," + (cc + 1));
                revealed[rr][cc + 1]++;
                visited.add((rr) + "," + (cc + 1));
            }

            if (cc + 1 < countBombs[0].length && rr + 1 < countBombs.length
                    && !visited.contains((rr + 1) + "," + (cc + 1)) && flags[rr + 1][cc + 1] < 1) {
                dfs.push((rr + 1) + "," + (cc + 1));
                revealed[rr + 1][cc + 1]++;
                visited.add((rr + 1) + "," + (cc + 1));
            }
            if (cc - 1 >= 0 && rr + 1 < countBombs.length && !visited.contains((rr + 1) + "," + (cc - 1))
                    && flags[rr + 1][cc - 1] < 1) {
                dfs.push((rr + 1) + "," + (cc - 1));
                revealed[rr + 1][cc - 1]++;
                visited.add((rr + 1) + "," + (cc - 1));
            }
            if (cc + 1 < countBombs[0].length && rr - 1 >= 0 && !visited.contains((rr - 1) + "," + (cc + 1))
                    && flags[rr - 1][cc + 1] < 1) {
                dfs.push((rr - 1) + "," + (cc + 1));
                revealed[rr - 1][cc + 1]++;
                visited.add((rr - 1) + "," + (cc + 1));
            }
            if (cc - 1 >= 0 && rr - 1 >= 0 && !visited.contains((rr - 1) + "," + (cc - 1))
                    && flags[rr - 1][cc - 1] < 1) {
                dfs.push((rr - 1) + "," + (cc - 1));
                revealed[rr - 1][cc - 1]++;
                visited.add((rr - 1) + "," + (cc - 1));
            }
        }

    }

    private void numColor(JButton b) {
        int num = Integer.parseInt(b.getText());

        switch (num) {
            case 1:
                b.setForeground(Color.BLUE);
                break;
            case 2:
                b.setForeground(Color.green);
                break;
            case 3:
                b.setForeground(Color.RED);
                break;
            case 4:
                b.setForeground(new Color(102, 0, 153));
                break;
            case 5:
                b.setForeground(new Color(128, 0, 0));
                break;
            case 6:
                b.setForeground(new Color(64, 224, 208));
                break;
            case 7:
                b.setForeground(new Color(0, 0, 0));
                break;
            case 8:
                b.setForeground(new Color(204, 204, 204));
                break;
        }
    }

    // printing out the board
    private void showBoard() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!(revealed[i][j] == 0)) {
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            board[i][j].setBackground(new Color(102, 102, 102));
                        } else {
                            board[i][j].setBackground(new Color(153, 153, 153));
                        }
                    } else {
                        if (j % 2 == 0) {
                            board[i][j].setBackground(new Color(153, 153, 153));
                        } else {
                            board[i][j].setBackground(new Color(102, 102, 102));
                        }
                    }

                    if (countBombs[i][j] > 0) {
                        board[i][j].setText("" + countBombs[i][j]);
                        numColor(board[i][j]);
                    } else {
                        board[i][j].setText("");
                    }
                } else {
                    board[i][j].setText("");
                }
            }
        }
    }

    // showing all the bombs
    private void showBombs() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (bombLocation[i][j] > 0) {
                    board[i][j].setIcon(null);
                    board[i][j].setIcon(imageScaling(board[i][j].getHeight(), board[i][j].getWidth(), constants.getBombImg()));
                } else if (!(revealed[i][j] == 0)) {
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            board[i][j].setBackground(new Color(102, 102, 102));
                        } else {
                            board[i][j].setBackground(new Color(153, 153, 153));
                        }
                    } else {
                        if (j % 2 == 0) {
                            board[i][j].setBackground(new Color(153, 153, 153));
                        } else {
                            board[i][j].setBackground(new Color(102, 102, 102));
                        }
                    }

                    if (countBombs[i][j] > 0) {
                        board[i][j].setText("" + countBombs[i][j]);
                        numColor(board[i][j]);
                    } else {
                        board[i][j].setText("");
                    }
                } else {
                    board[i][j].setText("");
                }
            }
        }
    }

    // action of the game
    private void action(int r, int c) {
        mineHit = (bombLocation[r][c] > 0) ? true : false;
        revealed[r][c]++;
        floodFill(r, c);
        gameWon = won();
    }

    // checking for the game finish
    private boolean won() {
        int count = 0;
        for (int i = 0; i < revealed.length; i++) {
            for (int j = 0; j < revealed[0].length; j++) {
                if (revealed[i][j] > 0)
                    count++;
            }
        }
        return count == revealed.length * revealed[0].length - bombs;
    }

    //finish actions
    private void finishGame()
    {
        showBombs();
        if (mineHit) {
            message.add(lossMessage);
            int n = JOptionPane.showOptionDialog(this, "Ooops bomb exloded!", "Your result", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,
            imageScaling(36, 45,constants.getOmgImg()),options,0);
            if(n==JOptionPane.YES_OPTION)
            {
                fisrtMove = true;
                new game(row,col,bombs,name,diff);
            }
            else if(n == JOptionPane.NO_OPTION)
            {
                fisrtMove = true;
                new newInterface();
            }
            this.dispose();
        } else {
            int score = timeCount.getScore();
            dataBase.insertToRecords(name, score,diff);
            String time = timeCount.getTime();
            message.add(winMessage);
            int n = JOptionPane.showOptionDialog(this, "Congrats you won! Finishing time->"+time, "Your result", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,
            imageScaling(46, 55, constants.getThumbImg()),options,0);
            if(n==JOptionPane.YES_OPTION)
            {
                this.dispose();
                fisrtMove = true;
                new game(row,col,bombs,name,diff);
            }
            else if(n == JOptionPane.NO_OPTION)
            {
                fisrtMove = true;
                new newInterface();
            }
            this.dispose();
        }
        timeCount.stop();
        return;
    }
    public static void main(String[] args) {
        new game(10, 10, 10,"temp","Easy");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (e.getSource().equals(board[i][j]) && flags[i][j] < 1) {
                    if (fisrtMove) {
                        createBombs(i, j);
                        fisrtMove = false;
                    }
                    action(i, j);

                    if (gameWon || mineHit) {
                        finishGame();
                    } else
                        showBoard();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (e.getSource().equals(board[i][j]) && revealed[i][j] == 0) {
                        if (flags[i][j] == 0  && flagCount < bombs) {
                            board[i][j]
                                    .setIcon(imageScaling(board[i][j].getHeight(), board[i][j].getWidth(), constants.getFlagImg()));
                            showBoard();
                            flags[i][j]=1;
                            flagCount++;
                        } else if(flags[i][j] == 1){
                            board[i][j].setIcon(null);
                            showBoard();
                            flags[i][j]=0;
                            flagCount--;
                        }
                        bombCount.setText(""+flagCount+"/"+bombs);
                    }
                }
            }
        }
        else if(SwingUtilities.isMiddleMouseButton(e))
        {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (e.getSource().equals(board[i][j]) && revealed[i][j] > 0) {
                        mineHit = false;
                        int tempFlagCount = 0;
                        if (i - 1 >= 0 && flags[i-1][j] > 0)
                            {tempFlagCount++;
                                
                            }
                        if (i + 1 < row && flags[i + 1][j] > 0)
                            {tempFlagCount++;
                                
                            }
                        if (j - 1 >= 0 && flags[i][j - 1] > 0)
                            {tempFlagCount++;
                                
                            }
                        if (j + 1 < col && flags[i][j + 1] > 0)
                            {tempFlagCount++;
                                
                            }
                        if (j + 1 < col && i + 1 < row && flags[i + 1][j + 1] > 0)
                            {tempFlagCount++;
                                
                            }
                        if (i - 1 >= 0 && j - 1 >= 0 && flags[i - 1][j - 1] > 0)
                            {tempFlagCount++;
                                
                            }
                        if (i + 1 < row && j - 1 >= 0 && flags[i + 1][j - 1] > 0)
                            {tempFlagCount++;
                               
                            }
                        if (j + 1 < col && i - 1 >= 0 && flags[i - 1][j + 1] > 0)
                            {tempFlagCount++;
                                
                            }

                            
                            if(tempFlagCount == countBombs[i][j])
                            {
                            if (i - 1 >= 0 && revealed[i - 1][j] == 0 && flags[i - 1][j] == 0)
                                {revealed[i - 1][j] = 1;
                                    floodFill(i-1, j);
                                    mineHit = mineHit || (bombLocation[i - 1][j] > 0) ? true : false;}

                            if (i + 1 < row && revealed[i + 1][j] == 0&& flags[i + 1][j] == 0)
                                {revealed[i + 1][j]  = 1;
                                    floodFill(i+1, j);
                                    mineHit = mineHit || (bombLocation[i + 1][j] > 0) ? true : false;}

                            if (j - 1 >= 0 && revealed[i][j - 1] == 0&& flags[i][j - 1] == 0)
                                {revealed[i][j - 1] = 1;
                                    floodFill(i, j-1);
                                    mineHit = mineHit || (bombLocation[i ][j- 1] > 0) ? true : false;}

                            if (j + 1 < col && revealed[i][j + 1] == 0&& flags[i][j + 1] == 0)
                                {revealed[i][j + 1] = 1;
                                    floodFill(i, j+1);
                                    mineHit = mineHit || (bombLocation[i ][j+ 1] > 0) ? true : false;}

                            if (j + 1 < col && i + 1 < row && revealed[i + 1][j + 1] == 0&& flags[i + 1][j + 1] == 0)
                                {revealed[i + 1][j + 1] = 1;
                                    floodFill(i+1, j+1);
                                    mineHit = mineHit || (bombLocation[i + 1][j + 1] > 0) ? true : false;}
                                
                            if (i - 1 >= 0 && j - 1 >= 0 && revealed[i - 1][j - 1] == 0&& flags[i - 1][j - 1] == 0)
                                {revealed[i - 1][j - 1] = 1;
                                    floodFill(i-1, j-1);
                                    mineHit = mineHit || (bombLocation[i - 1][j - 1] > 0) ? true : false;}

                            if (i + 1 < row && j - 1 >= 0 && revealed[i + 1][j - 1] == 0&& flags[i + 1][j - 1] == 0)
                                {revealed[i + 1][j - 1]  = 1;
                                    floodFill(i+1, j-1);
                                    mineHit = mineHit || (bombLocation[i + 1][j - 1] > 0) ? true : false;}

                            if (j + 1 < col && i - 1 >= 0 && revealed[i - 1][j + 1] == 0&& flags[i - 1][j + 1] == 0)
                               { revealed[i - 1][j + 1] = 1;
                                floodFill(i-1, j+1);
                                mineHit = mineHit || (bombLocation[i - 1][j + 1] > 0) ? true : false;}
                        }
                    }
                    gameWon = won();

                    if (gameWon || mineHit) {
                        finishGame();
                        return;
                    }
                    showBoard();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}