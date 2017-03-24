package JSON;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONObject;     //需要网上自己下载，六个JAR包
public class JsonDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashMap map = new HashMap();
         map.put("userID", "100");
         map.put("name", "zhangsan");
         map.put("Date", new String[]{"2013","2014"});

         JSONObject json = JSONObject.fromObject(map);
         String data = json.toString();
         System.out.println(data);
         
         JSONObject object = JSONObject.fromObject(data);
         String userID = object.getString("userID");
         System.out.println(userID);
         
         
         
	}

}
