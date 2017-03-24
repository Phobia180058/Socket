package NIOTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import net.sf.json.JSONObject;

public class ReceiveData {
    
	  static String jsonData;
	  public static String receiveData(SelectionKey key) throws IOException{
      //得到当前key对应的channel
      SocketChannel channel = (SocketChannel)key.channel();
  	  //key绑定的buffer
  	  ByteBuffer buffer = (ByteBuffer) key.attachment();
  	  //从通道读数据到buffer中
  	  int flag = channel.read(buffer);
  	  buffer.clear();
  	  //当客户端断开时取消key
  	  if(flag == -1){
  		channel.close();
  	  }
  	  //缓冲的缓冲
  	  ByteBuffer bb = ByteBuffer.wrap(buffer.array());
  	  //ByteBuffer中得到字符串
  	  jsonData = getString(bb);
  	  String close = "ConnectionClose";
  	  if(jsonData.equals(close)){
  		  channel.close();
  	  }
  	  return jsonData;
    }
    //ByteBuffer中提取数据转为字符串
    public static String getString(ByteBuffer buff) throws CharacterCodingException{
  	  Charset charset = null;
  	  CharsetDecoder decoder = null;
  	  CharBuffer charBuffer = null;
  	  charset = Charset.forName("utf-8");
  	  decoder = charset.newDecoder();
  	  charBuffer = decoder.decode(buff);
  	  return charBuffer.toString();
    }
    
}
