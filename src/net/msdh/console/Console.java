package net.msdh.console;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.net.NetServer;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.base.Queue;
import net.msdh.console.gui.Display;
import net.msdh.console.gui.View;
import net.msdh.kernel.utils.Log;
import net.msdh.jtconsole.ConsoleAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 07.06.16
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */

public class Console {
  private ConsoleReciver consoleReciver;
  Queue queue;
  //Connection connect;
  //NetServer ns = new NetServer();
  NetClient nc;
  //private String cmdLine;
  private String coreIP;
  private int corePort;

  public Console(){
    coreIP = Settings.getInstance().getCoreAdress();
    corePort = Settings.getInstance().getCorePort();
   // connect = new Connection();

    nc = new NetClient();
    queue = new Queue();
    //cmdLine="";
  }


    class ConsoleListener implements ConsoleAction {
        @Override
        public void onLine(String line) {

          String answer;
          try{
            queue.cmdParser(line);
            Command cmd = queue.getCommand();

            if(cmdValidator(cmd)){
              sendStatus();
              nc.Send(coreIP, corePort, cmd.toJson());
              answer = nc.Read();
              Display.getInstance().SetConsoleLine(0, answer, 'i');
              Display.getInstance().SetConsoleLine(0, View.Responce(answer), 'i');

            }

            int lines = Display.getInstance().getConsoleLine(0);
            Display.getInstance().SetConsoleLine(1,"Lines: " + lines , 'i');
          }
          catch (IOException e1) {
            Display.getInstance().SetConsoleLine(0, "CONSOLE::Ошибка отправки данных: " + e1, 'e');
          }
          catch (JSONRPC2ParseException e1) {
            Display.getInstance().SetConsoleLine(0, "CONSOLE::Ошибка обработки ответа: " + e1, 'e');
          }
        }
    }

