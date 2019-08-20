/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startfx;

/**
 *
 * @author SauravGupta
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Callback;


/**
 *
 * @author SauravGupta
 */

class Dx extends Pane
{
  int yy;

  /** Currently-interesting month and day */
   int mm, dd;

  /** The buttons to be displayed */
  protected Button labs[][];

  /** The number of day squares to leave blank at the start of this month */
  protected int leadGap = 0;

  /** A Calendar object used throughout */
  Calendar calendar = new GregorianCalendar();

  /** Today's year */
  protected final int thisYear = calendar.get(Calendar.YEAR);

  /** Today's month */
  protected final int thisMonth = calendar.get(Calendar.MONTH);

  /** One of the buttons. We just keep its reference for getBackground(). */
  private Button b0;

  /** The month choice */
  private ChoiceBox monthChoice;

  /** The year choice */
  private ComboBox yearChoice;
  
  //String num = "";
  
    
    Connection connectioncal;
    Statement statementcal;
    ResultSet resultcal;
    
    
    Connection connectioncal2;
    Statement statementcal2;
    ResultSet resultcal2;

  Dx() {
   super();
     // System.out.println("down of super");
    setYYMMDD(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH));
      //System.out.println("setyymmdd");
    buildGUI();
     // System.out.println("buildgui");
    recompute();
      //  System.out.println("recompute");
    MySQLcal();
     // System.out.println("mysql");
   // prefile();
     // System.out.println("prefile");
      
