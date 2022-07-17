import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
/*
 * easy 13x6   10 bombs
 * medium 20*9 35 bombs
 * hard 28x13  75 bombs
 */

/*
 * main tasks
   *board \/
   *creating bombs \/
   *functionality  \/
   *flood fill  \/
   *count of bombs \/
 */

// TODO: winning case problem \/
// TODO: count of bombs color \/
// TODO: setting bomb flag \/
// TODO: time count   
// TODO: bomb count   
// TODO: first click is safe \/
// TODO: popup menu with JPopup (restart,quit)

public class game extends JFrame implements MouseListener, ActionListener {
    public int row;
    public int col;
    public static ImageIcon flagImage = new ImageIcon("../images/flag.png");
    public static ImageIcon bombImage = new ImageIcon("../images/bomb.png");
    public static ImageIcon explosion = new ImageIcon("../images/explosion.png");
    public static ImageIcon omg = new ImageIcon("../images/omg.png");
    public static ImageIcon thumbsUp = new ImageIcon("../images/thumbsUp.png");
    public static int bombs;
    public int flagCount;
    public static JButton[][] board;
    public static JButton restart = new JButton("Restart");
    public static JButton mainMenu = new JButton("Main Menu");
    public static int[][] bombLocation;
    public static int[][] flags;
    public static int[][] revealed;
    public static int[][] countBombs;
    public JPanel bombPanel = new JPanel();
    public JPanel message = new JPanel();
    public JLabel winMessage = new JLabel("Congratulations you won!!!");
    public JLabel lossMessage = new JLabel("Ooops mine exploded!");
    public static String[] options = {"restart","menu","exit"};
    public static boolean gameWon = false;
    public static boolean mineHit = false;
    public static boolean fisrtMove = true;

    public game(int row, int col, int b) {
        super("MineSweeper");
        this.row = row;
        this.col = col;
        flagCount = 0;
        board = new JButton[row][col];
        revealed = new int[row][col];
        countBombs = new int[row][col];
        bombLocation = new int[row][col];
        flags = new int[row][col];
        bombPanel.setLayout(new GridLayout(row, col));
        Border emptyBorder = BorderFactory.createEmptyBorder();

        for (int index = 0; index < row; index++) {
            for (int i = 0; i < col; i++) {
                board[index][i] = new JButton("");
                board[index][i].setFocusable(false);
                board[index][i].setRolloverEnabled(false);
                board[index][i].setBorder(emptyBorder);
                board[index][i].addActionListener(this);
                board[index][i].addMouseListener(this);
                board[index][i].setFont(new Font("Arial", Font.PLAIN, 40));
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
        showBoard();
        // showBombs();
        message.setLayout(new GridBagLayout());
        message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.add(bombPanel);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static ImageIcon imageScaling(int h, int w, ImageIcon i) {
        ImageIcon image;
        Image img = i.getImage();
        Image imgScaled = img.getScaledInstance(h, w, Image.SCALE_SMOOTH);
        image = new ImageIcon(imgScaled);
        return image;
    }

    // creating bombs
    public void createBombs(int r, int c) {
        int b = bombs;
        Random rand = new Random();
        while (b > 0) {
            int i = rand.nextInt(row);
            int j = rand.nextInt(col);
            if (bombLocation[i][j] > 0 || (i == r && j == c))
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
    public static void floodFill(int r, int c) {
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

    public static void numColor(JButton b) {
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
    public void showBoard() {

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
    public static void showBombs() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (bombLocation[i][j] > 0) {
                    board[i][j].setIcon(null);
                    board[i][j].setIcon(imageScaling(board[i][j].getHeight(), board[i][j].getWidth(), bombImage));
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
    public static void action(int r, int c) {
        mineHit = (bombLocation[r][c] > 0) ? true : false;
        revealed[r][c]++;
        floodFill(r, c);
        gameWon = won();
        if (mineHit || gameWon)
            return;
    }

    // checking for the game finish
    private static boolean won() {
        int count = 0;
        for (int i = 0; i < revealed.length; i++) {
            for (int j = 0; j < revealed[0].length; j++) {
                if (revealed[i][j] > 0)
                    count++;
            }
        }
        return count == revealed.length * revealed[0].length - bombs;
    }

    public static void main(String[] args) {
        new game(5, 5, 8);
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
                        showBombs();
                        if (gameWon) {
                            message.add(winMessage);
                            int n = JOptionPane.showOptionDialog(this, "Congrats you won!", "Your result", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,
                            imageScaling(46, 55, thumbsUp),options,0);
                            if(n==JOptionPane.YES_OPTION)
                            {
                                this.dispose();
                                new game(row,col,bombs);
                            }
                            else if(n == JOptionPane.NO_OPTION)
                            {
                                this.dispose();
                            }
                            else 
                            {
                                this.dispose();
                            }
                        } else {
                            message.add(lossMessage);
                            int n = JOptionPane.showOptionDialog(this, "Ooops bomb exloded!", "Your result", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,
                            imageScaling(36, 45,omg),options,0);
                            if(n==JOptionPane.YES_OPTION)
                            {
                                this.dispose();
                                fisrtMove = true;
                                new game(row,col,bombs);
                            }
                            else if(n == JOptionPane.NO_OPTION)
                            {
                                this.dispose();
                            }
                            else 
                            {
                                this.dispose();
                            }
                        }
                        return;
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
                    if (e.getSource().equals(board[i][j])) {
                        if (flags[i][j] == 0 && revealed[i][j] == 0) {
                            board[i][j]
                                    .setIcon(imageScaling(board[i][j].getHeight(), board[i][j].getWidth(), flagImage));
                            showBoard();
                            flags[i][j]++;
                        } else {
                            board[i][j].setIcon(null);
                            showBoard();
                            flags[i][j]--;
                        }
                    }
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