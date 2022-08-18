import java.sql.*;
import java.util.ArrayList;

// TODO: remove static methods and variables

public class ConnectToDataBase {
    public static void insertToRecords(String userName,int score)
    {
        Statement sqlSt;
        ResultSet result;
        String SQL="SELECT * from userData where userName = '" + userName +  "'";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
            Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
            sqlSt = dbconnect.createStatement();
            result = sqlSt.executeQuery(SQL);
            if(result.next())
            {              
                String query = "update userData set score = ? where userName = ?";
                PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
                preparedStmt.setInt   (1, score);
                preparedStmt.setString(2, userName);
                preparedStmt.executeUpdate();
            }
            else 
            {
                String query = " insert into userData (username, score)"+ " values (?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
                preparedStmt.setString (1, userName);
                preparedStmt.setInt    (2, score);

                // execute the preparedstatement
                preparedStmt.execute();
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Class not found, check the JAR file");
        }
        catch(SQLException e)
        {
            System.out.println("Can't connect to database " + e.getMessage());
        }
    }
    public static Object[][] getResultss() throws SQLException
    {
        ArrayList<String>names= new ArrayList<>();
        ArrayList<String>score= new ArrayList<>();
        Statement sqlSt;
        ResultSet result;
        String SQL="select * from userData order by score desc";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
        Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
        sqlSt = dbconnect.createStatement();
        result = sqlSt.executeQuery(SQL);
        while(result.next())
        {
            names.add(result.getString("userName"));
            score.add(result.getString("score"));
        }       
        Object[][] out = new Object[names.size()][3];
        for(int i=0; i<names.size();i++)
        {
            out[i][0] = i+1;
            out[i][1] = names.get(i);
            out[i][2] = score.get(i);
            // System.out.println( out[i][0] +" " +  out[i][1] + " " +  out[i][2]);
        }
        return out;
    }
    public static String getResults() throws SQLException
    {
        Statement sqlSt;
        ResultSet result;
        String SQL="select * from userData order by score desc";
        StringBuilder res = new StringBuilder();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
        Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
        sqlSt = dbconnect.createStatement();
        result = sqlSt.executeQuery(SQL);
        while(result.next())
            res.append(result.getString("userName")+" "+result.getString("score")+"\n");
        
        return res.toString();
    }
    public static void clear() throws SQLException 
    {
        Statement sqlSt;
        String SQL="TRUNCATE TABLE userData";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
        Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
        sqlSt = dbconnect.createStatement();
        sqlSt.executeUpdate(SQL);
    }
    public static void main(String[] args) throws SQLException
    {
        System.out.println( getResultss());
    }
}
