package net.msdh.console.gui;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.console.Settings.Settings;
import net.msdh.console.base.Command;
import net.msdh.console.net.Connection;
import net.msdh.console.utils.Log;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class SystemTab extends JPanel {
    private String coreIP;
    private int corePort;
    private JButton[] hostView;

    public SystemTab() throws HeadlessException {
      super(new GridLayout(3,4));
      Connection connection = new Connection();
      coreIP = Settings.getInstance().getCoreAdress();
      corePort = Settings.getInstance().getCorePort();
      try{
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action","show");
        connection.Send(coreIP,corePort,new Command("host",params,1).toJson());

        String answer = connection.ReadClient();
       //  String answer = "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[{\"name\":\"core\"}]}";

        //String resultString=null;
        JSONRPC2Response respIn = null;
        respIn = JSONRPC2Response.parse(answer);

        if(respIn.indicatesSuccess()) {
          if(respIn.getResult() instanceof JSONArray){
            int i=0;
            hostView = new JButton[((JSONArray) respIn.getResult()).size()];
            for(Object r : (JSONArray)respIn.getResult()){
              String hostname = (String)((JSONObject)r).get("name");
              System.out.println(hostname+"\r\n");

                hostView[i]=new JButton();
                try{
                  Image img = ImageIO.read(new FileImageInputStream(new File("Resources/Images/sys/host.png")));
                  hostView[i].setIcon(new ImageIcon(img));
                }
                catch(IOException ex){
                  System.out.println("Error: " + ex.getMessage());
                }

                hostView[i].setMargin(new Insets(0, 0, 0, 0));
                hostView[i].setOpaque(false);
                hostView[i].setContentAreaFilled(false);
                hostView[i].setText(hostname);
                hostView[i].setSize(130,130);

                hostView[i].addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    Connection connection = new Connection();
                    Map<String,Object> params = new HashMap<String, Object>();
                    params.put("action","show");
                    params.put("item",((JButton)e.getSource()).getText());
                    try {
                        connection.Send(coreIP, corePort, new Command("host", params, 1).toJson());
                        String answer = connection.ReadClient();
                        System.out.println(answer);
                        connection.CloseClient();
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                  }
                });

                this.add(hostView[i]);
              //for(Map.Entry<String,Object> entry : ((JSONObject) r).entrySet()){
             //   resultString = resultString + entry.getKey()+":"+entry.getValue()+"\r\n";
             //   hostView[]
             // }
            }
		  }
        }
	    else {
 		  JSONRPC2Error err = respIn.getError();
          Log.getInstance().E("Console::Load",err.getMessage());
          //Display.getInstance().SetConsoleLine(2,err.getCode()+err.getMessage(), 'i');
	    }
//        Display.getInstance().SetConsoleLine(2, View.Command(answer), 'i');
        connection.CloseClient();
      }
      catch(IOException e){
        Log.getInstance().E("Console::Load",e.getMessage());

        JLabel errLabel = new JLabel("", JLabel.CENTER);
        errLabel.setText(e.getMessage());
        errLabel.setBounds(701,23,100,300);
        this.add(errLabel);
        //Display.getInstance().SetConsoleLine(2, "Console::Load " + e.getMessage(), 'i');
      }
    catch (JSONRPC2ParseException e) {
      Log.getInstance().E("Console::Loads",e.getMessage());
      //Display.getInstance().SetConsoleLine(2, "Console::Load " + e.getMessage(), 'i');
    }


    }



}
