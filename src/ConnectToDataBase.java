import java.sql.*;

public class ConnectToDataBase {
    public static void main(String[] args)
    {
        Statement sqlSt;
        ResultSet result;
        String SQL="select * from Persons order by FIrstName";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/myDatabase";
            Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
            sqlSt = dbconnect.createStatement();
            result = sqlSt.executeQuery(SQL);
            while(result.next())
                System.out.println(result.getString("LastName")+" "+result.getString("FirstName"));
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
