/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PoliceStation;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JOptionPane;

public class Complaint {
    public int complaintID;
    public int policeID;
    public int crimeID;
    public int complainantID;
    public String complaintStatus;
    public int password;
    /*final public String ActiveCase = "Active Case";
    final public String Registered = "Registered";*/
    public Complaint() {
        complaintID = 0;
        policeID = 0;
        crimeID = 0;
        complainantID = 0;
        complaintStatus = "";
        password = 0;
    }
    public int getComplaintID(){
        return complaintID;
    }
    
    public static boolean checkAuthenticity(int complaintID, int password) {
        int pass = -1;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select Password from Complaint where complaintID =  '" + (complaintID) + "';";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                pass = rs.getInt("Password");
            }
            con.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return (pass == password);
    }
    public static void changeCaseStatus(int complaintID,String Status){
        //String Status = "Active Case";
        int policeID = 0;
        if(Status.equals("Closed")){
            try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select policeID from Complaint where complaintID = '"+(complaintID)+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            policeID = rs.getInt("policeID");
            query = "Update Police set casesInvolved = CasesInvolved - 1 where policeID = '" + (policeID) + "' ;";
            stmt.executeUpdate(query);
            con.close();
            }
            catch(Exception e) {
                System.out.println(""+e.getMessage());
            }
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Update Complaint Set status = '"+(Status)+"' where complaintID = '"+(complaintID)+"';";
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public Complaint(int crimeID,int complainantID ){
        try{
            this.policeID = 0;
            this.complainantID = complainantID;
            this.crimeID = crimeID;
            complaintStatus = "Registered";
            complaintID = updateInComplaintTable();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private int updateInComplaintTable(){
        Random rand = new Random();
        password = rand.nextInt(10000);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Complaint(personID,crimeID,status,Password) values('"+(complainantID)+"','" + (crimeID) + "','"+(complaintStatus)+"','"+(password)+"');";
            stmt.executeUpdate(query);
            query = "Select max(complaintID) as Mcomp from Complaint;";
            ResultSet rs = stmt.executeQuery(query); 
            rs.next();//to approach row
            complaintID = rs.getInt("Mcomp");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        //Code to store in table
        return complaintID;
    }
    public static ResultSet getAllotedComplaint(int policeID){
        ResultSet rs = null;
        try {           
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Complaint where policeID = '"+(policeID)+"';";//complaints having policeID = 0 is not alloted
            rs = stmt.executeQuery(query); 
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
        // Find cases which is not alloted to police
    }
    public static ResultSet getAndAllotComplaint(){
        ResultSet rs = null;
        try {           
            int temppoliceID = 0; 
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Complaint where policeID is null;";//complaints having policeID = 0 is not alloted
            rs = stmt.executeQuery(query); 
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
        // Find cases which is not alloted to police
    }
    public static String getComplaintStatus(int complaintID){
        String complaintStatus = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select status from Complaint where complaintID = '"+(complaintID)+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            complaintStatus = rs.getString("status");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return complaintStatus;
        //Return complaint Status extracted from database
    }
    public static int getComplainantID(int complaintID) {
        int pID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select personID from Complaint where complaintID = '"+(complaintID)+"';";
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
    public static int getCrimeID(int complaintID) {
        int cID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select crimeID from Complaint where complaintID = '"+(complaintID)+"';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            cID = rs.getInt("crimeID");
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cID;
    }
    public static void assignPolice(int complaintID,int policeID) {
        int cases = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Update Complaint set policeID = '"+(policeID)+"' where complaintID = '" + (complaintID) + "';";
            stmt.executeUpdate(query);
            query = "Select casesInvolved from Police where policeID = '"+(policeID)+"';";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                cases = rs.getInt("casesInvolved");
            }
            cases++;
            query = "Update Police set casesInvolved = '"+(cases)+"' where policeID = '" + (policeID) + "';";
            stmt.executeUpdate(query);
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
