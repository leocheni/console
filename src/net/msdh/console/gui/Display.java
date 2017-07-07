package net.msdh.console.gui;

import net.msdh.console.utils.Utils;
import net.msdh.h3d.Home3D;
import net.msdh.jtconsole.ConsoleAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.16
 * Time: 9:24
 * To change this template use File | Settings | File Templates.
 */
public class Display extends JFrame{
  private static volatile Display instance;

  //private Queue queue;
  //private Connection connect;

  private JButton exitbutton;
  private JButton hostsButton;
  private JTabbedPane mainTab;
  private int height;
  private int width;

  private SystemTab systemTab;
  private ManagementTab managementTab;
  private MediaTab mediaTab;
  private ConsoleTab consoleTab;

  private String cmdLine;

  public static Display getInstance() {
    Display localInstance = instance;

    if(localInstance == null){
      synchronized (Display.class){
        localInstance = instance;
        if(localInstance == null) {
		  instance = localInstance = new Display();
		}
	  }
	}
	return localInstance;
  }

  private Display(){
    super("Console");
    System.out.println("enter display costructor");
    //systemTab.setVisible(false);
    width = 1024;
    height = 768;
    this.setBounds(100,100,width,height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    ImageIcon iconExit = new ImageIcon(Utils.createImageIcon("Resources/Images/sys/shutdown.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));
    exitbutton = new JButton("exit");
    exitbutton.setIcon(iconExit);
     exitbutton.setBounds(950,1,20,20);
    this.add(exitbutton);

    ImageIcon iconSystem = new ImageIcon(Utils.createImageIcon("Resources/Images/sys/general.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));
    ImageIcon iconManagers = new ImageIcon(Utils.createImageIcon("Resources/Images/sys/general.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));
    ImageIcon iconMedia = new ImageIcon(Utils.createImageIcon("Resources/Images/sys/general.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));
    ImageIcon iconConsole = new ImageIcon(Utils.createImageIcon("Resources/Images/sys/general.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));

    mainTab = new JTabbedPane();

    systemTab = new SystemTab();
    mainTab.addTab("Система", iconSystem, systemTab, "System");
    mainTab.setMnemonicAt(0, KeyEvent.VK_1);

    managementTab = new ManagementTab();
    mainTab.addTab("Управление", iconManagers, managementTab,"Management");
    mainTab.setMnemonicAt(1, KeyEvent.VK_2);

    mediaTab = new MediaTab();
    mainTab.addTab("Медиа", iconMedia, mediaTab,"Media");
    mainTab.setMnemonicAt(2, KeyEvent.VK_3);

    consoleTab  = new ConsoleTab(width,height);
    mainTab.addTab("Текстовая консоль", iconConsole, consoleTab,"Console");
    mainTab.setMnemonicAt(3, KeyEvent.VK_4);

    this.add(mainTab, BorderLayout.CENTER);

    System.out.println("display constructor exit");
  }

  public int getConsoleLine(int win){
      return consoleTab.getMain().getLinesSize();
  }

  public int SetConsoleLine(int win, String message, char type){
    Color fg = null;
    Color bg = null;
    if(win==0){
        switch(type){
                  case 'i':{
                    fg = Color.GRAY;
                    bg = Color.BLACK;
                    break;
                  }
                  case 'w':{
                    fg = Color.ORANGE;
                    bg = Color.BLACK;
                    break;
                  }
                  case 'e':{
                    fg = Color.RED;
                    bg = Color.BLACK;
                    break;
                  }
                }
       consoleTab.getMain().writeln(message,fg,bg);
    }
    else if(win==1){
        switch(type){
                  case 'i':{
                    fg = Color.GRAY;
                    bg = Color.BLACK;
                    break;
                  }
                  case 'w':{
                    fg = Color.ORANGE;
                    bg = Color.BLACK;
                    break;
                  }
                  case 'e':{
                    fg = Color.RED;
                    bg = Color.BLACK;
                    break;
                  }
                }

       consoleTab.getInfo().writeln(message,fg,bg);
    }
    else if(win==2){
        switch(type){
                  case 'i':{
                    fg = Color.GRAY;
                    bg = Color.BLACK;
                    break;
                  }
                  case 'w':{
                    fg = Color.ORANGE;
                    bg = Color.BLACK;
                    break;
                  }
                  case 'e':{
                    fg = Color.RED;
                    bg = Color.BLACK;
                    break;
                  }
                }

      consoleTab.getStatus().writeln(message,fg,bg);
    }
    return 0;
  }

  public void addConsoleKeyListener(KeyListener obj){
    consoleTab.getMain().addKeyListener(obj);
  }

  public void addExitButtonListener(ActionListener obj){
    exitbutton.addActionListener(obj);
  }

  public void addConsoleListener(ConsoleAction obj){
    consoleTab.getMain().addConsoleListener(obj);

  }

  public Home3D getHome3D(){
    return managementTab.getHome3D();
  }
}
