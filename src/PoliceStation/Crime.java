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

public class Crime {
    public String description;
    public String location;
    public String crimeName;
    public int crimeID;
    public String extra;

    public Crime() {
        crimeID = 0;
        description = "";
        location = "";
        crimeName = "";
    }
    public int getID(){
        return crimeID;
    }
    public Crime(String desc,String loc) {
        description = desc;
        location = loc;
        crimeName = "Other";
        updateCrimeTable();
    }
    public String getDescription(){
        return description;
    }
    protected final int updateCrimeTable(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Crime(crimeName,location,description) values('" + (crimeName) + "','" + (location)+"','"+(description)+"');";
            stmt.executeUpdate(query);
            query = "Select max(crimeID) as Mcrime from Crime;";//Primary key so multiple values not possible
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            crimeID = rs.getInt("Mcrime");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // code to store details in table
        // will return the alloted ID primary key
        return crimeID;
    }
    public static Crime getCrimeDetails(int crimeID){
        Crime temp = new Crime();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Crime where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            temp.crimeID = rs.getInt("crimeID");
            temp.crimeName = rs.getString("crimeName");
            temp.location = rs.getString("location");
            temp.description = rs.getString("description");
            // use setText for switch case
            String var = "other";
            if (temp.crimeName.equals("Kidnap")) {
                Kidnap temporaryKidnap = new Kidnap();
                var = temporaryKidnap.getKidnapDetails(crimeID);
            }
            else if (temp.crimeName.equals("Murder")) {
                Murder tempMurder = new Murder();
                var = tempMurder.getMurderDetails(crimeID);
            }
            else if (temp.crimeName.equals("RoadAccident")) {
                RoadAccident tempRoad = new RoadAccident();
                var = tempRoad.getRoadAccidentDetails(crimeID);
            }
            else if (temp.crimeName.equals("Robbery")) {
                Robbery tempRobbery = new Robbery();
                var = tempRobbery.getRobberyDetails(crimeID);
            }
            temp.extra = var;
            //use setText or whatever you need

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return temp;
    }
    public static void updateDetailsOfCrimeTable(int crimeID,String desc,String loc,String extra){
        try {
            String crimeName = "";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select crimeName from Crime where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()) {
                crimeName = rs.getString("crimeName");
            }
            query = "Update Crime set location = '"+(loc)+"',description = '"+(desc)+"' where crimeID = '" + (crimeID) + "';";
            stmt.executeUpdate(query);
            if (crimeName.equals("Kidnap")) {
                query = "Update Kidnap set carNumber = '"+(extra)+"' where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            else if (crimeName.equals("Murder")) {
                query = "Update Murder set weapon = '"+(extra)+"' where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            else if (crimeName.equals("RoadAccident")) {
                query = "Update RoadAccident set carNumber = '"+(extra)+"' where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            else if (crimeName.equals("Robbery")) {
                query = "Update Robbery set thingsStolen = '"+(extra)+"' where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }    
    }
    public static void deleteCrime(int crimeID){
        try {
            String crimeName;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select crimeName from Crime where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            crimeName = rs.getString("crimeName");
            if (crimeName.equals("Kidnap")) {
                query = "Delete from Kidnap where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            else if (crimeName.equals("Murder")) {
                query = "Delete from Murder where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            else if (crimeName.equals("RoadAccident")) {
                query = "Delete from RoadAccident where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            else if (crimeName.equals("Robbery")) {
                query = "Delete form Robbery where crimeID = '" + (crimeID) + "';";
                stmt.executeUpdate(query);
            }
            query = "Delete from Witness where crimeID = '" + (crimeID) + "';";
            stmt.executeUpdate(query);
            query = "Delete from Victims where crimeID = '" + (crimeID) + "';";
            stmt.executeUpdate(query);
            query = "Delete from Suspects where crimeID = '" + (crimeID) + "';";
            stmt.executeUpdate(query);
            query = "Delete from Crime where crimeID = '" + (crimeID) + "';";
            stmt.executeUpdate(query);

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }    
    }
}
