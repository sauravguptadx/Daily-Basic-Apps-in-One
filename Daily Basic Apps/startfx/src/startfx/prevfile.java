/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startfx;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author SauravGupta
 */
public class prevfile {
    
    
    String prefile[];
    String newprefile[];
    
     Connection connectionprev;
    Statement statementprev;
    ResultSet resultprev;
    
    Connection connectionprev2;
    Statement statementprev2;
    ResultSet resultprev2;
    
    prevfile()
    {
        MySQLprev();
        prefile();
    }
    
    
    public void MySQLprev(){
        String dx[] = new String[1000];
        int i = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            connectionprev = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123","root","Zxcvmnb987*");
            connectionprev2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123","root","Zxcvmnb987*");
            statementprev = connectionprev.createStatement();
            statementprev2 = connectionprev2.createStatement();
            System.out.println("Connected in prevfile");
            
            
        }catch(Exception exc)
        {
            System.out.println("Excption");
        }
        
       /* int k=0;
        while(h.prefile[k] !=null)
        {
            System.out.println("degen" + h.prefile[k]);
        }*/
        
        
        
    }
    
    Dx h = new Dx();
    
    
      public void prefile()
 {
     int count = 1;
     int incount = count;
     prefile = null;
     prefile = new String[100];
     int i = 0, k=0;
        
       try{
           /*  while(prefile[k] != null)
        {
            prefile[k] = null;
            k++;
        }*/
                String query = "Select eventname, public, date from events where public = '1' AND date = ' "+ h.yy +"-"+(h.mm+1)+"-"+ h.dd + " '  ";
                resultprev2 = statementprev2.executeQuery(query);
                //System.out.println("Records");
                while (resultprev2.next())
                {
                   // String uname = resultprev2.getString("username");
                    String name = resultprev2.getString("eventname");
                    prefile[i] = name;
                    System.out.println("name "+name);
                    i++;
                }
                int j=0;
        while(prefile[j] != null)
        {
            //delete[] newprefile;
            newprefile = new String[count];
            newprefile[count-1] = prefile[j];
            System.out.println("dx " + prefile[j]);
            j++;
            count++;
        }
        for (int a=0; prefile[a] != null; a++ )
        {
            newprefile[a] = prefile[a];
        }
        //delete [] prefile;
        
        
                
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in 2" + e);
            }
        
 }    
    
}
