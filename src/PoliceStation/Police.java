/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PoliceStation;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Police {

    public int policeID;
    public String name;
    public String contactNumber;
    public String city;
    public String residenceAddress;
    public int casesInvolved;
    public String password;
    public Police() {
        policeID = 0;
        name = "";
        contactNumber = "";
        city = "";
        residenceAddress = "";
        casesInvolved = 0;
        password = "";
    }
    public Police(String name, String contactNumber, String residenceAddress, String city,String pass) {
            casesInvolved = 0;
            this.name = name;
            this.contactNumber = contactNumber;
            this.residenceAddress = residenceAddress;
            this.city = city;
            password = pass;
            this.entryInPoliceTable();
    }
    private void entryInPoliceTable() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Police(Name,Contact,Residence,casesInvolved,city,password) values('" + (name) + "','" + (contactNumber) + "','" + (residenceAddress) + "','" + (casesInvolved) + "','" + (city) +"','"+(password)+"');";
            stmt.executeUpdate(query);
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
    public static int getFreePoliceID(){
        //Code to find first police involved in least number of cases in Police table
        int policeID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select PoliceID,min(casesInvolved) from Police limit 1;";// As limit is one so only one or first police will be returned
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            policeID = rs.getInt("policeID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return policeID;
    }
    public static int getID(){
        int pID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select max(policeID) as pID from Police;";//Primary key so multiple values not possible
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            pID = rs.getInt("pID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pID;
    }
    public static ResultSet searchByCity(String checkcity) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation",CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String query = "Select * from Police where city = '" + (checkcity) + "';";
            rs = stmt.executeQuery(query); 
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }
    public static ResultSet searchByName(String checkname) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation",CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String query = "Select * from Police where Name = '" + (checkname) + "';";
            rs = stmt.executeQuery(query); 
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }
    public static Police searchByID(int id) {
        Police temp = new Police();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Police where policeID = '" + (id) + "';";
            ResultSet rs = stmt.executeQuery(query); 
            while (rs.next()) {
                temp.policeID = rs.getInt("policeID");
                temp.name = rs.getString("Name");
                temp.contactNumber = rs.getString("Contact");
                temp.residenceAddress = rs.getString("Residence");
                temp.casesInvolved = rs.getInt("casesInvolved");
                temp.city = rs.getString("city");
            }
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return temp;
    }
    public static boolean checkLogin(int id, String pass) {
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Police where policeID = '" + (id) + "';";
            ResultSet rs = stmt.executeQuery(query); 
            while (rs.next()) {
                password = rs.getString("password");
            }
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pass.equals(password);
    }

    public static void updateRecord(int id, String name, String city, String residence, String contact, int casesInvolved) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Update Police set Name = '" + (name) + "',city ='"+(city)+"',Contact ='"+(contact)+"',Residence ='"+(residence)+"',casesInvolved = '"+(casesInvolved)+"'where policeID ='"+(id)+"'; ";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            System.out.println(""+e.getMessage());
        }
    }
    public static void deleteRecord(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Delete from Police where policeID = '" + (id) + "';";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
