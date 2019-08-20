/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startfx;
import java.io.File;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
/**
 *
 * @author SauravGupta
 */


public class Startfx extends Application {
    
    
    Connection connection;
    Statement statement;
    ResultSet result;
    
    Connection connection2;
    Statement statement2;
    ResultSet result2;
    

    Pane pane;
    
    
    String rfirstname;
    String rlastname;
    String rusername;
    String rpassword;
    String rcpassword;
    
    String lusername;
    String lpassword;
    
    String diaryuser;
    
    
    Pane login()
    {
        Pane login = new Pane();
        
        
        login.setPrefSize(650, 650);
        login.setLayoutX(0.0);
        login.setLayoutY(0.0);
        login.setStyle("-fx-background-color: DARKGREY;");
        
        
       
        Label username = new Label("Enter Username:");
        username.setPrefSize(200, 50);
        username.setLayoutX(120);
        username.setLayoutY(140);
        username.setFont(new Font("Comic Sans MS", 20));
        
        
        TextField textuser = new TextField();
        textuser.setPrefSize(200, 30);
        textuser.setLayoutX(320);
        textuser.setLayoutY(150);
        textuser.setPromptText("Enter Username (Case-Sensitive)");
     
        
        textuser.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
                 diaryuser = textuser.getText();
                 lusername = textuser.getText();
                 System.out.println(lusername);
                 
              }
});
        
        
        Label pass = new Label("Enter Password:");
        pass.setPrefSize(200, 50);
        pass.setLayoutX(120);
        pass.setLayoutY(220);
        pass.setFont(new Font("Comic Sans MS", 20));
        
        PasswordField textpass = new PasswordField();
        textpass.setPrefSize(200, 30);
        textpass.setLayoutX(320);
        textpass.setLayoutY(230);
        textpass.setPromptText("Enter Password (Case-Sensitive)");
        
        textpass.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                lpassword = textpass.getText();
                 System.out.println(lpassword);
             
              }
});
        
        Button loginbut = new Button("Log In");
        loginbut.setPrefSize(100, 50);
        loginbut.setLayoutX(250);
        loginbut.setLayoutY(300);
        
        loginbut.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
                diaryuser = textuser.getText();
                 textuser.getText();
                 if(textuser.getText().isEmpty() == true)
                    JOptionPane.showMessageDialog(null, "Usename cannot be empty");
                    
                
                else if(textpass.getText().isEmpty() == true)
                    JOptionPane.showMessageDialog(null, "Password cannot be empty!!");
                 
             if(textuser.getText().isEmpty() == false &&  textpass.getText().isEmpty() == false )   { 
            try{
                
                if(textuser.getText().isEmpty() == true)
                    JOptionPane.showMessageDialog(null, "Usename cant be NULL");
                    
                textpass.getText();
                String query = "Select * from users_info";
                result = statement.executeQuery(query);
                //System.out.println("Records");
                while (result.next())
                {
                    if(textuser.getText().matches(result.getString("username")) == true){
                            
                        if(textpass.getText().matches(result.getString("password")) == true)
                        {
                            pane.getChildren().remove(login);
                            pane.getChildren().add(toplogout(textuser.getText()));
                            pane.getChildren().addAll(pubfilespane(),logintextarea());
                           // pane.getChildren().add(createvent());
                            
                            JOptionPane.showMessageDialog(null, "Congratulations!!! \n Login Successful");
                            break;
                        }
                        
                        else
                        {
                           JOptionPane.showMessageDialog(null, "ERROR !!! \n Incorrect Password");
                           break;
                        }
                       
                    }
                }
                if(textuser.getText().matches(result.getString("username")) == false)
                 JOptionPane.showMessageDialog(null, "ERROR !!! \n Username Doesnt Exist!!");
                alarm();
                
            }catch(Exception ex)
            {
                System.out.println("Exception again" + ex);
                //if(textuser.getText().matches(resultprev.getString("username")) == false)
                JOptionPane.showMessageDialog(null, "ERROR !!! \n Username Doesnt Exist!!");
            }
             }
             
              }
                    
});
        
        
        
        Label acc = new Label("No Account ??");
        acc.setPrefSize(200, 50);
        acc.setLayoutX(250);
        acc.setLayoutY(400);
        acc.setFont(new Font("Comic Sans MS", 20));
        
        Label create = new Label("Create One ;)");
        create.setPrefSize(200, 50);
        create.setLayoutX(250);
        create.setLayoutY(430);
        create.setFont(new Font("Comic Sans MS", 20));
        
        Button signupbut = new Button("Sign Up");
        signupbut.setPrefSize(100, 50);
        signupbut.setLayoutX(250);
        signupbut.setLayoutY(490);
        signupbut.setFont(new Font("Comic Sans MS", 15));
        signupbut.setTextFill(Color.BLUE);
        
             signupbut.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                pane.getChildren().add(signup());
                pane.getChildren().remove(login);
             
              }
});
        
        
        login.getChildren().addAll( /*datePicker,*/ username, textuser, pass, textpass,loginbut, acc, create, signupbut);
        
   
        return login;
    }
   
    
    Pane signup()
    {
        
        Pane signup = new Pane();

        signup.setPrefSize(650, 650);
        signup.setLayoutX(0.0);
        signup.setLayoutY(0.0);
        signup.setStyle("-fx-background-color: yellow;");
        
        Label detail = new Label("Enter Your Details!!");
        detail.setPrefSize(400, 70);
        detail.setLayoutX(50);
        detail.setLayoutY(60);
        detail.setFont(new Font("Comic Sans MS", 40));
        
        Label fname = new Label("First Name:");
        fname.setPrefSize(200, 50);
        fname.setLayoutX(50);
        fname.setLayoutY(150);
        fname.setFont(new Font("Comic Sans MS", 20));
        
        TextField ftname = new TextField();
        ftname.setPrefSize(200, 30);
        ftname.setLayoutX(250);
        ftname.setLayoutY(160);
        ftname.setPromptText("Enter your first name");
        
        
        
        ftname.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                rfirstname = ftname.getText();
                 System.out.println(rfirstname);
             
              }
});
        
        Label lname = new Label("Last Name:");
        lname.setPrefSize(200, 50);
        lname.setLayoutX(50);
        lname.setLayoutY(200);
        lname.setFont(new Font("Comic Sans MS", 20));
        
        TextField ltname = new TextField();
        ltname.setPrefSize(200, 30);
        ltname.setLayoutX(250);
        ltname.setLayoutY(210);
        ltname.setPromptText("Enter your last name");
        
        //rlastname = ltname.getText();
        // System.out.println(rlastname);
        
        ltname.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                rlastname = ltname.getText();
                 System.out.println(rlastname);
             
              }
});
        
        Label uname = new Label("Username:");
        uname.setPrefSize(200, 50);
        uname.setLayoutX(50);
        uname.setLayoutY(250);
        uname.setFont(new Font("Comic Sans MS", 20));
        
        TextField utname = new TextField();
        utname.setPrefSize(200, 30);
        utname.setLayoutX(250);
        utname.setLayoutY(260);
        utname.setPromptText("Non-Empty and Case-Sensitive");
      
        
        utname.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                rusername = utname.getText();
                 System.out.println(rusername);
             
              }
});
        
        Label pword = new Label("Password:");
        pword.setPrefSize(200, 50);
        pword.setLayoutX(50);
        pword.setLayoutY(300);
        pword.setFont(new Font("Comic Sans MS", 20));
        
        PasswordField ptword = new PasswordField();
        ptword.setPrefSize(200, 30);
        ptword.setLayoutX(250);
        ptword.setLayoutY(310);
        ptword.setPromptText("Non-Empty and Case-Sensitive");
        
        ptword.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                rpassword = ptword.getText();
                 System.out.println(rpassword);
             
              }
});
        
        
        Label cpword = new Label("Confirm Password:");
        cpword.setPrefSize(200, 50);
        cpword.setLayoutX(50);
        cpword.setLayoutY(350);
        cpword.setFont(new Font("Comic Sans MS", 20));
        
        PasswordField cptword = new PasswordField();
        cptword.setPrefSize(200, 30);
        cptword.setLayoutX(250);
        cptword.setLayoutY(360);
        cptword.setPromptText("Re-Enter Password");
        
        cptword.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {

                rcpassword = cptword.getText();
                 System.out.println(rcpassword);
             
              }
});
        
        
        Button register = new Button("Register");
        register.setPrefSize(150, 50);
        register.setLayoutX(160);
        register.setLayoutY(450);
        register.setFont(new Font("Comic Sans MS", 15));
        
        //static int dxx = 0;
                
        
        register.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
                 diaryuser = utname.getText();
            ftname.getText();
            ltname.getText();
            utname.getText();
            ptword.getText();
            cptword.getText();
            
             if(utname.getText().isEmpty() == true)
                    JOptionPane.showMessageDialog(null, "Username cannot be empty");
             
             else if(ptword.getText().isEmpty() == true)
                    JOptionPane.showMessageDialog(null, "Password cannot be empty");
            
             else{
                 utname.getText();
            try{
            if(ptword.getText().matches(cptword.getText()) == true){
            System.out.println(utname.getText() + ftname.getText() + ltname.getText() + ptword.getText());    
             String sql2 = "INSERT INTO users_info (username, name, lastname, password) VALUES ('" + utname.getText() + "', '" + ftname.getText() + "', '" + ftname.getText() + "', '" + ptword.getText() + "')";
               
             System.out.println(sql2);
                connection.createStatement().executeUpdate(sql2);
                JOptionPane.showMessageDialog(null, "Congratulations !!! \n You have successfully registered for the Diary ");
                   System.out.println("it has been add");
                pane.getChildren().remove(signup);
                pane.getChildren().remove(login());
                
                pane.getChildren().addAll(toplogout(utname.getText()), pubfilespane(), logintextarea());   
                 
               
        }
            else{
                System.out.println("Wrong password");
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            }
                
            }catch(Exception excep)
            {
                System.out.println("going in exception");
            //   if( excep.equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '"+ rusername + "' for key 'PRIMARY'") == true)
               // System.out.println("Exception setdata" + excep);
               // Dialogs.
                JOptionPane.showMessageDialog(null, "Username already exists!! \n Please choose different username");
            }
                 
                 System.out.println("clicked");
              }
             }
});
       
        Button goback = new Button("Go Back");
        goback.setPrefSize(100, 50);
        goback.setLayoutX(0);
        goback.setLayoutY(0);
        goback.setFont(new Font("Comic Sans MS", 15));
        
        
        
        goback.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             /*panel.remove(panel3);
                          panel.add(login());
                          panel.revalidate();
                          panel.repaint();*/
             String name = goback.getText();
            // String name = .toString();
                 System.out.println(name);
                 pubfilespane().setVisible(false);
                pane.getChildren().removeAll( pubfilespane(), signup);
               // pane.getChildren().remove(pubfilespane());
                pane.getChildren().addAll(login(), coverprevfilespane());
             
    }
});
        signup.getChildren().addAll(detail, fname, ftname, lname, ltname,uname, utname, pword, ptword, cpword, cptword, register, goback);
        
       
        return signup;
        
    }
    
    
    Pane toplogout(String todisp)
    {
        Pane aflogin = new Pane();
        
        aflogin.setPrefSize(650, 120);
        aflogin.setLayoutX(0.0);
        aflogin.setLayoutY(0.0);
        aflogin.setStyle("-fx-background-color: yellow;");
        
        Label dispuser = new Label(todisp);
        dispuser.setPrefSize(300, 50);
        dispuser.setLayoutX(500);
        dispuser.setLayoutY(5);
        dispuser.setFont(new Font("Comic Sans MS", 20));
        
         
        Button logout = new Button("Log Out");
        logout.setPrefSize(100, 50);
        logout.setLayoutX(530);
        logout.setLayoutY(50);
        logout.setFont(new Font("Comic Sans MS", 15));
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
          
                pubfilespane().setVisible(false);
                pane.getChildren().remove(pubfilespane());
                pane.getChildren().remove(aflogin);
                pane.getChildren().addAll(login(), coverprevfilespane());
             
    }
});
        
       
        Label prevfile = new Label("Your Events");
        prevfile.setPrefSize(200, 50);
        prevfile.setLayoutX(50);
        prevfile.setLayoutY(5);
        prevfile.setFont(new Font("Serif", 25));
        
        
        //String [] events = {"Text", "Picture", "Notification", "Alarm"}; 
        
        Button prevfiles = new Button("Show Events");
        prevfiles.setPrefSize(150, 40);
        prevfiles.setLayoutX(50);
        prevfiles.setLayoutY(60);
        prevfiles.setStyle("-fx-font: 20px \"Serif\";");
        
        
           prevfiles.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
              pane.getChildren().add(showevents());
        
             
              
             }            
});
        
     
           
        Label notification = new Label("Notifications");
        notification.setPrefSize(200, 50);
        notification.setLayoutX(300);
        notification.setLayoutY(5);
        notification.setFont(new Font("Serif", 25));
        
        Button     notify = new Button("Notification");
        notify.setPrefSize(150, 40);
        notify.setLayoutX(300);
        notify.setLayoutY(60);
        notify.setStyle("-fx-font: 20px \"Serif\";");
      
        notify.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
              pane.getChildren().add(notifevent());
        
             
              
             }            
});
        
           
           
        
         aflogin.getChildren().addAll(dispuser, logout, prevfile, prevfiles, notification, notify);
        
        return aflogin;
        
    }
    
    
    TextArea textareaget;
    
    Pane logintextarea()
    {
        Pane lgout = new Pane();
        lgout.setPrefSize(650, 530);
        lgout.setLayoutX(0.0);
        lgout.setLayoutY(120.0);
        lgout.setStyle("-fx-background-color: DARKGREY;");
        
        Label dispuser = new Label("Welcome !!!");
        dispuser.setPrefSize(300, 100);
        dispuser.setLayoutX(300);
        dispuser.setLayoutY(100);
        dispuser.setFont(new Font("Comic Sans MS", 50));
        
        textareaget = new TextArea();
        textareaget.setPrefSize(560, 370);
        textareaget.setLayoutX(40.0);
        textareaget.setLayoutY(60.0);
        textareaget.setStyle("-fx-background-color: ORANGE;");
        textareaget.setFont(new Font("Comic Sans MS", 27));
        textareaget.setWrapText(true);
       
        //System.out.println(textareaget);
        
        Button save = new Button("Save");
        save.setPrefSize(120, 50);
        save.setLayoutX(300);
        save.setLayoutY(450);
        save.setFont(new Font("Comic Sans MS", 25));
        
        if(textareaget.getText().isEmpty()==true)
            save.setDisable(true);
        
        
          textareaget.setOnKeyTyped(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                save.setDisable(false);
                if(textareaget.getText().isEmpty()==true)
                    save.setDisable(true);
                
            }

        });
        
        
        
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(120, 50);
        cancel.setLayoutX(470);
        cancel.setLayoutY(450);
        cancel.setFont(new Font("Comic Sans MS", 25)); 
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         
                //pane.getChildren().removeAll( pubfilespane());
               // pane.getChildren().remove(pubfilespane());
                //pane.getChildren().addAll(login(), coverprevfilespane());
                save.setDisable(true);
                save.setVisible(false);
                cancel.setDisable(true);
                cancel.setVisible(false);
                textareaget.setDisable(true);
                textareaget.setVisible(false);
             
    }
});
        
        
        save.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
                 pane.getChildren().addAll(createvent());
                 evtype.setVisible(false);
                 eventype.setDisable(true);
                 eventype.setVisible(false);
                 create.setDisable(true);
                 create.setVisible(false);
                 savevent.setDisable(false);
                 savevent.setVisible(true);
                 eventname.setText("File Name");
                 savevent.setText("Save File");
                 
                 
                 savevent.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent eds) {
                 
                 texteventname.getText();
            textdate.getText();
            
           
            c.button_();
            System.out.println(" Date " +c.yy + "-" + c.mm + "-" + c.dd);
            textdate.setText(c.yy + "-" + c.mm + "-" + c.dd);
            
            int tihh = Integer.parseInt(hour.getSelectionModel().getSelectedItem().toString());
            int timm = Integer.parseInt(min.getSelectionModel().getSelectedItem().toString());
            int tiss = Integer.parseInt(sec.getSelectionModel().getSelectedItem().toString());
            
            
            try{
             
            String crevent;    
                
             crevent = "INSERT INTO events (username, eventname, date, time, text, public) VALUES ('" + diaryuser + "', '" + texteventname.getText() + "', '" + c.yy + "-" +(c.mm +1)+ "-" + c.dd + "', '" +tihh+":"+timm+":"+tiss+ "', '"+textareaget.getText()+"', '"+publicflag+"')";
               
            
             System.out.println(crevent);
                connection.createStatement().executeUpdate(crevent);
                JOptionPane.showMessageDialog(null, "Congratulations !!! \n File Saved Successfully !!! ");
                   System.out.println("it has been add");
               
               
        }
                
            catch(Exception excep)
            {
                System.out.println("going in exception " + excep);
                JOptionPane.showMessageDialog(null, "EventName already exists!! \n Please choose different EventName");
            }
                 

            } 
             
                 });
             }
                 
                
});
        
        
        
      
      
      
         //c.button_();   
        c.prevfiles.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
                 c.button_();
                 pane.getChildren().add(c.pubtable());
             }
        });
            
       
        lgout.getChildren().addAll(textareaget, save, cancel);
        
        
        
        
        
       // lgout.getChildren().addAll(dispuser);
        
        return lgout;
        
    }
    
  
    
    
    
    
    Label evtype;
    ChoiceBox eventype;
    Button create;
    Button savevent;
    Label eventname;
    
    
    TextField texteventname;
    int publicflag;
    TextField textdate;
    ChoiceBox hour;
    ChoiceBox min;
    ChoiceBox sec;
    
    RadioButton priv;
    RadioButton pub;
    
    String dxeventname;
    
    MediaPlayer mediaPlayer;
    public void checkalarm(int a, int b)
    {
    String ssound = "C:\\Users\\SauravGupta\\Desktop\\songs\\DX.mp3";
    
    Media sound = new Media(new File(ssound).toURI().toString());
    mediaPlayer= new MediaPlayer(sound);
        Thread t = new Thread(){
            
            public void run(){
                while(true){
                    Calendar d = new GregorianCalendar();
                    int hours = d.get(Calendar.HOUR);
                    int mins = d.get(Calendar.MINUTE);
                    if(a ==hours && b == mins)
                    {
       // 
                        mediaPlayer.play();
                        
                        
                        Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              //Run on UI thread
                              Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    pane.getChildren().add(alarmev());                                 
                                }
                            });
                          }
                        }, 1000);   
                
                        
                        
    
                        System.out.println("Alarm working");
                        break;
                    }
                    
                }
            }
            
        };
        t.setPriority(Thread.MIN_PRIORITY);
       
        t.start();
        
    }
    
    
    public void alarm()
    {
        try{
                System.out.println("diaryuser " + diaryuser);
                String query = "Select * from events where username = '"+diaryuser+"' AND  date = '"+ c.yy +"-"+(c.mm+1)+"-"+ c.dd + "' AND alarm = '1' ";
                result2 = statement2.executeQuery(query);
                System.out.println(query);
                while (result2.next())
                {   System.out.println("Working");
                   // String uname = result2.getString("username");
                    String time = result2.getString("time");
                    System.out.println(time);
                    
                    
                    char h[] = new char[2];
                    char m[] = new char[2];
                    h[0] = time.charAt(0);
                    h[1] = time.charAt(1);
                    String nh = new String(h);
                    System.out.println("new h "+ nh);
                    
                    m[0] = time.charAt(3);
                    m[1] = time.charAt(4);
                    String nm = new String(m);
                    System.out.println("new m "+ nm);
                    
                    int nnh = Integer.parseInt(nh);
                    int nnm = Integer.parseInt(nm);
                    
                    
                    Calendar dd = new GregorianCalendar();
                    int cho = dd.get(Calendar.HOUR);
                    int cmi = dd.get(Calendar.MINUTE);
                    System.out.println("sys time"+cho+ " " + cmi);
                    
                    checkalarm(nnh-12, nnm);
                    
                }
                System.out.println("try worked");
        
            }catch(Exception e)
            {
                System.out.println("exception in alarm " + e);
            }
        
    }
    
    Button stop;
    
    Pane alarmev()
    {
        Pane textevent = new Pane();
        
        textevent.setPrefSize(650, 530);
        textevent.setLayoutX(0.0);
        textevent.setLayoutY(120.0);
        textevent.setStyle("-fx-background-color: ORANGE;");
        
         Label username = new Label("ALARM RINGING");
        username.setPrefSize(500, 50);
        username.setLayoutX(120);
        username.setLayoutY(140);
        username.setFont(new Font("Comic Sans MS", 40));
    
        
        stop = new Button("Stop");
        stop.setPrefSize(120, 50);
        stop.setLayoutX(300);
        stop.setLayoutY(450);
        stop.setFont(new Font("Comic Sans MS", 25));
        
        
        
        Button cancel = new Button("Exit");
        cancel.setPrefSize(120, 50);
        cancel.setLayoutX(470);
        cancel.setLayoutY(450);
        cancel.setFont(new Font("Comic Sans MS", 25)); 
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         
               
                pane.getChildren().remove(textevent);
                mediaPlayer.stop();
                
                 Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              //Run on UI thread
                              Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    alarm();                                 
                                }
                            });
                          }
                        }, 60000);
               
             
    }
});
        stop.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         
               mediaPlayer.stop();
               // pane.getChildren().remove(textevent);
               Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              //Run on UI thread
                              Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    alarm();                                 
                                }
                            });
                          }
                        }, 60000);
               
             
    }
});
        
        
        
        
       
        textevent.getChildren().addAll(cancel, stop, username);
        
        return textevent;
        
    }
    
    
    Pane createvent()
    {
        Pane newevent = new Pane();
        
        
        newevent.setPrefSize(650, 530);
        newevent.setLayoutX(0.0);
        newevent.setLayoutY(120.0);
        newevent.setStyle("-fx-background-color: ORANGE;");
        
        evtype = new Label("Event Type");
        evtype.setPrefSize(200, 50);
        evtype.setLayoutX(25);
        evtype.setLayoutY(40);
        evtype.setFont(new Font("Comic Sans MS", 25));
        
        
        String [] events = {"Text", "Notification", "Alarm"}; 
        
        eventype = new ChoiceBox();
        eventype.setPrefSize(200, 40);
        eventype.setLayoutX(250);
        eventype.setLayoutY(25);
        eventype.setStyle("-fx-font: 25px \"Serif\";");
        eventype.getItems().addAll(events);
        eventype.getSelectionModel().select(events[0]);
        
        
        eventype.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             int i = eventype.getSelectionModel().getSelectedIndex();
            
             if(i !=0)
             {
                 priv.setDisable(true);
                 pub.setDisable(true);
             }
    }
});
        
        
        eventname = new Label("Event Name");
        eventname.setPrefSize(200, 50);
        eventname.setLayoutX(30);
        eventname.setLayoutY(110);
        eventname.setFont(new Font("Comic Sans MS", 25));
        
       
        texteventname = new TextField();
        texteventname.setPrefSize(250, 40);
        texteventname.setLayoutX(250);
        texteventname.setLayoutY(110);
        texteventname.setPromptText("Enter EventName (Case-Sensitive)");
        
        
        Label date = new Label("Date");
        date.setPrefSize(200, 50);
        date.setLayoutX(40);
        date.setLayoutY(190);
        date.setFont(new Font("Comic Sans MS", 25));
        
       
        textdate = new TextField();
        textdate.setPrefSize(250, 40);
        textdate.setLayoutX(250);
        textdate.setLayoutY(190);
        c.button_();
        textdate.setText(c.yy + "-" + (c.mm+1) + "-" + c.dd);
        //textdate.setTextFormatter(("Comic Sans MS", 25));
        textdate.setFont(new Font("Comic Sans MS", 20));
        
        
        Label time  = new Label("Time");
        time.setPrefSize(200, 50);
        time.setLayoutX(40);
        time.setLayoutY(280);
        time.setFont(new Font("Comic Sans MS", 25));
        
        
        Label HH = new Label("HH");
        HH.setPrefSize(200, 50);
        HH.setLayoutX(270);
        HH.setLayoutY(250);
        HH.setFont(new Font("Comic Sans MS", 20));
        
        
        String [] hours = {"00","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
                            , "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}; 
        
        hour = new ChoiceBox();
        hour.setPrefSize(60, 40);
        hour.setLayoutX(250);
        hour.setLayoutY(290);
        hour.setStyle("-fx-font: 25px \"Serif\";");
        hour.getItems().addAll(hours);
        hour.getSelectionModel().select(hours[0]);
        
        Label MM = new Label("MM");
        MM.setPrefSize(200, 50);
        MM.setLayoutX(390);
        MM.setLayoutY(250);
        MM.setFont(new Font("Comic Sans MS", 20));
        
        
        String [] mins = {"00", "05", "10", "15", "20", "25"
                            , "30", "35", "40", "45", "50", "55"}; 
        
        min = new ChoiceBox();
        min.setPrefSize(60, 40);
        min.setLayoutX(370);
        min.setLayoutY(290);
        min.setStyle("-fx-font: 25px \"Serif\";");
        min.getItems().addAll(mins);
        min.getSelectionModel().select(mins[7]);
        
        Label SS = new Label("SS");
        SS.setPrefSize(200, 50);
        SS.setLayoutX(510);
        SS.setLayoutY(250);
        SS.setFont(new Font("Comic Sans MS", 20));
        
        
        sec = new ChoiceBox();
        sec.setPrefSize(60, 40);
        sec.setLayoutX(490);
        sec.setLayoutY(290);
        sec.setStyle("-fx-font: 25px \"Serif\";");
        sec.getItems().addAll(mins);
        sec.getSelectionModel().select(mins[0]);
        
        
        
        Label publ  = new Label("Public");
        publ.setPrefSize(200, 50);
        publ.setLayoutX(300);
        publ.setLayoutY(360);
        publ.setFont(new Font("Comic Sans MS", 25));
        
        final ToggleGroup group = new ToggleGroup();
        
         pub = new RadioButton();
        pub.setLayoutX(270);
        pub.setLayoutY(375);
        pub.setToggleGroup(group);
        
        
        
        Label privl  = new Label("Private");
        privl.setPrefSize(200, 50);
        privl.setLayoutX(460);
        privl.setLayoutY(360);
        privl.setFont(new Font("Comic Sans MS", 25));
        
        priv = new RadioButton();
        priv.setLayoutX(430);
        priv.setLayoutY(375);
        priv.setToggleGroup(group);
        priv.setSelected(true);
        
        
        
        if(priv.isSelected()== true)
            //System.out.println("helllooo");
            publicflag = 0;
        
        
         group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ov,
          Toggle old_toggle, Toggle new_toggle) {
          
          
        if (group.getSelectedToggle() != null) {
          //System.out.println(group.getSelectedToggle().getUserData().toString());
         if( group.getSelectedToggle().equals(priv) == true)
                publicflag = 0;
                
         
         else
                publicflag = 1;
        }
      }
    });

        
        create = new Button("Create Event");
        create.setPrefSize(200, 50);
        create.setLayoutX(200);
        create.setLayoutY(440);
        create.setFont(new Font("Comic Sans MS", 20));
        
        savevent = new Button("Save Event");
        savevent.setPrefSize(200, 50);
        savevent.setLayoutX(200);
        savevent.setLayoutY(440);
        savevent.setFont(new Font("Comic Sans MS", 20));
        savevent.setDisable(true);
        savevent.setVisible(false);
        
        
        create.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
            dxeventname= texteventname.getText();
            texteventname.getText();
            textdate.getText();
            
            int x = eventype.getSelectionModel().getSelectedIndex();
            c.button_();
            System.out.println(" Date " +c.yy + "-" + c.mm + "-" + c.dd);
            textdate.setText(c.yy + "-" + c.mm + "-" + c.dd);
            
            int tihh = Integer.parseInt(hour.getSelectionModel().getSelectedItem().toString());
            int timm = Integer.parseInt(min.getSelectionModel().getSelectedItem().toString());
            int tiss = Integer.parseInt(sec.getSelectionModel().getSelectedItem().toString());
            
            
            if(texteventname.getText().isEmpty() == true)
                    JOptionPane.showMessageDialog(null, "EventName cannot be empty");
            
         
            else{
                
            try{
             
            String crevent;    
                
            if(x == 0){
               try{ 
             crevent = "INSERT INTO events (username, eventname, date, time, text, public) VALUES ('" + diaryuser + "', '" + texteventname.getText() + "', '" + c.yy + "-" +(c.mm +1)+ "-" + c.dd + "', '" +tihh+":"+timm+":"+tiss+ "', '" + "', '"+publicflag+"')";
            System.out.println(crevent);
                connection.createStatement().executeUpdate(crevent);
                JOptionPane.showMessageDialog(null, "Congratulations !!! \n Event Created Successfully !!! ");
                   System.out.println("it has been add");
                //pane.getChildren().remove(signup);
                pane.getChildren().remove(login());
                pane.getChildren().add(textevent());
                //pane.getChildren().addAll(toplogout(utname.getText()), pubfilespane(), logintextarea());   
                 
               
        }
                
            catch(Exception excep)
            {
                System.out.println("going in exception " + excep);
            //   if( excep.equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '"+ rusername + "' for key 'PRIMARY'") == true)
               // System.out.println("Exception setdata" + excep);
               // Dialogs.
                JOptionPane.showMessageDialog(null, "EventName already exists!! \n Please choose different EventName");
            }
            
            
            
            }
            
             else if(x == 1){
             String s1 = JOptionPane.showInputDialog(null, "Please Enter the Notification");
             crevent = "INSERT INTO events (username, eventname, date, time, public, notification) VALUES ('" + diaryuser + "', '" + texteventname.getText() + "', '" + c.yy + "-" +(c.mm +1)+ "-" + c.dd + "', '" +tihh+":"+timm+":"+tiss+ "', '"+publicflag+"', '" +s1+ "')";       //System.out.println("dx");       
            
             try{ 
                 
             System.out.println(crevent);
                connection.createStatement().executeUpdate(crevent);
                JOptionPane.showMessageDialog(null, "Congratulations !!! \n Event Created Successfully !!! ");
                   System.out.println("it has been add");
                //pane.getChildren().remove(signup);
                //pane.getChildren().remove(login());
                //pane.getChildren().add(textevent());
                //pane.getChildren().addAll(toplogout(utname.getText()), pubfilespane(), logintextarea());   
                 
               
        }
                
            catch(Exception excep)
            {
                System.out.println("going in exception " + excep);
            //   if( excep.equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '"+ rusername + "' for key 'PRIMARY'") == true)
               // System.out.println("Exception setdata" + excep);
               // Dialogs.
                JOptionPane.showMessageDialog(null, "EventName already exists!! \n Please choose different EventName");
            }
             
             
             
             }
             else{
                 
                 try{
             crevent = "INSERT INTO events (username, eventname, date, time, public, alarm) VALUES ('" + diaryuser + "', '" + texteventname.getText() + "', '" + c.yy + "-" +(c.mm +1)+ "-" + c.dd + "', '" +tihh+":"+timm+":"+tiss+ "', '"+publicflag+"', '" +1+ "')";
            
             System.out.println(crevent);
                connection.createStatement().executeUpdate(crevent);
                JOptionPane.showMessageDialog(null, "Congratulations !!! \n Event Created Successfully !!! ");
                   System.out.println("it has been add");
                //pane.getChildren().remove(signup);
               // pane.getChildren().remove(login());
               // pane.getChildren().add(textevent());
                //pane.getChildren().addAll(toplogout(utname.getText()), pubfilespane(), logintextarea());   
                
                 }
            catch(Exception excep)
            {
                System.out.println("going in exception " + excep);
            //   if( excep.equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '"+ rusername + "' for key 'PRIMARY'") == true)
               // System.out.println("Exception setdata" + excep);
               // Dialogs.
                JOptionPane.showMessageDialog(null, "EventName already exists!! \n Please choose different EventName");
            }
                 
             }
        }
                
            catch(Exception excep)
            {
                System.out.println("going in exception " + excep);
            //   if( excep.equals("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry '"+ rusername + "' for key 'PRIMARY'") == true)
               // System.out.println("Exception setdata" + excep);
               // Dialogs.
                JOptionPane.showMessageDialog(null, "EventName already exists!! \n Please choose different EventName");
            }
                 
                 //System.out.println("clicked");
            }  
             }
});
        
        
        
        
        newevent.getChildren().addAll(savevent, HH, MM, pub, publ, priv, privl, create, evtype, eventype, eventname, texteventname, date, textdate, time, min, hour, sec );
        
        return newevent;
        
    }
    
    TextArea textarea = new TextArea();
    
    Pane textevent()
    {
        Pane textevent = new Pane();
        
        textevent.setPrefSize(650, 530);
        textevent.setLayoutX(0.0);
        textevent.setLayoutY(120.0);
        textevent.setStyle("-fx-background-color: ORANGE;");
        
        
        textarea.setPrefSize(560, 370);
        textarea.setLayoutX(40.0);
        textarea.setLayoutY(60.0);
        textarea.setStyle("-fx-background-color: ORANGE;");
        textarea.setFont(new Font("Comic Sans MS", 27));
        textarea.setWrapText(true);
        
        Button save = new Button("Save");
        save.setPrefSize(120, 50);
        save.setLayoutX(300);
        save.setLayoutY(450);
        save.setFont(new Font("Comic Sans MS", 25));
        
        if(textarea.getText().isEmpty()==true)
            save.setDisable(true);
        
        
          textarea.setOnKeyTyped(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                save.setDisable(false);
                if(textarea.getText().isEmpty()==true)
                    save.setDisable(true);
                
            }

        });
        
        
        
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(120, 50);
        cancel.setLayoutX(470);
        cancel.setLayoutY(450);
        cancel.setFont(new Font("Comic Sans MS", 25)); 
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         
               
                pane.getChildren().add(logintextarea());
                crevent.setDisable(false);
             
    }
});
        
        
        save.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         
                //pane.getChildren().removeAll( pubfilespane());
               // pane.getChildren().remove(pubfilespane());
                //pane.getChildren().addAll(login(), coverprevfilespane());
                 
                try{
            String sql2 = "UPDATE events SET text = '" + textarea.getText() + "' WHERE eventname = '" + dxeventname + "'";
               
             System.out.println(sql2);
                connection.createStatement().executeUpdate(sql2);
              //  JOptionPane.showMessageDialog(null, "Congratulations !!! \n You have successfully registered for the Diary ");
              save.setDisable(true);
                   System.out.println("it has been saved");
                    
                }catch(Exception fds)
                {
                    System.out.println("exception in update " +fds);
                    
                }
             
    }
});
        
        
       
        textevent.getChildren().addAll(textarea, save, cancel);
        
        return textevent;
        
    }
    
    Dx c = new Dx();
    
    Button crevent;
    
    Pane pubfilespane()
    {
        Pane caflogin = new Pane();
        caflogin.setPrefSize(450, 180);
        caflogin.setLayoutX(650.0);
        caflogin.setLayoutY(470.0);
        caflogin.setStyle("-fx-background-color: ORANGE;");
        
         crevent = new Button("Create New Event");
        crevent.setPrefSize(150, 50);
        crevent.setLayoutX(260);
        crevent.setLayoutY(60);
        crevent.setFont(new Font("Comic Sans MS", 15));
        
        
        crevent.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
                 pane.getChildren().add(createvent());
                 crevent.setDisable(true);
                 //pane.getChildren().remove(caflogin);
                 alarm();
                 
             }
});
        
        
        Label pubfile = new Label("Show All Events");
        pubfile.setPrefSize(200, 50);
        pubfile.setLayoutX(20);
        pubfile.setLayoutY(5);
        pubfile.setFont(new Font("Serif", 25));
        //pubfile.getItems().addAll(h.tabletext);
        
        
        
        //String [] events = {"Text", "Picture", "Notification", "Alarm"}; 
        
        
        
        
        
        
        caflogin.getChildren().addAll(crevent, pubfile, c.choiceprevfil());
        
       
        
        
        return caflogin;
        
    }
    
    
    
    TabPane showevents()
    {
            
        TabPane tabpane = new TabPane();
        
        tabpane.setPrefSize(650, 530);
        tabpane.setLayoutX(0.0);
        tabpane.setLayoutY(120.0);
        tabpane.setStyle("-fx-background-color: ORANGE;");
        
        Tab text = new Tab();
        text.setText("             Text            ");
        text.setStyle("-fx-padding: 5px 5px 10px 5px;-fx-font-weight: bold;-fx-font-size: 15pt;-fx-text-fill: ORANGE;");
        
        Tab notify = new Tab();
        notify.setText("          Notificatons           ");
        notify.setStyle("-fx-padding: 5px 5px 10px 5px;-fx-font-weight: bold;-fx-font-size: 15pt;-fx-text-fill: ORANGE;");
        //notify.setStyle("-fx-font-size: 15pt;");
        
        Tab alarm = new Tab();
        alarm.setText("            Alarm         ");
        alarm.setStyle("-fx-padding: 5px 5px 10px 5px;-fx-font-weight: bold;-fx-font-size: 15pt;-fx-text-fill: ORANGE;");
        
        tabletext();
        text.setContent(tabletxt);
        
        
        
        
        
        
        tablealarm();
        alarm.setContent(tablealm);
        
        tablenotify();
        notify.setContent(tablenotif);
        
        
        tabpane.getTabs().addAll(text, notify, alarm);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        
        return tabpane;
    }
    
    
    TableView<String[]> tabletxt;
    TableView<String[]> tablenotif;
    TableView<String[]> tablealm;
    
    
    
    int  dxxt;
    int  dxxn;
    int dxxa;
    
    String kt;
    String kn;
    String kl;
    String [][] staffArray;
     public void tabletext()
 {
     
 staffArray = null;
        staffArray = new String[15][4];
        staffArray[0][0] = "Filename";
        staffArray[0][1] = "Date";
        staffArray[0][2] = "Time";
   
     int i = 1;
        
        try{
        
                String query = "Select * from events where username = '"+diaryuser+"' AND text IS NOT NULL";
                result = statement.executeQuery(query);
                System.out.println(query);
                while (result.next())
                {
                   // String uname = result2.getString("username");
                    String name = result.getString("eventname");
                    
                    String dat = result.getString("date");
                    String tm = result.getString("time");
                    //staffArray[i] = new String[1];
                    staffArray[i][0] = name;
                   
                    staffArray[i][1] = dat;
                    staffArray[i][2] = tm;
                    //System.out.println(name);
                    i++;
                }
                int j=0;
                while(staffArray[j][0]!= null){
                    System.out.println(staffArray[j]);
                    j++;
                }
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in calendar" + e);
            }
        
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data
        tabletxt = new TableView<>();
        
        System.out.println("length " + staffArray[0].length);
        
        for (int j = 0; j < staffArray[0].length; j++) {
            TableColumn tc = new TableColumn(staffArray[0][j]);
            final int colNo = j;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(220);
         
            tabletxt.setLayoutX(50);
            tabletxt.setLayoutY(50);
            tabletxt.getColumns().add(tc);
        }
        
        
        tabletxt.setItems(data);
        
        
        tabletxt.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent ed)
                {
                   dxxt = tabletxt.getSelectionModel().getSelectedIndex();
                     kt = staffArray[dxxt+1][0];
                     System.out.println("mouse clicled" + kt);
                     dxeventname = kt;
                    
        try{
        
                String query = "Select * from events where eventname = '"+kt+"' ";
                //System.out.println(connectioncal);
                //System.out.println(statementcal);
                
                result = statement.executeQuery(query);
               // System.out.println(result);
                System.out.println(query);
                while (result.next())
                {
                    String name = result.getString("text");
                    
                    System.out.println(name);
                    textevent();
                    textarea.setText(name);
                    pane.getChildren().remove(showevents());
                    textarea.setText(name);
                    pane.getChildren().add(textevent());
                    
                    
                    System.out.println("working in texttable");
                    
                }
                
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in texttable" + e);
            }
                     
                }
                
});    
        
        
 }  
     
     String [][] staffArrayn;
     
    public void tablenotify()
 {
    
 staffArrayn = null;
        staffArrayn = new String[15][4];
        staffArrayn[0][0] = "Notification Name";
        staffArrayn[0][1] = "Date";
        staffArrayn[0][2] = "Time";
   
     int i = 1;
        
        try{
        
                String query = "Select * from events where username = '"+diaryuser+"' AND notification IS NOT NULL";
                result = statement.executeQuery(query);
                System.out.println(query);
                while (result.next())
                {
                   // String uname = result2.getString("username");
                    String name = result.getString("eventname");
                    
                    String dat = result.getString("date");
                    String tm = result.getString("time");
                    //staffArray[i] = new String[1];
                    staffArrayn[i][0] = name;
                    staffArrayn[i][1] = dat;
                    staffArrayn[i][2] = tm;
                    //System.out.println(name);
                    i++;
                }
                int j=0;
                while(staffArrayn[j][0]!= null){
                    System.out.println(staffArrayn[j]);
                    j++;
                }
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in calendar" + e);
            }
        
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArrayn));
        data.remove(0);//remove titles from data
         tablenotif = new TableView<>();
        
        System.out.println("length " + staffArrayn[0].length);
        
        for (int j = 0; j < staffArrayn[0].length; j++) {
            TableColumn tc = new TableColumn(staffArrayn[0][j]);
            final int colNo = j;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(220);
         
            tablenotif.setLayoutX(50);
            tablenotif.setLayoutY(50);
            tablenotif.getColumns().add(tc);
        }
        tablenotif.setItems(data);
        
        
        
        tablenotif.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent ed)
                {
                   dxxt = tablenotif.getSelectionModel().getSelectedIndex();
                     kt = staffArrayn[dxxt+1][0];
                     System.out.println("mouse clicled" + kt);
                     //dxeventname = kt;
                    
        try{
        
                String query = "Select * from events where eventname = '"+kt+"' ";
                //System.out.println(connectioncal);
                //System.out.println(statementcal);
                
                result = statement.executeQuery(query);
               // System.out.println(result);
                System.out.println(query);
                while (result.next())
                {
                    String name = result.getString("notification");
                    
                    System.out.println(name);
                    JOptionPane.showMessageDialog(null, name);
                    
                    
                    System.out.println("working in nottiftable");
                    
                }
                
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in texttable" + e);
            }
                     
                }
                
});    
        
        
        
        
        
 }  
    
    String [][] staffArraynn;
    TableView<String[]> tablenotifnn;
    public void tablenotifynn()
 {
    
 staffArraynn = null;
        staffArraynn = new String[15][4];
        staffArraynn[0][0] = "Notification Name";
        staffArraynn[0][1] = "Date";
        staffArraynn[0][2] = "Time";
   
     int i = 1;
        
        try{
        
                String query = "Select * from events where username = '"+diaryuser+"' AND notification IS NOT NULL";
                result = statement.executeQuery(query);
                System.out.println(query);
                while (result.next())
                {
                   // String uname = result2.getString("username");
                    String name = result.getString("eventname");
                    
                    String dat = result.getString("date");
                    String tm = result.getString("time");
                    //staffArray[i] = new String[1];
                    staffArraynn[i][0] = name;
                    staffArraynn[i][1] = dat;
                    staffArraynn[i][2] = tm;
                    System.out.println(name);
                    System.out.println(dat);
                    System.out.println(tm);
                    i++;
                }
                int j=0;
                while(staffArraynn[j][0]!= null){
                    System.out.println(staffArraynn[j]);
                    j++;
                }
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in calendar" + e);
            }
        
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArraynn));
        data.remove(0);//remove titles from data
         tablenotifnn = new TableView<>();
        
        System.out.println("length " + staffArraynn[0].length);
        
        for (int j = 0; j < staffArraynn[0].length; j++) {
            TableColumn tc = new TableColumn(staffArraynn[0][j]);
            final int colNo = j;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(140);
         
            tablenotifnn.setLayoutX(50);
            tablenotifnn.setLayoutY(50);
            tablenotifnn.getColumns().add(tc);
        }
        tablenotifnn.setItems(data);
        
        
        
        tablenotifnn.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent ed)
                {
                   dxxt = tablenotifnn.getSelectionModel().getSelectedIndex();
                     kt = staffArraynn[dxxt+1][0];
                     System.out.println("mouse clicled" + kt);
                     //dxeventname = kt;
                    
        try{
                c.button_();
                String query = "Select * from events where eventname = '"+kt+"' AND date = '"+ c.yy +"-"+(c.mm+1)+"-"+ c.dd + "' ";
                //System.out.println(connectioncal);
                //System.out.println(statementcal);
                
                result = statement.executeQuery(query);
               // System.out.println(result);
                System.out.println(query);
                while (result.next())
                {
                    String name = result.getString("notification");
                    
                    System.out.println(name);
                    JOptionPane.showMessageDialog(null, name);
                    
                    
                    System.out.println("working in nottiftable");
                    
                }
                
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in texttable" + e);
            }
                     
                }
                
});    
        
        
        
        
        
 } 
         
    
     Pane notifevent()
    {
        Pane textevent = new Pane();
        
        textevent.setPrefSize(650, 530);
        textevent.setLayoutX(0.0);
        textevent.setLayoutY(120.0);
        textevent.setStyle("-fx-background-color: ORANGE;");
        
        tablenotifynn();
        textevent.getChildren().add(tablenotifnn);
        
        return textevent;
        
    }
         
         
         
  public void tablealarm()
 {
     
 String [][] staffArray = null;
        staffArray = new String[15][4];
        staffArray[0][0] = "Alarm Name";
        staffArray[0][1] = "Date";
        staffArray[0][2] = "Time";
   
     int i = 1;
        
        try{
        
                String query = "Select * from events where username = '"+diaryuser+"' AND alarm = '1' ";
                result = statement.executeQuery(query);
                System.out.println(query);
                while (result.next())
                {
                   // String uname = result2.getString("username");
                    String name = result.getString("eventname");
                    
                    String dat = result.getString("date");
                    String tm = result.getString("time");
                    //staffArray[i] = new String[1];
                    staffArray[i][0] = name;
                   
                    staffArray[i][1] = dat;
                    staffArray[i][2] = tm;
                    //System.out.println(name);
                    i++;
                }
                int j=0;
                while(staffArray[j][0]!= null){
                    System.out.println(staffArray[j]);
                    j++;
                }
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in calendar" + e);
            }
        
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data
         tablealm = new TableView<>();
        
        System.out.println("length " + staffArray[0].length);
        
        for (int j = 0; j < staffArray[0].length; j++) {
            TableColumn tc = new TableColumn(staffArray[0][j]);
            final int colNo = j;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(220);
         
            tablealm.setLayoutX(50);
            tablealm.setLayoutY(50);
            tablealm.getColumns().add(tc);
        }
        tablealm.setItems(data);
        
        
 }  
    
    
    
    Pane coverprevfilespane()
    {
        Pane cover = new Pane();
        cover.setPrefSize(450, 180);
        cover.setLayoutX(650.0);
        cover.setLayoutY(470.0);
        cover.setStyle("-fx-background-color: GREY;");
       
        
        return cover;
        
    }
 
    
    
    Pane calend()
    {
        Pane calcul = new Pane();
        
       calcul.setPrefSize(450, 650);
        calcul.setLayoutX(650.0);
        calcul.setLayoutY(0.0);
       calcul.setStyle("-fx-background-color: GRAY;");
       
        final Label clock = new Label();  
           clock.setPrefSize(300, 50);
        clock.setLayoutX(180);
        clock.setLayoutY(5);
        clock.setFont(new Font("Comic Sans MS", 30));
        final DateFormat format = DateFormat.getInstance();  
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {  
            @Override  
            public void handle(ActionEvent event) {  
            final Calendar cal = Calendar.getInstance();  
            clock.setText(format.format(cal.getTime()));  
     }  
}));  
timeline.setCycleCount(Animation.INDEFINITE);  
timeline.play(); 
        
        calcul.getChildren().addAll(c, clock);
       
        System.out.println(c.yy + " " + c.mm + " " + c.dd);
     
        return calcul;
    }
     
    
    Pane mainpane()
    {
         pane = new Pane();
         
        pane.getChildren().add(login());
       // pane.getChildren().add(createvent());
       //pane.getChildren().add(textevent());
       //pane.getChildren().add(showevents());
      //pane.getChildren().add(toplogout(lusername));
      
        pane.getChildren().add(calend());
        //pane.getChildren().add(new Dx());
        return pane;
        
    }
    
    
    public void MySQL(){
       
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123","root","Zxcvmnb987*");
            connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123","root","Zxcvmnb987*");
            statement = connection.createStatement();
            statement2 = connection2.createStatement();
            System.out.println("Connected in startfx");
            
            
      
            
        }catch(Exception exc)
        {
            System.out.println("Excption");
        }
        
        
    }
    
    
    public void start(Stage primaryStage)
    {
   
   
        MySQL();
          //prefile();
          
        
        primaryStage.setTitle("TITLE of the window");
 
        Scene scene = new Scene(mainpane(), 1100, 650);
        
        primaryStage.setScene(scene);
        
        primaryStage.setResizable(false);
       primaryStage.show();
 
    }
    
    
    
    public static void main(String[] args) {

        launch(args);

       
    }
    
}
