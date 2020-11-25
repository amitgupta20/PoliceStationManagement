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

public class Murder extends Crime {

    public String weapon;
    
    public Murder() {
        super();
        weapon = "";
    }
    public Murder(String desc,String location,String weapon){
        super.crimeName = "Murder";
        this.weapon = weapon;
        super.location = location;
        super.description = desc;
        updateInMurderTable();
    }
    public int getID(){
        return super.crimeID;
    }
    private void updateInMurderTable(){
        try {
            super.updateCrimeTable();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Insert into Murder(crimeID,Weapon) values('" + (super.crimeID)+"','"+(this.weapon)+"');";
            stmt.executeUpdate(query);
            query = "Select max(crimeID) as newID from Murder;";
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

    public static String getMurderDetails(int crimeID) {
        String weapon = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PoliceStation", CreateTables.USER, CreateTables.PASS);
            Statement stmt = (Statement) con.createStatement();
            String query = "Select * from Murder where crimeID = '" + (crimeID) + "';";
            ResultSet rs =  stmt.executeQuery(query);
            rs.next();
            weapon = rs.getString("Weapon");
            //use setText or whatever you need
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return weapon;
    }

}
