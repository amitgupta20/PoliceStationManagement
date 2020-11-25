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


public class Person {
    public int personID;
    public String name;
    public String contactNumber;
    public String aadharCardNumber;
    
    public Person() {
        personID = 0;
        name = "";
        contactNumber = "";
        aadharCardNumber = "";
    }
    public Person(String name, String contactNumber, String aadharCardNumber) {
        if (!checkDuplicates(aadharCardNumber)) {
            this.name = name;
            this.aadharCardNumber = aadharCardNumber;
            this.contactNumber = contactNumber;
            personID = this.enterInPersonTable();
        }
        else {
            try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Person where AadharCardNumber = '"+(aadharCardNumber)+"';"; // primary key so multiple values not possible
            ResultSet rs = stmt.executeQuery(query); 
            rs.next();
            personID = rs.getInt("personID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
    }
    private int enterInPersonTable(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Person(Name,contact,AadharCardNumber) values('" + (name) + "','" + (contactNumber) + "','" + (aadharCardNumber)+"');";
            stmt.executeUpdate(query);
            query = "Select max(personID) as newID from Person;"; // primary key so multiple values not possible
            ResultSet rs = stmt.executeQuery(query); 
            rs.next();
            personID = rs.getInt("newID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // code to store details in table
        // will return the alloted ID primary key
        return personID;
    }
    public static Person getPersonDetails(int personID){
        Person temp = new Person();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Person where personID = '" + (personID) + "';";
            ResultSet rs = stmt.executeQuery(query); 
            rs.next();
            temp.personID = rs.getInt("personID");
            temp.name = rs.getString("Name");
            temp.contactNumber = rs.getString("contact");
            temp.aadharCardNumber = rs.getString("AadharCardNumber");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }  
        return temp;
    }
    public static boolean checkDuplicates(String aadharCardNumber) {
        int count = 0;
        try {
            if (aadharCardNumber.isEmpty())
                return false;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select count(*) as personCount from Person where AadharCardNumber = '" + (aadharCardNumber) + "';";
            ResultSet rs = stmt.executeQuery(query); 
            while (rs.next()) {
                count = rs.getInt("personCount");
            }
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return (count > 0);
    }
    public static int getPersonIDthroughAadhar(String aadhar) {
        int pID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Person where AadharCardNumber = '" + (aadhar) + "';";
            ResultSet rs = stmt.executeQuery(query); 
            rs.next();
            pID = rs.getInt("personID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }  
        return pID;
    } 
    public static void updateRecord(int id, String name, String contact, String aadharCardNumber) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Update Person set Name = '" + (name) + "',contact ='"+(contact)+"',AadharCardNumber = '"+(aadharCardNumber)+"'where personID ='"+(id)+"'; ";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public static void deleteRecord(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Delete from Person where personID = '" + (id) + "';";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }  
    public static void markAsSuspect(int crimeID, int personID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Suspects(personID,crimeID) values('"+(personID)+"','"+(crimeID)+"');";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public static void markAsWitness(int crimeID, int personID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Witness(personID,crimeID) values('"+(personID)+"','"+(crimeID)+"');";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public static void markAsVictim(int crimeID, int personID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Victims(personID,crimeID) values('"+(personID)+"','"+(crimeID)+"');";
            stmt.executeUpdate(query); 
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public static ResultSet getSuspects(int crimeID){
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Suspects where crimeID = '"+(crimeID)+"';";
            rs =  stmt.executeQuery(query);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }
    public static ResultSet getWitness(int crimeID){
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Witness where crimeID = '"+(crimeID)+"';";
            rs =  stmt.executeQuery(query);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }
    public static ResultSet getVictims(int crimeID) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Victims where crimeID = '"+(crimeID)+"';";
            rs =  stmt.executeQuery(query);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }
    
    public static int getCriminalRecord(String aadhar){//will return crimeID is want details then there is method to get crimedetails
        int id = 0,personID;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Person where AadharCardNumber = '"+(aadhar)+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            personID = rs.getInt("personID");
            query = "Select * from CriminalRecord where personID = '"+(personID)+"';";
            rs = stmt.executeQuery(query); 
            rs.next();
            id = rs.getInt("CrimeID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }
    public static boolean checkCriminalRecord(String aadhar){//will return crimeID is want details then there is method to get crimedetails
        int count = 0,personID;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Person where AadharCardNumber = '"+(aadhar)+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            personID = rs.getInt("personID");
            query = "Select count(*) as C from CriminalRecord where personID = '"+(personID)+"';";
            rs = stmt.executeQuery(query); 
            rs.next();
            count = rs.getInt("C");
            con.close();
        }
        catch (Exception e) {
        }
        return (count > 0);
    }
    public static int enterInPrisonerRecord(int personID,int crimeID){ //will return prisonerId
        int id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into PrisonerRecord(personID,crimeID) values('"+(personID)+"','"+(crimeID)+"');";
            stmt.executeUpdate(query); 
            query = "Select max(PrisonerID) as PID from PrisonerRecord;";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            id = rs.getInt("PID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }
    public static int checkPrisonerRecord(String aadhar){//will return crimeID is want details then there is method to get crimedetails
        int id = 0,personID;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Person where AadharCardNumber = '"+(aadhar)+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            personID = rs.getInt("personID");
            query = "Select * from PrisonerRecord where personID = '"+( personID )+"';";
            rs = stmt.executeQuery(query); 
            rs.next();
            id = rs.getInt("CrimeID");
            con.close();
        }
        catch (Exception e) {
        }
        return id;
    }
    
    public static int enterInCriminalRecord(int personID,int crimeID){// will return criminal id
        int id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into CriminalRecord(personID,crimeID) values('"+(personID)+"','"+(crimeID)+"');";
            stmt.executeUpdate(query); 
            query = "Select max(CriminalID) as newID from CriminalRecord;";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            id = rs.getInt("newID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }
}
