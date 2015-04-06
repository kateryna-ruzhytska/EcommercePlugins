package Shared;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by kruzhitskaya on 06.04.15.
 */
public class LogParserPage {
    private static ChannelSftp sftpChannel;
    private static Session session;

    public static void setConnection(int port, String fileName) throws IOException {

        String user = "administrator";
        String password = "pass@word1";
        String host = "10.0.1.17";

        try
        {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating SFTP Channel.");
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");

            sftpChannel.rm(fileName);
            Thread.sleep(1000);

        }
        catch(Exception e){System.err.print(e);}

    }
    public static void readFile(String fileName) throws IOException {
        try {
            InputStream out = null;
            out = sftpChannel.get(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
            br.close();

            session.disconnect();
        }
        catch(Exception e){System.err.print(e);}
    }
}
