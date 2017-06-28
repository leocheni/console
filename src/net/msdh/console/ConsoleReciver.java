package net.msdh.console;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import net.msdh.console.base.Command;
import net.msdh.console.base.Queue;
import net.msdh.console.gui.Display;
import net.msdh.console.gui.View;
import net.msdh.console.net.Connection;
import net.msdh.console.utils.Log;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.16
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleReciver implements Runnable{

    private Thread t;
    private boolean running = true;
    private String name;

    public ConsoleReciver(int p, String name){
      this.name = name;
      this.t = new Thread(this, name);
      this.t.setPriority(p);
    }

    public void run(){

    Display.getInstance().SetConsoleLine(2,"ConsoleReciver start",'i');
    //console.SetLine(2,"ConsoleReciver start",'i');

    Connection p = new Connection();
    Queue q = new Queue();
    try{
      p.Listen(60001);

      while(running) {  // main accept() loop
        //Command c;
        p.Accept();
        String line;
        try{
          line = p.ReadServer();

          try{
            Display.getInstance().SetConsoleLine(2, View.Request(line),'i');
          }
          catch (JSONRPC2ParseException e) {

            Display.getInstance().SetConsoleLine(2, View.Responce(line),'i');
          }

          //q.JParser(line);
         // c = q.getCommand();

//          switch(c.getId()){
//            case 1:{
//              if(c.getMethod().equals("block")){
//                Display.getInstance().SetConsoleLine(2, View.Command(c.toJson()),'i');
//              }
//              else if(c.getMethod().equals("notification")){
//                Display.getInstance().SetConsoleLine(2, View.Command(c.toJson()),'i');
//              }
//              else if(c.getMethod().equals("error")){
//                Display.getInstance().SetConsoleLine(2, View.Command(c.toJson()),'e');
//              }
//              else if(c.getMethod().equals("warning")){
//                Display.getInstance().SetConsoleLine(2, View.Command(c.toJson()),'w');
//              }
////              else if(c.getMethod().equals("message")){
////                Display.getInstance().SetConsoleLine(2, View.Command(c.toJson()),'w');
////              }
//              break;
//            }
//          }//switch
        }
        catch(Exception ce){
          Log.getInstance().E("CONSOLE::ConsoleRecv","Error: " + ce.getMessage());
          //Display::getInstance().SetLine(INFO,"Error: " + ce.GetMessage(),'e');
        }
        p.CloseServerAccept();
      }//while
    p.CloseServer();
  }
  catch(Exception e){
    Log.getInstance().E("CONSOLE::ConsoleRecv", e.getMessage());
    //Display::getInstance().SetLine(STATUS,e.getMessage(),'e');
  }

  //Log::getInstance().D("CONSOLE::ConsoleRecv","exit from ConsoleReciver");
  //Display::getInstance().SetLine(INFO,"exit from ConsoleReciver",'i');
   Display.getInstance().SetConsoleLine(2, "exit from ConsoleReciver", 'i');
  }

  public void stop(){
    running = false;
  }
  public void start(){
    t.start();
  }

  public Thread.State getState(){
    return t.getState();
  }

  public void join() throws InterruptedException {
    try {
      t.join();
    }
    catch (InterruptedException e) {
      Log.getInstance().E("Console Reciver","Error: "+e.getMessage());
    }
  }
}
