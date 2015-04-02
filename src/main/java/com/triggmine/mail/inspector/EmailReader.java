package com.triggmine.mail.inspector;
/**
 * Created by sromanov on 02.04.15.
 */

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

public class EmailReader {
    private ArrayList<String> mailContent = new ArrayList<String>();
    private String receivingHost;

    public ArrayList<String> readGmail(String login, String password) {

        /*this will print subject of all messages in the inbox of sender@gmail.com*/

        this.receivingHost = "imap.gmail.com";//for imap protocol

        Properties props2 = System.getProperties();

        props2.setProperty("mail.store.protocol", "imaps");
        // I used imaps protocol here

        Session session2 = Session.getDefaultInstance(props2, null);

        try {

            Store store = session2.getStore("imaps");

            store.connect(this.receivingHost, login, password);

            Folder folder = store.getFolder("INBOX");//get inbox

            folder.open(Folder.READ_ONLY);//open folder only to read

            Message message[] = folder.getMessages();

            if (message.length == 0) {

                for (int i = 0; i >= message.length; i++) {

                    Thread.sleep(30000);
                    message = folder.getMessages();

                }
            }

            for (int i = 0; i < message.length; i++) {

                mailContent.add(i, message[i].getContent().toString());

            }

            //close connections

            folder.close(true);

            store.close();

        } catch (
                Exception e
                )

        {

            System.out.println(e.toString());

        }

        return mailContent;

    }

    public void deleteAllMessages(String login, String password) throws GeneralSecurityException {

        Properties pros = System.getProperties();
        MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
        socketFactory.setTrustAllHosts(true);
        pros.put("mail.imaps.ssl.socketFactory", socketFactory);

        try {
            Session session = Session.getDefaultInstance(pros, null);

            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", login, password);

            Folder inbox = store.getFolder("INBOX");
            Folder f[] = store.getDefaultFolder().list();
            for (Folder fd : f) {
            }
            inbox.open(Folder.READ_WRITE);
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message message[] = inbox.search(ft);

            for (int i = 0; i <= message.length - 1; i++) {
                message[i].setFlag(Flags.Flag.DELETED, true);
            }
            inbox.close(true);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

}
