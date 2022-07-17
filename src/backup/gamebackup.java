package backup;
import java.util.*;

import javax.swing.*;

/*
 * easy 13x6   10 bombs
 * medium 20*9 35 bombs
 * hard 28x13  75 bombs
 */

/*
 * main tasks
   *board \/
   *creating bombs \/
   *functionality  
   *flood fill  \/
   *count of bombs \/
 */

public class gamebackup {
    private int row;
    private int col;
    public static int bombs;
    // private JButton[][] board;
    public static char[][] boardBombs;
    public static int[][] revealed;
    public static int[][] countBombs;

    public static boolean gameWon = false;
    public static boolean mineHit = false;

    public gamebackup(int row, int col, int b) {
        this.row = row;
        this.col = col;
        boardBombs = new char[row][col];
        revealed = new int[row][col];
        countBombs = new int[row][col];
        for (int index = 0; index < row; index++) {
            for (int i = 0; i < col; i++) {
                boardBombs[index][i] = ' ';
            }
        }
        bombs = b;
        createBombs();
        

    }
    //creating bombs
    public void createBombs()
    {
        int b=bombs;
        Random rand = new Random();
        while(b > 0)
        {
            int i = rand.nextInt(row);
            int j = rand.nextInt(col);
            if(boardBombs[i][j] != ' ') continue;
            boardBombs[i][j] = 'x';
            if(i-1 >= 0) countBombs[i-1][j]++;
            if(i+1 < row) countBombs[i+1][j]++;
            if(j-1 >= 0) countBombs[i][j-1]++;
            if(j+1 < col) countBombs[i][j+1]++;
            if(j+1 < col && i+1 < row) countBombs[i+1][j+1]++;
            if(i-1 >= 0 && j-1 >= 0) countBombs[i-1][j-1]++;
            if(i+1 < row && j-1 >= 0) countBombs[i+1][j-1]++;
            if(j+1 < col && i-1 >= 0) countBombs[i-1][j+1]++;
            b--;
        }
    }

    //flood fill 
    public static void floodFill(int r, int c)
    {
        HashSet<String>visited = new HashSet<>();
        Stack<String> dfs = new Stack<>();
        String cord = r+","+c;
        visited.add(cord);
        dfs.push(cord);
        revealed[r][c]++;
        while(dfs.size() > 0)
        {
            String[] arr = dfs.pop().split(",");
            int rr = Integer.parseInt(arr[0]);
            int cc = Integer.parseInt(arr[1]);
            if(countBombs[rr][cc] > 0) 
            {
                revealed[rr][cc]++;
                continue;
            }
            // System.out.println(dfs.size());
            if(rr-1 >= 0 && !visited.contains((rr-1)+","+cc) )
            {
                dfs.push((rr-1)+","+cc);
                revealed[rr-1][cc]++;
                visited.add((rr-1)+","+cc);
            }
            if(cc-1 >= 0 && !visited.contains((rr)+","+(cc-1)) )
            {
                dfs.push((rr)+","+(cc-1));
                revealed[rr][cc-1]++;
                visited.add((rr)+","+(cc-1));
            }
            if(rr+1 < countBombs.length && !visited.contains((rr+1)+","+(cc)) )
            {
                dfs.push((rr+1)+","+cc);
                revealed[rr+1][cc]++;
                visited.add((rr+1)+","+cc);
            }
            if(cc+1 < countBombs[0].length && !visited.contains((rr)+","+(cc+1)) )
            {
                dfs.push((rr)+","+(cc+1));
                revealed[rr][cc+1]++;
                visited.add((rr)+","+(cc+1));
            }

            if(cc+1 < countBombs[0].length && rr+1 < countBombs.length && !visited.contains((rr+1)+","+(cc+1)) )
            {
                dfs.push((rr+1)+","+(cc+1));
                revealed[rr+1][cc+1]++;
                visited.add((rr+1)+","+(cc+1));
            }
            if(cc-1 >= 0 && rr+1 < countBombs.length && !visited.contains((rr+1)+","+(cc-1)) )
            {
                dfs.push((rr+1)+","+(cc-1));
                revealed[rr+1][cc-1]++;
                visited.add((rr+1)+","+(cc-1));
            }
            if(cc+1 < countBombs[0].length && rr-1 >= 0 && !visited.contains((rr-1)+","+(cc+1)) )
            {
                dfs.push((rr-1)+","+(cc+1));
                revealed[rr-1][cc+1]++;
                visited.add((rr-1)+","+(cc+1));
            }
            if(cc-1 >= 0 && rr-1 >= 0 && !visited.contains((rr-1)+","+(cc-1)) )
            {
                dfs.push((rr-1)+","+(cc-1));
                revealed[rr-1][cc-1]++;
                visited.add((rr-1)+","+(cc-1));
            }
        }

    }

    //printing out the board
    public void showBoard() {
        System.out.print("  ");
        for (int j = 0; j < col; j++) {
            System.out.print(j);
        }
        System.out.println("");
        for (int i = 0; i < row; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < col; j++) {
                if(!(revealed[i][j] == 0))
                    if(countBombs[i][j] == 0)
                        System.out.print(" ");
                    else 
                        System.out.print(countBombs[i][j]);
                else 
                    System.out.print("#");
            }
            System.out.println("");
        }
    }

    //showing all the bombs
    public static void showBombs() {
        System.out.print("  ");
        for (int j = 0; j < boardBombs[0].length; j++) {
            System.out.print(j);
        }
        System.out.println("");
        for (int i = 0; i < boardBombs.length; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < boardBombs[0].length; j++) {
                if(boardBombs[i][j] == 'x')
                    System.out.print('x');
                else if(!(revealed[i][j] == 0))
                    if(!(boardBombs[i][j] == 'x') && countBombs[i][j] == 0)
                        System.out.print(" ");
                    else 
                        System.out.print(countBombs[i][j]);
                else 
                    System.out.print("#");
            }
            System.out.println("");
        }
    }
    //action of the game
    public static void action(int r,int c)
    {
        mineHit = (boardBombs[r][c] =='x')?true:false;
        gameWon = won();
        if(mineHit||gameWon)return;
        floodFill(r,c);
    }
    //checking for the game finish
    private static boolean won()
    {
        int count = 0;
        for (int i = 0; i < revealed.length; i++) {
            for (int j = 0; j < revealed[0].length; j++) {
                if(revealed[i][j] > 0)
                    count++;
            }
        }
        return count == revealed.length*revealed[0].length-bombs;
    }
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        gamebackup g= new gamebackup(10, 10, 10);
        // g.showBoard2();

        while( !gameWon && !mineHit )
        {
            System.out.print("\033[H\033[2J");  
            System.out.flush();
            g.showBoard();
            System.out.print("give the coordinates: ");
            String trash2 = read.nextLine();
            String[] input=trash2.split(" ");
            int c=Integer.parseInt(input[1]);
            int r=Integer.parseInt(input[0]);
            action(r, c);
            if(gameWon)
            {
                System.out.println("congratulations!");
                break;
            }
            if(mineHit)
            {
                System.out.println("Ooops!");
                showBombs();
                break;
            }

        }
        read.close();
    }
} 
