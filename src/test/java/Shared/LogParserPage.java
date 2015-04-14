package Shared;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


/**
 * Created by kruzhitskaya on 06.04.15.
 */
public class LogParserPage {
    private static ChannelSftp sftpChannel;
    private static Session session;

    public static void setConnection(int port, String file) throws IOException {
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

            sftpChannel.rm(file);
            Thread.sleep(1000);
        }
        catch(Exception ex){System.err.print(ex);}

    }

   /* public static ArrayList<String> readFile(String fileName) throws IOException {
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
        }
        catch(Exception e){System.err.print(e);}
        return log;
    }*/
    public static void readJson(String file, String key){
        JSONParser parser = new JSONParser();
        try {
            System.out.println("Reading JSON file");
            FileReader fileReader = new FileReader(file);
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            JSONArray requestResponse = (JSONArray) jsonObject.get(key);

            Iterator iterator = requestResponse.iterator();
            while (iterator.hasNext()) {
                System.out.println(" " + iterator.next());
            }
        } catch(Exception ex){System.err.print(ex);}

    }






}
