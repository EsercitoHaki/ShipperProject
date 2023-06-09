package ConnectDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    public static Connection ConnectDB(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;database=QuanLyShipper;user=sa;password=Phamquan2003;encrypt=true;trustServerCertificate=true";
            connection = DriverManager.getConnection(url);
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }

    public PreparedStatement prepareStatement(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ConnectDB();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
