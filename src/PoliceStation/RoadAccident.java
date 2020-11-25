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

public class RoadAccident extends Crime{
    public String suspectedCarNumber;
    
    public RoadAccident() {
        super();
        suspectedCarNumber = "";
    }
    public RoadAccident(String location,String suspectedCarNumber,String desc){
        super.location = location;
        super.crimeName = "RoadAccident";
        this.suspectedCarNumber = suspectedCarNumber;
        super.description = desc;
        updateInRoadAccidentTable();
    }
    private void updateInRoadAccidentTable(){
        try {
            super.updateCrimeTable();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into RoadAccident(crimeID,carNumber) values('" + (super.crimeID)+"','"+(suspectedCarNumber)+"');";
            stmt.executeUpdate(query);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // code to store details in table
        // will return the alloted ID primary key
    }

    public static String getRoadAccidentDetails(int crimeID) {
        String suspectedCarNumber = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from RoadAccident where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            suspectedCarNumber = rs.getString("carNumber");
            
            //use setText or whatever you need
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return suspectedCarNumber;
    }
    public int getID(){
        return super.crimeID;
    }

}