  class ExitButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         System.out.println("exit");
         Unload();
         System.exit(0);
      }
  }

  public int Load(){

    consoleReciver = new ConsoleReciver(Thread.NORM_PRIORITY,"ConsoleReciver");
    consoleReciver.start();

    Display.getInstance().addExitButtonListener(new ExitButtonListener());
    Display.getInstance().addConsoleListener(new ConsoleListener());

    Map<String,Object> params = new HashMap<String, Object>();

    params.put("item","console");
    params.put("action","start");
    params.put("port","60001");

    try{
      nc.Send(coreIP,corePort,new Command("mod",params,1).toJson());
      //String answer = connect.ReadClient();
      //Display.getInstance().SetConsoleLine(2, View.Responce(answer), 'i');
      nc.Close();
    }
    catch(IOException e){
      Log.getInstance().E("Console::Load",e.getMessage());
      Display.getInstance().SetConsoleLine(2, "Console::Load " + e.getMessage(), 'e');
    }

//    catch (JSONRPC2ParseException e) {
//      Log.getInstance().E("Console::Loads",e.getMessage());
//      Display.getInstance().SetConsoleLine(2, "Console::Load " + e.getMessage(), 'e');
//    }
    return 0;
  }

  private boolean cmdValidator(Command cmd){

  Map<String,Object> Params = cmd.getParams();
  if(!cmd.getMethod().equals("")){
    if(cmd.getMethod().equals("user")){
      if(Params.get("action").equals("login")){
        if((!Params.containsKey("item"))||(!Params.containsKey("add"))){
          Display.getInstance().SetConsoleLine(0, "insert username and password please", 'w');
          return false;
        }
      }
      else if(Params.get("action").equals("logout")){
        if(!Params.containsKey("item")){
          Display.getInstance().SetConsoleLine(0, "insert username please", 'w');
          return false;
        }
      }
      else if(Params.get("action").equals("show")){
        return true;
      }
      else{
        Display.getInstance().SetConsoleLine(0, "use: login|logout|show", 'w');
        return false;
      }
    }
    else if(cmd.getMethod().equals("system")){
      if((!Params.containsKey("action"))||((!Params.get("action").equals("down"))&&(!Params.get("action").equals("restart"))&&(!Params.get("action").equals("status")))){
        Display.getInstance().SetConsoleLine(0, "use:down|restart|status", 'w');
        return false;
      }

    }
    else if(cmd.getMethod().equals("host")){
      if((!Params.containsKey("action"))||((!Params.get("action").equals("show"))&&(!Params.get("action").equals("start"))&&(!Params.get("action").equals("shutdown"))&&(!Params.get("action").equals("reboot"))&&(!Params.get("action").equals("wakeup")))){
        Display.getInstance().SetConsoleLine(0, "use:show:start:shutdown:reboot:wakeup", 'w');
        return false;
      }
    }
	else if(cmd.getMethod().equals("timer")){

      if((!Params.containsKey("action"))||((!Params.get("action").equals("add"))&&(!Params.get("action").equals("del"))&&(!Params.get("action").equals("show")))){
        Display.getInstance().SetConsoleLine(0, "use:add|del|show", 'w');
        return false;
      }
      else {
        if(Params.get("action").equals("add")){
          if(!Params.containsKey("item")){
            Display.getInstance().SetConsoleLine(0, "use:timer|add|[command]|[time]", 'w');
            return false;
          }
        }
        else if(Params.get("action").equals("del")){
          if(!Params.containsKey("item")){
            Display.getInstance().SetConsoleLine(0, "use:timer|del|[number timer]", 'w');
            return false;
          }
        }
        else if(Params.get("action").equals("show")){
          return true;
        }
      }

	}
	else if(cmd.getMethod().equals("mod")){
	  if((!Params.containsKey("action"))||((!Params.get("action").equals("load"))&&(!Params.get("action").equals("unload"))&&(!Params.get("action").equals("show")))){
          Display.getInstance().SetConsoleLine(0, "use:load|unload|show", 'w');
          return false;
      }
	  else{
        if(Params.get("action").equals("load")){
	      if(!Params.containsKey("item")){
            Display.getInstance().SetConsoleLine(0, "use:mod|load|[name]", 'w');
            return false;
          }
        }
        else if(Params.get("action").equals("unload")){
          if(!Params.containsKey("item")){
            Display.getInstance().SetConsoleLine(0, "use:mod|unload|[name]", 'w');
            return false;
          }
        }
        else if(Params.get("action").equals("show")) {
          return true;
        }
	  }
	}
	else if(cmd.getMethod().equals("conf")){
      if((!Params.containsKey("action"))||((!Params.get("action").equals("add"))&&(!Params.get("action").equals("del"))&&(!Params.get("action").equals("save"))&&(!Params.get("action").equals("save_as"))&&(!Params.get("action").equals("show")))){
        Display.getInstance().SetConsoleLine(0, "use:add|del|save|save_as|show", 'w');
        return false;
      }

	}else if(cmd.getMethod().equals("dev")){
	  if((!Params.containsKey("action"))||((!Params.get("action").equals("disable"))&&(!Params.get("action").equals("add"))&&(!Params.get("action").equals("del"))&&(!Params.get("action").equals("stat"))&&(!Params.get("action").equals("on"))&&(!Params.get("action").equals("off"))&&(!Params.get("action").equals("send"))&&(!Params.get("action").equals("show")))){
          Display.getInstance().SetConsoleLine(0, "use:enable|disable|add|del|stat|on|off|send|show", 'w');
          return false;
      }

	}else if(cmd.getMethod().equals("rule")){
	  if((!Params.containsKey("action"))||((!Params.get("action").equals("enable"))&&(!Params.get("action").equals("disable"))&&(!Params.get("action").equals("add"))&&(!Params.get("action").equals("del"))&&(!Params.get("action").equals("show")))){
        Display.getInstance().SetConsoleLine(0, "use:enable|disable|add|del|show", 'w');
        return false;
      }

	}else if(cmd.getMethod().equals("rescan")){
	  if((!Params.containsKey("action"))||((!Params.get("action").equals("dev"))&&(!Params.get("action").equals("mod")))){
        Display.getInstance().SetConsoleLine(0, "use:dev|mod", 'w');
        return false;
      }

	}else if(cmd.getMethod().equals("media")){
	  if((!Params.containsKey("action"))||((!Params.get("action").equals("message"))&&(!Params.get("action").equals("sources"))&&(!Params.get("action").equals("list"))&&(!Params.get("action").equals("play"))&&(!Params.get("action").equals("pause"))&&(!Params.get("action").equals("stop"))&&(!Params.get("action").equals("exit")))){
        Display.getInstance().SetConsoleLine(0, "use:sources|list|play|pause|stop|message|exit", 'w');
        return false;
      }
	}
	else if(cmd.getMethod().equals("?")){

	 Display.getInstance().SetConsoleLine(0, "system\n" +
             "mod\n" +
             "host\n" +
             "conf\n" +
             "dev\n" +
             "rule\n" +
             "timer\n" +
             "media\n" +
             "rescan\n" +
             "exit", 'w');
     return false;
	}
	else if(cmd.getMethod().equals("exit")){
	  try {
        Unload();
        Log.getInstance().I("CONSOLE.Main","--------------Stop exit------------");
        Log.getInstance().Close();
      }
      catch (IOException e1) {
        JOptionPane.showMessageDialog(null,"Error: " + e1.getMessage(),"Output",JOptionPane.PLAIN_MESSAGE);
      }
      System.exit(0);
	}
	else{
	  Display.getInstance().SetConsoleLine(0, "Unknown comand, type ?", 'w');
      return false;
	}
  }
    return true;
  }

  public int Unload(){

    if(consoleReciver!=null){
      consoleReciver.stop();
   //   try {
   //     consoleReciver.join();
  //    }
   //   catch (InterruptedException ignored) {
  //    }
    }

    Map<String,Object> params = new HashMap<String, Object>();
    params.put("item","console");
    params.put("action","stop");
    params.put("port","60001");

    try {
      nc.Send(coreIP,corePort,new Command("mod",params,1).toJson());
      nc.Close();
    }
    catch (IOException e) {
      Log.getInstance().E("Console::Unload",e.getMessage());
    }
    return 0;
  }

  public void sendStatus(){

    Map<String,Object> params = new HashMap<>();
    params.put("item","console");
    params.put("action","set");
    params.put("param","status");
    params.put("value","up");

    try {
      nc.Send(coreIP,corePort,new Command("mod",params,1).toJson());
      nc.Close();
    }
    catch (IOException e) {
      Log.getInstance().E("Console.SendStatus: ",e.getMessage());
    }
  }
}
