package net.msdh.console;

import net.msdh.kernel.settings.Settings;
import net.msdh.console.gui.Display;
import net.msdh.kernel.utils.Log;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.16
 * Time: 9:33
 * To change this template use File | Settings | File Templates.
 */
public class Main {
  public static void main(String[] argv) {
    try {

      System.out.println("start");

      Log.getInstance().setLevel("DEBUG");
      //Log.getInstance().Open("F:\\dev\\projects\\lab\\console\\out\\artifacts\\console\\Console.log");
      Log.getInstance().Open("Console.log");
      Log.getInstance().I("CONSOLE.Main","--------------Start------------");
      System.out.println("log started");
      //Settings.getInstance().setCoreAdress("172.29.249.139");
      Settings.getInstance().setCoreAdress("127.0.0.1");
      //Settings.getInstance().setCoreAdress("192.168.0.11");
      Settings.getInstance().setCorePort(60000);
      System.out.println("enter display");
      Display.getInstance().setVisible(true);


      System.out.println("Display created");

      Console console = new Console();
      console.Load();
      System.out.println("Window created");
      Log.getInstance().I("CONSOLE.Main","Window created");
      System.out.println("stop");
     // console.Unload();
     // Log.getInstance().I("CONSOLE.Main","--------------Stop main------------");
     // Log.getInstance().Close();
    }
    catch (IOException e) {
      JOptionPane.showMessageDialog(null,"Error: " + e.getMessage(),"Output",JOptionPane.PLAIN_MESSAGE);
    }
  }
}
