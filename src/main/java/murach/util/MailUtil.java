package murach.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailUtil {

    public static void sendMail(String to, String from,
                                String subject, String body, boolean bodyIsHTML,
                                String username, String password)
            throws MessagingException {

        // Đọc cấu hình SMTP linh hoạt từ biến môi trường (phục vụ Deploy Render/Cloud)
        String host = System.getenv("SMTP_HOST");
        if (host == null || host.trim().isEmpty()) {
            host = "smtp.gmail.com";
        }

        String port = System.getenv("SMTP_PORT");
        if (port == null || port.trim().isEmpty()) {
            port = "465";
        }

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");

        if ("465".equals(port)) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }

        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=utf-8");
        } else {
            message.setText(body);
        }

        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        Transport.send(message);
    }
}
