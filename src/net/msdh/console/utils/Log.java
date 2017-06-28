package net.msdh.console.utils;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 10.06.16
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */

public class Log {
  private static volatile Log instance;
  private String level;
  private BufferedWriter logFile = null;
  private String fname;
  private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  public static Log getInstance() {
	Log localInstance = instance;

	if(localInstance == null){
	  synchronized (Log.class){
	    localInstance = instance;
		if(localInstance == null) {
		  instance = localInstance = new Log();
		}
	  }
	}
	return localInstance;
  }

  public boolean Open(String fname) throws IOException{
    logFile = new BufferedWriter(new FileWriter(fname));
    return true;

  }

  public void setLevel(String lv){
    level=lv;
  }

  public void D(String source, String message){
    if(level=="DEBUG"){
        try {
            logFile.write(dateFormat.format(new Date())+" DEBUG::"+source+"::"+message+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
  }

  public void I(String source, String message){

    if(level=="DEBUG"||level=="INFO"){
        try {
            logFile.write(dateFormat.format(new Date())+" INFO::"+source+"::"+message+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
  }

  public void W(String source, String message){
    if(level=="DEBUG"||level=="INFO"||level=="WARN"){
        try {
            logFile.write(dateFormat.format(new Date())+" WARN::"+source+"::"+message+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
  }

  public void E(String source, String message){
    if(level=="DEBUG"||level=="INFO"||level=="WARN"||level=="ERROR"){
        try {
            logFile.write(dateFormat.format(new Date())+" ERROR::"+source+"::"+message+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
  }

  public void F(String source, String message){
      try {
          logFile.write(dateFormat.format(new Date())+" FATAL::"+source+"::"+message+"\r\n");
      } catch (IOException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
  }

  public void Close() throws IOException {
    if(logFile!=null){
      logFile.close();
    }
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }
}
