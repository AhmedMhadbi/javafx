package services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    String host = "smtp.gmail.com";
    String port = "587"; // Port SMTP pour TLS
    String username = "dridi.mohamedsadok@gmail.com";
    String password = "cmxf rbng zfwx mazb";

    public EmailService() {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String to, String subject, String content) throws MessagingException {
        // Propriétés pour configurer la session mail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Création d'une session mail avec authentification
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Création de l'objet Message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username)); // Adresse de l'expéditeur
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Adresse du destinataire
        message.setSubject(subject); // Sujet de l'e-mail
        message.setText(content); // Contenu de l'e-mail

        // Envoi du message
        Transport.send(message);
    }
}
