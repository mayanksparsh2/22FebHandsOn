import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
   private String username;
   private String password;

    public Admin(String username, String password) {

        this.username = username;
        this.password = password;
    }

    void createUser( String name, int bal, String phNo, String userName, String pass) throws SQLException {
        int count = 0;
        if (username.equals("admin") && password.equals("password")) {
            count=getUserCount();
            String query = "insert into bank values (?,?,?,?,?,?);";
            PreparedStatement preparedStatement = BuildConnections.getConnection().prepareStatement(query);
            preparedStatement.setString(1, getUserCount()+1+"");
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, bal);
            preparedStatement.setString(4, phNo);
            preparedStatement.setString(5, userName);
            preparedStatement.setString(6, pass);
            preparedStatement.executeUpdate();
            if (getUserCount() == count) {
                System.out.println("Unable to add user");
            }else {
                System.out.println("User added successfully");
            }

        } else {
            System.out.println("Invalid UserID and password for admin");
        }

    }

    int getUserCount() throws SQLException {
        int count = 0;
        String query = "SELECT count(accountNo) FROM Bank;";
        PreparedStatement preparedStatement = BuildConnections.getConnection().prepareStatement(query);
        preparedStatement.executeQuery();
        try {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                count = (rs.getInt(1));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
