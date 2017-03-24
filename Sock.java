package ServerSockets;



import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.Socket;



public class Sock extends Thread{

   private Socket socket;           //用于连接的socket

   InputStream is = null;           //用于接收数据的IO流

   

public Socket getSocket() {         

	return socket;

}



public void setSocket(Socket socket) {

	this.socket = socket;

}



public void sendMessage(Socket socket,String message) throws IOException{

	   OutputStream os = socket.getOutputStream();      //IO流发送数据

	   os.write(message.getBytes());                    //把数据转为字节流

   }

   

   public void receiveMessage() throws IOException{

	   is = socket.getInputStream();                  //接收数据

	   byte[] buff = new byte[512];                   //创建字节数组来接收数据

	   int flag = is.read(buff);

	   if(flag == -1){

		   System.out.println("receive success!");

	   }

	   String s = new String(buff);                   //转为字符串输出

	   

	   System.out.println("receive:"+s);

   }

   @Override

   public void run(){                                 //重写run来调用线程

	  try { 

		receiveMessage();                             //获取数据

		sendMessage(socket,"Message received");       //发送数据

	} catch (IOException e) {

		// TODO Auto-generated catch block

		e.printStackTrace();

	}

	   

   }

}
