package net.msdh.console.gui;

import net.msdh.console.gui.jconsole.JConsole;

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


  private JConsole main;
  private JConsole info;
  private JConsole status;

  private int height;
  private int width;

  public ConsoleTab(int width,int height) {
    this.width = width;
    this.height = height;

  //  main = new JTextArea();
  //  scrollmain=new JScrollPane(main);

    //main = new JConsole(60,24);
    main = new JConsole(60,24);
    main.setFocusable(true);
    main.setCursorVisible(true);
    main.write("Hello World\n",Color.GREEN,Color.BLACK);

    info = new JConsole(34,34);
    info.write("Info\n", Color.GREEN, Color.BLACK);

    status = new JConsole(60,10);
    status.write("Status\n", Color.GREEN, Color.BLACK);

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

  public JConsole getMain() {
    return main;
  }

//  public JTextArea getMain() {
//    return main;
//  }

  public JConsole getInfo() {
        return info;
  }

  public JConsole getStatus() {
        return status;
  }

  public void addConsoleKeyListener(KeyListener obj){
    main.addKeyListener(obj);
  }

}
