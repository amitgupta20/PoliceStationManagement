/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PoliceStation;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class CreateTables {
    public static String USER = "";
    public static String PASS = "";
    public static void createAllTables() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "show databases like 'policestation';";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                query = "Create database PoliceStation;";
                stmt.executeUpdate(query);
                con.close();
                con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
                stmt = (Statement) con.createStatement();
                query = "Create table Person(personID int primary key auto_increment, Name varchar(55),Contact varchar(12), AadharCardNumber varchar(12));";
                stmt.executeUpdate(query);
                query = "Create table Police(policeID int primary key auto_increment, Name varchar(55),Contact varchar(12),Residence varchar(200), casesInvolved int,city varchar(35),password varchar(55));";
                stmt.executeUpdate(query);
                query = "Create table Crime(crimeID int primary key auto_increment, crimeName varchar(55),description varchar(500),location varchar(55));";
                stmt.executeUpdate(query);
                query = "Create table Witness(personID int,crimeID int,foreign key(personID) references Person(personID) on delete cascade, foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table Suspects(personID int,crimeID int,foreign key(personID) references Person(personID) on delete cascade, foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table Victims(personID int,crimeID int,foreign key(personID) references Person(personID) on delete cascade, foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table Kidnap(crimeID int, carNumber varchar(35),foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table Murder(crimeID int, Weapon varchar(55),foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table Robbery(crimeID int, thingsStolen varchar(55),foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table RoadAccident(crimeID int, carNumber varchar(35),foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table CriminalRecord(criminalID int primary key auto_increment,personID int,crimeID int,foreign key(personID) references Person(personID) on delete cascade, foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table PrisonerRecord(prisonerID int primary key auto_increment,personID int,crimeID int,foreign key(personID) references Person(personID) on delete cascade, foreign key(crimeID) references Crime(crimeID) on delete cascade);";
                stmt.executeUpdate(query);
                query = "Create table Complaint(complaintID int primary key auto_increment,policeID int,personID int,crimeID int,status varchar(55),password varchar(55),foreign key(personID) references Person(personID) on delete cascade, foreign key(crimeID) references Crime(crimeID) on delete cascade,foreign key(policeID) references Police(policeID) on delete cascade);";
                stmt.executeUpdate(query);
                con.close();
            }
            else {
                //To continue if database already exist, else create.
                throw new ArithmeticException();
            }
            
        }
        catch (ArithmeticException e) {    
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
    }
    
}
