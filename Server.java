package NIOTest;      //异步通信
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONObject;

public class Server {
  private Selector selector;
  //服务器连接类与逻辑类的写通道
  public static ByteBuffer writebuffer = ByteBuffer.allocate(512);
  //用来存储已经连接的客户端的ID和channel
  public static HashMap channelMap = new HashMap();
  //连接的客户端的数量和ID
  int clientID = 0;
  public void initServer(int port) throws IOException{
	  //通过open()创建服务器的通道
	  ServerSocketChannel serverChannel = ServerSocketChannel.open();
	  //设置通道为非阻塞
	  serverChannel.configureBlocking(false);
	  //绑定服务器的端口号
	  serverChannel.bind(new InetSocketAddress(port));
	  //通过open()创建选择器
	  this.selector = Selector.open();
	  serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	  System.out.println("Server Creates!");
  }
  
  public void listen() throws IOException{
	  System.out.println("Server Starts!");
	  //循环侦听
	  while(true){
		//调用select()有事件发生则处理，没有事件则堵塞
		int flag = selector.select();

		//SelectionKey的集合
		Set<SelectionKey> keySet = this.selector.selectedKeys();
		//遍历keySet
		Iterator<SelectionKey> iter = keySet.iterator();
		while(iter.hasNext()){
			SelectionKey key = (SelectionKey)iter.next();
			iter.remove();
			if(key.isAcceptable()){
				System.out.println("New client coming");
				//获得key对应的那个ServerSocketChannel
				ServerSocketChannel server= (ServerSocketChannel)key.channel();
				//得到客户端连接并采用SOCKETCHANNEL与之联系
				SocketChannel channel = server.accept();
			   
				
				//设置为非阻塞
				channel.configureBlocking(false);
				//回消息给客户端
				channel.write(ByteBuffer.wrap(new String("Fuckyou").getBytes()));
				//为此通道注册读事件
				channelMap.put(++clientID,channel );
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				channel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE,buffer);
             //有数据传来时
			}else if(key.isReadable()){

				ReceiveData.receiveData(key);
				
				System.out.println(ReceiveData.jsonData);
				
			}else{
				//传递通道被写入数据时
				if(writebuffer.position() != 0){
					//System.out.println("data complete");
					//缓冲的缓冲
					ByteBuffer bb = ByteBuffer.wrap(writebuffer.array());
					//将传递通道的数据清除
					writebuffer.clear();
				    //将收到的数据转为字符串
					String data = ReceiveData.getString(bb);
				    //解析JSON串
					JSONObject object = JSONObject.fromObject(data);
				    //取得需要传递的客户端ID
					int id = (int) object.get("ClientID");
				    //System.out.println(id);
				    //获得客户端ID对应的通道
					SocketChannel channel = (SocketChannel) channelMap.get(id);
				    //System.out.println(data);
			    	//传递数据
					channel.write(ByteBuffer.wrap(data.getBytes()));
				    }
				
			}
			
			
			/*if(key.isWritable()){
				SocketChannel channel = (SocketChannel)key.channel();
				if(writebuffer.position() != 0){
					System.out.println("isWritable()");
					ByteBuffer bb = ByteBuffer.wrap(writebuffer.array());
				    String data = ReceiveData.getString(bb);
					channel.write(ByteBuffer.wrap(data.getBytes()));
					writebuffer.clear();
				}
			}*/

		}
	  }
    }
  

  //ByteBuffer转化成字符串
  
}
