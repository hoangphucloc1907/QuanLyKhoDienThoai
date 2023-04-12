/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author sang
 */


public class ConnectionSQL {
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433; databaseName = Inventory; encrypt = false";
    String UserName = "sa";
    String Password= "123345";
    
    
    Connection conn = null;
    Statement st = null;

    public ConnectionSQL(){
        
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, UserName, Password);
            st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, UserName, Password);
            System.out.println("Connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    
    
}
   
