package Utilities;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import org.testng.asserts.SoftAssert;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    public static void sendTestReport(List<String> recipients, String reportPath) {
        SoftAssert softAssert = new SoftAssert();
        try {
            final String username = "admin.support@intellectinfo.com";
            final String password = "125"; 

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Session
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            // Multiple recipients
            InternetAddress[] recipientAddresses = new InternetAddress[recipients.size()];
            for (int i = 0; i < recipients.size(); i++) {
                recipientAddresses[i] = new InternetAddress(recipients.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            message.setSubject("🚀 Automation Test Report - ExtentReports");

            // Email body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hi Team,\n\nPlease find the attached ExtentReports HTML report.\n\nRegards,\nAutomation Team");

            
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File reportFile = new File(reportPath);
            attachmentPart.attachFile(reportFile);

            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            
            Transport.send(message);

            softAssert.assertTrue(true, "✅ Report sent successfully to: " + recipients);

        } catch (MessagingException | IOException e) {
            softAssert.fail("❌ Failed to send report: " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }
}
