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

public class Kidnap extends Crime {
    public String carNumber;
    
    public Kidnap() {
        super();
        carNumber = "";
    }
    public Kidnap(String desc,String carNumber,String address){
        super.crimeName = "Kidnap";
        this.carNumber = carNumber;
        super.location = address;
        super.description = desc;
        updateInKidnapTable();

    }
    public int getID(){
        return super.crimeID;
    }
    private void updateInKidnapTable(){
        try {
            super.updateCrimeTable();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Kidnap(crimeID,carNumber) values('" + (super.crimeID)+"','"+(carNumber)+"');";
            stmt.executeUpdate(query);
            query = "Select max(crimeID) as newID from Kidnap;";
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

    public static String getKidnapDetails(int crimeID) {
        String carNumber = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Kidnap where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            carNumber = rs.getString("carNumber");
            //use setText or whatever you need
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return carNumber;
    }

}
