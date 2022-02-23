import java.sql.*;
import java.util.*;

import java.sql.*;
import java.util.*;
public class Bankk
{
    public static void main(String args[])
    {
        int balance;
        double phone_number;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcom to XYZ bank");
        System.out.println("Enter customer name :- ");
        String name = sc.nextLine();
        System.out.println("Enter account number :- ");
        int account_number = sc.nextInt();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver is loading...");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/genpact","root","root");
            System.out.println("Connecting to database");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bank where Account_number = "+account_number);
            System.out.println("Data");
            while(rs.next())
            {
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getDouble(4));
            }
            int acNo=rs.getInt(1);
            if ( acNo == account_number )
            {
                int bal=rs.getInt(3);
                System.out.println("available balance :- "+bal);
                System.out.println("enter 1 for deposite and 2 for withdraw :- ");
                int ch = sc.nextInt();
                if(ch==1||ch==2)
                {
                    if(ch==1)
                    {
                        System.out.println("Enter the amount to deposite :- ");
                        int amt = sc.nextInt();
                        PreparedStatement pr =con.prepareStatement("update bank set balance =? where account_number =?");
                        pr.setInt(1, bal+amt);
                        pr.setInt(2, account_number);
                        pr.executeUpdate();
                        System.out.println(amt+" amount deposited");
                    }
                    if(ch==2)
                    {
                        if(bal<=10000)
                        {
                            System.out.println("Balance is less.");
                        }
                        else
                        {
                            System.out.println("Enter the amount to withdraw :- ");
                            int withdraw = sc.nextInt();
                            PreparedStatement pr1 =con.prepareStatement("update bank set balance =? where account_number =?");
                            pr1.setInt(1, bal-withdraw);
                            pr1.setInt(2, account_number-withdraw);
                            pr1.executeUpdate();
                            System.out.println(withdraw+" amount withdraw");
                        }
                    }
                }
                else
                {
                    System.out.println("You are not registred in bank XYZ");
                }
                con.close();
            }
        }
        catch(Exception e)
        {

        }
    }
}