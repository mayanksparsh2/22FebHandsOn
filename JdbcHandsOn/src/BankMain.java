import java.sql.*;

class BankMain {
    public static void main(String args[]) throws SQLException {
        Admin admin=new Admin("admin","password") ;
       // admin.createUser("Sanjay Kumar",90000,"7739837201","004","004");
        Customer c = new Customer("002", "002");

        try {
            // System.out.println(c.checkBalance());
//            c.deposit(2000);
//            System.out.println("Available Balance is: "+ c.checkBalance());
//            c.withdraw(3000);

            c.latestTransactions();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}  