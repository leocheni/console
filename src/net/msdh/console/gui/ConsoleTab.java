package net.msdh.console.gui;

import net.msdh.jtconsole.JTConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleTab extends JPanel {


  private JTConsole main;
  private JTConsole info;
  private JTConsole status;

  private int height;
  private int width;

  public ConsoleTab(int width,int height) {
    this.width = width;
    this.height = height;

    main = new JTConsole(60,24);
    main.setFocusable(true);
    main.setCursorVisible(true);
    main.writeln("Welcome to MSDH",Color.GREEN,Color.BLACK);

    info = new JTConsole(34,34);
    info.writeln("Info", Color.GREEN, Color.BLACK);

    status = new JTConsole(60,10);
    status.writeln("Status", Color.GREEN, Color.BLACK);

    this.setPreferredSize(new Dimension(410, 50));
    this.setLayout(null);
    int w = (int) ((width-30)*0.75);
    int h = (int) ((height-80)*0.75);

    main.setBounds(5, 5, main.width,main.height);
    status.setBounds(5,main.height + 10, status.width, status.height);
    info.setBounds(main.width+10,5,info.width,info.height);

	this.add(main);
    this.add(info);
    this.add(status);

  }

  public JTConsole getMain() {
    return main;
  }

//  public JTextArea getMain() {
//    return main;
//  }

  public JTConsole getInfo() {
        return info;
  }

  public JTConsole getStatus() {
        return status;
  }

  public void addConsoleKeyListener(KeyListener obj){
    main.addKeyListener(obj);
  }

}
