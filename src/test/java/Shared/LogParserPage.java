package Shared;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by kruzhitskaya on 06.04.15.
 */
public class LogParserPage {
    private static ChannelSftp sftpChannel;
    private static Session session;

    public static void setConnection(String file) throws IOException {
        String user = "root";
        String password = "pass@word1";
        String host = "triggmine.videal.local";
        int port = 22;

        try {
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

        } catch (Exception ex) {
            System.err.print(ex);
        }

    }

    public static void removeFile(String file) throws SftpException, InterruptedException {
        sftpChannel.rm(file);
        Thread.sleep(3000);
    }

    public static ArrayList<String> readFile(String fileName) throws IOException {
        ArrayList<String> log = new ArrayList<String>();
        try {
            InputStream out = null;
            out = sftpChannel.get(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                log.add(line);
            }
            br.close();
        } catch (Exception ex) {
            System.err.print(ex);
        }
        return log;
    }

    public static JSONObject getJson(String file) throws ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(file);
        JSONObject jsonObject = (JSONObject) object;

        return jsonObject;
    }


}
