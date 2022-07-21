import java.sql.*;

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
                preparedStmt.setInt   (1, 6000);
                preparedStmt.setString(2, userName);
          
                // execute the java preparedstatement
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
    public static String getResults() throws SQLException
    {
        StringBuilder s= new StringBuilder();
        Statement sqlSt;
        ResultSet result;
        String SQL="select * from userData order by score";
        StringBuilder res = new StringBuilder();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/GameRecords";
        Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
        sqlSt = dbconnect.createStatement();
        insertToRecords("Ali",54);
        result = sqlSt.executeQuery(SQL);
        while(result.next())
            res.append(result.getString("userName")+" "+result.getString("score")+"\n");
        
        return res.toString();
    }
    public static void main(String[] args) throws SQLException
    {
        insertToRecords("userName", 1);
        System.out.println( getResults());
    }
}
