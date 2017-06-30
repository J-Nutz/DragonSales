package mutual.security;

/*
 * Created by Jonah on 12/8/2016.
 */


import mutual.types.Result;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class VerificationEmailSender implements Runnable
{
    /**
     * Should be used in conjunction with a pay service
     *
     * Pay -> onPaymentReceived -> Send Verification Code -> Allow Access???
     * Whats the point tho?
     * Pay -> onPaymentReceived -> Allow Access
     *
     * Use to send data???
     * PDF or some sort for statistics table/employment info/etc.
     */

    private Properties properties;
    private Session session;
    private Transport transporter;

    private Result result = new Result();
    private String receiver;
    private String verificationCode;

    public VerificationEmailSender()
    {
        properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.connectiontimeout", 1500);
        properties.put("mail.smtp.timeout", 1500);

        session = Session.getDefaultInstance(properties, null);
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public void setVerificationCode(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    @Override
    public void run()
    {
        try
        {
            transporter = session.getTransport("smtp");
            InternetAddress receiversAddress = new InternetAddress(receiver);
            MimeMessage email = new MimeMessage(session);
            String content = "Your Verification Code Is: " + verificationCode + "\n\n Enter This Code Within 24 Hours Of Receiving It To Start Using Dragon Sales";

            receiversAddress.validate();

            email.addRecipient(Message.RecipientType.TO, receiversAddress);
            email.setSubject("Dragon Sales Verification");
            email.setContent(content, "text/html");

            transporter.connect("smtp.gmail.com", "jdaldragonsales@gmail.com", "johndewey17");

            if(transporter.isConnected())
            {
                transporter.sendMessage(email, email.getAllRecipients());
                transporter.close();
                result.setCompleted(true);
                result.setMessage("Email Sent!");
            }
            else
            {
                result.setCompleted(false);
                result.setMessage("No Internet Connection Or SMTP Blocked");
            }
        }
        catch(MessagingException e)
        {
            e.printStackTrace();

            result.setCompleted(false);
            result.setMessage("No Internet Connection Or SMTP Blocked");
        }
    }

    public Result getResult()
    {
        return this.result;
    }

    public Result sendEmail(String receiver, String verificationCode)
    {
        try
        {
            setReceiver(receiver);
            setVerificationCode(verificationCode);

            Thread thread = new Thread(this);
            thread.start();
            thread.join();

            return getResult();
        }
        catch(InterruptedException e1)
        {
            e1.printStackTrace();
            return null;
        }
    }
}