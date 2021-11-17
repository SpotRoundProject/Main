package com.example.spotround.modle;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMailAPI extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private Context context;
    private Multipart _multipart = new MimeMultipart();

    private static final java.security.Security Security = null;

    static {
        Security.addProvider(new com.example.spotround.modle.Utils());
    }

    public JavaMailAPI(Context context,String user, String password) {
        this.context = context;
        this.user = user;
        this.password = password;

        /*Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.socketFactory.port", "2525");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");*/
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailhost);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
// *** BEGIN CHANGE
        properties.put("mail.smtp.user", user);

        session = Session.getDefaultInstance(properties, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendPaymentMail(String subject, String body, String date, String id, String applicationID, String from, String mail) throws Exception {
        try{
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setFrom(new InternetAddress(from));
            message.setSender(new InternetAddress(from));
            message.setSubject(subject);
            message.setDataHandler(handler);

            BodyPart messageBodyPart = new MimeBodyPart();
            InputStream is = context.getAssets().open("payment_mail.html");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String str = new String(buffer);
            str=str.replace("$$id$$", id);
            str=str.replace("$$email$$", mail);
            str=str.replace("$$applicationId$$", applicationID);
            str=str.replace("$$date$$", date);
            messageBodyPart.setContent(str,"text/html; charset=utf-8");

            _multipart.addBodyPart(messageBodyPart);

            // Put parts in message

            message.setContent(_multipart);

            if (mail.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void sendSeatAcceptedMail(String subject, String body, String date, String applicationID, String from, String mail, Result result) throws Exception {
        try{
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setFrom(new InternetAddress(from));
            message.setSender(new InternetAddress(from));
            message.setSubject(subject);
            message.setDataHandler(handler);

            BodyPart messageBodyPart = new MimeBodyPart();
            InputStream is = context.getAssets().open("seat_accepted.html");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String str = new String(buffer);
            str=str.replace("$$email$$", mail);
            str=str.replace("$$applicationId$$", applicationID);
            str=str.replace("$$date$$", date);
            str=str.replace("$$seatCode$$", result.getChoiceCode());
            str=str.replace("$$seatType$$", result.checkType().toUpperCase());
            str=str.replace("$$preferenceNo$$", result.getPreferenceNo());
            messageBodyPart.setContent(str,"text/html; charset=utf-8");

            _multipart.addBodyPart(messageBodyPart);

            // Put parts in message

            message.setContent(_multipart);

            if (mail.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}