package net.msdh.console.gui;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.utils.Log;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
      NetClient nc = new NetClient();
      coreIP = Settings.getInstance().getCoreAdress();
      corePort = Settings.getInstance().getCorePort();
      try{
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("action","show");
        nc.Send(coreIP, corePort, new Command("host", params, 1).toJson());
        String answer = nc.Read();

        JSONRPC2Response respIn = JSONRPC2Response.parse(answer);

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
                    NetClient nc = new NetClient();
                    Map<String,Object> params = new HashMap<String, Object>();
                    params.put("action","show");
                    params.put("item",((JButton)e.getSource()).getText());
                    try {
                        nc.Send(coreIP, corePort, new Command("host", params, 1).toJson());
                        String answer = nc.Read();
                        //System.out.println(answer);
                        JOptionPane.showMessageDialog(null,View.Responce(answer),"Output",JOptionPane.PLAIN_MESSAGE);
                        nc.Close();
                    }
                    catch (IOException e1) {
                      Log.getInstance().E("Console.SyatemTab",e1.getMessage());
                    }
                    catch (JSONRPC2ParseException e1) {
                      Log.getInstance().E("Console.SystemTab",e1.getMessage());
                    }
                  }
                });

                this.add(hostView[i]);
            }
		  }
        }
	    else {
 		  JSONRPC2Error err = respIn.getError();
          Log.getInstance().E("Console.SystemTab",err.getMessage());
          //Display.getInstance().SetConsoleLine(2,err.getCode()+err.getMessage(), 'i');
	    }
//        Display.getInstance().SetConsoleLine(2, View.Command(answer), 'i');
        nc.Close();
      }
      catch(IOException e){
        Log.getInstance().E("Console.SystemTab",e.getMessage());

        JLabel errLabel = new JLabel("", JLabel.CENTER);
        errLabel.setText(e.getMessage());
        errLabel.setBounds(701,23,100,300);
        this.add(errLabel);
        //Display.getInstance().SetConsoleLine(2, "Console::Load " + e.getMessage(), 'i');
      }
      catch (JSONRPC2ParseException e) {
        Log.getInstance().E("Console.SystemTab",e.getMessage());
        //Display.getInstance().SetConsoleLine(2, "Console::Load " + e.getMessage(), 'i');
      }
    }
}
