/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startfx;
import java.sql.*;
/**
 *
 * @author SauravGupta
 */
public class Mysql {
    
        
     private Connection con;
    //private Statement st;
   // private ResultSet rs;
    
    public Mysql()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database","root","Zxcvmnb987*");
            System.out.println("Connected");
            
        }catch(Exception e)
        {
            System.out.println("Excption");
        }
    }
    
    
    
}


