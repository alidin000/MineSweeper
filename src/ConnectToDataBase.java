import java.sql.*;

public class ConnectToDataBase {
    public static void insertToRecords(String userName,int score)
    {
        Statement sqlSt;
        ResultSet result;
        String SQL="SELECT * from userData where userName = '" + userName +  "' )";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
            Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
            sqlSt = dbconnect.createStatement();
            result = sqlSt.executeQuery(SQL);
            if(result.next())
            {              
                System.out.println("working");  
                String query = "update userData set score = ? where userName = ?";
                PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
                preparedStmt.setInt   (2, score);
                preparedStmt.setString(1, userName);
                preparedStmt.executeUpdate();
            }
            else 
            {
                String query = " insert into users (username, score)"+ " values (?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
                preparedStmt.setString (1, "username");
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
    public static void main(String[] args)
    {
        Statement sqlSt;
        ResultSet result;
        String SQL="select * from userData order by score";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
            Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
            sqlSt = dbconnect.createStatement();
            insertToRecords("Ali",5);
            result = sqlSt.executeQuery(SQL);
            while(result.next())
                System.out.println(result.getString("userName")+" "+result.getString("score"));
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
}
