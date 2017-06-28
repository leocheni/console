package net.msdh.console.gui.jconsole.demo;
import net.msdh.console.gui.jconsole.JConsole;
import javax.swing.*;
import java.awt.*;

public class ConsoleApp {

    public static void main(String[] args) {	
    	JConsole jc=new JConsole(40,20);
    	jc.setCursorVisible(true);

        jc.writeln("000000");
        jc.writeln("111111");
        jc.writeln("222222");
        jc.writeln("333333");
        jc.writeln("444444");
        jc.writeln("555555");
        jc.writeln("666666");
        jc.writeln("777777");
        jc.writeln("888888");
        jc.writeln("999999");
        jc.writeln("101010");
        jc.writeln("111111");
        jc.writeln("121212");
        jc.writeln("131313");
        jc.writeln("141414");
        jc.writeln("151515");
        jc.writeln("161616");
        jc.writeln("171717");
        jc.writeln("181818");
        jc.writeln("191919");
        jc.writeln("202020");
        jc.writeln("212121");
        jc.writeln("test",Color.GREEN,Color.BLACK);
      //  jc.write("test2",Color.GREEN,Color.BLACK);
        jc.setFocus();





//        jc.write("0000000000");
//        jc.write("1111111111");
//        jc.write("2222222222");
//        jc.write("3333333333");
//        jc.write("4444444444");
//        jc.write("5555555555");
//        jc.write("6666666666");
//        jc.write("7777777777");
//        jc.write("8888888888");
//        jc.write("9999999999");

    	//System.out.println("Normal output");
    	//jc.captureStdOut();
    	//System.out.println("Captured output");

    	//jc.setCursorPos(0, 0);



        JFrame frame = new JFrame("Swing Text Console");
    	frame.setLayout(new BorderLayout());
		frame.add(jc, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);



    }

}
