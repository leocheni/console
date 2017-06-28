package net.msdh.console.base;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.16
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class Queue {


  private Command command;

  public Queue() {
    this.command = new Command();
  }

  public Command getCommand() {
    return command;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

  public void cmdParser(String line){
    Map<String,Object> params =  new HashMap<String, Object>();

    String[] tokens = line.split(" ");
      //if(tokens.length>4) return;

    command.setMethod(tokens[0]);

    switch(tokens.length){
      case 4:{params.put("add",tokens[3]);      }
      case 3:{params.put("item",tokens[2]);      }
      case 2:{params.put("action",tokens[1]);}
    }

//    if(tokens.length==2) params.put("action",tokens[1]);
//    if(tokens.length==3) {
//      params.put("action",tokens[1]);
//      params.put("item",tokens[2]);
//    }
//    if(tokens.length==4){
//      params.put("action",tokens[1]);
//      params.put("item",tokens[2]);
//    }

   // for(int i=1;i<tokens.length;i++){
  //    params.put("P"+i, tokens[i]);
  //  }

    command.setParams(params);
    //Display.getInstance().SetLine(2,command.toJson(),'i');

  }

  public void JParser(String line) throws JSONRPC2ParseException {
    JSONRPC2Request reqIn;

    reqIn = JSONRPC2Request.parse(line);

    command.setId(Integer.parseInt(reqIn.getID().toString()));
    command.setMethod(reqIn.getMethod());
    command.setParams(reqIn.getNamedParams());

  }
}
