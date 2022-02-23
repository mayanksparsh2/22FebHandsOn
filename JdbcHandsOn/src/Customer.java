import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Customer {
    String username;
    String password;
    private String balanceStatement;
    private String validStatement;


    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        balanceStatement = " select balance from bank where username=\"" + username + "\" and pass=\"" + password + "\" ;";
        validStatement = " select balance from bank where username=\"" + username + "\" and pass=\"" + password + "\" limit 1;";


    }

    int checkBalance() {
        int balance = -1;
        if (checkValidUser()) {
            Connection connection = BuildConnections.getConnection();
            try {

                assert connection != null;
                ResultSet rs = connection
                        .createStatement()
                        .executeQuery(this.balanceStatement);
                while (rs.next())
                    balance = (rs.getInt(1));


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return balance;
    }

    void deposit(int amount) throws SQLException {
        if (checkValidUser()) {
            int bal = checkBalance();
            String query1 = "update bank set balance = ? where username = ? and pass=?";
            PreparedStatement preparedStmt = Objects.requireNonNull(BuildConnections.getConnection()).prepareStatement(query1);
            preparedStmt.setInt(1, bal + amount);
            preparedStmt.setString(2, username);
            preparedStmt.setString(3, password);
            preparedStmt.executeUpdate();

            if (bal + amount == checkBalance()) {
                System.out.println("Deposited ₹" + amount);
                String query2 = "Insert into Transaction (userId, trans) values (?,?)";
                PreparedStatement preparedStmt2 = Objects.requireNonNull(BuildConnections.getConnection()).prepareStatement(query2);
                preparedStmt2.setString(1, username);
                preparedStmt2.setInt(2, amount);
                preparedStmt2.executeUpdate();
            } else {
                System.out.println("Failed to deposit");
            }

        }
    }

    void withdraw(int amount) throws SQLException {
        if (checkValidUser()) {
            int bal = checkBalance();
            int diff = bal - amount;
            if (diff <= 0) {
                System.out.println("Insufficient fund");
            } else if (diff < 10000) {
                System.out.println("Minimum ₹10,000 required");
            } else {
                String query = "update bank set balance=? where username=? and pass=?";
                PreparedStatement preparedStatement = BuildConnections.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, diff);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.executeUpdate();
                System.out.println("Balance after ₹" + amount + " withdrawal is : " + checkBalance());
            }
            if (checkBalance() != diff) {
                System.out.println("---- ₹" + amount + " Withdrawal Unsuccessful ! ----");
            } else {
                String query2 = "Insert into Transaction (userId, trans) values (?,?)";
                PreparedStatement preparedStmt2 = Objects.requireNonNull(BuildConnections.getConnection()).prepareStatement(query2);
                preparedStmt2.setString(1, username);
                preparedStmt2.setInt(2, amount * -1);
                preparedStmt2.executeUpdate();
            }
        }
    }

    boolean checkValidUser() {
        ResultSet rs = null;
        try {
            rs = BuildConnections.getConnection()
                    .createStatement()
                    .executeQuery(validStatement);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    void latestTransactions() throws SQLException {
        if (checkValidUser()) {
            String query = "select transactionID,trans,timestamp from transaction where userId=? order by transactionid DESC limit 5";
            PreparedStatement preparedStatement = BuildConnections.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Trans ID \t Amount \t Date");
            System.out.println("------------------------------------");
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "\t\t\t" + rs.getInt(2) + "\t\t" + rs.getDate(3));
            }
        } else {
            System.out.println("Not a valid user");
        }
    }
}
