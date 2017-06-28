package net.msdh.console.Settings;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 11:24
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
   private static volatile Settings instance;
   private String coreAdress;
   private int corePort;


   public static Settings getInstance() {
	Settings localInstance = instance;

	if(localInstance == null){
	  synchronized (Settings.class){
	    localInstance = instance;
		if(localInstance == null) {
		  instance = localInstance = new Settings();
		}
	  }
	}
	return localInstance;
  }


    public String getCoreAdress() {
        return coreAdress;
    }

    public void setCoreAdress(String coreAdress) {
        this.coreAdress = coreAdress;
    }

    public int getCorePort() {
        return corePort;
    }

    public void setCorePort(int corePort) {
        this.corePort = corePort;
    }
}
