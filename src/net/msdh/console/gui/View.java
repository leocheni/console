package net.msdh.console.gui;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 10.06.16
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class View {


    public View() {

    }

    static public String Responce(String c) throws JSONRPC2ParseException {
      String resultString="";
      JSONRPC2Response respIn;
      respIn = JSONRPC2Response.parse(c);

      if(respIn.indicatesSuccess()) {
        if(respIn.getResult() instanceof JSONArray){
          for(Object r : (JSONArray)respIn.getResult()){
            for(Map.Entry<String,Object> entry : ((JSONObject) r).entrySet()){
              resultString = resultString + entry.getKey()+":"+entry.getValue()+"\r\n";
            }
            resultString = resultString + "==============\r\n";
          }
		}
		else if (respIn.getResult() instanceof JSONObject) {
          if(((JSONObject) respIn.getResult()).containsKey("message")){
            resultString = (String)((JSONObject) respIn.getResult()).get("message");
          }
          else{
            for(Map.Entry<String,Object> entry : ((JSONObject) respIn.getResult()).entrySet()){
              resultString = resultString + entry.getKey()+":"+entry.getValue()+"\r\n";
            }
            resultString = resultString + "==============\r\n";

          }
		}
      }
	  else {
 		JSONRPC2Error err = respIn.getError();
        resultString = err.getCode()+err.getMessage();//+err.getData();
	  }

      return resultString;
    }

    static public String Request(String c) throws JSONRPC2ParseException {

      String resultString="";

      JSONRPC2Request reqIn;

      reqIn = JSONRPC2Request.parse(c);
      resultString = reqIn.getID().toString();
      resultString = "id: " + resultString + " method: " + reqIn.getMethod();


      Map<String,Object> params = reqIn.getNamedParams();
      if(params!=null){
        resultString = resultString + "\nParams:\n";
        for(Map.Entry<String,Object> entry : params.entrySet()){
          resultString = resultString + entry.getKey()+":"+entry.getValue()+"\r\n";
        }
      }
      //resultString = resultString + "==============\r\n";
      return resultString;
    }

}
