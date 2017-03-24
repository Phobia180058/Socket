package NIOTest;

import java.io.IOException;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class ServerDemo {         //测试用
         public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
         Server server = new Server();
         server.initServer(9090);
         HashMap hm = new HashMap();
         hm.put("ClientID", 1);
         hm.put("username", "sun");
         hm.put("password", "12345");
         JSONObject object = JSONObject.fromObject(hm);
         String data = object.toString();
         server.writebuffer.put(data.getBytes());
         //System.out.println(server.writebuffer.position());
         server.listen();
         
	}

}
