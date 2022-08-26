import java.sql.*;
import java.util.ArrayList;


public class ConnectToDataBase {
    public ConnectToDataBase()
    {}
    public void insertToRecords(String userName,int score,String difficulty)
    {
        Statement sqlSt;
        ResultSet result;
        String SQL="SELECT * from userdata where userName = '" + userName +  "'";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/mydatabase";
            Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
            sqlSt = dbconnect.createStatement();
            result = sqlSt.executeQuery(SQL);
            if(result.next())
            {              
                String query = "update userdata set time = ?, difficulty = ? where userName = ?";
                PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
                preparedStmt.setInt   (1, score);
                preparedStmt.setString(3, userName);
                preparedStmt.setString(2, difficulty);
                preparedStmt.executeUpdate();
            }
            else 
            {
                String query = " insert into userdata (username, time ,difficulty)"+ " values (?, ?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
                preparedStmt.setString (1, userName);
                preparedStmt.setString (3, difficulty);
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
    public Object[][] getResultss(String diff) throws SQLException
    {
        ArrayList<String>names= new ArrayList<>();
        ArrayList<String>score= new ArrayList<>();
        Statement sqlSt;
        ResultSet result;
        String SQL="select * from userdata where difficulty ='"+diff+"' and time != 0 order by time asc";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/mydatabase";
        Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
        sqlSt = dbconnect.createStatement();
        result = sqlSt.executeQuery(SQL);
        while(result.next())
        {
            names.add(result.getString("userName"));
            score.add(result.getString("time"));
        }       
        Object[][] out = new Object[names.size()][3];
        for(int i=0; i<names.size();i++)
        {
            out[i][0] = i+1;
            out[i][1] = names.get(i);
            out[i][2] = score.get(i);
        }
        return out;
    }
    public void clear() throws SQLException
    {
        Statement sqlSt;
        String SQL="TRUNCATE TABLE userData";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/mydatabase";
        Connection dbconnect = DriverManager.getConnection(dbURL, "root","mySqlAli2022");
        sqlSt = dbconnect.createStatement();
        sqlSt.executeUpdate(SQL);
    }
}
