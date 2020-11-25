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

public class Robbery extends Crime{
    String thingsStolen;
    
    public Robbery() {
        super();
        thingsStolen = "";
    }
    public Robbery(String location,String desc,String thingsStolen){
        super.crimeName = "Robbery";
        this.thingsStolen = thingsStolen;
        super.location = location;
        super.description = desc;
        updateInOtherCrimeTable();
    }
    public int getID(){
        return super.crimeID;
    }
    private void updateInOtherCrimeTable(){
        try {
            super.updateCrimeTable();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Robbery(crimeID,ThingsStolen) values('" + (super.crimeID)+"','"+(thingsStolen)+"');";
            stmt.executeUpdate(query);
            query = "Select max(crimeID) as newID from Robbery;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                super.crimeID = rs.getInt("newID");
            }
            con.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // code to store details in table
        // will return the alloted ID primary key
    } 

    public static String getRobberyDetails(int crimeID) {
        String thingsStolen = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Robbery where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            thingsStolen = rs.getString("ThingsStolen");
            //use setText or whatever you need
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return thingsStolen;
    }


}
