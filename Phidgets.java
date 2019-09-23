import java.awt.*;
import java.io.File;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import com.phidgets.*;
import com.phidgets.event.*;
import javax.sound.sampled.AudioSystem;  
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class Phidgets extends JFrame
{
   private AdvancedServoPhidget servo;
   private InterfaceKitPhidget ik;
   private JPanel panel; 
   private final int WINDOW_WIDTH = 1440;  
   private final int WINDOW_HEIGHT = 900;
   private JLabel messageLabel;
   private int count=0;
   private boolean b=false;
   String s="Learn about the Government!";
   
   public Phidgets() 
   {  
    
      setTitle("Learn About the Government!");
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      try 
      {
         buildPanel();
      }
      catch(Exception e)
      {
         System.out.println("Motor Error");
      }  
      add(panel);
      setVisible(true); 
   }   
   
   private void buildPanel()throws Exception
   {  
      messageLabel = new JLabel("Press the sensor to learn about each branch of the government");
      panel = new JPanel();
      panel.add(messageLabel); 
      messageLabel.setFont(messageLabel.getFont().deriveFont(32.0f));  
      try{
         ik = new InterfaceKitPhidget();           
         ik.openAny();
         ik.waitForAttachment();
         ik.addSensorChangeListener( new InterfaceSensorChangeListener());
         servo = new AdvancedServoPhidget();
         servo.openAny();
         System.out.println("waiting for AdvancedServo attachment...");
         servo.waitForAttachment();
      
         
         servo.setEngaged(0, false);
         servo.setSpeedRampingOn(0, false);
          
         System.out.println("Serial: " + servo.getSerialNumber());
         System.out.println("Servos: " + servo.getMotorCount());
      }
      catch (Exception e)
      {
         System.out.println("phidget Error");
      }   
   }
  
   private class InterfaceSensorChangeListener implements SensorChangeListener
   {    
      public void sensorChanged(SensorChangeEvent se){   
              
        // Get the port number of the analog sensor that has changed.
         
         int port = se.getIndex();
         int value = se.getValue();
         int c=0;
        
         if(value>550)
         {
            try
            {
               servo.setPosition(0, 100);
               servo.setEngaged(0, true);          
               Thread.sleep(270);
               servo.setEngaged(0, false);
               count+=1;
            }
            catch(Exception ex)
            {
               System.out.println("Servo Error");
            }
         } 
         else if(value<550)
         {
            count+=1;
         } 
            
           
         if(count==2)
         {
            try
            {        
               judicial();   
            }
            catch(Exception ex)
            {
               System.out.println("Servo Error");
            }
         } 
      
         if(count==4)
         {
            try
            {
               executive();  
            }
            catch(Exception ex)
            {
               System.out.println("Servo Error");
            } 
         }
         if(count==6)
         {
            try
            {
               legislative();
            }
            catch(Exception ex)
            {
               System.out.println("Servo Error");
            } 
         }
         if(count>6)
         {
         count=-1;
         }

      }
      
   }
   
   public void audio(String s)
   {
       File file;
       Clip clip;
        try
         {
            file=new File(s);
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start(); 
         }
         catch(Exception e)
         {
            System.out.println("No file found");
         } 
       }     
   public void executive() 
   {
      
      Clip clip;
   
      Random executiveFacts= new Random();
      int exec=executiveFacts.nextInt(4)+1;
     
    
      if(exec==1)
      {
        
       audio("executiveAudio1.wav");
        
      }
      
      if(exec==2)
      {
         audio("executiveAudio2.wav");
      }
      
      if(exec==3)
      {
       audio("executiveAudio3.wav");
      }
      if(exec==4)
      {
       audio("executiveAudio4.wav");
      }
       
   }   
   
   public void judicial() 
   {
      File judicialFile1;
      File judicialFile2;
      File judicialFile3;
      File judicialFile4;
      Clip clip;
    
      Random judicialFacts=new Random();
      int jud=judicialFacts.nextInt(3)+1;
       
        
      if(jud==1)
      {
      audio("judicialAudio1.wav"); 
      }
      
      if(jud==2)
      {
        audio("judicialAudio2.wav"); 
      }
      if(jud==3)
      {
       audio("judicialAudio3.wav");   
      }
      if(jud==4)
      {
        audio("judicialAudio4.wav");
      }       
      
   }
         
      

   public void legislative()
   {
          
      File legislativeFile1;
      File legislativeFile2;
      File legislativeFile3;
      File legislativeFile4;
      Clip clip;
      
      Random legislativeFacts=new Random();
      int leg=legislativeFacts.nextInt(3)+1;
      if(leg==1)
      {
         try
         {
            legislativeFile1=new File("legislativeAudio1.wav");
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(legislativeFile1));
            clip.start(); 
         }
         catch(Exception e)
         {
            System.out.println(e.getMessage());
         }     
      }
      
      if(leg==2)
      {
         try
         {
            legislativeFile2=new File("legislativeAudio2.wav");
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(legislativeFile2));
            clip.start(); 
         }
         catch(Exception e)
         {
            System.out.println(e.getMessage());
         }     
      }
      if(leg==3)
      {
         try
         {
            legislativeFile3=new File("legislativeAudio3.wav");
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(legislativeFile3));
            clip.start(); 
         }
         catch(Exception e)
         {
            System.out.println(e.getMessage());
         }     
      }
      if(leg==4)
      {
         try
         {
            legislativeFile4=new File("legislativeAudio4.wav");
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(legislativeFile4));
            clip.start(); 
         }
         catch(Exception e)
         {
            System.out.println(e.getMessage());
         }
      }      
        
   }    
        
   
   public static void main(String[] args)throws Exception
   {
      Phidgets ph = new Phidgets();
   } 
}






