package net.msdh.console.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 08.06.16
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class Connection {

    private  Socket sidClient;
    private  ServerSocket sidServer;
    private  Socket sidServerAccept;

    //private  struct sockaddr_in addr;
    private  char termSimbol;
    //private  socklen_t n;
    private  boolean connected;


    public Connection(){

    }


    public void Listen(int port) throws IOException {
       sidServer = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
    }

    public void Accept() throws IOException {
      sidServerAccept = sidServer.accept();
    }

    public String ReadServer() throws IOException {

      InputStream is = sidServerAccept.getInputStream();
      byte[] buf = new byte[1];
      String cmd = "";
      while(true){
        int r = is.read(buf);
        //int r = sidClient.getInputStream().read(buf);
        if(r==0){
          break;
        }
        if(buf[0]=='#'){
          break;
        }
        //cmd += buf[0];
        cmd += new String(buf,0,1);
      }
      //return new String(buf, 0, r);
      return cmd;


     // InputStream is = sidServerAccept.getInputStream();
    //  byte buf[] = new byte[64*1024];
    //  int r = is.read(buf);
    //  return new String(buf, 0, r);
    }

    public void Open(String ip,int port) throws IOException {
      sidClient = new Socket(ip,port);
    }

    public void Send(String ip,int port,String message) throws IOException {
      Open(ip,port);
      Send(message);
    }

    public void Send(String message) throws IOException {
      sidClient.getOutputStream().write((message+"#").getBytes());
    }

    public void SendServer(String message) throws IOException {
      OutputStream os = sidServerAccept.getOutputStream();
      os.write(message.getBytes());
    }

    public String ReadClient() throws IOException {

      byte[] buf = new byte[1];
      String cmd = "";
      while(true){
        int r = sidClient.getInputStream().read(buf);
        if(r==0){
          break;
        }
        if(r==-1){
          throw (new IOException("Recive empty line"));

        }
        if(buf[0]=='#'){
          break;
        }
        cmd += new String(buf,0,1);

      }
      return cmd;

//     InputStream is = sidClient.getInputStream();
//      byte buf[] = new byte[64*1024];
//      int r = is.read(buf);
//      return new String(buf, 0, r);

    }

    public void CloseServer() throws IOException {
      sidServer.close();
    }

    public void CloseServerAccept() throws IOException {
      sidServerAccept.close();
    }

    public void CloseClient() throws IOException {
      sidClient.close();
    }
}