      System.out.println(yy +" " + mm + " " + dd);
    
  }
  
 
  
  
  

  private void setYYMMDD(int year, int month, int today) {
    yy = year;
    mm = month;
    dd = today;
    //choiceprevfil();
    //prefile();
  }
  
  String[] months = { "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December" };
  
  public final static int dom[] = { 31, 28, 31, 30, /* jan feb mar apr */
  31, 30, 31, 31, /* may jun jul aug */
  30, 31, 30, 31 /* sep oct nov dec */
  };
  
  protected void recompute() {
    // System.out.println("Cal::recompute: " + yy + ":" + mm + ":" + dd);
    if (mm < 0 || mm > 11)
      throw new IllegalArgumentException("Month " + mm
          + " bad, must be 0-11");
    clearDayActive();
    calendar = new GregorianCalendar(yy, mm, dd);

    // Compute how much to leave before the first.
    // getDay() returns 0 for Sunday, which is just right.
    leadGap = new GregorianCalendar(yy, mm, 1).get(Calendar.DAY_OF_WEEK) - 1;
    // System.out.println("leadGap = " + leadGap);

    int daysInMonth = dom[mm];
    if (isLeap(calendar.get(Calendar.YEAR)) && mm == 1)
//    if (isLeap(calendar.get(Calendar.YEAR)) && mm > 1)
      ++daysInMonth;

    // Blank out the labels before 1st day of month
    for (int i = 0; i < leadGap; i++) {
      labs[0][i].setText("");
      //labs[0][i].setDisable(true);
    }

    // Fill in numbers for the day of month.
    for (int i = 1; i <= daysInMonth; i++) {
      Button b = labs[(leadGap + i - 1) / 7][(leadGap + i - 1) % 7];
      b.setText(Integer.toString(i));
      
    }

    // 7 days/week * up to 6 rows
    for (int i = leadGap + 1 + daysInMonth; i < 6 * 7; i++) {
      labs[(i) / 7][(i) % 7].setText("");
     // labs[(i) / 7][(i) % 7].setDisable(true);
    }

    // Shade current day, only if current month
    if (thisYear == yy && mm == thisMonth)
      setDayActive(dd); // shade the box for today

    // Say we need to be drawn on the screen
    //repaint();
  }
  
  
  public boolean isLeap(int year) {
    if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))
      return true;
    return false;
  }
  
  
  public void setDate(int yy, int mm, int dd) {
    // System.out.println("Cal::setDate");
    this.yy = yy;
    this.mm = mm; // starts at 0, like Date
    this.dd = dd;
    prefile();
    recompute();
  }
  
  
   private void clearDayActive() {
    Button b;

    // First un-shade the previously-selected square, if any
    if (activeDay > 0) {
      b = labs[(leadGap + activeDay - 1) / 7][(leadGap + activeDay - 1) % 7];
      b0 = b;
      //b.setBackground(b0.getBackground());
   // b.setStyle("-fx-base: RED");
     // b.setStyle("-fx-background-color: NULL");
      //b.setBackground(b0.getBackground());
    //  //b.repaint();
   b.setStyle("-fx-focus-color: transparent;");
     //b.setStyle("-fx-background-color: transparent;");
      activeDay = -1;
      
    }
  }
  
   private int activeDay = -1;
   
   public void setDayActive(int newDay) {

    clearDayActive();

    // Set the new one
    if (newDay <= 0)
      dd = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
    else
      dd = newDay;
    // Now shade the correct square
    Button square = labs[(leadGap + newDay - 1) / 7][(leadGap + newDay - 1) % 7];
    //square.setBackground(Color.red);
    square.setStyle("-fx-base: GREEN");
   // square.setStyle("-fx-background-insets: 100, 100, 100, 0");
    //square.setStyle("-fx-focus-color: transparent;");
   // //square.repaint();
    activeDay = newDay;
  }
   
   
   private void buildGUI() {
    //getAccessibleContext().setAccessibleDescription(
      //  "Calendar not accessible yet. Sorry!");
    //setBorder(BorderFactory.createEtchedBorder());

    //setLayout(new BorderLayout());

    Pane tp = new Pane();
   // tp.setPrefSize(450, 650);
        tp.setLayoutX(0.0);
        tp.setLayoutY(0.0);
        tp.setStyle("-fx-background-color: GRAY;");
        
        setPrefSize(450, 650);
        setLayoutX(0.0);
        setLayoutY(0.0);
        setStyle("-fx-background-color: GRAY;");
    
    
    tp.getChildren().add(monthChoice = new ChoiceBox());
        monthChoice.setPrefSize(200, 40);
        monthChoice.setLayoutX(30);
        monthChoice.setLayoutY(60);
        monthChoice.setStyle("-fx-font: 20px \"Serif\";");
    //tp.add(monthChoice = new ComboBox());
    for (int i = 0; i < months.length; i++)
        monthChoice.getItems().addAll(months[i]);
      //monthChoice.addItem(months[i]);
      monthChoice.getSelectionModel().select(months[mm]);
       //System.out.println(months[mm]);
   // monthChoice.setSelectedItem(months[mm]);
   
        monthChoice.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             int i = monthChoice.getSelectionModel().getSelectedIndex();
            
                // System.out.println("i"+i);
        if (i >= 0) {
          mm = i;
          //System.out.println(mm);
          // System.out.println("Month=" + mm);
          recompute();
        } 
    }
});


    tp.getChildren().add(yearChoice = new ComboBox());
    
        yearChoice.setPrefSize(130, 40);
        yearChoice.setLayoutX(270);
        yearChoice.setLayoutY(60);
        yearChoice.setStyle("-fx-font: 20px \"Serif\";");
    
    yearChoice.setEditable(true);
    for (int i = yy - 5; i < yy + 5; i++)
      yearChoice.getItems().addAll(Integer.toString(i));
    yearChoice.getSelectionModel().select(Integer.toString(yy));
       System.out.println(yy);
    yearChoice.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
        int i = yearChoice.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
          yy = Integer.parseInt(yearChoice.getSelectionModel().getSelectedItem().toString());
          System.out.println("Year=" + yy);
          recompute();
        }
      }
    });
    Pane bp = new Pane(); 
    
   // bp.setPrefSize(450, 650);
        bp.setLayoutX(0.0);
        bp.setLayoutY(0.0);
        bp.setStyle("-fx-background-color: GRAY;");
    
    //GridPane bp = new GridPane(); 
    Button [][] day = new Button[1][7];
        day[0][0] = new Button("Sun");
        day[0][1] = new Button("Mon");
        day[0][2] = new Button("Tue");
        day[0][3] = new Button("Wed");
        day[0][4] = new Button("Thu");
        day[0][5] = new Button("Fri");
        day[0][6] = new Button("Sat");
        
    
    for(int j=0; j<7; j++)
        {
        day[0][j].setPrefSize(60, 40);
        day[0][j].setLayoutX(15 + j*60 );
        day[0][j].setLayoutY(160);
        day[0][j].setFont(new Font("Comic Sans MS", 13));
        //day[0][j].setDisable(true);
        tp.getChildren().addAll(day[0][j]);
        }
    
          /*ActionListener dateSetter = new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        //String  = e.getActionCommand();
        String num = e.getActionCommand();;
        System.out.println(num);
        if (!num.equals("")) {
          // set the current day highlighted
          setDayActive(Integer.parseInt(num));
          // When this becomes a Bean, you can
          // fire some kind of DateChanged event here.
          // Also, build a similar daySetter for day-of-week btns.
        }
      }
    };*/
    
    labs = new Button[6][7];
    // Construct all the buttons, and add them.
    for (int i = 0; i < 6; i++)
      for (int j = 0; j < 7; j++) {
          //(35 + j*60,200 + i*40)
          labs[i][j] = new Button("");
        labs[i][j].setPrefSize(60, 40);
        labs[i][j].setLayoutX(15 + j*60 );
        labs[i][j].setLayoutY(200 + i*40);
        labs[i][j].setFont(new Font("Comic Sans MS", 13));
        tp.getChildren().addAll(labs[i][j]);// = new Button(""));
       // String num = labs[i][j].getText();
          //System.out.println(num);
        
       // labs[i][j].addEventHandler(dateSetter);//.addActionListener(dateSetter);
       
    /*labs[i][j].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
     
      String num = labs[i][j].getText();
                 System.out.println(yy + " " + mm + " " + dd);
    
          System.out.println(num);
        if (!num.equals("")) {
 
      }
      }
    });*/
       
      /* labs[i][j].addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
          
        String num = e.getActionCommand();
          System.out.println(num);
        if (!num.equals("")) {
          // set the current day highlighted
          setDayActive(Integer.parseInt(num));
      }
      }
    }); */
      }
   
       button_();
       
        //getChildren().addAll(q);
        getChildren().addAll(tp);
        //getChildren().add(tp);
  }
   
   
   public void MySQLcal(){
       // String dx[] = new String[1000];
       // int i = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            
            connectioncal = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123","root","Zxcvmnb987*");
            statementcal = connectioncal.createStatement();
            connectioncal2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/database123","root","Zxcvmnb987*");
            statementcal2 = connectioncal2.createStatement();
            System.out.println("Connected in calendar");
               
        }catch(Exception exc)
        {
            System.out.println("Excption in calenar" + exc);
        }
        
   }
 
 String dx[] = null;
 ObservableList<String[]> data;
 String [][] staffArray;
 
 public void prefile()
 {
     
 staffArray = null;
        staffArray = new String[15][4];
        staffArray[0][0] = "Filename";
        staffArray[0][1] = "Username";
        staffArray[0][2] = "Date";
        staffArray[0][3] = "Time";
   
     int i = 1;
        
        try{
        
                String query = "Select * from events where public = '1' AND date = '"+ yy +"-"+(mm+1)+"-"+ dd + "' AND text IS NOT NULL";
                resultcal = statementcal.executeQuery(query);
                System.out.println(query);
                while (resultcal.next())
                {
                   // String uname = result2.getString("username");
                    String name = resultcal.getString("eventname");
                    String usname = resultcal.getString("username");
                    String dat = resultcal.getString("date");
                    String tm = resultcal.getString("time");
                    //staffArray[i] = new String[1];
                    staffArray[i][0] = name;
                    staffArray[i][1] = usname;
                    staffArray[i][2] = dat;
                    staffArray[i][3] = tm;
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
        
        
        data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data
         table = new TableView<>();
        
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
            tc.setPrefWidth(135);
            table.setLayoutX(50);
            table.setLayoutY(50);
            table.getColumns().add(tc);
        }
        table.setItems(data);
        int dx;
   
        
 }    
  String k;      
  int dxx;
   TableView<String[]> table;
   
   TableView<String[]> tablen;
   
 String [][] staffArrayn;  
  
   
   
   
  
   Pane pubtable()
    {
        Pane textevent = new Pane();
        
        textevent.setPrefSize(650, 530);
        textevent.setLayoutX(0.0);
        textevent.setLayoutY(120.0);
        textevent.setStyle("-fx-background-color: ORANGE;");
        
        
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(120, 50);
        cancel.setLayoutX(300);
        cancel.setLayoutY(460);
        cancel.setFont(new Font("Comic Sans MS", 25)); 
        
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             textevent.getChildren().remove(textevent);
                 System.out.println("cancel printed");
             textevent.setDisable(true);
             textevent.setVisible(false);
    }
});
        
        
        textarea1 = new TextArea();
        textarea1.setPrefSize(560, 370);
        textarea1.setLayoutX(40.0);
        textarea1.setLayoutY(60.0);
        textarea1.setStyle("-fx-background-color: ORANGE;");
        textarea1.setFont(new Font("Comic Sans MS", 27));
        textarea1.setWrapText(true);
       
       
        
        back = new Button("Back");
        back.setPrefSize(120, 50);
        back.setLayoutX(470);
        back.setLayoutY(450);
        back.setFont(new Font("Comic Sans MS", 25)); 
        
        back.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         textevent.getChildren().remove(textarea1);
         textevent.getChildren().remove(back);
         textevent.getChildren().add(table);
         textevent.getChildren().add(cancel);
                 System.out.println("backpressed");
    }
});
        
        
    table.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent ed)
                {
                   dxx = table.getSelectionModel().getSelectedIndex();
                     k = staffArray[dxx+1][0];
                     
                     int i = 1;
        
        try{
        
                String query = "Select * from events where eventname = '"+k+"' ";
                System.out.println(connectioncal);
                System.out.println(statementcal);
                
                resultcal = statementcal.executeQuery(query);
                System.out.println(resultcal);
                System.out.println(query);
                while (resultcal.next())
                {
                    String name = resultcal.getString("text");
                    System.out.println(name);
                    textevent.getChildren().remove(cancel);
                    textevent.getChildren().remove(table);
                    textevent.getChildren().add(showarea());
                    //textarea1();
                   textarea1.setText(name);
                   textarea1.setEditable(false);
                   textevent.getChildren().addAll(textarea1, back);
                   //textevent.getChildren().add(showarea());
                   
                   //
                   //pubtable().getChildren().add(showarea());
                    System.out.println("dxxxxxxxxxxxx");
                    
                }
                
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in table" + e);
            }
        
                     
                     
                     
                     
                     
                }
                
});    
             
        
        
        textevent.getChildren().addAll(cancel, table);
        
        return textevent;
        
    }
   
   
   
   
   
   
   TextArea  textarea1;
   Button back;
   
   Pane showarea()
    {
        Pane showev = new Pane();
        
        showev.setPrefSize(650, 530);
        showev.setLayoutX(0.0);
        showev.setLayoutY(120.0);
        showev.setStyle("-fx-background-color: ORANGE;");
        
        
        
        
        
       
        showev.getChildren().addAll(back, textarea1);
        
        return showev;
        
    }
   
   
   Button prevfiles;
   
   Button notify;
   
   Button notific()
   {
        notify = new Button("Notification");
        notify.setPrefSize(150, 40);
        notify.setLayoutX(300);
        notify.setLayoutY(60);
        notify.setStyle("-fx-font: 20px \"Serif\";");
       return notify;
   }
   
   
   
    Pane noti()
    {
        Pane textevent = new Pane();
        
        textevent.setPrefSize(650, 530);
        textevent.setLayoutX(0.0);
        textevent.setLayoutY(120.0);
        textevent.setStyle("-fx-background-color: ORANGE;");
        
        
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(120, 50);
        cancel.setLayoutX(300);
        cancel.setLayoutY(460);
        cancel.setFont(new Font("Comic Sans MS", 25)); 
        
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             textevent.getChildren().remove(textevent);
                 System.out.println("cancel printed");
             textevent.setDisable(true);
             textevent.setVisible(false);
    }
});
        
        
        textarea1 = new TextArea();
        textarea1.setPrefSize(560, 370);
        textarea1.setLayoutX(40.0);
        textarea1.setLayoutY(60.0);
        textarea1.setStyle("-fx-background-color: ORANGE;");
        textarea1.setFont(new Font("Comic Sans MS", 27));
        textarea1.setWrapText(true);
       
       
        
        back = new Button("Back");
        back.setPrefSize(120, 50);
        back.setLayoutX(470);
        back.setLayoutY(450);
        back.setFont(new Font("Comic Sans MS", 25)); 
        
        back.setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
         textevent.getChildren().remove(textarea1);
         textevent.getChildren().remove(back);
         textevent.getChildren().add(table);
         textevent.getChildren().add(cancel);
                 System.out.println("backpressed");
    }
});
        
        
    table.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent ed)
                {
                   dxx = table.getSelectionModel().getSelectedIndex();
                     k = staffArray[dxx+1][0];
                     
                     int i = 1;
        
        try{
        
                String query = "Select * from events where eventname = '"+k+"' ";
                System.out.println(connectioncal);
                System.out.println(statementcal);
                
                resultcal = statementcal.executeQuery(query);
                System.out.println(resultcal);
                System.out.println(query);
                while (resultcal.next())
                {
                    String name = resultcal.getString("text");
                    System.out.println(name);
                    textevent.getChildren().remove(cancel);
                    textevent.getChildren().remove(table);
                    textevent.getChildren().add(showarea());
                    //textarea1();
                   textarea1.setText(name);
                   textarea1.setEditable(false);
                   textevent.getChildren().addAll(textarea1, back);
                   //textevent.getChildren().add(showarea());
                   
                   //
                   //pubtable().getChildren().add(showarea());
                    System.out.println("dxxxxxxxxxxxx");
                    
                }
                
   
        
            }catch(Exception e)
            {
                System.out.println("Fuck yeah exception in table" + e);
            }
        
                     
                     
                     
                     
                     
                }
                
});    
             
        
        
        textevent.getChildren().addAll(cancel, table);
        
        return textevent;
        
    }
   
   
   
   Button choiceprevfil()
   {
       prevfiles = new Button("Show Events");
        prevfiles.setPrefSize(150, 40);
        prevfiles.setLayoutX(20);
        prevfiles.setLayoutY(60);
        prevfiles.setStyle("-fx-font: 20px \"Serif\";");
        prefile();
       
       return prevfiles;
   }
  String textget; 
   
   public void button_()
{
    labs[0][0].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][0].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
                 prefile();
           
             
    }
});
    
     labs[0][1].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][1].getText();
             dd = Integer.parseInt(name);
   
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
     
     labs[0][2].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][2].getText();
             dd = Integer.parseInt(name);
   
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[0][3].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][3].getText();
               dd = Integer.parseInt(name);

                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[0][4].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][4].getText();
             dd = Integer.parseInt(name);
         
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
    }
             
});
     
     labs[0][5].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][5].getText();
             dd = Integer.parseInt(name);
          
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
     labs[0][6].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[0][6].getText();
             dd = Integer.parseInt(name);
          
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
     
      labs[1][0].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][0].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
    
     labs[1][1].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][1].getText();
             dd = Integer.parseInt(name);
          
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
     
     labs[1][2].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][2].getText();
             dd = Integer.parseInt(name);
          
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[1][3].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][3].getText();
             dd = Integer.parseInt(name);
   
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
             prefile();
    }
             
});
     
     labs[1][4].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][4].getText();
             dd = Integer.parseInt(name);
           
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
    }
             
});
     
     labs[1][5].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][5].getText();
             dd = Integer.parseInt(name);
        
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
     labs[1][6].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[1][6].getText();
             dd = Integer.parseInt(name);
           
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
    
     
      labs[2][0].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][0].getText();
             dd = Integer.parseInt(name);
         
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
    
     labs[2][1].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][1].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           
             prefile();
    }
});
     
     labs[2][2].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][2].getText();
             dd = Integer.parseInt(name);
        
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[2][3].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][3].getText();
               dd = Integer.parseInt(name);
      
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
                 
             prefile();
    }
             
});
     
     labs[2][4].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][4].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
    }
             
});
     
     labs[2][5].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][5].getText();
             dd = Integer.parseInt(name);
      
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
     labs[2][6].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[2][6].getText();
             dd = Integer.parseInt(name);
      
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
           //prevfiles.getItems().removeAll(prevfiles.getItems());
                 
             prefile();
    }
             
});
     
     
      labs[3][0].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][0].getText();
             dd = Integer.parseInt(name);
      
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                // prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
    
     labs[3][1].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][1].getText();
             dd = Integer.parseInt(name);
    
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
     
     labs[3][2].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][2].getText();
             dd = Integer.parseInt(name);
   
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
           //prevfiles.getItems().removeAll(prevfiles.getItems());
             prefile();
    }
             
});
     
     labs[3][3].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][3].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           
             prefile();
    }
             
});
     
     labs[3][4].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][4].getText();
             dd = Integer.parseInt(name);
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
    }
             
});
     
     labs[3][5].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][5].getText();
             dd = Integer.parseInt(name);
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
     labs[3][6].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[3][6].getText();
             dd = Integer.parseInt(name);
   
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           
             prefile();
    }
             
});
     
     String b40;
      labs[4][0].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][0].getText();
            // b40 = labs[4][0].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
      
    
     labs[4][1].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][1].getText();
             dd = Integer.parseInt(name);
     
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                // prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
     
     labs[4][2].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][2].getText();
             dd = Integer.parseInt(name);
      
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                // prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[4][3].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][3].getText();
             dd = Integer.parseInt(name);
       
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                // prevfiles.getItems().removeAll(prevfiles.getItems());
                 
           prefile();
             
    }
             
});
     
     labs[4][4].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][4].getText();
             dd = Integer.parseInt(name);
       
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                // prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
    }
             
});
     
     labs[4][5].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][5].getText();
             dd = Integer.parseInt(name);

                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
     labs[4][6].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[4][6].getText();
             dd = Integer.parseInt(name);

                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     
      labs[5][0].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][0].getText();
             dd = Integer.parseInt(name);

                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
    
     labs[5][1].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][1].getText();
             dd = Integer.parseInt(name);
 
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
});
     
     labs[5][2].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][2].getText();
             dd = Integer.parseInt(name);

                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                // prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[5][3].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][3].getText();
             dd = Integer.parseInt(name);
             
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
     labs[5][4].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][4].getText();
             dd = Integer.parseInt(name);
            
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
    }
             
});
     
     labs[5][5].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][5].getText();
             dd = Integer.parseInt(name);
         
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                 //prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});

     
     
     labs[5][6].setOnAction(new EventHandler<ActionEvent>() {
             @Override 
             public void handle(ActionEvent e) {
             String name = labs[5][6].getText();
             dd = Integer.parseInt(name);
    
                 System.out.println(dd);
                 //setDayActive(Integer.parseInt(name));
                 setDayActive(dd);
                 
                // prevfiles.getItems().removeAll(prevfiles.getItems());
           prefile();
             
    }
             
});
     
    // offbutton();

}
   
   boolean checknum(int dx)
   {
       if (dx == 1)
           return true;
       
       if (dx == 2)
           return true;
       
       if (dx == 3)
           return true;
       
       if (dx == 4)
           return true;
       
       if (dx == 5)
           return true;
       
       if (dx == 6)
           return true;
       
       if (dx == 23)
           return true;
       
       if (dx == 24)
           return true;
       
       if (dx == 25)
           return true;
       
       if (dx == 26)
           return true;
       
       if (dx == 27)
           return true;
       
       if (dx == 28)
           return true;
       
       if (dx == 29)
           return true;
       
       if (dx == 30)
           return true;
       
       if (dx == 31)
           return true;
       
       else 
           return false;
       
   }
   
   public void offbutton()
   {
       //if(checknum(Integer.parseInt(labs[4][0].getText())) == false)
                 labs[4][0].setDisable(true);  
       
        if(labs[4][1].getText() == "")
                 labs[4][1].setDisable(true);   
        
        if(labs[4][2].getText() == "")
                 labs[4][2].setDisable(true);   
           
        if(labs[4][3].getText() == "")
                 labs[4][3].setDisable(true);   
              
        if(labs[4][4].getText() == "dx")
                 labs[4][4].setDisable(true);   
                 
        if(labs[4][5].getText() == "")
                 labs[4][5].setDisable(true);   
        
        if(labs[4][6].getText() == "")
                 labs[4][6].setDisable(true); 
        
        if(labs[5][0].getText() == "")
                 labs[5][0].setDisable(true);  
       
        if(labs[5][1].getText() == "")
                 labs[5][1].setDisable(true);   
        
        if(labs[5][2].getText() == "")
                 labs[5][2].setDisable(true);   
           
        if(labs[5][3].getText() == "")
                 labs[5][3].setDisable(true);   
              
        if(labs[5][4].getText() == "")
                 labs[5][4].setDisable(true);   
                 
        if(labs[5][5].getText() == "")
                 labs[5][5].setDisable(true);   
        
        if(labs[5][6].getText() == "")
                 labs[5][6].setDisable(true); 
        
        
        
        
   }
   

}



