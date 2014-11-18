/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionsh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;


/**
 *
 * @author y9bmc8
 */
public class ConectionPorta {

    private static final String user = "user";
    private static final String server = "server";
    private static final int port = 22;
    private static final String pwd = "password";

    public static void main(String[] args) throws Exception {
        forCommand();

    }

    public static void forCommand() {
        try {
            //Create conexion
            JSch jSSH = new JSch();
            Session session = jSSH.getSession(user, server, port);
            UserInfo ui = new UserSession(pwd, null);
            session.setUserInfo(ui);
            session.setPassword(pwd);
            session.connect();

            //Create channel:
            Channel channel = session.openChannel("shell");

            //Configure the output and imput channel:
            OutputStream inputstream_for_the_channel = channel.getOutputStream();
            PrintStream commander = new PrintStream(inputstream_for_the_channel, true);

            channel.setOutputStream(null);
            channel.connect(100);

            //Command to run:
            commander.println("cd /opt/weblogic/..../FolderScript; ./script.sh param1 param2; exit");
            commander.flush();

            //System.out.println(channel.getExitStatus());

            InputStream outputstream_from_the_channel = channel.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
            String line = null;
            

            StringBuilder sb = new StringBuilder();
            

            while ((line = br.readLine()) != null) {
                 sb.append(line.trim());
                
            }
            System.out.println("Result = " + sb.toString());
            
            channel.disconnect();
            session.disconnect();
            
            System.out.println("Completed .. ");
            
        } catch (JSchException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
