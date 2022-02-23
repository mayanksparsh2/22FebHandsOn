import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BuildConnection {
    static Connection connection = null;
    static void setupConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/GENPACT", "root", "11111111");
                System.out.println("Connection Established");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static Connection getConnecton() {
        setupConnection();
        return connection;
    }


}
