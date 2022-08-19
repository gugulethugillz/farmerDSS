/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.logic;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author gugsy
 */

public class farmerMail implements java.lang.Runnable {
   private Thread t;
   public String a;
   public String b;
   public String c;
   public String d;
   public String e;
   public String g;
   public String h="  ";

  public farmerMail( String farmerId, String password, String email, String fname , String surname) {
      a= farmerId;
      b =email;
      c = password;
      d = fname;
      e = surname;
      g = d+h+e;
      System.out.println("syaffika yini laa: "+a);
      start();
              
      
   }

    farmerMail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
 
   
   
   public void run()  {
       
       
      System.out.println("Running thread for applicant ID: "+a);
       // Step1
       try {
       System.out.println("Step 1:Setting up Mail Server Properties*****************");
       Properties mailServerProperties = System.getProperties();
       //DECLARING SERVER PROPERTIES
       mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");//to change to port for .238 server
       mailServerProperties.put("mail.smtp.auth", "true");
       mailServerProperties.put("mail.smtp.starttls.enable", "true");
       /* mailServerProperties.put("mail.smtp.port", "9222");
            mailServerProperties.put("mail.smtp.host", "196.220.96.9");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");**/
       System.out.println("Mail Server Properties have been setup successfully**********");
       // Step2 SETTING SENDER AND RECEIPIENT
       System.out.println("Step 2:Getting Mail Session*********************************");
       Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
       MimeMessage generateMailMessage = new MimeMessage(getMailSession);
           
            generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("gugulethugmasuku@gmail.com"));


         generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(b));
         //SETTING MAIL SUBJECT  
         generateMailMessage.setSubject("NOTICE ::ACCOUNT CREATION ON FARMERS DECISION SUPPORT");
       
                //String emailBody = 
                     
                      // "Thank you for signing up for NUST Online Application\n.Your login details are Applicant Id : \n" + a + "\n and your password is : \n"+c+"\n. To complete your application please click on the following link  http://apply.nust.ac.zw/login.jsp. \n If you proceed to pay online using the PayNow, may you please be checking your to confirm the status of your payment.\nRegards NUST Admissions and Students Records Section.";
              
                generateMailMessage.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
"<head>\n" +
"<meta name=\"viewport\" content=\"width=device-width\" />\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
"<title>NUST Online Application</title>\n" +
"</head>\n" +
"<body style=\"margin:0px; background: #f8f8f8; \">\n" +
"<div width=\"100%\" style=\"background: #f8f8f8; padding: 0px 0px; font-family:arial; line-height:28px; height:100%; width: 100%; color: #514d6a;\">\n" +
"<div style=\"max-width: 700px; padding:0px 0; margin: 0px auto; font-size: 14px\">\n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; margin-bottom: 20px\">\n" +
"<tbody>\n" +
"<tr>\n" +
"<td style=\"width:900px;height:165px;vertical-align: top; padding-bottom:5px;\" align=\"center\"><a href=\"#\" target=\"_blank\"><img src=\"\" alt=\"\" style=\"border:none\"><br/>\n" +
"<img src=\"\" alt=\"Empowering Farmers\" style=\"border:none\"></a> </td>\n" +
"</tr>\n" +
"</tbody>\n" +
"</table>\n" +
"<div style=\"padding: 40px; background: #fff;\">\n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%;\">\n" +
"<tbody>\n" +
"<tr>\n" +
"<td><b>Dear "+g+"  ,</b>\n" +
"<p>Welcome you have successfully created your Farmers Decision Support System account. Below are your login details </p>\n" +
"<p>Your Farmer ID is : "+a+" </p>\n" +
"<p>Your Password is: "+c+" </p>\n" +
"<p>Click on the button below to proceed.</p>\n" +
"<a href=\"localhost:8084/farmerDSS/login.jsp\" style=\"display: inline-block; padding: 11px 30px; margin: 20px 0px 30px; font-size: 15px; color: #fff; background: #00c0c8; border-radius: 60px; text-decoration:none;\"> Click Here To Login </a>\n" +
"                        <b>- Thank You</b> </td>\n" +
"                </tr>\n" +
"                </tbody>\n" +
"            </table>\n" +
"        </div>\n" +
"        <div style=\"text-align: center; font-size: 12px; color: #b2b2b5; margin-top: 20px\">\n" +
"            <p> Powered by gugulethumasuku<br>\n" +
"                <a href=\"javascript: void(0);\" style=\"color: #b2b2b5; text-decoration: underline;\"></a> </p>\n" +
"        </div>\n" +
"    </div>\n" +
"</div>\n" +
"</body>\n" +
"</html>", "text/html");
       
       System.out.println("Mail Session has been created successfully******************");
       // Step3
       System.out.println("Step 3:Get Session and Send mail with applicant ID:_"+a);
       Transport transport = null;
       transport = getMailSession.getTransport("smtp");
       
       System.out.println("********Get Session and Send mail test 1************");
       
           // Enter your correct gmail UserID and Password
           // if you have 2FA enabled then provide App Specific Password
                transport.connect("smtp.gmail.com", "mariona.ngwenya@gmail.com", "nonhlanhla&1996");//relay.nust.ac.zw
       
       System.out.println(":Transport connection established****************Attempting to send email*******");
      
                transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
       
       System.out.println("**************::Email Sent succesfully::**********************");
       
        transport.close();
      
       System.out.println("Thread for applicant Id:_" +  a  + "_:exiting.");
       
       } catch (AddressException ex) {
           Logger.getLogger(farmerMail.class.getName()).log(Level.SEVERE, null, ex);
       } catch (MessagingException ex) {
           Logger.getLogger(farmerMail.class.getName()).log(Level.SEVERE, null, ex);
      
       }
       
       
   }
   
   public void start () {
      System.out.println("Starting " +  a );
      if (t == null) {
         t = new Thread (this, a);
         t.start ();
      }
   }
}

 class TestThread {

   public static void main(String args[]) {
     
   }   
}
